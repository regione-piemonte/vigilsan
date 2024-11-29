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

@Component({
  selector: 'app-dialog-delete-attivita',
  templateUrl: './dialog-delete-attivita.component.html',
  styleUrls: ['./dialog-delete-attivita.component.css']
})
export class DialogDeleteAttivitaComponent implements OnInit {

  isLoadingDeleteAttivita: boolean = false;
  at: Attivita | null = null;

  constructor(public dialogRef: MatDialogRef<DialogDeleteAttivitaComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any, public client: Client, private errorHandlerService: ErrorHandlerService) {
    ({ at: this.at } = data);
  }

  ngOnInit(): void {
  }


  async closeModal() {
    this.isLoadingDeleteAttivita = true;
    if (this.client.selectedExistPratica) {
      let params: NewPratica = {
        praticaId: this.client.selectedExistPratica.praticaId,
        praticaTipoId: this.client.selectedExistPratica.praticaTipoId ? this.client.selectedExistPratica.praticaTipoId : null,
        strutturaId: this.client.selectedExistPratica.strutturaId,
        praticaDettaglio: {
          praticaDetId: this.at ? this.at.praticaDetId : null,
          praticaId: this.client.selectedExistPratica.praticaId ? this.client.selectedExistPratica.praticaId : null,
          prescrizioneId: null,
          appuntamentoId: null,
          azioneId: this.at ? this.at.azioneId : null,
          dataoraAzione: this.at ? this.at.dataoraAzione : null,
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
      const loginObservable = this.client.postAddPratica(params);
      const data = await lastValueFrom(loginObservable)
        .catch(
          error => {
            this.dialogRef.close();
            this.errorHandlerService.handleError(error, '');
          }
        );
      if (data) {
        this.dialogRef.close(true);
      }
    }
  }
}
