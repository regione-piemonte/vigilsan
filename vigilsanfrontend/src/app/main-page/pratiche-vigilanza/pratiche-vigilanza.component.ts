/*
 * Copyright Regione Piemonte - 2024
 * SPDX-License-Identifier: EUPL-1.2
 */

import { AfterViewInit, Component, Injectable, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { lastValueFrom } from 'rxjs';
import { Client, Navigation } from 'src/app/Client';
import { AppuntamentoStato, AppuntamentoTipo, PraticaStato, PraticaTipo, PrescrizioneStato, PrescrizioneTipo } from 'src/app/DTO/PraticheVigilanzaDecodificheDTO';
import { PraticheAggiunte } from 'src/app/DTO/PraticheVigilanzaNuovaPratica';
import { ErrorHandlerService } from '../ErrorHandlerService';

export interface MyParamsPraticheAggiunte {
  tipo_pratica_id?: number;
  stato_pratica_id?: number;
  data_pratica_apertura_da?: string;
  data_pratica_apertura_a?: string;
  data_pratica_chiusura_da?: string;
  data_pratica_chiusura_a?: string;

  tipo_prescrizione_id?: number;
  stato_prescrizione_id?: number;
  data_apertura_prescrizione_da?: string;
  data_apertura_prescrizione_a?: string;
  data_chiusura_prescrizione_da?: string;
  data_chiusura_prescrizione_a?: string;

  tipo_appuntamento_id?: number;
  stato_appuntamento_id?: number;
  data_inizio_appuntamento_da?: string;
  data_inizio_appuntamento_a?: string;
  data_fine_appuntamento_da?: string;
  data_fine_appuntamento_a?: string;

  filter_struttura?: string;

  page_number?: number;
  row_per_page?: number;
  descending?: boolean;
  order_by?: number;
}

@Component({
  selector: 'app-pratiche-vigilanza',
  templateUrl: './pratiche-vigilanza.component.html',
  styleUrls: ['./pratiche-vigilanza.component.css']
})
@Injectable({ providedIn: 'root' })
export class PraticheVigilanzaComponent implements OnInit, AfterViewInit {

  // GENERIC VARIABLE
  isSpinEmitter: boolean = false;
  public formatTimePicket: number = 24;
  placeholderDate = "yyyy/mm/dd"
  placeholderDateTime = "yyyy/mm/dd"

  // TABLES
  // strutturaEnte: string | null = null;
  columns: string[] = ['Icona', 'Pratica', 'Sopralluogo', 'Prescrizione'];
  isSpinEmitterPraticheAggiunte: boolean = false;

  // STYLE
  styleLabelFilter: string = 'font-weight-bold display-4 m-0 p-0';
  classColumnFilter: string = 'col-12 col-lg-4 text-lg-rigth text-left mt-lg-0 mt-2';

  // PRATICA
  StatoPVuoto: PraticaStato | null = null;
  TipoPVuoto: PraticaTipo | null = null;
  // ULTIMA PRESCRIZIONE
  StatoUlVuoto: PrescrizioneStato | null = null;
  TipoUlVuoto: PrescrizioneTipo | null = null;
  // ULTIMA APPUNTAMENTO
  StatoUpVuoto: AppuntamentoStato | null = null;
  TipoUpVuoto: AppuntamentoTipo | null = null;

  // page: number = 1;
  // pageSize: number = 5;
  collectionSize: number = 0;
  descending: boolean = false;
  orderBy: string = '';

  //TABS
  choice: string = '';

  constructor(public dialog: MatDialog, private errorHandlerService: ErrorHandlerService, public router: Router,
    public client: Client, private route: ActivatedRoute) {
    this.choice = this.route.snapshot.params['choice'];
    switch (this.choice) {
      case 'scadenziario':
        this.client.active = 0;
        break;
      case 'ricerca_pratiche':
        this.client.active = 1;
        break;
      default:
        this.client.active = 0;
        break;
    }
  }
  ngAfterViewInit(): void {
    setTimeout(() => {
      this.client.textRoute = 'Pratiche Vigilanza';
    }, 0);
  }

  async ngOnInit() {
    this.isSpinEmitter = true;
    this.client.moduloToshow = null;
    this.client.praticaDettaglio = null;
    this.client.azioniPerPratiche = null;

    if (this.client.checkAzioni('vigil_pra-scad') !== 'R') {
      this.client.active = 1;
      this.isSpinEmitterPraticheAggiunte = true;
      await this.getListaProfili();
      await this.getListaPraticaStato();
      await this.getListaPraticaTipo();
      await this.getListaPrescrizioneStato();
      await this.getListaPrescrizioneTipo();
      await this.getListaAppuntamentoStato();
      await this.getListaAppuntamentoTipo();
      await this.getListaPraticheAggiunte(this.client.pagePraticheVigilanza, this.client.pageSizePraticheVigilanza);
      this.isSpinEmitterPraticheAggiunte = false;
    }

    this.isSpinEmitter = false;
  }

  async onChangeTab() {
    switch (this.client.active) {
      case 0: //SCADENZIARIO
        break;
      case 1: // PRATICHE
        this.isSpinEmitterPraticheAggiunte = true;
        await this.getListaProfili();
        await this.getListaPraticaStato();
        await this.getListaPraticaTipo();
        await this.getListaPrescrizioneStato();
        await this.getListaPrescrizioneTipo();
        await this.getListaAppuntamentoStato();
        await this.getListaAppuntamentoTipo();
        await this.getListaPraticheAggiunte(this.client.pagePraticheVigilanza, this.client.pageSizePraticheVigilanza);
        this.isSpinEmitterPraticheAggiunte = false;
        break;
      case 2: // COMMISSIONE
        break;
      default:
        break;
    }
  }

  async getListaProfili() {
    if (this.client.listaProfili === null) {
      await lastValueFrom(this.client.getListaProfili())
        .then(data => {
          if (data !== null) {
            this.client.listaProfili = data;
          }
        })
        .catch(
          error => {
            this.isSpinEmitter = false;
            this.errorHandlerService.handleError(error, 'getListaProfili');
          }
        );
    }
  }

  async getListaPraticaStato() {
    if (this.client.statoP === null) {
      await lastValueFrom(this.client.getListaPraticaStato())
        .then(data => {
          if (data !== null) {
            this.client.statoP = data;
          }
        })
        .catch(
          error => {
            this.isSpinEmitter = false;
            this.errorHandlerService.handleError(error, 'getListaPraticaStato');
          }
        );
    }
  }

  async getListaPraticaTipo() {
    if (this.client.tipoP === null) {
      await lastValueFrom(this.client.getListaPraticaTipo())
        .then(data => {
          if (data !== null) {
            this.client.tipoP = data;
          }
        })
        .catch(
          error => {
            this.isSpinEmitter = false;
            this.errorHandlerService.handleError(error, 'getListaPraticaTipo');
          }
        );
    }
  }

  async getListaPrescrizioneStato() {
    if (this.client.statoUl === null) {
      await lastValueFrom(this.client.getListaPrescrizioneStato())
        .then(data => {
          if (data !== null) {
            this.client.statoUl = data;
          }
        })
        .catch(
          error => {
            this.isSpinEmitter = false;
            this.errorHandlerService.handleError(error, 'getListaPrescrizioneStato');
          }
        );
    }
  }

  async getListaPrescrizioneTipo() {
    if (this.client.tipoUl === null) {
      await lastValueFrom(this.client.getListaPrescrizioneTipo())
        .then(data => {
          if (data !== null) {
            this.client.tipoUl = data;
          }
        })
        .catch(
          error => {
            this.isSpinEmitter = false;
            this.errorHandlerService.handleError(error, 'getListaPrescrizioneTipo');
          }
        );
    }
  }

  async getListaAppuntamentoStato() {
    if (this.client.statoUp === null) {
      await lastValueFrom(this.client.getListaAppuntamentoStato())
        .then(data => {
          if (data !== null) {
            this.client.statoUp = data;
          }
        })
        .catch(
          error => {
            this.isSpinEmitter = false;
            this.errorHandlerService.handleError(error, 'getListaAppuntamentoStato');
          }
        );
    }
  }

  async getListaAppuntamentoTipo() {
    if (this.client.tipoUp === null) {
      await lastValueFrom(this.client.getListaAppuntamentoTipo())
        .then(data => {
          if (data !== null) {
            this.client.tipoUp = data;
          }
        })
        .catch(
          error => {
            this.isSpinEmitter = false;
            this.errorHandlerService.handleError(error, 'getListaAppuntamentoTipo');
          }
        );
    }
  }

  async showAddPratica() {
    this.client.dataOraDialogAddAzione = null;
    this.client.dataOraInizioDialogAddAzione = null;
    this.client.noteDialogAddAzione = '';
    this.client.selectedAzioneDialogAddAzione = null;
    this.client.moduloToshow = null;
    this.router.navigate([Navigation.ADD_PRATICA], { skipLocationChange: true });
  }

  onRowClick(praticaAggiunta: PraticheAggiunte) {
    this.client.selectedExistPratica = praticaAggiunta;
    this.router.navigate([Navigation.DETTAGLIO_PRATICA], { skipLocationChange: true });
  }

  // Pratiche gia' aggiunte
  async cerca() {
    this.isSpinEmitterPraticheAggiunte = true;
    await this.getListaPraticheAggiunte(this.client.pagePraticheVigilanza, this.client.pageSizePraticheVigilanza);
    this.isSpinEmitterPraticheAggiunte = false;
  }

  async cercaButton() {
    this.isSpinEmitterPraticheAggiunte = true;
    this.client.pagePraticheVigilanza = 1;
    this.client.pageSizePraticheVigilanza = 5;
    this.collectionSize = 0;
    this.descending = false;
    await this.getListaPraticheAggiunte(this.client.pagePraticheVigilanza, this.client.pageSizePraticheVigilanza);
    this.isSpinEmitterPraticheAggiunte = false;
  }

  async cercaButtonPage() {
    this.isSpinEmitterPraticheAggiunte = true;
    this.client.pagePraticheVigilanza = 1;
    this.collectionSize = 0;
    this.descending = false;
    await this.getListaPraticheAggiunte(this.client.pagePraticheVigilanza, this.client.pageSizePraticheVigilanza);
    this.isSpinEmitterPraticheAggiunte = false;
  }

  async getListaPraticheAggiunte(pageNumber: number | null, rowPerPage: number | null) {
    let params: MyParamsPraticheAggiunte = {};
    if (this.client.selectedTipoP && this.client.selectedTipoP.praticaTipoId) {
      params.tipo_pratica_id = this.client.selectedTipoP.praticaTipoId;
    }
    if (this.client.selectedStatoP && this.client.selectedStatoP.praticaStatoId) {
      params.stato_pratica_id = this.client.selectedStatoP.praticaStatoId;
    }
    if (this.client.selectedTipoUl && this.client.selectedTipoUl.prescrizioneTipoId) {
      params.tipo_prescrizione_id = this.client.selectedTipoUl.prescrizioneTipoId;
    }
    if (this.client.selectedStatoUl && this.client.selectedStatoUl.prescrizioneStatoId) {
      params.stato_prescrizione_id = this.client.selectedStatoUl.prescrizioneStatoId;
    }
    if (this.client.selectedTipoUp && this.client.selectedTipoUp.appuntamentoTipoId) {
      params.tipo_appuntamento_id = this.client.selectedTipoUp.appuntamentoTipoId;
    }
    if (this.client.selectedStatoUp && this.client.selectedStatoUp.appuntamentoStatoId) {
      params.stato_appuntamento_id = this.client.selectedStatoUp.appuntamentoStatoId;
    }

    // PRATICA - DATE
    //  data_pratica_chiusura_da data_pratica_chiusura_a data_pratica_apertura_a data_pratica_apertura_da
    if (this.client.dateTimeP) {
      params.data_pratica_chiusura_da = `${this.client.dateTimeP.year}-${this.client.dateTimeP.month}-${this.client.dateTimeP.day} 00:00:00`;
    }
    if (this.client.dateTimeAlP) {
      params.data_pratica_chiusura_a = `${this.client.dateTimeAlP.year}-${this.client.dateTimeAlP.month}-${this.client.dateTimeAlP.day} 23:59:59`;
    }
    if (this.client.dateTimeTwoP) {
      params.data_pratica_apertura_a = `${this.client.dateTimeTwoP.year}-${this.client.dateTimeTwoP.month}-${this.client.dateTimeTwoP.day} 00:00:00`;
    }
    if (this.client.dateTimeAlTwoP) {
      params.data_pratica_apertura_da = `${this.client.dateTimeAlTwoP.year}-${this.client.dateTimeAlTwoP.month}-${this.client.dateTimeAlTwoP.day} 23:59:59`;
    }

    // PRESCRIZIONE - DATE
    if (this.client.dateTime) {
      params.data_apertura_prescrizione_da = `${this.client.dateTime.year}-${this.client.dateTime.month}-${this.client.dateTime.day} 00:00:00`;
    }
    if (this.client.dateTimeAl) {
      params.data_apertura_prescrizione_a = `${this.client.dateTimeAl.year}-${this.client.dateTimeAl.month}-${this.client.dateTimeAl.day} 23:59:59`;
    }
    if (this.client.dateTimeTwo) {
      params.data_chiusura_prescrizione_da = `${this.client.dateTimeTwo.year}-${this.client.dateTimeTwo.month}-${this.client.dateTimeTwo.day} 00:00:00`;
    }
    if (this.client.dateTimeAlTwo) {
      params.data_chiusura_prescrizione_a = `${this.client.dateTimeAlTwo.year}-${this.client.dateTimeAlTwo.month}-${this.client.dateTimeAlTwo.day} 23:59:59`;
    }

    // APPUNTAMENTO - DATE
    if (this.client.dateTimeUp) {
      params.data_inizio_appuntamento_da = `${this.client.dateTimeUp.year}-${this.client.dateTimeUp.month}-${this.client.dateTimeUp.day} 00:00:00`;
    }
    if (this.client.dateTimeAlUp) {
      params.data_inizio_appuntamento_a = `${this.client.dateTimeAlUp.year}-${this.client.dateTimeAlUp.month}-${this.client.dateTimeAlUp.day} 23:59:59`;
    }
    if (this.client.dateTimeTwoUp) {
      params.data_fine_appuntamento_da = `${this.client.dateTimeTwoUp.year}-${this.client.dateTimeTwoUp.month}-${this.client.dateTimeTwoUp.day} 00:00:00`;
    }
    if (this.client.dateTimeAlTwoUp) {
      params.data_fine_appuntamento_a = `${this.client.dateTimeAlTwoUp.year}-${this.client.dateTimeAlTwoUp.month}-${this.client.dateTimeAlTwoUp.day} 23:59:59`;
    }

    // FILTRO RICERCA STRUTTURA
    if (this.client.strutturaEnte && this.client.strutturaEnte.trim() !== '') {
      params.filter_struttura = this.client.strutturaEnte.trim();
    }

    // PAGINATION
    if (pageNumber) {
      params.page_number = pageNumber;
    }
    if (rowPerPage) {
      params.row_per_page = rowPerPage;
    }
    // params.descending = this.descending;
    // params.order_by = this.orderBy;

    await lastValueFrom(this.client.getListaPraticheAggiunte(params))
      .then(data => {
        const header = data.headers;
        const collectionSize = header.get('Rows-Number');
        if (collectionSize) {
          this.client.collectionSize = parseInt(collectionSize);
        }
        if (data.body) {
          this.client.listaPraticheAggiunte = data.body;
        }
      })
      .catch(
        error => {
          this.isSpinEmitterPraticheAggiunte = false;
          this.errorHandlerService.handleError(error, 'getListaPraticheAggiunte');
        }
      );
  }

  // Gestione filtri di ricerca
  eraseData(type: string) {
    switch (type) {
      case 'dateTimeP':
        this.client.dateTimeP = null;
        this.client.textDateTimeP = '';
        this.client.timeP.patchValue(null);
        break;
      case 'dateTimeAlP':
        this.client.dateTimeAlP = null;
        this.client.textDateTimeAlP = '';
        this.client.timeAlP.patchValue(null);
        break;
      case 'dateTimeTwoP':
        this.client.dateTimeTwoP = null;
        this.client.textDateTimeTwoP = '';
        this.client.timeTwoP.patchValue(null);
        break;
      case 'dateTimeAlTwoP':
        this.client.dateTimeAlTwoP = null;
        this.client.textDateTimeAlTwoP = '';
        this.client.timeAlTwoP.patchValue(null);
        break;
      case 'dateTime':
        this.client.dateTime = null;
        this.client.textDateTime = '';
        this.client.time.patchValue(null);
        break;
      case 'dateTimeAl':
        this.client.dateTimeAl = null;
        this.client.textDateTimeAl = '';
        this.client.timeAl.patchValue(null);
        break;
      case 'dateTimeTwo':
        this.client.dateTimeTwo = null;
        this.client.textDateTimeTwo = '';
        this.client.timeTwo.patchValue(null);
        break;
      case 'dateTimeAlTwo':
        this.client.dateTimeAlTwo = null;
        this.client.textDateTimeAlTwo = '';
        this.client.timeAlTwo.patchValue(null);
        break;
      case 'dateTimeUp':
        this.client.dateTimeUp = null;
        this.client.textDateTimeUp = '';
        this.client.timeUp.patchValue(null);
        break;
      case 'dateTimeAlUp':
        this.client.dateTimeAlUp = null;
        this.client.textDateTimeAlUp = '';
        this.client.timeAlUp.patchValue(null);
        break;
      case 'dateTimeTwoUp':
        this.client.dateTimeTwoUp = null;
        this.client.textDateTimeTwoUp = '';
        this.client.timeTwoUp.patchValue(null);
        break;
      case 'dateTimeAlTwo':
        this.client.dateTimeAlTwoUp = null;
        this.client.textDateTimeAlTwoUp = '';
        this.client.timeAlTwoUp.patchValue(null);
        break;
      default:
        break;
    }
  }

  onDateTimeChangeP() {
    if (this.client.dateTimeP) {
      this.client.textDateTimeP = `${this.client.dateTimeP.day}/${this.client.dateTimeP.month}/${this.client.dateTimeP.year}`;
    } else {
      this.client.textDateTimeP = '';
    }
  }
  onDateTimeChangeAlP() {
    if (this.client.dateTimeAlP) {
      this.client.textDateTimeAlP = `${this.client.dateTimeAlP.day}/${this.client.dateTimeAlP.month}/${this.client.dateTimeAlP.year}`;
    } else {
      this.client.textDateTimeAlP = '';
    }
  }
  onDateTimeChangeTwoP() {
    if (this.client.dateTimeTwoP) {
      this.client.textDateTimeTwoP = `${this.client.dateTimeTwoP.day}/${this.client.dateTimeTwoP.month}/${this.client.dateTimeTwoP.year}`;
    } else {
      this.client.textDateTimeTwoP = '';
    }
  }
  onDateTimeChangeAlTwoP() {
    if (this.client.dateTimeAlTwoP) {
      this.client.textDateTimeAlTwoP = `${this.client.dateTimeAlTwoP.day}/${this.client.dateTimeAlTwoP.month}/${this.client.dateTimeAlTwoP.year}`;
    } else {
      this.client.textDateTimeAlTwoP = '';
    }
  }

  onDateTimeChange() {
    if (this.client.dateTime) {
      this.client.textDateTime = `${this.client.dateTime.day}/${this.client.dateTime.month}/${this.client.dateTime.year}`;
    } else {
      this.client.textDateTime = '';
    }
  }
  onDateTimeChangeAl() {
    if (this.client.dateTimeAl) {
      this.client.textDateTimeAl = `${this.client.dateTimeAl.day}/${this.client.dateTimeAl.month}/${this.client.dateTimeAl.year}`;
    } else {
      this.client.textDateTimeAl = '';
    }
  }
  onDateTimeChangeTwo() {
    if (this.client.dateTimeTwo) {
      this.client.textDateTimeTwo = `${this.client.dateTimeTwo.day}/${this.client.dateTimeTwo.month}/${this.client.dateTimeTwo.year}`;
    } else {
      this.client.textDateTimeTwo = '';
    }
  }
  onDateTimeChangeAlTwo() {
    if (this.client.dateTimeAlTwo) {
      this.client.textDateTimeAlTwo = `${this.client.dateTimeAlTwo.day}/${this.client.dateTimeAlTwo.month}/${this.client.dateTimeAlTwo.year}`;
    } else {
      this.client.textDateTimeAlTwo = '';
    }
  }

  onDateTimeChangeUp() {
    if (this.client.dateTimeUp) {
      this.client.textDateTimeUp = `${this.client.dateTimeUp.day}/${this.client.dateTimeUp.month}/${this.client.dateTimeUp.year}`;
    } else {
      this.client.textDateTimeUp = '';
    }
  }
  onDateTimeChangeAlUp() {
    if (this.client.dateTimeAlUp) {
      this.client.textDateTimeAlUp = `${this.client.dateTimeAlUp.day}/${this.client.dateTimeAlUp.month}/${this.client.dateTimeAlUp.year}`;
    } else {
      this.client.textDateTimeAlUp = '';
    }
  }
  onDateTimeChangeTwoUp() {
    if (this.client.dateTimeTwoUp) {
      this.client.textDateTimeTwoUp = `${this.client.dateTimeTwoUp.day}/${this.client.dateTimeTwoUp.month}/${this.client.dateTimeTwoUp.year}`;
    } else {
      this.client.textDateTimeTwoUp = '';
    }
  }
  onDateTimeChangeAlTwoUp() {
    if (this.client.dateTimeAlTwoUp) {
      this.client.textDateTimeAlTwoUp = `${this.client.dateTimeAlTwoUp.day}/${this.client.dateTimeAlTwoUp.month}/${this.client.dateTimeAlTwoUp.year}`;
    } else {
      this.client.textDateTimeAlTwoUp = '';
    }
  }

}
