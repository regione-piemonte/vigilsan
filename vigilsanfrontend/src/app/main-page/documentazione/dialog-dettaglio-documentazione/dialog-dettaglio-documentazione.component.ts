/*
 * Copyright Regione Piemonte - 2024
 * SPDX-License-Identifier: EUPL-1.2
 */

import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { lastValueFrom } from 'rxjs';
import { Client } from 'src/app/Client';
import { SendDocumentazione } from 'src/app/DTO/Documentazione';
import { DocumentazioneCompilata } from 'src/app/DTO/DocumentazioniCompilateDTO';
import { ErrorHandlerService } from '../../ErrorHandlerService';
import { ModuloCommon } from '../../ModuloCommon';

@Component({
  selector: 'app-dialog-dettaglio-documentazione',
  templateUrl: './dialog-dettaglio-documentazione.component.html',
  styleUrls: ['./dialog-dettaglio-documentazione.component.css']
})
export class DialogDettaglioDocumentazioneComponent implements OnInit {

  title: string = '';
  documento: DocumentazioneCompilata;
  alertValidModulo: boolean = false;
  isLoadingAddDocumentazione: boolean = false;

  constructor(@Inject(MAT_DIALOG_DATA) public data: any, public client: Client, public moduloCommon: ModuloCommon,
    public dialogRef: MatDialogRef<DialogDettaglioDocumentazioneComponent>, private errorHandlerService: ErrorHandlerService) {
    ({ title: this.title, documento: this.documento } = data);
  }

  ngOnInit(): void {
    this.alertValidModulo = false;
  }


  async sendDocumentazioneModal() {
    this.alertValidModulo = false;
    this.isLoadingAddDocumentazione = true;

    this.client.selectedAzioneDialoagAddPratica = null; //Anullo struttura idSwap per altri calcoli
    this.moduloCommon.sendModulo();
    if (!this.client.isValidModulo) {
      this.alertValidModulo = true;
      this.isLoadingAddDocumentazione = false;
      this.errorHandlerService.displayErrorMessage('ERRORE IN COMPILAZIONE');
      return;
    }

    if (this.documento) {
      const documentazioneToSend: SendDocumentazione = {
        documentazione: {
          documentazioneId: this.documento.documentazioneId,
          strutturaId: this.documento.strutturaId,
          strCatId: this.documento.strCatId,
          moduloCompilatoId: this.documento.moduloCompilatoId,
          moduloConfigId: this.documento.moduloConfigId,
          dataoraDocumentazione: new Date().getTime(),
          occorrenza: this.documento.occorrenza
        },
        moduloCompilato: {
          note: this.client.moduloToSend.note,
          modulo: this.client.moduloToSend
        }
      }

      await lastValueFrom(this.client.postSalvaDocumentazione(documentazioneToSend))
        .then(data => {
          this.dialogRef.close('updated');
        })
        .catch(
          error => {
            this.isLoadingAddDocumentazione = false;
            this.errorHandlerService.handleError(error, 'postSalvaDocumentazione');
          }
        );
    } else {
      this.errorHandlerService.displayErrorMessage('Errore interno');
    }
  }

}
