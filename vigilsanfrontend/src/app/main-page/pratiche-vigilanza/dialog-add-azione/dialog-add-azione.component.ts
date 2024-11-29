/*
 * Copyright Regione Piemonte - 2024
 * SPDX-License-Identifier: EUPL-1.2
 */

import { AfterViewInit, Component, OnInit } from '@angular/core';
import { FormControl } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { lastValueFrom } from 'rxjs';
import { Client, Navigation } from 'src/app/Client';
import { Azione } from 'src/app/DTO/PraticheVigilanzaDecodificheDTO';
import { NewPratica } from 'src/app/DTO/PraticheVigilanzaNuovaPratica';
import { ErrorHandlerService } from '../../ErrorHandlerService';
import { ModuloCommon } from '../../ModuloCommon';

@Component({
  selector: 'app-dialog-add-azione',
  templateUrl: './dialog-add-azione.component.html',
  styleUrls: ['./dialog-add-azione.component.css']
})
export class DialogAddAzioneComponent implements OnInit, AfterViewInit {

  isSpinEmitter: boolean = false;
  addAzioneButton: boolean = false;
  choice: string | null = null;
  title: string = '';

  classLabelFilter: string = 'text-nowrap m-0 p-0 font-weight-bold display-4';

  // DATE
  public formatTimePicket: number = 24;
  dataOraInizio: any = null;
  dataOraFine: any = null;

  listaAzioni: Azione[] = [];

  constructor(public client: Client, public moduloCommon: ModuloCommon, public router: Router, private route: ActivatedRoute,
    private errorHandlerService: ErrorHandlerService) {
    this.choice = this.route.snapshot.params['choice'];
    if (!this.choice) {
      this.choice = null;
    }
  }

  ngAfterViewInit(): void {
    setTimeout(() => {
      this.client.textRoute = 'Nuova AttivitÃ ';
    }, 0);
  }

