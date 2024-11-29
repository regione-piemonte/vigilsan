/*
 * Copyright Regione Piemonte - 2024
 * SPDX-License-Identifier: EUPL-1.2
 */

import { AfterViewInit, Component, OnInit, ViewChild } from '@angular/core';
import { MatStepper } from '@angular/material/stepper';
import { Router } from '@angular/router';
import { lastValueFrom } from 'rxjs';
import { Client, Navigation } from 'src/app/Client';
import { NuovaPraticaPraticaTipo, NuovaPraticaStrutturaTipo } from 'src/app/DTO/PraticheVigilanzaDecodificheDTO';
import { NewPratica, RicercaPratiche } from 'src/app/DTO/PraticheVigilanzaNuovaPratica';
import { ErrorHandlerService } from '../../ErrorHandlerService';
import { ModuloCommon } from '../../ModuloCommon';
import { PraticheVigilanzaComponent } from '../pratiche-vigilanza.component';
import { DateUtilities } from 'src/app/dateUtilities';

export interface MyParamsNuovaPratica {
  pratica_tipo_id?: number;
  struttura_tipo_id?: number;
  data_chiusura_max?: string;
  filter_struttura?: string;
  page_number?: number;
  row_per_page?: number;
  descending?: number;
  order_by?: number;
}

@Component({
  selector: 'app-dialog-add-pratica',
  templateUrl: './dialog-add-pratica.component.html',
  styleUrls: ['./dialog-add-pratica.component.css']
})
export class DialogAddPraticaComponent implements OnInit, AfterViewInit {

  @ViewChild('stepper') stepper!: MatStepper;

  isSpinEmitter: boolean = false;
  isSpinEmitterTable: boolean = false;
  isSpinEmitterSceltaAttivita: boolean = false;
  columns: string[] = ['Struttura', 'Tipo pratica', 'Ultima esecuzione'];

  classLabelFilter: string = 'text-nowrap m-0 p-0 font-weight-bold display-4';

  ultimaEsecuzione: any = null;
  struttura: string = '';
  listaStrutturaTipo: NuovaPraticaStrutturaTipo[] = [];
  selectedStrutturaTipo: NuovaPraticaStrutturaTipo | null = null;
  listaPraticaTipo: NuovaPraticaPraticaTipo[] = [];
  selectedPraticaTipo: NuovaPraticaPraticaTipo | null = null;

  listaPraticheToAdd: RicercaPratiche[] | null = null;
  selectedPratica: RicercaPratiche | null = null;

  isPraticaSelected: boolean = false;
  dataAttivita: any = null;

  page: number = 1;
  pageSize: number = 5;
  collectionSize: number = 0;
  descending: boolean = false;
  orderBy: string = 'struttura_desc';

  isEditable: boolean = false;
  alertValidModulo: boolean = false;
  addPraticaButton: boolean = false;

  constructor(private errorHandlerService: ErrorHandlerService, public client: Client,
    private router: Router, public moduloCommon: ModuloCommon, private praticaComponent: PraticheVigilanzaComponent) { }

  ngAfterViewInit(): void {
    setTimeout(() => {
      this.client.textRoute = 'Nuova pratica';
    }, 0);
  }

  async ngOnInit() {
    this.isSpinEmitter = true;
    this.addPraticaButton = false;
    this.client.textNumeroSopralluogo = null;
    this.client.textNumeroPrescrizione = null;
    this.client.textNumeroPratica = null;
    await this.getListaNuovaPraticaPratica();
    await this.getListaNuovaPraticaStruttura();
    await this.getListaNuovaPratica();
    this.isSpinEmitter = false;
  }

  goBack() {
    this.client.idStrutturaSwap = null; // Azzero struttura id swap per selezione struttura in addPratiche
    this.router.navigate([Navigation.PRATICA + '/ricerca_pratiche'], { skipLocationChange: true });
  }

  async cerca() {
    this.isSpinEmitterTable = true;
    await this.getListaNuovaPratica();
    this.isSpinEmitterTable = false;
  }

