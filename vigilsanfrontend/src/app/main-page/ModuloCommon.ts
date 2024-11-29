/*
 * Copyright Regione Piemonte - 2024
 * SPDX-License-Identifier: EUPL-1.2
 */

import { Injectable } from '@angular/core';
import * as _ from 'lodash';
import { lastValueFrom } from 'rxjs';
import { Client } from '../Client';
import { SezioniEntity, VociEntity } from '../DTO/SezioniEntity';
import { ErrorHandlerService } from './ErrorHandlerService';
import { TypeModule } from './test/test.component';

@Injectable({
  providedIn: 'root'
})
export class ModuloCommon {

  constructor(private client: Client, private errorHandlerService: ErrorHandlerService) { }

  /**
   * PRESA DEI DATI SE MODULO COMPILATO
   */
  moduloCompilato() {
    // V
    if (this.client.modulo.voci && this.client.modulo.voci.length > 0) {
      // VV
      for (let voce of this.client.modulo.voci) {
        this.insertOldValue(voce);
        // VVV
        if (voce.voci) {
          for (let subVoce of voce.voci) {
            if (subVoce) {
              this.insertOldValue(subVoce);
            }
          }
        }
      }
    }
    //S
    if (this.client.modulo.sezioni && this.client.modulo.sezioni.length > 0) {
      // SS
      for (let sezione of this.client.modulo.sezioni) {
        // SSS
        if (sezione.sezioni) {
          for (let subsezione of sezione.sezioni) {
            // SSSV
            if (subsezione.voci) {
              for (let voce of subsezione.voci) {
                this.insertOldValue(voce);
              }
            }
            // SSSS
            if (subsezione.sezioni) {
              for (let sezione of subsezione.sezioni) {
                // SSSSV
                if (sezione.voci) {
                  for (let voce of sezione.voci) {
                    this.insertOldValue(voce);
                  }
                }
              }
            }
          }
        }
        // SSV
        if (sezione.voci) {
          for (let voce of sezione.voci) {
            this.insertOldValue(voce);
            // SSVV
            if (voce.voci) {
              for (let subVoce of voce.voci) {
                if (subVoce) {
                  this.insertOldValue(subVoce);
                }
              }
            }
          }
        }
      }
    }
  }