  ngOnInit(): void {
    this.isSpinEmitter = true;
    this.addAzioneButton = false;
    this.client.textDataOraDialogAddAzione = null;

    // Numeri pratica/sopralluogo/prescrizione
    if (this.client.praticaDettaglio) {
      this.client.textNumeroPratica = this.client.praticaDettaglio.praticaNumero;
      if (this.client.attivitaSelected) {
        const searchSopralluogo = this.client.praticaDettaglio.appuntamenti.filter((e) => e.appuntamentoId === this.client.attivitaSelected?.appuntamentoId);
        if (searchSopralluogo && searchSopralluogo.length > 0) {
          this.client.textNumeroSopralluogo = searchSopralluogo[0].appuntamentoNumero;
        } else {
          this.client.textNumeroSopralluogo = null;
        }
        const searchPrescrizone = this.client.praticaDettaglio.prescrizioni.filter((e) => e.prescrizioneId === this.client.attivitaSelected?.prescrizioneId);
        if (searchPrescrizone && searchPrescrizone.length > 0) {
          this.client.textNumeroPrescrizione = searchPrescrizone[0].prescrizioneNumero;
        } else {
          this.client.textNumeroPrescrizione = null;
        }
      }
      if (this.client.selectedPraticaDettaglio) {
        if (this.client.selectedPraticaDettaglio.isSopralluogo) {
          this.client.textNumeroSopralluogo = this.client.selectedPraticaDettaglio.appuntamentoNumero;
        } else {
          this.client.textNumeroSopralluogo = null;
        }
        if (this.client.selectedPraticaDettaglio.isPrescrizione) {
          this.client.textNumeroPrescrizione = this.client.selectedPraticaDettaglio.prescrizioneNumero;
        } else {
          this.client.textNumeroPrescrizione = null;
        }
      }
    }

    // Init Date
    const today = new Date();
    let datetoday = {
      year: today.getFullYear(),
      month: today.getMonth() + 1,
      day: today.getDate()
    }
    let hours: string | number = today.getHours();
    let minutes: string | number = today.getMinutes();
    if (hours <= 9) {
      hours = `0${hours}`;
    }
    if (minutes <= 9) {
      minutes = `0${minutes}`;
    }
    this.client.timeDataOraDialogAddAzione = new FormControl();
    this.client.timeDataOraInizioDialogAddAzione = new FormControl();
    this.client.timeDataOraFineDialogAddAzione = new FormControl();

    this.client.dataOraDialogAddAzione = datetoday;
    this.client.timeDataOraDialogAddAzione.patchValue(`${hours}:${minutes}`);
    this.client.textDataOraDialogAddAzione = `${this.client.dataOraDialogAddAzione.year}/${this.client.dataOraDialogAddAzione.month}/${this.client.dataOraDialogAddAzione.day} ${this.client.timeDataOraDialogAddAzione.value}`;
    this.client.dataOraInizioDialogAddAzione = datetoday;
    this.client.timeDataOraInizioDialogAddAzione.patchValue(`${hours}:${minutes}`);
    this.client.timeDataOraFineDialogAddAzione.patchValue(`${hours}:${minutes}`);

    // Get azione preselezionata
    if (this.client.selectedAzioneDialoagAddPratica) {
      this.client.selectedAzioneDialogAddAzione = this.client.selectedAzioneDialoagAddPratica;
    } else {
      this.client.selectedAzioneDialogAddAzione = null;
      this.client.selectedAzioneDialoagAddPratica = null;
    }
    if (this.client.azioniPerPratiche && this.choice) {
      switch (this.choice) {
        case 'AttivitÃ PRA':
          this.title = 'Nuova attivitÃ ';
          const getStatiPratica = this.getStatiPratica();
          if (this.client.azioniPerPratiche.pratica) {
            for (let i = 0; i < this.client.azioniPerPratiche.pratica.length; i++) {
              for (let j = 0; j < this.client.azioniPerPratiche.pratica[i].statoIdLista.length; j++) {
                if (getStatiPratica.includes(this.client.azioniPerPratiche.pratica[i].statoIdLista[j])) {
                  this.listaAzioni.push(this.client.azioniPerPratiche.pratica[i]);
                  break;
                }
              }
            }
          }
          if (this.listaAzioni.length <= 0 || !this.listaAzioni) {
            this.goBack();
            this.errorHandlerService.displayInfoMessageError('Non sono disponibili AttivitÃ  da aggiungere');
          }
          break;
        case 'AttivitÃ PRE':
          this.title = 'Nuova prescrizione';
          if (this.client.selectedPraticaDettaglio) {
            const prescrizioneTipoId = this.client.selectedPraticaDettaglio.tipoId;
            if (this.client.selectedPraticaDettaglio.id) {
              const getStatiPrescrizioni = this.getStatiPrescrizioni(this.client.selectedPraticaDettaglio.id);
              if (this.client.azioniPerPratiche.prescrizione) {
                const azioniPRE = this.client.azioniPerPratiche.prescrizione.filter((e) => e.prescrizioneTipoId === prescrizioneTipoId);
                for (let i = 0; i < azioniPRE.length; i++) {
                  for (let j = 0; j < azioniPRE[i].statoIdLista.length; j++) {
                    if (getStatiPrescrizioni.includes(azioniPRE[i].statoIdLista[j])) {
                      this.listaAzioni.push(azioniPRE[i]);
                      break;
                    }
                  }
                }
              }
            }
          }
          if (this.listaAzioni.length <= 0 || !this.listaAzioni) {
            this.goBack();
            this.errorHandlerService.displayInfoMessageError('Non sono disponibili AttivitÃ  da aggiungere');
          }
          break;
        case 'AttivitÃ APP':
          this.title = 'Nuovo sopralluogo';
          if (this.client.selectedPraticaDettaglio) {
            const appuntamentoTipoId = this.client.selectedPraticaDettaglio.tipoId;
            if (this.client.selectedPraticaDettaglio.id) {
              const getStatiAppuntamenti = this.getStatiAppuntamenti(this.client.selectedPraticaDettaglio.id);
              if (this.client.azioniPerPratiche.appuntamento && this.client.azioniPerPratiche.appuntamento.length > 0) {
                const azioniAPP = this.client.azioniPerPratiche.appuntamento.filter((e) => e.appuntamentoTipoId === appuntamentoTipoId);
                for (let i = 0; i < azioniAPP.length; i++) {
                  for (let j = 0; j < azioniAPP[i].statoIdLista.length; j++) {
                    if (getStatiAppuntamenti.includes(azioniAPP[i].statoIdLista[j])) {
                      this.listaAzioni.push(azioniAPP[i]);
                      break;
                    }
                  }
                }
              }
            }
          }
          if (this.listaAzioni.length <= 0 || !this.listaAzioni) {
            this.goBack();
            this.errorHandlerService.displayInfoMessageError('Non sono disponibili AttivitÃ  da aggiungere');
          }
          break;
        case 'Sopralluogo':
          this.client.textNumeroSopralluogo = null;
          const filterPRAS = this.client.azioniPerPratiche.pratica?.filter((e) => e.azioneInizialeAppuntamento === true) || [];
          this.listaAzioni = filterPRAS;
          if (this.listaAzioni.length <= 0 || !this.listaAzioni) {
            this.goBack();
            this.errorHandlerService.displayInfoMessageError('Non sono disponibili Sopralluoghi da aggiungere');
          }
          break;
        case 'Prescrizione':
          this.client.textNumeroPrescrizione = null;
          const filterPRAP = this.client.azioniPerPratiche.pratica?.filter((e) => e.azioneInizialePrescrizione === true) || [];
          this.listaAzioni = filterPRAP;
          if (this.listaAzioni.length <= 0 || !this.listaAzioni) {
            this.goBack();
            this.errorHandlerService.displayInfoMessageError('Non sono disponibili Prescrizioni da aggiungere');
          }
          break;
        case 'ModificaAttivita':
          if (this.client.attivitaSelected) {
            // AZIONE ATTIVITA
            this.listaAzioni = this.client.azioniPerPratiche.pratica ? this.client.azioniPerPratiche.pratica : [];
            let searchAzione = this.listaAzioni.filter((e) => e.azioneId === this.client.attivitaSelected?.azioneId);
            if (!searchAzione || searchAzione.length <= 0) {
              this.listaAzioni = this.client.azioniPerPratiche.appuntamento ? this.client.azioniPerPratiche.appuntamento : [];
              searchAzione = this.listaAzioni.filter((e) => e.azioneId === this.client.attivitaSelected?.azioneId);
              if (!searchAzione || searchAzione.length <= 0) {
                this.listaAzioni = this.client.azioniPerPratiche.prescrizione ? this.client.azioniPerPratiche.prescrizione : [];
                searchAzione = this.listaAzioni.filter((e) => e.azioneId === this.client.attivitaSelected?.azioneId);
              }
            }
            if (searchAzione && searchAzione.length > 0) {
              this.client.selectedAzioneDialogAddAzione = searchAzione[0];
            } else {
              this.goBack();
              this.errorHandlerService.displayInfoMessageError('Non sono disponibili azioni per questa attivitÃ ');
            }
            // DATE ATTIVITA
            if (this.client.attivitaSelected.dataoraAzione) {
              const dataOraAzione = new Date(this.client.attivitaSelected.dataoraAzione);
              let dateTime = {
                year: dataOraAzione.getFullYear(),
                month: dataOraAzione.getMonth() + 1,
                day: dataOraAzione.getDate()
              }
              this.client.dataOraDialogAddAzione = dateTime;
              this.client.textDataOraDialogAddAzione = `${this.client.dataOraDialogAddAzione.year}/${this.client.dataOraDialogAddAzione.month}/${this.client.dataOraDialogAddAzione.day} 00:00`;
            }
            if (this.client.attivitaSelected && this.client.attivitaSelected.appuntamentoId && this.client.praticaDettaglio) {
              const appuntamentoAttivitaId = this.client.attivitaSelected.appuntamentoId;
              const searchAppuntamento = this.client.praticaDettaglio.appuntamenti.filter((e) => e.appuntamentoId === appuntamentoAttivitaId);
              if (searchAppuntamento && searchAppuntamento.length === 1) {
                const dataInizio = searchAppuntamento[0].dataoraInizio ? new Date(searchAppuntamento[0].dataoraInizio) : new Date();
                let dataInizioObj = {
                  year: dataInizio.getFullYear(),
                  month: dataInizio.getMonth() + 1,
                  day: dataInizio.getDate()
                }
                let hoursInizio: string | number = dataInizio.getHours();
                let minutesInizio: string | number = dataInizio.getMinutes();
                if (hoursInizio <= 9) {
                  hoursInizio = `0${hoursInizio}`;
                }
                if (minutesInizio <= 9) {
                  minutesInizio = `0${minutesInizio}`;
                }
                const dataFine = searchAppuntamento[0].dataoraFine ? new Date(searchAppuntamento[0].dataoraFine) : new Date();
                let dataFineObj = {
                  year: dataFine.getFullYear(),
                  month: dataFine.getMonth() + 1,
                  day: dataFine.getDate()
                }
                let hoursFine: string | number = dataFine.getHours();
                let minutesFine: string | number = dataFine.getMinutes();
                if (hoursFine <= 9) {
                  hoursFine = `0${hoursFine}`;
                }
                if (minutesFine <= 9) {
                  minutesFine = `0${minutesFine}`;
                }
                this.client.dataOraInizioDialogAddAzione = dataInizioObj;
                this.client.timeDataOraInizioDialogAddAzione.patchValue(`${hoursInizio}:${minutesInizio}`);
                this.client.timeDataOraFineDialogAddAzione.patchValue(`${hoursFine}:${minutesFine}`);
              }
            }
            // NOTE
            if (this.client.attivitaSelected.note) {
              this.client.noteDialogAddAzione = this.client.attivitaSelected.note;
            }
          }
          break;
        default:
          break;
      }
    }
    this.isSpinEmitter = false;
  }

