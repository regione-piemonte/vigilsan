/*
 * Copyright Regione Piemonte - 2024
 * SPDX-License-Identifier: EUPL-1.2
 */

import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { lastValueFrom } from 'rxjs';
import { Client } from 'src/app/Client';
import { RilevazioniAggiungi, SendRilevazione } from 'src/app/DTO/RilevazioniAggiungiDTO';
import { RilevazioniTab } from 'src/app/DTO/RilevazioniTabModel';
import { ErrorHandlerService } from '../../ErrorHandlerService';
import { ModuloCommon } from '../../ModuloCommon';


@Component({
  selector: 'app-dialog-aggiungi',
  templateUrl: './dialog-aggiungi.component.html',
  styleUrls: ['./dialog-aggiungi.component.css']
})

export class DialogAggiungiComponent implements OnInit {

  isValidDate: boolean = false;
  isLoadingAddRilevazione: boolean = false;
  alertValidModulo: boolean = false;
  isSpinEmitter: boolean = false;
  visualizzazione: boolean = false;

  rilevazione: RilevazioniAggiungi = {
    rilevazioneId: null,
    strutturaId: null,
    strCatId: null,
    moduloCompilatoId: null,
    moduloConfigId: null,
    dataoraRilevazione: null,
    validitaInizio: null,
    validitaFine: null,
    modulo: null,
    strutturaCategoria: null
  };
  tab: RilevazioniTab = {
    moduloConfigCod: '',
    moduloConfigDesc: '',
    moduloConfigId: 0,
    moduloConfigOrd: '',
    reccount: 0,
  }

  constructor(@Inject(MAT_DIALOG_DATA) public data: any, public client: Client,
    public moduloCommon: ModuloCommon,
    public dialogRef: MatDialogRef<DialogAggiungiComponent>, private errorHandlerService: ErrorHandlerService) {
    ({ rilevazione: this.rilevazione, tab: this.tab, visualizzazione: this.visualizzazione } = data);
    // Check if is valid rilevazione
    if (this.rilevazione.validitaInizio && this.rilevazione.validitaFine) {
      this.isValidDate = this.checkIsValid(this.rilevazione.validitaInizio, this.rilevazione.validitaFine);
    } else if (this.rilevazione.validitaInizio && !this.rilevazione.validitaFine) {
      this.isValidDate = true;
    } else {
      this.isValidDate = false;
    }
  }

  checkIsValid(validitaInizio: number, validitaFine: number): boolean {
    const dataInizio = new Date(validitaInizio);
    const dataFine = new Date(validitaFine);
    const oggi: Date = new Date();
    return oggi >= dataInizio && oggi <= dataFine;
  }

  async ngOnInit() {
    this.isSpinEmitter = true;
    this.alertValidModulo = false;
    // If errore nel modulo
    if (this.client.erroreModulo) {
      this.client.moduloToshow = null;
      this.client.moduloCompilatoToshow = null;
      this.dialogRef.close('');
    }
    // If client.modulo.visible is not created/set befero
    if (this.client.modulo.visible === undefined) {
      this.client.modulo.visible = true;
    }
    this.isSpinEmitter = false;
  }

  async sendRilevazione() {
    this.alertValidModulo = false;
    this.isLoadingAddRilevazione = true;
    this.client.selectedAzioneDialoagAddPratica = null; //Anullo struttura idSwap per altri calcoli
    this.moduloCommon.sendModulo();

    if (!this.client.isValidModulo) {
      this.alertValidModulo = true;
      this.errorHandlerService.displayErrorMessage('ERRORE IN COMPILAZIONE');
      this.isLoadingAddRilevazione = false;
      return;
    }
    const rilevazioneToSend: SendRilevazione = {
      rilevazione: {
        rilevazioneId: this.rilevazione.rilevazioneId,
        strutturaId: this.rilevazione.strutturaId,
        strCatId: this.rilevazione.strCatId,
        moduloCompilatoId: this.rilevazione.moduloCompilatoId,
        moduloConfigId: this.rilevazione.moduloConfigId,
        dataoraRilevazione: this.rilevazione.dataoraRilevazione,
        validitaInizio: this.rilevazione.validitaInizio,
        validitaFine: this.rilevazione.validitaFine
      },
      moduloCompilato: {
        note: this.client.moduloToSend.note,
        modulo: this.client.moduloToSend
      }
    }

    await lastValueFrom(this.client.postRilevazione(rilevazioneToSend))
      .then(data => {
        this.errorHandlerService.displaySuccessMessage('Rilevazione salvata con successo');
        this.dialogRef.close('updated');
        // this.isLoadingAddRilevazione = false;
      })
      .catch(
        error => {
          this.isLoadingAddRilevazione = false;
          this.errorHandlerService.handleError(error, 'postRilevazione');
        }
      );
  }

}