  insertOldValue(voce: VociEntity) {
    // Cambio value per input generico
    if (voce.voceControl && !voce.voceControlTime) {
      // if (voce.voceControl.value === '' || voce.voceControl.value === null) {
      //   voce.valore = null;
      // } else {
      if ([TypeModule.INT.toString(), TypeModule.DEC1.toString(), TypeModule.DEC2.toString()].includes(voce.moduloVoceTipoDati ? voce.moduloVoceTipoDati : '')) {
        if (voce.valore) {
          if (voce.moduloVoceTipoDati === TypeModule.INT) {
            voce.voceControl.patchValue(parseInt(voce.valore, 10));
          } else if ([TypeModule.DEC1.toString(), TypeModule.DEC2.toString()].includes(voce.moduloVoceTipoDati ? voce.moduloVoceTipoDati : '')) {
            voce.voceControl.patchValue(parseFloat(voce.valore));
          }
        }
      } else if ([TypeModule.TEXT.toString(), TypeModule.TEL.toString(), TypeModule.EMAIL.toString()].includes(voce.moduloVoceTipoDati ? voce.moduloVoceTipoDati : '')) {
        if (voce.valore) {
          voce.voceControl.patchValue(voce.valore);
        }
      } else if ([TypeModule.DATE.toString(), TypeModule.DATETIME.toString(), TypeModule.TIME.toString()].includes(voce.moduloVoceTipoDati ? voce.moduloVoceTipoDati : '')) {
        // TODO mettere nel control value la data
        if (voce.moduloVoceTipoDati === TypeModule.DATE) {
          if (voce.valore) {
            const parts = voce.valore?.split("-").map((part: string) => parseInt(part));
            const dateObject = new Date(parts[0], parts[1] - 1, parts[2]);
            voce.voceControl.patchValue(dateObject);
          }
        }
      } else if (voce.moduloVoceTipoDati === TypeModule.BOOL) {
        if (voce.valore === 'true') {
          voce.voceControl.patchValue(true);
        } else {
          voce.voceControl.patchValue(false);
        }
      } else if (voce.moduloVoceTipoDati === TypeModule.LIST) {
        if (voce.lista && voce.lista.moduloListaOccMin && voce.lista.moduloListaOccMax
          && voce.lista.moduloListaOccMin !== voce.lista.moduloListaOccMax) {
          let selected: string[] = [];
          if (voce.lista?.valori) {
            for (let item of voce.lista?.valori) {
              if (item.valore && item.moduloListaValoreCod) {
                selected.push(item.moduloListaValoreCod);
              }
            }
          }
          voce.voceControl.patchValue(selected);
        } else {
          if (voce.lista?.valori) {
            for (let item of voce.lista?.valori) {
              if (item.valore) {
                voce.voceControl.patchValue(item.moduloListaValoreCod);
              }
            }
          }
        }
      }
    }
    // Cambio value per input di tipo TIME
    // if (voce.voceControlTime && !voce.voceControl) {
    //   if (!voce.voceControlTime.valid) {
    //   }
    //   if (voce.voceControlTime.value === '' || voce.voceControlTime.value === null) {
    //     voce.valore = null;
    //   } else {
    //     if ([TypeModule.DATE.toString(), TypeModule.DATETIME.toString(), TypeModule.TIME.toString()].includes(voce.moduloVoceTipoDati ? voce.moduloVoceTipoDati : '')) {
    //       voce.valore = voce.voceControlTime.value;
    //     }
    //   }
    // }
    // Cambio value per input di tipo DATETIME
    // if (voce.voceControl && voce.voceControlTime) {
    //   if (!voce.voceControlTime.valid || !voce.voceControl.valid) {
    //   }
    //   if ((voce.voceControl.value !== '' || voce.voceControl.value !== null) && (voce.voceControlTime.value !== '' || voce.voceControlTime.value !== null)) {
    //     if (voce.moduloVoceTipoDati === TypeModule.DATETIME) {
    //       const date = new Date(voce.voceControl.value);
    //       const year = date.getFullYear();
    //       const month = date.getMonth() + 1;
    //       const day = date.getDate();
    //       voce.valore = `${year}-${month}-${day}T${voce.voceControlTime.value}`;
    //     }
    //   }
    // }
    // FILES
    if (voce.moduloVoceTipoDati === TypeModule.FILE) {
      if (voce.fileGruppo?.fileTipi) {
        for (let index = 0; index < voce.fileGruppo?.fileTipi?.length; index++) {
          if (!voce.fileGruppo.fileTipi[index].fileId) {
            voce.fileGruppo.fileTipi[index].voceControl.patchValue('Nessun file caricato');
          } else {
            if (voce.fileGruppo.fileTipi[index].fileTipoDesc) {
              voce.fileGruppo.fileTipi[index].voceControl.patchValue(voce.fileGruppo.fileTipi[index].fileName);
              // voce.fileGruppo.fileTipi[index].fileNomeCaricato = voce.fileGruppo.fileTipi[index].fileTipoDesc;
            }
          }
        }
      }
    }
  }