  async cercaButton() {
    this.isSpinEmitterTable = true;
    this.page = 1;
    this.pageSize = 5;
    this.collectionSize = 0;
    this.descending = false;
    await this.getListaNuovaPratica();
    this.isSpinEmitterTable = false;
  }

  async getListaNuovaPratica() {
    let params: MyParamsNuovaPratica = {};
    if (this.selectedStrutturaTipo && this.selectedStrutturaTipo.strutturaTipoId) {
      params.struttura_tipo_id = this.selectedStrutturaTipo.strutturaTipoId;
    }
    if (this.selectedPraticaTipo && this.selectedPraticaTipo.praticaTipoId) {
      params.pratica_tipo_id = this.selectedPraticaTipo.praticaTipoId;
    }
    if (this.struttura && this.struttura.trim() !== '') {
      params.filter_struttura = this.struttura;
    }
    if (this.ultimaEsecuzione) {
      params.data_chiusura_max = `${this.ultimaEsecuzione.year}-${this.ultimaEsecuzione.month}-${this.ultimaEsecuzione.day}`;
    }
    params.page_number = this.page;
    params.row_per_page = this.pageSize;
    // params.descending = this.descending;
    // params.order_by = this.orderBy;

    await lastValueFrom(this.client.getListaNuovaPratica(params))
      .then(data => {
        const header = data.headers;
        const collectionSize = header.get('Rows-Number');
        if (collectionSize) {
          this.collectionSize = parseInt(collectionSize);
        }
        if (data.body) {
          this.listaPraticheToAdd = data.body;
        }
      })
      .catch(
        error => {
          this.isSpinEmitter = false;
          this.errorHandlerService.handleError(error, 'getListaNuovaPratica');
        }
      );
  }

  async getListaNuovaPraticaPratica() {
    await lastValueFrom(this.client.getListaNuovaPraticaPratica())
      .then(data => {
        if (data !== null) {
          this.listaPraticaTipo = data;
        }
      })
      .catch(
        error => {
          this.isSpinEmitter = false;
          this.errorHandlerService.handleError(error, 'getListaNuovaPraticaPratica');
        }
      );
  }

  async getListaNuovaPraticaStruttura() {
    await lastValueFrom(this.client.getListaNuovaPraticaStruttura())
      .then(data => {
        if (data !== null) {
          this.listaStrutturaTipo = data;
        }
      })
      .catch(
        error => {
          this.isSpinEmitter = false;
          this.errorHandlerService.handleError(error, 'getListaNuovaPraticaStruttura');
        }
      );
  }

  async getAzioniPerPratica(praticaId: number) {
    let params = {
      pratica_tipo_id: praticaId
    }
    await lastValueFrom(this.client.getAzioniPerPratica(params))
      .then(data => {
        if (data !== null) {
          this.client.azioniPerPratiche = data;
        }
      })
      .catch(
        error => {
          this.isSpinEmitter = false;
          this.client.azioniPerPratiche = null;
          this.errorHandlerService.handleError(error, 'getAzioniPerPratica');
        }
      );
  }

  eraseData(type: string) {
    switch (type) {
      case 'ultimaEsecuzione':
        this.ultimaEsecuzione = null;
        break;
      case 'dataAttivita':
        this.dataAttivita = null;
        break;
      default:
        break;
    }
  }


