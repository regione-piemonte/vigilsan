/*
 * Copyright Regione Piemonte - 2024
 * SPDX-License-Identifier: EUPL-1.2
 */

import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { ObjListMembroSopralluogo } from '../mebro-sopralluogo.component';
import { ErrorHandlerService } from 'src/app/main-page/ErrorHandlerService';
import { Client } from 'src/app/Client';
import { lastValueFrom } from 'rxjs';

@Component({
  selector: 'app-dialog-elimina-membro',
  templateUrl: './dialog-elimina-membro.component.html',
  styleUrls: ['./dialog-elimina-membro.component.css']
})
export class DialogEliminaMembroComponent implements OnInit {

  isLoadingDeleteMembro: boolean = false;
  partecipante: ObjListMembroSopralluogo | null = null;

  constructor(public dialogRef: MatDialogRef<DialogEliminaMembroComponent>, @Inject(MAT_DIALOG_DATA) public data: any,
    public client: Client, private errorHandlerService: ErrorHandlerService) {
    ({ partecipante: this.partecipante } = data);
  }

  ngOnInit(): void {
  }

  async closeModal() {
    this.isLoadingDeleteMembro = true;
    if (this.partecipante) {
      const params = {
        appuntamentoSoggettoId: this.partecipante.appuntamentoSoggettoId,
      }
      await lastValueFrom(this.client.postDeletePartecipante(params))
        .then(data => {
          this.dialogRef.close(true);
        })
        .catch(
          error => {
            this.isLoadingDeleteMembro = false;
            this.errorHandlerService.handleError(error, 'postAddPartecipante');
          }
        );
    }
    this.isLoadingDeleteMembro = false;
  }
}