  /**
   * INVIO DEL MODULO
   */
  sendModulo() {
    let moduloToSend = _.cloneDeep(this.client.modulo);
    this.client.isValidModulo = true;
    // Sezione principale del modulo
    if (moduloToSend.visible || moduloToSend.visible === false) { delete moduloToSend.visible } // Cancello attributi per controlli sezioni
    if (moduloToSend.visibleMsg) { delete moduloToSend.visibleMsg } // Cancello attributi per controlli sezioni
    // V
    if (moduloToSend.voci && moduloToSend.voci.length > 0) {
      // VV
      for (let voce of moduloToSend.voci) {
        if (voce.voceControl) {
          voce.voceControl.markAsTouched(); //touched
        }
        this.changeValue(voce);
        this.deleteAttribute(voce);
        // VVV
        if (voce.voci) {
          for (let subVoce of voce.voci) {
            if (subVoce) {
              if (subVoce.voceControl) {
                subVoce.voceControl.markAsTouched(); //touched
              }
              this.changeValue(subVoce);
              this.deleteAttribute(subVoce);
            }
          }
        }
      }
    }
    //S
    if (moduloToSend.sezioni && moduloToSend.sezioni.length > 0) {
      // SS
      for (let sezione of moduloToSend.sezioni) {
        // SSS
        if (sezione.sezioni) {
          for (let subsezione of sezione.sezioni) {
            // SSSV
            if (subsezione.voci) {
              for (let voce of subsezione.voci) {
                if (subsezione.visible && subsezione.visible === true) {
                  if (voce.voceControl) {
                    voce.voceControl.markAsTouched(); //touched
                  }
                  this.changeValue(voce);
                }
                this.deleteAttribute(voce);
              }
            }
            // SSSS
            if (subsezione.sezioni) {
              for (let sezione of subsezione.sezioni) {
                // SSSSV
                if (sezione.voci) {
                  for (let voce of sezione.voci) {
                    if (sezione.visible && sezione.visible === true) {
                      if (voce.voceControl) {
                        voce.voceControl.markAsTouched(); //touched
                      }
                      this.changeValue(voce);
                    }
                    this.deleteAttribute(voce);
                  }
                }
                if (sezione.visible || sezione.visible === false) { delete sezione.visible } // Cancello attributi per controlli sezioni
                if (sezione.visibleMsg) { delete sezione.visibleMsg } // Cancello attributi per controlli sezioni
                if (sezione.sezioni) {
                  for (let subsezione of sezione.sezioni) {
                    if (subsezione.voci) {
                      for (let voce of subsezione.voci) {
                        if (subsezione.visible && subsezione.visible === true) {
                          if (voce.voceControl) {
                            voce.voceControl.markAsTouched(); //touched
                          }
                          this.changeValue(voce);
                        }
                        this.deleteAttribute(voce);
                      }
                    }
                    if (subsezione.visible || subsezione.visible === false) { delete subsezione.visible } // Cancello attributi per controlli sezioni
                    if (subsezione.visibleMsg) { delete subsezione.visibleMsg } // Cancello attributi per controlli sezioni
                  }
                }
              }
            }
            if (subsezione.visible || sezione.visible === false) { delete subsezione.visible } // Cancello attributi per controlli sezioni
            if (subsezione.visibleMsg) { delete subsezione.visibleMsg } // Cancello attributi per controlli sezioni
          }
        }
        // SSV
        if (sezione.voci) {
          for (let voce of sezione.voci) {
            if (sezione.visible && sezione.visible === true) {
              if (voce.voceControl) {
                voce.voceControl.markAsTouched(); //touched
              }
              this.changeValue(voce);
            }
            this.deleteAttribute(voce);
            // SSVV
            if (voce.voci) {
              for (let subVoce of voce.voci) {
                if (subVoce) {
                  // Se checkbox a false
                  if (voce.valore === 'false') {
                    subVoce.valore = null;
                  } else {
                    if (sezione.visible && sezione.visible === true) {
                      if (subVoce.voceControl) {
                        subVoce.voceControl.markAsTouched(); //touched
                      }
                      this.changeValue(subVoce);
                    }
                  }
                  this.deleteAttribute(subVoce);
                }
              }
            }
          }
        }
        if (sezione.visible || sezione.visible === false) { delete sezione.visible } // Cancello attributi per controlli sezioni
        if (sezione.visibleMsg) { delete sezione.visibleMsg } // Cancello attributi per controlli sezioni
      }
    }

    this.client.moduloToSend = _.cloneDeep(moduloToSend);
  }

