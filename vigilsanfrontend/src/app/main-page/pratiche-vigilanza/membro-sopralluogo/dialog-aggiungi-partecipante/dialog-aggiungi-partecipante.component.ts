/*
 * Copyright Regione Piemonte - 2024
 * SPDX-License-Identifier: EUPL-1.2
 */

import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { lastValueFrom } from 'rxjs';
import { Client } from 'src/app/Client';
import { SoggettoCommissione } from 'src/app/DTO/CommissioneDTO';
import { ErrorHandlerService } from 'src/app/main-page/ErrorHandlerService';
import { ObjListMembroSopralluogo } from '../mebro-sopralluogo.component';
import { GenericAppPre } from '../../dettaglio-pratica/dettaglio-pratica.component';

@Component({
  selector: 'app-dialog-aggiungi-partecipante',
  templateUrl: './dialog-aggiungi-partecipante.component.html',
  styleUrls: ['./dialog-aggiungi-partecipante.component.css']
})
export class DialogAggiungiPartecipanteComponent implements OnInit {

  isSpinEmitter: boolean = false;
  columnsCommissione: string[] = ['CF', 'Cognome', 'Nome', 'ruolo', 'Inizio validitÃ ', 'Fine validitÃ '];
  partecipante: ObjListMembroSopralluogo | null = null;
  sopralluogo: GenericAppPre | null = null;

  constructor(public dialogRef: MatDialogRef<DialogAggiungiPartecipanteComponent>, @Inject(MAT_DIALOG_DATA) public data: any,
    public client: Client, private errorHandlerService: ErrorHandlerService) {
    ({ partecipante: this.partecipante, sopralluogo: this.sopralluogo } = data);
  }

  async ngOnInit() {
    this.isSpinEmitter = true;
    this.client.listaSoggettiCommissione = null;
    await this.getListaSoggettiCommmissione();
    this.isSpinEmitter = false;
  }

  async getListaSoggettiCommmissione() {
    await lastValueFrom(this.client.getListaSoggettiCommmissione())
      .then(data => {
        if (data !== null) {
          this.client.listaSoggettiCommissione = data;
        }
      })
      .catch(
        error => {
          this.isSpinEmitter = false;
          this.errorHandlerService.handleError(error, 'getListaSoggettiCommmissione');
        }
      );
  }

  async closeModal(s: SoggettoCommissione) {
    this.isSpinEmitter = true;
    if (this.sopralluogo && this.partecipante) {
      const params = {
        appuntamentoId: this.sopralluogo.id,
        soggettoId: s.soggettoId,
        ruoloAppuntamentoSoggettoId: this.partecipante.ruoloAppuntamentoSoggettoId
      }
      await lastValueFrom(this.client.postAddPartecipante(params))
        .then(data => {
          this.client.listaSoggettiCommissione = null;
          this.dialogRef.close(true);
        })
        .catch(
          error => {
            this.isSpinEmitter = false;
            this.errorHandlerService.handleError(error, 'postAddPartecipante');
          }
        );
    }
    this.isSpinEmitter = false;
  }

}