  getStatiPratica(): number[] {
    if (this.client.praticaDettaglio && this.client.praticaDettaglio.attivita && this.client.praticaDettaglio.attivita.length > 0) {
      const statiId: number[] = this.client.praticaDettaglio.attivita
        .filter((e) => e.praticaStatoId !== null)
        .map((e) => e.praticaStato.praticaStatoId as number);
      return statiId;
    }
    return [];
  }
  getStatiAppuntamenti(appuntamentoId: number): number[] {
    if (this.client.praticaDettaglio && this.client.praticaDettaglio.attivita && this.client.praticaDettaglio.attivita.length > 0) {
      const statiId: number[] = this.client.praticaDettaglio.attivita
        .filter((e) => e.appuntamentoId !== null && e.appuntamentoId === appuntamentoId && e.appuntamentoStato)
        .map((e) => e.appuntamentoStato.appuntamentoStatoId as number);
      return statiId;
    }
    return [];
  }
  getStatiPrescrizioni(prescrizioneId: number): number[] {
    if (this.client.praticaDettaglio && this.client.praticaDettaglio.attivita && this.client.praticaDettaglio.attivita.length > 0) {
      const statiId: number[] = this.client.praticaDettaglio.attivita
        .filter((e) => e.prescrizioneId !== null && e.prescrizioneId === prescrizioneId && e.prescrizioneStato)
        .map((e) => e.prescrizioneStatoId as number);
      return statiId;
    }
    return [];
  }

