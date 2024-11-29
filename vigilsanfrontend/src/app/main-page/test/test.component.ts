/*
 * Copyright Regione Piemonte - 2024
 * SPDX-License-Identifier: EUPL-1.2
 */

import { AfterViewInit, Component, Injectable, OnInit } from '@angular/core';
import { AbstractControl, FormControl, ValidationErrors, ValidatorFn, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { lastValueFrom } from 'rxjs';
import { Client } from 'src/app/Client';
import { ErrorHandlerService } from '../ErrorHandlerService';
import { ModuloCommon } from '../ModuloCommon';
import { FileTipiEntity, VociEntity } from './../../DTO/SezioniEntity';



export enum TypeModule {
  BOOL = 'bool',
  TEXT = 'text',
  EMAIL = 'email',
  TEL = 'tel',
  LIST = 'list',
  INT = 'int',
  DEC1 = 'dec1',
  DEC2 = 'dec2',
  DATE = 'date',
  DATETIME = 'datetime',
  TIME = 'time',
  FILE = 'file'
}

export enum TypeRules {
  NOT_NULL = 'not null'
}

@Component({
  selector: 'app-test',
  templateUrl: './test.component.html',
  styleUrls: ['./test.component.css', '../../../styles.css']
})
@Injectable({
  providedIn: 'root',
})
export class TestComponent implements OnInit, AfterViewInit {
  isLoading: boolean = false;

  styleMatCard: string = "px-4 py-3 mb-2 mat-elevation-z1";
  classModuloDesc: string = 'font-weight-bolder display-4 mt-3';

  enum: typeof TypeModule = TypeModule;

  // MODULO
  moduloId: number | null = null;
  moduloCompilatoId: number | null = null;
  isShowModulo: boolean = false;

  constructor(public client: Client, public moduloCommon: ModuloCommon,
    public router: Router, public dialog: MatDialog, private errorHandlerService: ErrorHandlerService) { }

  ngAfterViewInit(): void {
    setTimeout(() => {
      this.client.textRoute = 'Pagina di test';
    }, 0);
  }

  async ngOnInit() {
    if (this.router.url === '/main-page/test') {
      this.client.moduloToshow = null;
      this.client.moduloCompilatoToshow = null;
    }

    if (this.client.moduloToshow !== null) {
      this.moduloId = this.client.moduloToshow;
      this.showModulo();
    }

    if (this.client.moduloCompilatoToshow !== null) {
      this.showModulo(true);
    }
  }

  async showModulo(type?: boolean) {
    this.isLoading = true;
    this.isShowModulo = false;
    this.client.erroreModulo = false;
    this.client.warningModulo = false;

    let params: any = null;

    if (this.router.url === '/main-page/test') {
      type ?
        params = {
          modulo_compilato_id: this.moduloCompilatoId
        } :
        params = {
          modulo_id: this.moduloId
        }
    } else {
      if (this.client.moduloToshow) {
        this.client.moduloCompilatoToshow = null;
        params = {
          modulo_id: this.moduloId
        }
      } else if (this.client.moduloCompilatoToshow) {
        this.client.moduloToshow == null;
        params = {
          modulo_compilato_id: this.client.moduloCompilatoToshow
        }
      }
    }
    await lastValueFrom(this.client.getModulo(params))
      .then(data => {
        if (data !== null) {
          this.client.modulo = data;
        }
      })
      .catch(
        error => {
          this.isLoading = false;
          this.isShowModulo = false;
          this.client.erroreModulo = true;
          this.dialog.closeAll();
          this.errorHandlerService.handleError(error, 'getModulo');
        }
      );

    this.client.modulo.visible = true; // SETTO DEFAULT VISIBILE, per regole su sezioni, prima sezione del modulo
    //Pimo ramo VOCI
    if (this.client.modulo.voci && this.client.modulo.voci.length > 0) {
      for (let voce of this.client.modulo.voci) {
        this.addFormControl(voce);
      }
    }
    //Pimo ramo SEZIONI
    if (this.client.modulo.sezioni && this.client.modulo.sezioni.length > 0) {
      for (let sezione of this.client.modulo.sezioni) {
        sezione.visible = true; // SETTO DEFAULT VISIBILE, per regole su sezioni
        //Pimo ramo SEZIONI - SEZIONI
        if (sezione.sezioni) {
          for (let subsezione of sezione.sezioni) {
            subsezione.visible = true; // SETTO DEFAULT VISIBILE, per regole su sezioni
            if (subsezione.voci) {
              for (let voce of subsezione.voci) {
                this.addFormControl(voce);
              }
            }
            //Pimo ramo SEZIONI - SEZIONI - SEZIONI
            if (subsezione.sezioni) {
              for (let subSubsezione of subsezione.sezioni) {
                subSubsezione.visible = true; // SETTO DEFAULT VISIBILE, per regole su sezioni
                if (subSubsezione.voci) {
                  for (let voce of subSubsezione.voci) {
                    this.addFormControl(voce);
                  }
                }
              }
            }
          }
        }
        //Pimo ramo SEZIONI - VOCI
        if (sezione.voci) {
          for (let voce of sezione.voci) {
            this.addFormControl(voce);
            //Pimo ramo SEZIONI - VOCI - VOCI
            if (voce.voci) {
              for (let subVoce of voce.voci) {
                if (subVoce) {
                  this.addFormControl(subVoce);
                }
              }
            }
          }
        }
      }
    }

    if (type) {
      this.moduloCommon.moduloCompilato();
    }

    // CAMPI CALCOLATI -----------------------------------------------
    //Pimo ramo VOCI
    if (this.client.modulo.voci && this.client.modulo.voci.length > 0) {
      for (let voce of this.client.modulo.voci) {
        this.campiCalcolati(voce);
        this.addRulesFraCampi(voce);
      }
    }
    //Pimo ramo SEZIONI
    if (this.client.modulo.sezioni && this.client.modulo.sezioni.length > 0) {
      for (let sezione of this.client.modulo.sezioni) {
        //Pimo ramo SEZIONI - SEZIONI
        if (sezione.sezioni) {
          for (let subsezione of sezione.sezioni) {
            if (subsezione.voci) {
              for (let voce of subsezione.voci) {
                this.campiCalcolati(voce);
                this.addRulesFraCampi(voce);
              }
            }
            //Pimo ramo SEZIONI - SEZIONI - SEZIONI
            if (subsezione.sezioni) {
              for (let subSubsezione of subsezione.sezioni) {
                if (subSubsezione.voci) {
                  for (let voce of subSubsezione.voci) {
                    this.campiCalcolati(voce);
                    this.addRulesFraCampi(voce);
                  }
                }
              }
            }
          }
        }
        //Pimo ramo SEZIONI - VOCI
        if (sezione.voci) {
          for (let voce of sezione.voci) {
            this.campiCalcolati(voce);
            this.addRulesFraCampi(voce);
            //Pimo ramo SEZIONI - VOCI - VOCI
            if (voce.voci) {
              for (let subVoce of voce.voci) {
                if (subVoce) {
                  this.campiCalcolati(subVoce);
                  this.addRulesFraCampi(voce);
                }
              }
            }
          }
        }
      }
    }

    await this.moduloCommon.setDataModify();
    await this.moduloCommon.setDataOnlyRead();

    if (!this.client.modulo.visible && !this.client.modeDettaglio) {
      this.isLoading = false;
      this.isShowModulo = false;
      this.client.warningModulo = true;
    } else {
      this.isLoading = false;
      this.isShowModulo = true;
    }
  }

  // Campo operazione campo
  addRulesFraCampi(voce: VociEntity) {
    if (voce.regole && voce.regole.length > 0) {
      for (let i = 0; i < voce.regole.length; i++) {
        let campi: string[] = [];
        let op: string[] = [];
        let splitList = voce.regole[i].moduloVoceRegolaExec.trim().split(' ');
        for (let i = 0; i < splitList.length; i++) {
          if (splitList[i].endsWith(";")) {
            splitList[i] = splitList[i].slice(0, -1);
            campi.push(splitList[i]);
          } else if (splitList[i].endsWith("&")) {
            op.push(splitList[i].slice(0, -1));
          }
        }
        if (campi.length < 2 || op.length < 1) {
          continue;
        }
        let vociControl: FormControl[] = [];
        // CERCO LE VOCI CONTROL
        for (let vc of campi) {
          //Pimo ramo VOCI
          if (this.client.modulo.voci && this.client.modulo.voci.length > 0) {
            for (let voce of this.client.modulo.voci) {
              if (voce.moduloVoceCod === vc && voce.voceControl) {
                vociControl.push(voce.voceControl);
              }
            }
          }
          //Pimo ramo SEZIONI
          if (this.client.modulo.sezioni && this.client.modulo.sezioni.length > 0) {
            for (let sezione of this.client.modulo.sezioni) {
              //Pimo ramo SEZIONI - SEZIONI
              if (sezione.sezioni) {
                for (let subsezione of sezione.sezioni) {
                  if (subsezione.voci) {
                    for (let voce of subsezione.voci) {
                      if (voce.moduloVoceCod === vc && voce.voceControl) {
                        vociControl.push(voce.voceControl);
                      }
                    }
                  }
                  //Pimo ramo SEZIONI - SEZIONI - SEZIONI
                  if (subsezione.sezioni) {
                    for (let subSubsezione of subsezione.sezioni) {
                      if (subSubsezione.voci) {
                        for (let voce of subSubsezione.voci) {
                          if (voce.moduloVoceCod === vc && voce.voceControl) {
                            vociControl.push(voce.voceControl);
                          }
                        }
                      }
                    }
                  }
                }
              }
              //Pimo ramo SEZIONI - VOCI
              if (sezione.voci) {
                for (let voce of sezione.voci) {
                  if (voce.moduloVoceCod === vc && voce.voceControl) {
                    vociControl.push(voce.voceControl);
                  }
                  //Pimo ramo SEZIONI - VOCI - VOCI
                  if (voce.voci) {
                    for (let subVoce of voce.voci) {
                      if (subVoce) {
                        if (voce.moduloVoceCod === vc && voce.voceControl) {
                          vociControl.push(voce.voceControl);
                        }
                      }
                    }
                  }
                }
              }
            }
          }
        }
        if (vociControl.length !== 2) {
          continue;
        }
        vociControl[0].addValidators(this.validateFraCampi(vociControl, voce, op, i));

        // Assicurarsi che il validator si aggiorni quando cambia il primo controllo
        vociControl[1].valueChanges.subscribe(() => {
          vociControl[0].updateValueAndValidity();
        });
      }
    }
  }
  // Validazione campi
  validateFraCampi(vociControl: FormControl[], voce: VociEntity, op: string[], i: number): ValidatorFn {
    return (control: AbstractControl): { [key: string]: any } | null => {
      if (!vociControl[1] || !control) {
        return null;
      }
      let isValid;
      switch (op[0]) {
        case 'eq':
          isValid = control.value == vociControl[1].value; break;
        case 'gt':
          isValid = control.value > vociControl[1].value; break;
        case 'ge':
          isValid = control.value >= vociControl[1].value; break;
        case 'lt':
          isValid = control.value < vociControl[1].value; break;
        case 'le':
          isValid = control.value <= vociControl[1].value; break;
        default: // Se l'operazione non Ã¨ riconosciuta, considera la validazione come non superata
          isValid = true; break;
      }
      if (voce.regole && voce.regole.length > 0) {
        voce.regole[i].nomeRegola = "dynamicErrorCampi" + i;
      }

      return isValid ? null : { ["dynamicErrorCampi" + i]: true };
    };
  }

  // Regole per i campi calcolati
  campiCalcolati(voce: VociEntity) {
    if (voce.regole && voce.regole.length > 0) {
      for (let index = 0; index < voce.regole.length; index++) {
        if (voce.regole[index].moduloVoceRegolaTipo === 'calc') {
          let campi: string[] = [];
          let operazioni: string[] = [];

          let splitList = voce.regole[index].moduloVoceRegolaExec.trim().split(' ');
          for (let i = 0; i < splitList.length; i++) {
            if (splitList[i].endsWith(";")) {
              splitList[i] = splitList[i].slice(0, -1);
              campi.push(splitList[i]);
            } else {
              operazioni.push(splitList[i]);
            }
          }
          let vociControl: FormControl[] = [];
          // CERCO LE VOCI CONTROL
          for (let vc of campi) {
            //Pimo ramo VOCI
            if (this.client.modulo.voci && this.client.modulo.voci.length > 0) {
              for (let voce of this.client.modulo.voci) {
                if (voce.moduloVoceCod === vc && voce.voceControl) {
                  vociControl.push(voce.voceControl);
                }
              }
            }
            //Pimo ramo SEZIONI
            if (this.client.modulo.sezioni && this.client.modulo.sezioni.length > 0) {
              for (let sezione of this.client.modulo.sezioni) {
                //Pimo ramo SEZIONI - SEZIONI
                if (sezione.sezioni) {
                  for (let subsezione of sezione.sezioni) {
                    if (subsezione.voci) {
                      for (let voce of subsezione.voci) {
                        if (voce.moduloVoceCod === vc && voce.voceControl) {
                          vociControl.push(voce.voceControl);
                        }
                      }
                    }
                    //Pimo ramo SEZIONI - SEZIONI - SEZIONI
                    if (subsezione.sezioni) {
                      for (let subSubsezione of subsezione.sezioni) {
                        if (subSubsezione.voci) {
                          for (let voce of subSubsezione.voci) {
                            if (voce.moduloVoceCod === vc && voce.voceControl) {
                              vociControl.push(voce.voceControl);
                            }
                          }
                        }
                      }
                    }
                  }
                }
                //Pimo ramo SEZIONI - VOCI
                if (sezione.voci) {
                  for (let voce of sezione.voci) {
                    if (voce.moduloVoceCod === vc && voce.voceControl) {
                      vociControl.push(voce.voceControl);
                    }
                    //Pimo ramo SEZIONI - VOCI - VOCI
                    if (voce.voci) {
                      for (let subVoce of voce.voci) {
                        if (subVoce) {
                          if (subVoce.moduloVoceCod === vc && subVoce.voceControl) {
                            vociControl.push(subVoce.voceControl);
                          }
                        }
                      }
                    }
                  }
                }
              }
            }
            // ----------------------------------------------
            // voce.voceControl = new FormControl({ value: vociControl[0].value, disabled: this.client.disabilitaCampi ? true : false });
            vociControl.forEach((control, index) => {
              // QUando viene caricato il modulo la prima volta
              let calc = 0;
              for (let i = 0; i < vociControl.length; i++) {
                if (i === 0) {
                  calc += vociControl[i].value;
                } else {
                  if (operazioni[i - 1] === '+') {
                    calc += vociControl[i].value;
                  } else if (operazioni[i - 1] === '-') {
                    calc -= vociControl[i].value;
                  } else if (operazioni[i - 1] === '*') {
                    calc *= vociControl[i].value;
                  }
                }
              }
              if (voce.voceControl) {
                voce.voceControl.patchValue(calc);
              }
              // Descrivo cosa fare se cambia il contenuto di un elemento
              control.valueChanges.subscribe(() => {
                let calc = 0;
                for (let i = 0; i < vociControl.length; i++) {
                  if (i === 0) {
                    calc += vociControl[i].value;
                  } else {
                    if (operazioni[i - 1] === '+') {
                      calc += vociControl[i].value;
                    } else if (operazioni[i - 1] === '-') {
                      calc -= vociControl[i].value;
                    } else if (operazioni[i - 1] === '*') { // TODO aggiungere altre operazioni, controllo isNan ecc
                      calc *= vociControl[i].value;
                    }
                  }
                }
                if (voce.voceControl) {
                  voce.voceControl.patchValue(calc);
                }
              });
            });
          }
        }
      }
    }
  }

  // Form control and rules
  addFormControl(voce: VociEntity) {
    if (voce.moduloVoceTipoDati === this.enum.TIME) {
      voce.voceControlTime = new FormControl({ value: null, disabled: this.client.disabilitaCampi ? true : false }, this.createRules(voce));
      return;
    } else if (voce.moduloVoceTipoDati === this.enum.DATETIME) {
      voce.voceControlTime = new FormControl({ value: null, disabled: this.client.disabilitaCampi ? true : false }, this.createRules(voce));
      voce.voceControl = new FormControl({ value: null, disabled: this.client.disabilitaCampi ? true : false }, this.createRules(voce));
      return;
    } else if (voce.moduloVoceTipoDati === this.enum.FILE) {
      if (voce.fileGruppo && voce.fileGruppo?.fileTipi) {
        for (let file of voce.fileGruppo?.fileTipi) {
          let rules: ValidatorFn[] = [];
          rules.push(this.validateFiles(file));
          file.voceControl = new FormControl({ value: null }, rules);
          file.voceControl.patchValue('Nessun file caricato');
        }
      }
      return;
    }
    voce.voceControl = new FormControl({ value: null, disabled: this.client.disabilitaCampi ? true : false }, this.createRules(voce));
  }

  createRules(voce: VociEntity): ValidatorFn[] {
    let rules: ValidatorFn[] = [];
    // Email check
    if (voce.moduloVoceTipoDati === TypeModule.EMAIL) {
      rules.push(Validators.email);
    }
    // Multiple selections check
    if (voce.moduloVoceTipoDati === TypeModule.LIST) {
      if (voce.lista?.moduloListaOccMin && voce.lista?.moduloListaOccMax && voce.lista?.moduloListaOccMin !== voce.lista?.moduloListaOccMax) {
        rules.push(this.validateSelectionCount(voce.lista?.moduloListaOccMin, voce.lista?.moduloListaOccMax));
      }
    }
    // Dynamic check
    if (voce.regole && voce.regole.length > 0) {
      for (let index = 0; index < voce.regole.length; index++) {
        if (voce.regole[index].moduloVoceRegolaTipo !== 'calc') {
          if (voce.regole[index].moduloVoceRegolaExec === TypeRules.NOT_NULL) {
            rules.push(Validators.required);
          } else {
            if (voce.moduloVoceTipoDati !== TypeModule.TIME && voce.moduloVoceTipoDati !== TypeModule.DATETIME) { // TODO pezza per non supporto ai tipi di dato
              voce.regole[index].nomeRegola = "dynamicError" + index;
              rules.push(this.dynamicValidator(voce.regole[index].moduloVoceRegolaExec, voce, voce.regole[index].nomeRegola));
            }
          }
        }
      }
    }
    return rules;
  }

  // Validatore per controllare il numero di opzioni selezionate
  validateSelectionCount(min: number, max: number): ValidatorFn {
    return (control: AbstractControl): { [key: string]: any } | null => {
      const selectedCount = control.value ? control.value.length : 0;
      if (selectedCount < min || selectedCount > max) {
        return { invalidSelectionCount: true };
      }
      return null;
    };
  }

  // Validatore per controllare il numero di opzioni selezionate
  validateFiles(file: FileTipiEntity): ValidatorFn {
    return (control: AbstractControl): { [key: string]: any } | null => {
      if (file?.fileTipoObbligatorio && !file.fileId) {
        return { invalidSelectionCount: true };
      }
      return null;
    };
  }

  dynamicValidator(validationString: string, voce: VociEntity, nomeRegola: string | undefined): ValidatorFn {
    return (control: AbstractControl): ValidationErrors | null => {
      let operation = '';
      let value = '';
      let voceCod = '';

      let splitList = validationString.trim().split(' '); // Remove spaces at start/end, divide string by spaces
      for (let i = 0; i < splitList.length; i++) {
        if (splitList[i].endsWith("$")) {
          splitList[i] = splitList[i].slice(0, -1); // Remove the last caracter from string
          value = splitList[i];
          continue;
        }
        if (splitList[i].endsWith("&")) {
          splitList[i] = splitList[i].slice(0, -1);
          operation = splitList[i];
          continue;
        }
        if (splitList[i].endsWith(";")) {
          splitList[i] = splitList[i].slice(0, -1);
          voceCod = splitList[i];
          continue;
        }
      }

      if (voceCod !== voce.moduloVoceCod) // Errore: il dato non parla del controllo attuale e della voce attuale
        return null;

      // TODO controllo per date diverso da cosi'
      // TODO DATETIME non funziona con i controlli insieme
      let controlValue = control.value;
      let isValid = false;
      if (voce.moduloVoceTipoDati === this.enum.DATE) {
        switch (operation) {
          case 'eq':
            isValid = controlValue == new Date(value); break;
          case 'gt':
            isValid = controlValue > new Date(value); break;
          case 'ge':
            isValid = controlValue >= new Date(value); break;
          case 'lt':
            isValid = controlValue < new Date(value); break;
          case 'le':
            isValid = controlValue <= new Date(value); break;
          default: // Se l'operazione non Ã¨ riconosciuta, considera la validazione come non superata
            isValid = true; break;
        }
      } else {
        if (!control.value) {
          controlValue = 0;
        }
        switch (operation) {
          case 'eq':
            isValid = controlValue == value; break;
          case 'gt':
            isValid = controlValue > value; break;
          case 'ge':
            isValid = controlValue >= value; break;
          case 'lt':
            isValid = controlValue < value; break;
          case 'le':
            isValid = controlValue <= value; break;
          default: // Se l'operazione non Ã¨ riconosciuta, considera la validazione come non superata
            isValid = true; break;
        }
      }
      if (voce.voceControl) {
        if (voce.voceControl.value === '' || voce.voceControl.value === null) {
          isValid = true;
        }
      }
      // if null input is valid
      return isValid ? null : { [nomeRegola ? nomeRegola : '']: true };
    };
  }

  // Post modulo
  sendModulo() {
    this.client.selectedAzioneDialoagAddPratica = null; //Anullo struttura idSwap per altri calcoli
    this.moduloCommon.sendModulo();
  }

  modificaDocumentazione() {
    this.client.disabilitaCampi = !this.client.disabilitaCampi;
  }
}