  async addPratica() {
    this.isSpinEmitter = true;
    this.addPraticaButton = true;
    if (this.selectedPratica && this.selectedPratica.azioneIniziale && this.selectedPratica.praticaTipo) {
      if (this.client.selectedAzioneDialoagAddPratica) {
        if (this.client.selectedAzioneDialoagAddPratica.azioneInizialePratica) {
          if (!this.client.textNumeroPratica || this.client.textNumeroPratica.trim() === '') {
            this.errorHandlerService.displayErrorMessage('Numero Pratica obbligatorio');
            this.isSpinEmitter = false;
            this.addPraticaButton = false;
            return;
          }
        }
        if (this.client.selectedAzioneDialoagAddPratica.appuntamentoTipoId || this.client.selectedAzioneDialoagAddPratica.azioneInizialeAppuntamento) {
          if (!this.client.textNumeroSopralluogo || this.client.textNumeroSopralluogo.trim() === '') {
            this.errorHandlerService.displayErrorMessage('Numero Sopralluogo obbligatorio');
            this.isSpinEmitter = false;
            this.addPraticaButton = false;
            return;
          }
        }
        if (this.client.selectedAzioneDialoagAddPratica.prescrizioneTipoId || this.client.selectedAzioneDialoagAddPratica.azioneInizialePrescrizione) {
          if (!this.client.textNumeroPrescrizione || this.client.textNumeroPrescrizione.trim() === '') {
            this.errorHandlerService.displayErrorMessage('Numero Prescrizione obbligatorio');
            this.isSpinEmitter = false;
            this.addPraticaButton = false;
            return;
          }
        }
      }

      if (this.selectedPratica.moduloId) {
        this.alertValidModulo = false;
        this.client.selectedAzioneDialoagAddPratica = null; //Anullo struttura idSwap per altri calcoli
        this.moduloCommon.sendModulo();
        if (!this.client.isValidModulo) {
          this.alertValidModulo = true;
          this.errorHandlerService.displayErrorMessage('ERRORE IN COMPILAZIONE');
          return;
        }
      }
      let params: NewPratica = {
        praticaId: null,
        praticaTipoId: this.selectedPratica.praticaTipo.praticaTipoId ? this.selectedPratica.praticaTipo.praticaTipoId : null,
        strutturaId: this.selectedPratica.strutturaId,
        praticaDettaglio: {
          praticaDetId: null,
          praticaId: this.selectedPratica.praticaId ? this.selectedPratica.praticaId : null,
          prescrizioneId: null,
          appuntamentoId: null,
          azioneId: this.selectedPratica.azioneIniziale.azioneId,
          dataoraAzione: this.client.dataOraDialogAddAzione ? this.getDateFromDatePicker(this.client.dataOraDialogAddAzione).getTime() : new Date().getTime(),
          moduloCompilatoId: null,
          note: this.client.noteDialogAddAzione,
          flgScadenza: null,
          profiloIdScadenza: null,
          dataoraInizio: null,
          dataoraFine: null,
          appuntamentoNumero: this.client.textNumeroSopralluogo,
          prescrizioneNumero: this.client.textNumeroPrescrizione
        },
        praticaNumero: this.client.textNumeroPratica,
        validitaInizio: null,
        validitaFine: null
      }
      if (params.praticaDettaglio && this.client.dataOraInizioDialogAddAzione && this.client.timeDataOraInizioDialogAddAzione.value) {
        const dateTime = DateUtilities.getDateTimeFromStringTypeDate(this.client.dataOraInizioDialogAddAzione, this.client.timeDataOraInizioDialogAddAzione.value);
        if (dateTime) {
          params.praticaDettaglio.dataoraInizio = dateTime.getTime()
        } else {
          params.praticaDettaglio.dataoraInizio = new Date().getTime();
        }
      }
      if (params.praticaDettaglio && this.client.dataOraInizioDialogAddAzione && this.client.timeDataOraFineDialogAddAzione.value) {
        const dateTime = DateUtilities.getDateTimeFromStringTypeDate(this.client.dataOraInizioDialogAddAzione, this.client.timeDataOraFineDialogAddAzione.value);
        if (dateTime) {
          params.praticaDettaglio.dataoraFine = dateTime.getTime()
        } else {
          params.praticaDettaglio.dataoraFine = new Date().getTime();
        }
      }

      if (this.selectedPratica.moduloId) {
        let moduloCompilato = {
          note: this.client.moduloToSend.note,
          modulo: this.client.moduloToSend
        }
        params.moduloCompilato = moduloCompilato;
      }

      const loginObservable = this.client.postAddPratica(params);
      const data = await lastValueFrom(loginObservable)
        .catch(
          error => {
            this.isSpinEmitter = false;
            this.isPraticaSelected = false;
            this.addPraticaButton = false;
            this.selectedPratica = null;
            this.client.noteDialogAddAzione = '';
            this.client.dataOraDialogAddAzione = null;
            this.errorHandlerService.handleError(error, 'postAddPratica');
          }
        );
      if (data) {
        this.client.moduloToshow = null;
        this.isSpinEmitter = false;
        this.addPraticaButton = false;
        this.router.navigate([Navigation.PRATICA + '/ricerca_pratiche'], { skipLocationChange: true });
        await this.praticaComponent.getListaPraticheAggiunte(null, null);
        await this.praticaComponent.ngOnInit();
        if (this.client.listaPraticheAggiunte) {
          const lastElement = this.client.listaPraticheAggiunte.filter((e) => e.praticaId === data.praticaId);
          if (lastElement && lastElement.length === 1) {
            this.praticaComponent.onRowClick(lastElement[0]);
          } else {
            this.isSpinEmitter = false;
            this.addPraticaButton = false;
          }
        }
        this.client.noteDialogAddAzione = '';
        this.client.dataOraDialogAddAzione = null;
        this.errorHandlerService.displaySuccessMessage('Pratica aggiunta con successo');
      } else {
        this.client.moduloToshow = null;
        this.isSpinEmitter = false;
        this.addPraticaButton = false;
      }
    }
  }