  async getPraticaDettaglio() {
    if (this.client.selectedExistPratica) {
      let params = {
        pratica_id: this.client.selectedExistPratica.praticaId
      }
      await lastValueFrom(this.client.getPraticaDettaglio(params))
        .then(data => {
          if (data !== null) {
            this.client.praticaDettaglio = data;
          }
        })
        .catch(
          error => {
            this.isSpinEmitter = false;
            this.errorHandlerService.handleError(error, 'getPraticaDettaglio');
          }
        );
    }
  }

  goBack() {
    this.router.navigate([Navigation.DETTAGLIO_PRATICA], { skipLocationChange: true });
  }

  eraseData(type: string) {
    switch (type) {
      case 'dataOraDialogAddAzione':
        this.client.dataOraDialogAddAzione = null;
        this.client.textDataOraDialogAddAzione = null;
        this.client.timeDataOraDialogAddAzione.patchValue(null);
        break;
      case 'dataOraInizioDialogAddAzione':
        this.client.dataOraInizioDialogAddAzione = null;
        this.client.timeDataOraInizioDialogAddAzione.patchValue(null);
        this.client.timeDataOraFineDialogAddAzione.patchValue(null);
        break;
      default:
        break;
    }
  }

  async addAzione() {
    this.isSpinEmitter = true;
    this.addAzioneButton = true;

    // if (this.selectedPratica.moduloId) {
    //   // this.alertValidModulo = false;
    //   this.client.selectedAzioneDialoagAddPratica = null; //Anullo struttura idSwap per altri calcoli
    //   this.moduloCommon.sendModulo();
    //   if (!this.client.isValidModulo) {
    //     // this.alertValidModulo = true;
    //     this.errorHandlerService.displayErrorMessage('ERRORE IN COMPILAZIONE');
    //     return;
    //   }
    // }

    if (this.client.selectedAzioneDialogAddAzione) {
      if (this.client.selectedAzioneDialogAddAzione.azioneInizialePratica) {
        if (!this.client.textNumeroPratica || this.client.textNumeroPratica.trim() === '') {
          this.errorHandlerService.displayErrorMessage('Numero Pratica obbligatorio');
          this.isSpinEmitter = false;
          this.addAzioneButton = false;
          return;
        }
      }
      if (this.client.selectedAzioneDialogAddAzione.appuntamentoTipoId || this.client.selectedAzioneDialogAddAzione.azioneInizialeAppuntamento) {
        if (!this.client.textNumeroSopralluogo || this.client.textNumeroSopralluogo.trim() === '') {
          this.errorHandlerService.displayErrorMessage('Numero Sopralluogo obbligatorio');
          this.isSpinEmitter = false;
          this.addAzioneButton = false;
          return;
        }
      }
      if (this.client.selectedAzioneDialogAddAzione.prescrizioneTipoId || this.client.selectedAzioneDialogAddAzione.azioneInizialePrescrizione) {
        if (!this.client.textNumeroPrescrizione || this.client.textNumeroPrescrizione.trim() === '') {
          this.errorHandlerService.displayErrorMessage('Numero Prescrizione obbligatorio');
          this.isSpinEmitter = false;
          this.addAzioneButton = false;
          return;
        }
      }
    } else if (this.client.selectedAzioneDialoagAddPratica) {
      if (this.client.selectedAzioneDialoagAddPratica.azioneInizialePratica) {
        if (!this.client.textNumeroPratica || this.client.textNumeroPratica.trim() === '') {
          this.errorHandlerService.displayErrorMessage('Numero Pratica obbligatorio');
          this.isSpinEmitter = false;
          this.addAzioneButton = false;
          return;
        }
      }
      if (this.client.selectedAzioneDialoagAddPratica.appuntamentoTipoId || this.client.selectedAzioneDialoagAddPratica.azioneInizialeAppuntamento) {
        if (!this.client.textNumeroSopralluogo || this.client.textNumeroSopralluogo.trim() === '') {
          this.errorHandlerService.displayErrorMessage('Numero Sopralluogo obbligatorio');
          this.isSpinEmitter = false;
          this.addAzioneButton = false;
          return;
        }
      }
      if (this.client.selectedAzioneDialoagAddPratica.prescrizioneTipoId || this.client.selectedAzioneDialoagAddPratica.azioneInizialePrescrizione) {
        if (!this.client.textNumeroPrescrizione || this.client.textNumeroPrescrizione.trim() === '') {
          this.errorHandlerService.displayErrorMessage('Numero Prescrizione obbligatorio');
          this.isSpinEmitter = false;
          this.addAzioneButton = false;
          return;
        }
      }
    }


    let dataOra: number | null;
    if (this.client.timeDataOraDialogAddAzione.value) {
      let split = this.client.timeDataOraDialogAddAzione.value.split(':');
      let hour = split[0] as number;
      let minute = split[1] as number;
      dataOra = this.getDateFromDatePicker(this.client.dataOraDialogAddAzione, hour, minute).getTime();
    } else {
      dataOra = this.getDateFromDatePicker(this.client.dataOraDialogAddAzione).getTime();
    }
    let dataOraInizio: number | null;
    let dataOraFine: number | null;
    if (this.client.selectedAzioneDialogAddAzione?.azioneInizialeAppuntamento) {
      if (this.client.timeDataOraInizioDialogAddAzione.value) {
        let split = this.client.timeDataOraInizioDialogAddAzione.value.split(':');
        let hour = split[0] as number;
        let minute = split[1] as number;
        dataOraInizio = this.getDateFromDatePicker(this.client.dataOraInizioDialogAddAzione, hour, minute).getTime();
      } else {
        dataOraInizio = this.getDateFromDatePicker(this.client.dataOraInizioDialogAddAzione).getTime();
      }
      if (this.client.timeDataOraFineDialogAddAzione.value) {
        let split = this.client.timeDataOraFineDialogAddAzione.value.split(':');
        let hour = split[0] as number;
        let minute = split[1] as number;
        dataOraFine = this.getDateFromDatePicker(this.client.dataOraInizioDialogAddAzione, hour, minute).getTime();
      } else {
        dataOraFine = this.getDateFromDatePicker(this.client.dataOraInizioDialogAddAzione, 23, 59).getTime();
      }
    } else {
      dataOraInizio = null;
      dataOraFine = null;
    }
    let id: number | null;
    if (this.client.selectedPraticaDettaglio) {
      if (this.client.selectedPraticaDettaglio.id) {
        id = this.client.selectedPraticaDettaglio.id;
      } else {
        id = null;
      }
    } else {
      id = null;
    }

    let params: NewPratica | null = null;
    if (this.client.attivitaSelected && this.client.selectedExistPratica && this.choice === 'ModificaAttivita') {
      params = {
        praticaId: this.client.attivitaSelected.praticaId ? this.client.attivitaSelected.praticaId : null,
        praticaTipoId: this.client.selectedExistPratica.praticaTipoId ? this.client.selectedExistPratica.praticaTipoId : null,
        strutturaId: this.client.selectedExistPratica.strutturaId,
        praticaDettaglio: {
          praticaDetId: this.client.attivitaSelected.praticaDetId,
          praticaId: this.client.attivitaSelected.praticaId ? this.client.attivitaSelected.praticaId : null,
          prescrizioneId: this.client.attivitaSelected && this.client.attivitaSelected.prescrizioneId ? this.client.attivitaSelected.prescrizioneId : null,
          appuntamentoId: this.client.attivitaSelected && this.client.attivitaSelected.appuntamentoId ? this.client.attivitaSelected.appuntamentoId : null,
          azioneId: null,
          dataoraAzione: dataOra,
          moduloCompilatoId: null,
          note: this.client.noteDialogAddAzione,
          flgScadenza: null,
          profiloIdScadenza: null,
          dataoraInizio: dataOraInizio,
          dataoraFine: dataOraFine,
          appuntamentoNumero: this.client.textNumeroSopralluogo,
          prescrizioneNumero: this.client.textNumeroPrescrizione
        },
        praticaNumero: this.client.textNumeroPratica,
        validitaInizio: null,
        validitaFine: null
      }
    }
    if (this.client.selectedExistPratica && this.choice !== 'ModificaAttivita') {
      params = {
        praticaId: this.client.selectedExistPratica.praticaId,
        praticaTipoId: this.client.selectedExistPratica.praticaTipoId ? this.client.selectedExistPratica.praticaTipoId : null,
        strutturaId: this.client.selectedExistPratica.strutturaId,
        praticaDettaglio: {
          praticaDetId: null,
          praticaId: this.client.selectedExistPratica.praticaId ? this.client.selectedExistPratica.praticaId : null,
          prescrizioneId: this.client.selectedPraticaDettaglio && this.client.selectedPraticaDettaglio.isPrescrizione && !this.client.selectedPraticaDettaglio.isSopralluogo ? id : null,
          appuntamentoId: this.client.selectedPraticaDettaglio && !this.client.selectedPraticaDettaglio.isPrescrizione && this.client.selectedPraticaDettaglio.isSopralluogo ? id : null,
          azioneId: this.client.selectedAzioneDialogAddAzione ? this.client.selectedAzioneDialogAddAzione.azioneId : null,
          dataoraAzione: dataOra,
          moduloCompilatoId: null,
          note: this.client.noteDialogAddAzione,
          flgScadenza: null,
          profiloIdScadenza: null,
          dataoraInizio: dataOraInizio,
          dataoraFine: dataOraFine,
          appuntamentoNumero: this.client.textNumeroSopralluogo,
          prescrizioneNumero: this.client.textNumeroPrescrizione
        },
        praticaNumero: this.client.textNumeroPratica,
        validitaInizio: null,
        validitaFine: null
      }
    }

    if (params) {
      const loginObservable = this.client.postAddPratica(params);
      const data = await lastValueFrom(loginObservable)
        .catch(
          error => {
            this.addAzioneButton = false;
            this.isSpinEmitter = false;
            this.errorHandlerService.handleError(error, 'postAddPratica');
          }
        );
      if (data) {
        this.client.noteDialogAddAzione = '';
        this.client.dataOraDialogAddAzione = null;
        this.client.textDataOraDialogAddAzione = null;
        this.client.timeDataOraDialogAddAzione.patchValue(null);
        this.client.praticaDettaglio = null;
        await this.getPraticaDettaglio();
        this.isSpinEmitter = false;
        this.router.navigate([Navigation.DETTAGLIO_PRATICA], { skipLocationChange: true });
        this.errorHandlerService.displaySuccessMessage(this.choice + ' aggiunta con successo');
      }
    }
    this.isSpinEmitter = false;
  }

