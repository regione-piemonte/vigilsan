/*
 * Copyright Regione Piemonte - 2024
 * SPDX-License-Identifier: EUPL-1.2
 */

import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { SoggettoCommissione } from 'src/app/DTO/CommissioneDTO';

@Component({
  selector: 'app-dialog-conferma-eliminazione',
  templateUrl: './dialog-conferma-eliminazione.component.html',
  styleUrls: ['./dialog-conferma-eliminazione.component.css']
})
export class DialogConfermaEliminazioneComponent implements OnInit {

  isLoadingDeleteMembro: boolean = false;
  membro: SoggettoCommissione | null = null;

  constructor(public dialogRef: MatDialogRef<DialogConfermaEliminazioneComponent>, @Inject(MAT_DIALOG_DATA) public data: any) {
    ({ membro: this.membro } = data);
  }

  ngOnInit(): void {
  }

  closeModal() {
    this.isLoadingDeleteMembro = true;
    this.dialogRef.close(true);
  }
}
