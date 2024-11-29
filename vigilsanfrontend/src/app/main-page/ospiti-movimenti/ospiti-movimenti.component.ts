/*
 * Copyright Regione Piemonte - 2024
 * SPDX-License-Identifier: EUPL-1.2
 */

import { AfterViewInit, Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { lastValueFrom } from 'rxjs';
import { Client, Navigation } from 'src/app/Client';
import { Ospite } from 'src/app/DTO/OspiteDTO';
import { StatoOspite } from 'src/app/DTO/StatiOspiteDTO';
import { ErrorHandlerService } from '../ErrorHandlerService';
import { CambioStrutturaComponent } from '../cambio-struttura/cambio-struttura.component';

interface MyParams {
  ospite_stato_id?: number;
  data_ingresso_da?: string;
  data_ingresso_a?: string;
  data_uscita_da?: string;
  data_uscita_a?: string;
  filter?: string; // ProprietÃ  opzionale
  page_number?: number;
  row_per_page?: number;
  descending?: boolean;
  order_by?: string;
}

@Component({
  selector: 'app-ospiti-movimenti',
  templateUrl: './ospiti-movimenti.component.html',
  styleUrls: ['./ospiti-movimenti.component.css']
})
export class OspitiMovimentiComponent implements OnInit, AfterViewInit {

  isSpinEmitter: boolean = false;
  isSpinEmitterTable: boolean = false;

  classLabelFilter: string = 'text-nowrap m-0 p-0 font-weight-bold display-4';

  columns: string[] = ['CF', 'Cognome e nome', 'Data di nascita', 'Stato', 'Categoria', 'Primo ingresso', 'Ultima uscita'];

  statoOspiteSelezionatoVuoto: StatoOspite = {
    validitaInizio: null,
    validitaFine: null,
    dataCreazione: null,
    dataModifica: null,
    dataCancellazione: null,
    utenteCreazione: null,
    utenteModifica: null,
    utenteCancellazione: null,
    ospiteStatoId: null,
    ospiteStatoCod: null,
    ospiteStatoDesc: null,
    ospiteStatoOrd: null,
    ospiteStatoHint: null,
    flgPresenza: null,
    flgPosto: null,
  };

  page: number = 1;
  pageSize: number = 10;
  descending: boolean = false;
  selectedColumn: string = '';
  collectionSize: number = 0;
  listaOspitiPaginations: Ospite[] | null = null;

  constructor(public client: Client, private router: Router, private errorHandlerService: ErrorHandlerService, public dialog: MatDialog) { }

  ngAfterViewInit(): void {
    setTimeout(() => {
      this.client.textRoute = 'Ospiti';
    }, 0);
  }

  async ngOnInit() {
    let result: boolean = false;
    if (this.client.isEnte && !this.client.isStruttura) {
      const dialogRef = this.dialog.open(CambioStrutturaComponent, {
        minWidth: '80vh',
        maxHeight: '90vh',
        panelClass: 'custom-modal',
        // data: { rilevazione: rilevazione, tab: tab }
      });  // Apro la modal
      result = await lastValueFrom(dialogRef.afterClosed());
    }
    if (this.client.isStruttura || result) {
      this.client.isStruttura = true;
      this.client.ospite = '';
      this.client.ingressoDal = null;
      this.client.ingressoAl = null;
      this.client.uscitaDal = null;
      this.client.uscitaAl = null;

      this.isSpinEmitter = true;

      // if (this.client.listaOspiti === null) {
      await this.getListaOspiti();
      // }
      // if (this.client.listaStatiOspite === null) {
      await this.getDecodificaStatiOspite();
      // }

      this.refreshOspiti();

      this.isSpinEmitter = false;
    } else {
      this.router.navigate([Navigation.MAIN_PAGE], { skipLocationChange: true });
    }
  }

  async getListaOspiti(ordinamento?: any) {
    let params: MyParams = {}
    if (this.client.ospite !== '') {
      params.filter = this.client.ospite;
    }
    if (this.client.uscitaDal) {
      params.data_uscita_da = `${this.client.uscitaDal.year}-${this.client.uscitaDal.month}-${this.client.uscitaDal.day}`;
    }
    if (this.client.uscitaAl) {
      params.data_uscita_a = `${this.client.uscitaAl.year}-${this.client.uscitaAl.month}-${this.client.uscitaAl.day}`;
    }
    if (this.client.ingressoDal) {
      params.data_ingresso_da = `${this.client.ingressoDal.year}-${this.client.ingressoDal.month}-${this.client.ingressoDal.day}`; // this.client.ingressoDal;
    }
    if (this.client.ingressoAl) {
      params.data_ingresso_a = `${this.client.ingressoAl.year}-${this.client.ingressoAl.month}-${this.client.ingressoAl.day}`;
    }
    if (this.client.statoOspiteSelezionato.ospiteStatoId) {
      params.ospite_stato_id = this.client.statoOspiteSelezionato.ospiteStatoId;
    }
    params.page_number = this.page;
    params.row_per_page = this.pageSize;

    if (ordinamento) {
      params.descending = ordinamento.descending;
      params.order_by = ordinamento.order_by;
    }

    const getListaOspitiObservable = this.client.getListaOspiti(params);
    await lastValueFrom(getListaOspitiObservable)
      .then(data => {
        if (data !== null) {
          const header = data.headers;
          const collectionSize = header.get('Rows-Number');
          if (collectionSize) {
            this.collectionSize = parseInt(collectionSize);
          }
          if (data.body) {
            this.client.listaOspiti = data.body;
            this.listaOspitiPaginations = this.client.listaOspiti;
          }
        }
      })
      .catch(
        error => {
          this.isSpinEmitter = false;
          this.errorHandlerService.handleError(error, 'getListaOspiti');
        }
      );
  }

  async getDecodificaStatiOspite() {
    const getDecodificaStatiOspiteObservable = this.client.getDecodificaStatiOspite();
    await lastValueFrom(getDecodificaStatiOspiteObservable)
      .then(data => {
        if (data !== null) {
          this.client.listaStatiOspite = data;
        }
      })
      .catch(
        error => {
          this.isSpinEmitter = false;
          this.errorHandlerService.handleError(error, 'getDecodificaStatiOspite');
        }
      );
  }

  async cerca() {
    this.isSpinEmitterTable = true;
    await this.getListaOspiti();
    this.isSpinEmitterTable = false;
  }
  azzeraFiltri() {
    this.page = 1;
    this.pageSize = 10;
  }

  eraseData(type: string) {
    switch (type) {
      case 'uscitaDal':
        this.client.uscitaDal = null;
        break;
      case 'uscitaAl':
        this.client.uscitaAl = null;
        break;
      case 'ingressoDal':
        this.client.ingressoDal = null;
        break;
      case 'ingressoAl':
        this.client.ingressoAl = null;
        break;

      default:
        break;
    }
  }


  nuovoOspite() {
    this.router.navigate([Navigation.NEW_OSPITE], { skipLocationChange: true });
  }

  async scaricaCsv() {
    let params: MyParams = {}
    if (this.client.ospite !== '') {
      params.filter = this.client.ospite;
    }
    if (this.client.uscitaDal) {
      params.data_uscita_da = `${this.client.uscitaDal.year}-${this.client.uscitaDal.month}-${this.client.uscitaDal.day}`;
    }
    if (this.client.uscitaAl) {
      params.data_uscita_a = `${this.client.uscitaAl.year}-${this.client.uscitaAl.month}-${this.client.uscitaAl.day}`;
    }
    if (this.client.ingressoDal) {
      params.data_ingresso_da = `${this.client.ingressoDal.year}-${this.client.ingressoDal.month}-${this.client.ingressoDal.day}`; // this.client.ingressoDal;
    }
    if (this.client.ingressoAl) {
      params.data_ingresso_a = `${this.client.ingressoAl.year}-${this.client.ingressoAl.month}-${this.client.ingressoAl.day}`;
    }
    if (this.client.statoOspiteSelezionato.ospiteStatoId) {
      params.ospite_stato_id = this.client.statoOspiteSelezionato.ospiteStatoId;
    }
    params.page_number = this.page;
    params.row_per_page = this.pageSize;

    await lastValueFrom(this.client.getListaOspitiCSV(params))
      .then(blob => {
        const headers = blob.headers;
        if (blob.headers) {
          const fileNameHeader = headers.get('Content-Disposition');
          if (fileNameHeader) {
            const parts = fileNameHeader.split(';');
            const partsSwap = parts[1].split('=');
            const fileName = partsSwap[1].replace(/"/g, '');
            if (blob.body) {
              let fileURL = URL.createObjectURL(blob.body);
              const a = document.createElement('a');
              document.body.appendChild(a);
              a.style.display = 'none';
              a.href = fileURL;
              a.download = fileName;
              a.click();
              window.URL.revokeObjectURL(fileURL);
              document.body.removeChild(a);
            }
          }
        }
      })
      .catch(
        error => {
          this.errorHandlerService.handleError(error, 'getListaOspitiCSV');
        }
      );
  }


  onRowClick(ospite: Ospite) {
    this.client.ospiteSelezionato = ospite;
    this.router.navigate([Navigation.DETTAGLIO_OSPITE], { skipLocationChange: true });
  }

  refreshOspiti() {
    if (this.client.listaOspiti) {
      this.listaOspitiPaginations = this.client.listaOspiti
        .map((ospite, i) => ({ id: i + 1, ...ospite }))
        .slice((this.page - 1) * this.pageSize, (this.page - 1) * this.pageSize + this.pageSize);
    }
  }

  async onHeaderClick(column: string) {
    this.isSpinEmitterTable = true;
    this.descending = !this.descending;
    this.selectedColumn = column;
    let ordinamento = {
      descending: false,
      order_by: ''
    }
    if (this.descending) {
      ordinamento.descending = this.descending;
    }
    switch (column) {
      case 'CF':
        ordinamento.order_by = 'CF';
        break;
      case 'Cognome e nome':
        ordinamento.order_by = 'NOME';
        break;
      case 'Data di nascita':
        ordinamento.order_by = 'NASCITA';
        break;
      case 'Stato':
        ordinamento.order_by = 'STATO';
        break;
      case 'Primo ingresso':
        ordinamento.order_by = 'INGRESSO';
        break;
      case 'Ultima uscita':
        ordinamento.order_by = 'USCITA';
        break;
      case 'Categoria':
        ordinamento.order_by = 'CATEGORIA';
        break;
      default:
        break;
    }
    await this.getListaOspiti(ordinamento);
    this.isSpinEmitterTable = false;
  }

}