  getDateFromDatePicker(date: { day: number, month: number, year: number }, hour?: number, minute?: number): Date {
    if (hour && minute) {
      return new Date(date.year, date.month - 1, date.day, hour, minute);
    } else {
      return new Date(date.year, date.month - 1, date.day);
    }
  }

  onDateTimeChangeDataOraDialogAddAzione() {
    if (this.client.dataOraDialogAddAzione) {
      if (this.client.timeDataOraDialogAddAzione.value) {
        this.client.textDataOraDialogAddAzione = `${this.client.dataOraDialogAddAzione.year}/${this.client.dataOraDialogAddAzione.month}/${this.client.dataOraDialogAddAzione.day} ${this.client.timeDataOraDialogAddAzione.value}`;
      } else {
        this.client.textDataOraDialogAddAzione = `${this.client.dataOraDialogAddAzione.year}/${this.client.dataOraDialogAddAzione.month}/${this.client.dataOraDialogAddAzione.day} 00:00`;
      }
    } else {
      this.client.textDataOraDialogAddAzione = null;
    }
  }


  onTimeSetInizio(event: any): void {
    if (event) {
      const timeInizioStr = event;
      const timeInizio = this.convertStringToDate(timeInizioStr);
      this.client.timeDataOraInizioDialogAddAzione.setValue(timeInizioStr);
      const timeFineStr = this.client.timeDataOraFineDialogAddAzione.value;
      if (timeFineStr) {
        const timeFine = this.convertStringToDate(timeFineStr);
        if (!this.isValidTimeRange(timeInizio, timeFine)) {
          this.errorHandlerService.displayErrorMessage('La data di inizio deve essere minore o uguale alla data di fine.');
          this.client.timeDataOraInizioDialogAddAzione.reset();
        }
      }
    }
  }

  onTimeSetFine(event: any): void {
    if (event) {
      const timeFineStr = event;
      const timeFine = this.convertStringToDate(timeFineStr);
      this.client.timeDataOraFineDialogAddAzione.setValue(timeFineStr);
      const timeInizioStr = this.client.timeDataOraInizioDialogAddAzione.value;
      if (timeInizioStr) {
        const timeInizio = this.convertStringToDate(timeInizioStr);
        if (!this.isValidTimeRange(timeInizio, timeFine)) {
          this.errorHandlerService.displayErrorMessage('La data di fine deve essere maggiore o uguale alla data di inizio.');
          this.client.timeDataOraFineDialogAddAzione.reset();
        }
      }
    }
  }

  // Funzione per convertire una stringa "HH:mm" in un oggetto Date
  convertStringToDate(timeStr: string): Date {
    const today = new Date();
    const [hours, minutes] = timeStr.split(':').map(Number);
    const date = new Date(today.getFullYear(), today.getMonth(), today.getDate(), hours, minutes);
    return date;
  }

  // Funzione per controllare la validitÃ  del range di orari
  isValidTimeRange(timeInizio: Date, timeFine: Date): boolean {
    return timeInizio.getTime() <= timeFine.getTime();
  }
}
