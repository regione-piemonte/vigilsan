/*
 * Copyright Regione Piemonte - 2024
 * SPDX-License-Identifier: EUPL-1.2
 */

import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialog, MatDialogRef } from '@angular/material/dialog';
import { lastValueFrom } from 'rxjs';
import { Client, Navigation } from 'src/app/Client';
import { RequisitoPratica, RequisitoPraticaLista } from 'src/app/DTO/RequisitiDTO';
import { ErrorHandlerService } from '../../ErrorHandlerService';
import { Router } from '@angular/router';
import { Attivita } from 'src/app/DTO/PraticheVigilanzaNuovaPratica';
import { ModuloCommon } from '../../ModuloCommon';
import { CambioStrutturaComponent } from '../../cambio-struttura/cambio-struttura.component';

export interface MyParamsRequisitiPratica {
  pratica_id: number | null,
  appuntamento_id?: number | null,
  prescrizione_id?: number | null
}

@Component({
  selector: 'app-requisiti-pratiche',
  templateUrl: './requisiti-pratiche.component.html',
  styleUrls: ['./requisiti-pratiche.component.css']
})
export class RequisitiPraticheComponent implements OnInit {

  isSpinEmitter: boolean = false;
  isLoadingAddModulo: boolean = false;

  panelOpenState: boolean = false;

  listaRequisitiPratica: RequisitoPratica[] | null = null;
  listaRequisitiPraticaTo: RequisitoPraticaLista[] | null = null;

  filter: string = '';
  decodificaRequisito: string = '';

  constructor(public client: Client, private errorHandlerService: ErrorHandlerService, public dialog: MatDialog, private router: Router, public moduloCommon: ModuloCommon) {
  }

  async ngOnInit() {
    let result: boolean = false;
    if (this.client.isEnte && !this.client.isStruttura) {
      const dialogRef = this.dialog.open(CambioStrutturaComponent, {
        width: '1200px',
        maxHeight: '90vh',
        panelClass: 'custom-modal',
        // data: { rilevazione: rilevazione, tab: tab }
      });  // Apro la modal
      result = await lastValueFrom(dialogRef.afterClosed());
    }
    if (this.client.isStruttura || result) {
      this.isSpinEmitter = true;
      this.client.listaRequisitiPraticaToClient = null;
      this.client.reqSelected = null;
      await this.getDecodificaRequisiti();
      await this.getRequisitiPratica();

      if (this.listaRequisitiPratica) {
        const listPadreRequisiti: RequisitoPraticaLista[] = [];
        this.listaRequisitiPratica.forEach((e) => {
          if (!e.clreqIdPadre) {
            const swapRquisito: RequisitoPraticaLista = {
              clreqCod: e.clreqCod,
              clreqDesc: e.clreqDesc,
              clreqHint: e.clreqHint,
              clreqId: e.clreqId,
              clreqIdPadre: e.clreqIdPadre,
              clreqOrd: e.clreqOrd,
              flgSelezionabile: e.flgSelezionabile,
              flgSelezionato: e.flgSelezionato,
              flgConforme: e.flgConforme,
              moduloId: e.moduloId,
              list: this.getRequisitoFiglio(e.clreqId),
              mouseHover: false,
              isSelected: false,
              checked: e.flgSelezionato ? true : false,
            }
            listPadreRequisiti.push(swapRquisito)
          }
        });
        this.listaRequisitiPraticaTo = listPadreRequisiti;
        this.client.listaRequisitiPraticaToClient = listPadreRequisiti;
      }

      this.isSpinEmitter = false;
    } else {
      this.goBack();
    }

  }

  getRequisitoFiglio(clreqId: number): RequisitoPraticaLista[] {
    const listRequisiti: RequisitoPraticaLista[] = [];
    if (this.listaRequisitiPratica) {
      const swapList = this.listaRequisitiPratica.filter((e) => e.clreqIdPadre === clreqId);
      if (!swapList || swapList.length <= 0) { return listRequisiti; }
      swapList.forEach((e) => {
        const swapRquisito: RequisitoPraticaLista = {
          clreqCod: e.clreqCod,
          clreqDesc: e.clreqDesc,
          clreqHint: e.clreqHint,
          clreqId: e.clreqId,
          clreqIdPadre: e.clreqIdPadre,
          clreqOrd: e.clreqOrd,
          flgSelezionabile: e.flgSelezionabile,
          flgSelezionato: e.flgSelezionato,
          flgConforme: e.flgConforme,
          moduloId: e.moduloId,
          list: this.getRequisitoFiglio(e.clreqId),
          mouseHover: false,
          isSelected: false,
          checked: e.flgSelezionato ? true : false,
        }
        listRequisiti.push(swapRquisito);
      });
    }
    return listRequisiti;
  }

  filterData(newValue: string) {
    this.isSpinEmitter = true;
    if (this.filter !== '' && newValue && newValue !== '' && this.listaRequisitiPratica) {
      this.client.reqSelected = null;
      switch (newValue) {
        case 'tutti':
          const listTutti: RequisitoPratica[] = this.listaRequisitiPratica;
          console.log('listTutti', listTutti);//DEBUG
          this.filterDataRequisiti(listTutti);
          break;
        case 'selezionabili':
          const listSelezionabili: RequisitoPratica[] = this.listaRequisitiPratica.filter(
            (e) => e.clreqOrd.length <= 3 || (e.clreqOrd.length > 3 && e.flgSelezionabile)
          );
          console.log('listSelezionabili', listSelezionabili);//DEBUG
          this.filterDataRequisiti(listSelezionabili);
          break;
        case 'selezionati':
          const listSelezionati: RequisitoPratica[] = this.listaRequisitiPratica.filter((e) => e.clreqOrd.length <= 3 || (e.clreqOrd.length > 3 && e.flgSelezionato));
          console.log('listSelezionati', listSelezionati);//DEBUG
          this.filterDataRequisiti(listSelezionati);
          break;
        case 'nonconformi': // TODO da finire
          const nonConformi: RequisitoPratica[] = this.listaRequisitiPratica.filter((e) => e.flgConforme);
          console.log('nonconformi', nonConformi);//DEBUG
          this.filterDataRequisiti(nonConformi);
          break;
        default:
          break;
      }
    }
    this.isSpinEmitter = false;
  }

