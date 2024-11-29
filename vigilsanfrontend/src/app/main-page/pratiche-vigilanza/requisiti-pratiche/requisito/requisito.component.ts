/*
 * Copyright Regione Piemonte - 2024
 * SPDX-License-Identifier: EUPL-1.2
 */

import { Component, Input, OnInit } from '@angular/core';
import { lastValueFrom } from 'rxjs';
import { Client } from 'src/app/Client';
import { RequisitoPraticaLista } from 'src/app/DTO/RequisitiDTO';
import { ErrorHandlerService } from 'src/app/main-page/ErrorHandlerService';

@Component({
  selector: 'app-requisito',
  templateUrl: './requisito.component.html',
  styleUrls: ['./requisito.component.css']
})
export class RequisitoComponent implements OnInit {
  @Input() listaRequisitiPraticaTo: RequisitoPraticaLista[] | null = null;  // Lista di requisiti ricevuta dal componente padre
  clreqList: RequisitoPraticaLista[] = [];

  constructor(public client: Client, private errorHandlerService: ErrorHandlerService) { }


  ngOnInit(): void {
  }


  onSelected(req: RequisitoPraticaLista) {
    this.client.isSpinEmitterCardDx = true;
    if (this.client.isLoadingDataRequisito) {
      this.client.isSpinEmitterCardDx = false;
      return;
    }
    this.client.reqSelected = null;
    this.client.moduloToshow = null;
    this.client.moduloCompilatoToshow = null;
    if (this.client.listaRequisitiPraticaToClient) {
      this.client.reqSelected = req;
      this.recurtionUnselection(this.client.listaRequisitiPraticaToClient);
      req.isSelected = true;
      if (this.client.loggedUser && this.client.loggedUser.struttura && this.client.loggedUser.struttura.strutturaId) {
        this.client.idStrutturaSwap = this.client.loggedUser.struttura.strutturaId;
        if (this.client.reqSelected.moduloId) {
          this.client.moduloToshow = this.client.reqSelected.moduloId;
          this.client.moduloCompilatoToshow = null;
        } else {
          this.client.moduloToshow = null;
          this.client.moduloCompilatoToshow = null;
        }
      }
    } else {
      this.client.reqSelected = null;
    }
    this.client.isSpinEmitterCardDx = false;
  }

  recurtionUnselection(list: RequisitoPraticaLista[]) {
    if (list && list.length > 0) {
      list.forEach((e) => {
        e.isSelected = false;
        if (e.list && e.list.length > 0) {
          this.recurtionUnselection(e.list);
        }
      });
    }
  }

  async toggle(req: RequisitoPraticaLista) {
    this.client.isLoadingDataRequisito = true;
    if (!this.client.listaRequisitiPraticaToClient) {
      setTimeout(() => {
        this.client.isLoadingDataRequisito = false;
      }, 100);

      this.errorHandlerService.displayErrorMessage('[INTERNAL-ERROR] - listaRequisitiPraticaToClient not found');
      return;
    }
    this.getClreqList(this.client.listaRequisitiPraticaToClient);
    if (!this.clreqList) {
      setTimeout(() => {
        this.client.isLoadingDataRequisito = false;
      }, 100);
      return;
    }
    if (this.clreqList && this.clreqList.length > 0 && this.client.praticaDettaglio) {
      const clreqListIds = this.clreqList.map((e) => e.clreqId);
      let payload = {
        praticaId: this.client.genericAppPreRequisiti ? this.client.genericAppPreRequisiti.praticaId : this.client.praticaDettaglio.praticaId,
        prescrizioneId: this.client.genericAppPreRequisiti ? this.client.genericAppPreRequisiti.id : null,
        appuntamentoId: null,
        clreqList: clreqListIds
      }
      await lastValueFrom(this.client.postRequisito(payload))
        .then(data => {
          if (data) {
            this.clreqList = [];
            this.errorHandlerService.displaySuccessMessage('Requisito aggiunto con successo');
          }
          setTimeout(() => {
            this.client.isLoadingDataRequisito = false;
          }, 100);
        })
        .catch(
          error => {
            setTimeout(() => {
              this.client.isLoadingDataRequisito = false;
            }, 100);
            this.clreqList = [];
            this.errorHandlerService.handleError(error, 'postRequisito');
          }
        );
    }
    this.clreqList = [];
  }

  getClreqList(list: RequisitoPraticaLista[]) {
    if (list && list.length > 0) {
      list.forEach((e) => {
        if (e.checked) {
          this.clreqList.push(e);
        }
        if (e.list && e.list.length > 0) {
          this.getClreqList(e.list);
        }
      });
    }
  }

  splitString(text: string | null, maxChars: number): { firstPart: string; secondPart: string | null } {
    if (!text) { return { firstPart: '', secondPart: null }; }
    if (text.length > maxChars) {
      const firstPart = text.substring(0, maxChars);
      const secondPart = text.substring(maxChars);
      return { firstPart, secondPart };
    }
    return { firstPart: text, secondPart: null };
  }

}