  getDateFromDatePicker(date: { day: number, month: number, year: number }): Date {
    return new Date(`${date.year}-${date.month}-${date.day}`);
  }

  async advanceStepper(pratica: RicercaPratiche) {
    this.isSpinEmitterSceltaAttivita = true;
    this.selectedPratica = pratica;

    // if (this.client.azioniPerPratiche && this.selectedPratica && this.selectedPratica.azioneIniziale) {
    //   if (this.client.azioniPerPratiche.pratica) {
    //     let searchAzione = this.client.azioniPerPratiche.pratica.filter((e) => e.azioneId === (this.selectedPratica && this.selectedPratica.azioneIniziale ? this.selectedPratica.azioneIniziale.azioneId : 0));
    //     if ((!searchAzione || searchAzione.length <= 0) && this.client.azioniPerPratiche.appuntamento) {
    //       searchAzione = this.client.azioniPerPratiche.appuntamento.filter((e) => e.azioneId === (this.selectedPratica && this.selectedPratica.azioneIniziale ? this.selectedPratica.azioneIniziale.azioneId : 0));
    //       if ((!searchAzione || searchAzione.length <= 0) && this.client.azioniPerPratiche.prescrizione) {
    //         searchAzione = this.client.azioniPerPratiche.prescrizione.filter((e) => e.azioneId === (this.selectedPratica && this.selectedPratica.azioneIniziale ? this.selectedPratica.azioneIniziale.azioneId : 0));
    //       }
    //     }
    //     if (searchAzione && searchAzione.length === 1) {
    //       this.client.selectedAzioneDialoagAddPratica = searchAzione[0];
    //     }
    //   }
    // }
    this.client.selectedAzioneDialoagAddPratica = this.selectedPratica.azioneIniziale;
    if (this.selectedPratica.praticaTipoId) {

      this.isSpinEmitterSceltaAttivita = false;
      this.isPraticaSelected = true;
      // Nel caso esista un modulo da visualizzare
      if (this.selectedPratica.moduloId) {
        this.client.moduloToshow = this.selectedPratica.moduloId;
      } else {
        this.client.moduloToshow = null;
      }

      // Choice strutturaId
      this.client.idStrutturaSwap = pratica.strutturaId;

      this.stepper.next();

    } else {
      this.isSpinEmitterSceltaAttivita = false;
      return;
    }
  }

  backStepper() {
    this.selectedPratica = null;
    this.client.moduloToshow = null;
    this.eraseData('dataAttivita');
    this.client.noteDialogAddAzione = '';
    this.client.selectedAzioneDialoagAddPratica = null;
    this.client.idStrutturaSwap = null; // Azzero struttura id swap per selezione struttura in addPratiche
    this.isPraticaSelected = false;
  }

  // stepClickOne() {
  //
  // }
}