  filterDataRequisiti(list: RequisitoPratica[]) {
    const listPadreRequisiti: RequisitoPraticaLista[] = [];
    if (this.listaRequisitiPratica && list && list.length > 0) {
      this.listaRequisitiPratica.forEach((e) => {
        if (!e.clreqIdPadre) {
          const swapRquisito: RequisitoPraticaLista = {
            clreqCod: e.clreqCod,
            clreqDesc: e.clreqDesc,
            clreqHint: e.clreqHint,
            clreqId: e.clreqId,
            clreqIdPadre: e.clreqIdPadre,
            clreqOrd: e.clreqOrd,
            flgSelezionabile: e.flgSelezionabile,
            flgSelezionato: e.flgSelezionato,
            moduloId: e.moduloId,
            list: this.getRequisitoFiglioFilter(e.clreqId, list),
            mouseHover: false,
            isSelected: false,
            flgConforme: e.flgConforme,
            checked: e.flgSelezionato ? true : false,
          }
          listPadreRequisiti.push(swapRquisito);
        }
      });
    }
    this.listaRequisitiPraticaTo = listPadreRequisiti;
    this.client.listaRequisitiPraticaToClient = listPadreRequisiti;
  }
  getRequisitoFiglioFilter(clreqId: number, list: RequisitoPratica[]): RequisitoPraticaLista[] {
    const listRequisiti: RequisitoPraticaLista[] = [];
    if (list && list.length > 0) {
      const swapList = list.filter((e) => e.clreqIdPadre === clreqId);
      if (!swapList || swapList.length <= 0) { return listRequisiti; }
      swapList.forEach((e) => {
        const swapRquisito: RequisitoPraticaLista = {
          clreqCod: e.clreqCod,
          clreqDesc: e.clreqDesc,
          clreqHint: e.clreqHint,
          clreqId: e.clreqId,
          clreqIdPadre: e.clreqIdPadre,
          clreqOrd: e.clreqOrd,
          flgSelezionabile: e.flgSelezionabile,
          flgSelezionato: e.flgSelezionato,
          flgConforme: e.flgConforme,
          moduloId: e.moduloId,
          list: this.getRequisitoFiglio(e.clreqId),
          mouseHover: false,
          isSelected: false,
          checked: e.flgSelezionato ? true : false,
        }
        listRequisiti.push(swapRquisito);
      });
    }
    return listRequisiti;
  }


  async getDecodificaRequisiti() {
    if (this.client.listaDecodificaRequisiti === null) {
      await lastValueFrom(this.client.getDecodificaRequisiti())
        .then(data => {
          if (data !== null) {
            this.client.listaDecodificaRequisiti = data;
          }
        })
        .catch(
          error => {
            this.isSpinEmitter = false;
            this.errorHandlerService.handleError(error, 'getDecodificaRequisiti');
          }
        );
    }
  }

  async getRequisitiPratica() {
    if (this.client.praticaDettaglio) {
      let params: MyParamsRequisitiPratica = {
        pratica_id: this.client.praticaDettaglio.praticaId
      }
      if (this.client.selectedPraticaDettaglio) {
        if (this.client.selectedPraticaDettaglio.isPrescrizione) {
          params.prescrizione_id = this.client.selectedPraticaDettaglio.id;
        } else if (this.client.selectedPraticaDettaglio.isSopralluogo) {
          params.appuntamento_id = this.client.selectedPraticaDettaglio.id;
        }
      }
      await lastValueFrom(this.client.getRequisitiPratica(params))
        .then(data => {
          if (data !== null) {
            this.listaRequisitiPratica = data;
          }
        })
        .catch(
          error => {
            this.isSpinEmitter = false;
            this.errorHandlerService.handleError(error, 'getDecodificaRequisiti');
          }
        );
    }
  }

  getNumeroAttivita(at: Attivita): string | null{
    if (!at) {
      this.errorHandlerService.displayErrorMessage('Errore in ricerzione attivitÃ ');
      return null;
    }
    if (this.client.praticaDettaglio) {
      const searchSopralluogo = this.client.praticaDettaglio.appuntamenti.filter((e) => e.appuntamentoId === at.appuntamentoId);
      if (searchSopralluogo && searchSopralluogo.length > 0) {
        return searchSopralluogo[0].appuntamentoNumero;
      }
      const searchPrescrizone = this.client.praticaDettaglio.prescrizioni.filter((e) => e.prescrizioneId === at.prescrizioneId);
      if (searchPrescrizone && searchPrescrizone.length > 0) {
        return searchPrescrizone[0].prescrizioneNumero;
      }
      return '';
    } else {
      return null;
    }
  }

  addModulo() {

  }

  goBack() {
    this.router.navigate([Navigation.DETTAGLIO_PRATICA], { skipLocationChange: true });
  }

}