  deleteAttribute(voce: VociEntity) {
    if (voce.voceControl) { delete voce.voceControl }
    if (voce.voceControlTime) { delete voce.voceControlTime }
    if (voce.fileGruppo && voce.fileGruppo.fileTipi) {
      for (let file of voce.fileGruppo.fileTipi) {
        if (file.fileNomeCaricato) { delete file.fileNomeCaricato }
        if (file.voceControl) { delete file.voceControl }
      }
    }

    // DELETE not necessary attribute rules
    if (voce.regole && voce.regole.length > 0) {
      for (let i = 0; i < voce.regole.length; i++) {
        delete voce.regole[i].nomeRegola;
      }
    }
  }

  changeValue(voce: VociEntity) {
    // Cambio value per input generico
    if (voce.voceControl && !voce.voceControlTime) {
      if (!voce.voceControl.valid) {
        const errors = voce.voceControl.errors;
        if (errors && errors['required']) {
          this.client.isValidModulo = false;
        } else {
          if (voce.voceControl.value === '' || voce.voceControl.value === null) {
            this.client.isValidModulo = true;
          } else {
            this.client.isValidModulo = false;
          }
        }
      }
      if (voce.voceControl.value === '' || voce.voceControl.value === null) {
        voce.valore = null;
      } else {
        if ([TypeModule.INT.toString(), TypeModule.DEC1.toString(), TypeModule.DEC2.toString()].includes(voce.moduloVoceTipoDati ? voce.moduloVoceTipoDati : '')) {
          voce.valore = voce.voceControl.value.toString();
        } else if ([TypeModule.TEXT.toString(), TypeModule.TEL.toString(), TypeModule.EMAIL.toString()].includes(voce.moduloVoceTipoDati ? voce.moduloVoceTipoDati : '')) {
          voce.valore = voce.voceControl.value;
        } else if ([TypeModule.DATE.toString(), TypeModule.DATETIME.toString(), TypeModule.TIME.toString()].includes(voce.moduloVoceTipoDati ? voce.moduloVoceTipoDati : '')) {
          if (voce.moduloVoceTipoDati === TypeModule.DATE) {
            const date = new Date(voce.voceControl.value);
            const year = date.getFullYear();
            const month = date.getMonth() + 1;
            const day = date.getDate();
            voce.valore = `${year}-${month < 10 ? `0${month}` : `${month}`}-${day < 10 ? `0${day}` : `${day}`}`;
          }
          // TODO aggiungere supporto per DATETIME, TIME
        } else if (voce.moduloVoceTipoDati === TypeModule.BOOL) {
          if (voce.voceControl.value) {
            voce.valore = 'true';
          } else {
            voce.valore = 'false';
          }
        } else if (voce.moduloVoceTipoDati === TypeModule.LIST) {
          // Cancello eventuali scelte effettuate precedentemente
          if (voce.lista?.valori) {
            for (let item of voce.lista?.valori) {
              item.valore = false;
            }
          }
          // Check selezione multipla
          if (voce.lista && voce.lista.moduloListaOccMin && voce.lista.moduloListaOccMax
            && voce.lista.moduloListaOccMin !== voce.lista.moduloListaOccMax) {
            const length = voce.voceControl.value.length;
            if (length < voce.lista.moduloListaOccMin || length > voce.lista.moduloListaOccMax) {
              this.client.isValidModulo = false;
            }
            if (!voce.voceControl.value) {
              this.client.isValidModulo = false;
            }
            // Setto la scelta giusta
            if (voce.lista?.valori) {
              for (let voceChecked of voce.lista?.valori) {
                for (let voceValore of voce.voceControl.value) {
                  if (voceValore === voceChecked.moduloListaValoreCod) {
                    voceChecked.valore = true;
                  }
                }
              }
            }
          } else {  // Check selezione singola
            // Setto la scelta giusta
            let itemToModify = voce.lista?.valori?.find((e) => e.moduloListaValoreCod === voce.voceControl?.value);
            if (itemToModify) {
              itemToModify.valore = true;
            }
          }
        }
      }
    }
    // Cambio value per input di tipo TIME
    if (voce.voceControlTime && !voce.voceControl) {
      // if (!voce.voceControlTime.valid) {
      //   isValid = false;
      // }
      // if (voce.voceControlTime.value === '' || voce.voceControlTime.value === null) {
      //   voce.valore = null;
      //   return isValid;
      // } else {
      //   if ([TypeModule.DATE.toString(), TypeModule.DATETIME.toString(), TypeModule.TIME.toString()].includes(voce.moduloVoceTipoDati ? voce.moduloVoceTipoDati : '')) {
      //     voce.valore = voce.voceControlTime.value;
      //   }
      // }
    }
    // Cambio value per input di tipo DATETIME
    if (voce.voceControl && voce.voceControlTime) {
      if ((voce.voceControl.value !== '' || voce.voceControl.value !== null) && (voce.voceControlTime.value !== '' || voce.voceControlTime.value !== null)) {
        if (voce.moduloVoceTipoDati === TypeModule.DATETIME) {
          const date = new Date(voce.voceControl.value);
          const year = date.getFullYear();
          const month = date.getMonth() + 1;
          const day = date.getDate();
          voce.valore = `${year}-${month < 10 ? `0${month}` : `${month}`}-${day < 10 ? `0${day}` : `${day}`} ${voce.voceControlTime.value}`;
        }
      } else {
        voce.valore = null;
      }
    }
    // Nel caso di file
    if (voce.moduloVoceTipoDati === TypeModule.FILE) {
      if (voce.fileGruppo?.fileTipi) {
        for (let index = 0; index < voce.fileGruppo?.fileTipi?.length; index++) {
          if (voce.fileGruppo.fileTipi[index].fileTipoObbligatorio && !voce.fileGruppo.fileTipi[index].fileId) {  // Controllo del file se obbligatorio
            this.client.isValidModulo = false;
          }
        }
      }
    }
  }

