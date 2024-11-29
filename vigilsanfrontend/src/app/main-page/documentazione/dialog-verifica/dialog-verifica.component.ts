/*
 * Copyright Regione Piemonte - 2024
 * SPDX-License-Identifier: EUPL-1.2
 */

import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { lastValueFrom } from 'rxjs';
import { Client } from 'src/app/Client';
import { DocumentazioneCompilata } from 'src/app/DTO/DocumentazioniCompilateDTO';
import { ErrorHandlerService } from '../../ErrorHandlerService';

@Component({
  selector: 'app-dialog-verifica',
  templateUrl: './dialog-verifica.component.html',
  styleUrls: ['./dialog-verifica.component.css']
})
export class DialogVerificaComponent implements OnInit {

  isLoadingVerifica: boolean = false;
  conformita: string = '';
  note: string = '';

  documento: DocumentazioneCompilata;

  constructor(@Inject(MAT_DIALOG_DATA) public data: any, public client: Client, public dialogRef: MatDialogRef<DialogVerificaComponent>,
    private errorHandlerService: ErrorHandlerService) {
    ({ documento: this.documento } = data);
  }

  ngOnInit(): void {
    this.documento.verificaDocumentazione.esitoVerifica ? this.conformita = 'conforme' : this.conformita = 'nonConforme';
    this.note = this.documento.verificaDocumentazione.note ? this.documento.verificaDocumentazione.note : '';
  }


  async sendVerifica() {
    this.isLoadingVerifica = true;
    let isConforme: boolean = false;
    if (this.conformita === 'conforme') {
      isConforme = true;
    }
    if (!isConforme && (!this.note || this.note === '')) {
      this.isLoadingVerifica = false;
      this.errorHandlerService.displayErrorMessage('Nota obbligatoria con la scelta "Non conforme"');
      return;
    }
    let payload = {
      documentazioneId: this.documento.documentazioneId,
      esitoVerifica: isConforme,
      note: this.note
    }
    await lastValueFrom(this.client.postVerificaDocumentazione(payload))
      .then(data => {
        this.dialogRef.close(true);
      })
      .catch(
        error => {
          this.isLoadingVerifica = false;
          this.errorHandlerService.handleError(error, 'postVerificaDocumentazione');
        }
      );
  }
}
