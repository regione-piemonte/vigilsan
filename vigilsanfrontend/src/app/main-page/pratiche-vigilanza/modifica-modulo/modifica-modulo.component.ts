/*
 * Copyright Regione Piemonte - 2024
 * SPDX-License-Identifier: EUPL-1.2
 */

import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { lastValueFrom } from 'rxjs';
import { Client } from 'src/app/Client';
import { Attivita, NewPratica } from 'src/app/DTO/PraticheVigilanzaNuovaPratica';
import { ErrorHandlerService } from '../../ErrorHandlerService';
import { ModuloCommon } from '../../ModuloCommon';

@Component({
  selector: 'app-modifica-modulo',
  templateUrl: './modifica-modulo.component.html',
  styleUrls: ['./modifica-modulo.component.css']
})
export class ModificaModuloComponent implements OnInit {

  title: string | null = null;
  alertValidModulo: boolean = false;
  isSpinEmitter: boolean = false;
  isLoadingAddDocumentazione: boolean = false;
  praticaDetId: number | null;


  moduloId: number | null = null;
  moduloCompilatoId: number | null = null;
  at: Attivita | null = null;

  constructor(@Inject(MAT_DIALOG_DATA) public data: any, public client: Client, public moduloCommon: ModuloCommon,
    public dialogRef: MatDialogRef<ModificaModuloComponent>, private errorHandlerService: ErrorHandlerService) {
    ({ title: this.title, praticaDetId: this.praticaDetId, moduloId: this.moduloId, moduloCompilatoId: this.moduloCompilatoId, at: this.at } = data);
  }

  ngOnInit(): void {
    this.isSpinEmitter = true;
    this.alertValidModulo = false;
    if (!this.moduloCompilatoId) {
      this.client.moduloToshow = this.moduloId;
      this.client.moduloCompilatoToshow = null;
    } else {
      this.client.moduloToshow = null;
      this.client.moduloCompilatoToshow = this.moduloCompilatoId;
    }
    this.isSpinEmitter = false;
  }

  async sendDocumentazioneModal() {
    this.alertValidModulo = false;
    this.isLoadingAddDocumentazione = true;
    this.isSpinEmitter = true;

    this.moduloCommon.sendModulo();
    // if (!this.client.isValidModulo) {
    //   this.alertValidModulo = true;
    //   this.isLoadingAddDocumentazione = false;
    //   this.isSpinEmitter = false;
    //   this.errorHandlerService.displayErrorMessage('ERRORE IN COMPILAZIONE');
    //   return;
    // }

    let params: NewPratica = {
      praticaId: this.client.praticaDettaglio?.praticaId,
      praticaTipoId: null,
      strutturaId: null,
      praticaDettaglio: {
        praticaDetId: this.praticaDetId,
        praticaId: this.client.praticaDettaglio?.praticaId,
        prescrizioneId: null,
        appuntamentoId: null,
        azioneId: null,
        dataoraAzione: null,
        moduloCompilatoId: null,
        note: null,
        flgScadenza: null,
        profiloIdScadenza: null,
        dataoraInizio: null,
        dataoraFine: null,
        appuntamentoNumero: null,
        prescrizioneNumero: null
      },
      praticaNumero: null,
      validitaInizio: null,
      validitaFine: null
    }
    let moduloCompilato = {
      note: this.client.moduloToSend.note,
      modulo: this.client.moduloToSend
    }
    params.moduloCompilato = moduloCompilato;

    const loginObservable = this.client.postAddPraticaModulo(params);
    const data = await lastValueFrom(loginObservable)
      .catch(
        error => {
          this.isSpinEmitter = false;
          this.alertValidModulo = false;
          this.isLoadingAddDocumentazione = false;
          this.errorHandlerService.handleError(error, 'postAddPratica');
        }
      );
    if (data) {
      this.client.praticaDettaglio = null;
      this.alertValidModulo = false;
      this.isLoadingAddDocumentazione = false;
      this.dialogRef.close(true);
      this.isSpinEmitter = false;
    } else {
      this.isSpinEmitter = false;
      this.alertValidModulo = false;
      this.isLoadingAddDocumentazione = false;
    }
  }

}