  // DISABILITA TUTTI I CAMPI SENZA EFFETTUARE LA CHIAMATA
  async disabilitaCampi(isModified: boolean) {
    let listaFlagStruttura: { chiave: string, valore: string }[] = [];
    if (isModified) {
      let params = {
        struttura_id: this.client.idStrutturaSwap ? this.client.idStrutturaSwap : this.client.loggedUser.struttura?.strutturaId
      }

      await lastValueFrom(this.client.getFlagStrutture(params))
        .then(data => {
          if (data !== null) {
            listaFlagStruttura = data;
          } else {
            let error: any;
            this.errorHandlerService.handleError(error, 'getFlagStrutture');
            return;
          }
        })
        .catch(
          error => {
            this.errorHandlerService.handleError(error, 'getFlagStrutture');
          }
        );
    }

    if (isModified) { // Sezione principale del modulo
      this.checkRulesSezioni(this.client.modulo, listaFlagStruttura);
    }
    //Pimo ramo VOCI
    if (this.client.modulo.voci && this.client.modulo.voci.length > 0) {
      for (let voce of this.client.modulo.voci) {
        this.disableAnableFormControl(voce);
      }
    }
    //Pimo ramo SEZIONI
    if (this.client.modulo.sezioni && this.client.modulo.sezioni.length > 0) {
      for (let sezione of this.client.modulo.sezioni) {
        if (isModified) {
          this.checkRulesSezioni(sezione, listaFlagStruttura);
        }
        //Pimo ramo SEZIONI - SEZIONI
        if (sezione.sezioni) {
          for (let subsezione of sezione.sezioni) {
            if (isModified) {
              this.checkRulesSezioni(sezione, listaFlagStruttura);
            }
            if (subsezione.voci) {
              for (let voce of subsezione.voci) {
                this.disableAnableFormControl(voce);
              }
            }
            if (subsezione.sezioni) {
              for (let subSubsezione of subsezione.sezioni) {
                if (isModified) {
                  this.checkRulesSezioni(subSubsezione, listaFlagStruttura);
                }
                if (subSubsezione.voci) {
                  for (let voce of subSubsezione.voci) {
                    this.disableAnableFormControl(voce);
                  }
                }
              }
            }
          }
        }
        //Pimo ramo SEZIONI - VOCI
        if (sezione.voci) {
          for (let voce of sezione.voci) {
            this.disableAnableFormControl(voce);
            //Pimo ramo SEZIONI - VOCI - VOCI
            if (voce.voci) {
              for (let subVoce of voce.voci) {
                if (subVoce) {
                  this.disableAnableFormControl(subVoce);
                }
              }
            }
          }
        }
      }
    }
  }

