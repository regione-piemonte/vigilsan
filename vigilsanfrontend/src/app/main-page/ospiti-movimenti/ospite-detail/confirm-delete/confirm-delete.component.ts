/*
 * Copyright Regione Piemonte - 2024
 * SPDX-License-Identifier: EUPL-1.2
 */

import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { lastValueFrom } from 'rxjs';
import { Client } from 'src/app/Client';
import { ErrorHandlerService } from 'src/app/main-page/ErrorHandlerService';

@Component({
  selector: 'app-confirm-delete',
  templateUrl: './confirm-delete.component.html',
  styleUrls: ['./confirm-delete.component.css']
})
export class ConfirmDeleteComponent implements OnInit {

  isSpinEmmitterDelete: boolean = false;
  id: number | null = null;

  constructor(@Inject(MAT_DIALOG_DATA) public data: any, private client: Client, public dialogRef: MatDialogRef<ConfirmDeleteComponent>, private errorHandlerService: ErrorHandlerService) {

    ({ id: this.id } = data);
  }

  ngOnInit(): void {
  }

  async eliminaMovimento() {
    this.isSpinEmmitterDelete = true;
    await lastValueFrom(this.client.deleteMovimento(this.id))
      .then(data => {
        this.errorHandlerService.displaySuccessMessage('Movimento eliminato con successo.');
        this.isSpinEmmitterDelete = false;
        this.dialogRef.close('delete');
      })
      .catch(
        error => {
          this.isSpinEmmitterDelete = false;
          this.dialogRef.close('');
          this.errorHandlerService.handleError(error, 'deleteMovimento');
        }
      );
  }
}