  disableAnableFormControl(voce: VociEntity) {
    if (voce.moduloVoceTipoDati === TypeModule.TIME) {
      if (voce.voceControlTime) {
        this.client.disabilitaCampi ? voce.voceControlTime.disable() : voce.voceControlTime.enable();
      }
      return;
    } else if (voce.moduloVoceTipoDati === TypeModule.DATETIME) {
      if (voce.voceControlTime) {
        this.client.disabilitaCampi ? voce.voceControlTime.disable() : voce.voceControlTime.enable();
      }
      if (voce.voceControl) {
        this.client.disabilitaCampi ? voce.voceControl.disable() : voce.voceControl.enable();
      }
      return;
    } else if (voce.moduloVoceTipoDati === TypeModule.DATE) {
      if (voce.voceControl) {
        this.client.disabilitaCampi ? voce.voceControl.disable() : voce.voceControl.enable();
      }
      return;
    } else if (voce.moduloVoceTipoDati === TypeModule.FILE) {
      if (voce.fileGruppo && voce.fileGruppo?.fileTipi) {
        for (let file of voce.fileGruppo?.fileTipi) {
          this.client.disabilitaCampi ? file.voceControl.disable() : file.voceControl.enable();
        }
      }
      return;
    }
    if (voce.voceControl) {
      this.client.disabilitaCampi ? voce.voceControl.disable() : voce.voceControl.enable();
    }
  }

  checkRulesSezioni(sezione: SezioniEntity, listaFlagStruttura: { chiave: string, valore: string }[]) {
    if (sezione.regole && sezione.regole.length > 0) {
      for (let regola of sezione.regole) {
        let campo: string = '';
        let valore: string = '';
        if (regola.moduloRegolaTipo === 'check') {
          if (regola.moduloRegolaExec) {
            let parts = regola.moduloRegolaExec.trim().split(' ');
            for (let part of parts) {
              if (part.endsWith(';')) {
                campo = part.slice(0, -1);
              } else if (part.endsWith('$')) {
                valore = part.slice(0, -1);
              }
            }
          }
          const flgStruttura: { chiave: string, valore: string }[] = listaFlagStruttura.filter((e) => e.chiave === campo);
          if (flgStruttura[0].valore !== null) {
            if (valore === 'true') {
              if (flgStruttura[0].valore === 'true') {
                sezione.visible = true;
              } else if (flgStruttura[0].valore === 'false') {
                sezione.visible = false;
              }
            } else if (valore === 'false') {
              if (flgStruttura[0].valore === 'true') {
                sezione.visible = false;
              } else if (flgStruttura[0].valore === 'false') {
                sezione.visible = true;
              }
            }
            sezione.visibleMsg = regola.moduloRegolaErrore ? regola.moduloRegolaErrore : '';
          }
        }
      }
    }
  }
  // Trigger per mettere in modulo in modifica o sola visualizzazione
  async modificaDocumentazione() {
    this.client.disabilitaCampi = !this.client.disabilitaCampi;
    await this.disabilitaCampi(!this.client.disabilitaCampi);
  }

  async setDataOnlyRead() {
    this.client.disabilitaCampi = true;
    await this.disabilitaCampi(false);
  }
  async setDataModify() {
    this.client.disabilitaCampi = false;
    await this.disabilitaCampi(true);
  }
}
