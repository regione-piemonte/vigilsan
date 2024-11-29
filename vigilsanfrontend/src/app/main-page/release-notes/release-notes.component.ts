/*
 * Copyright Regione Piemonte - 2024
 * SPDX-License-Identifier: EUPL-1.2
 */

import { Component, OnDestroy, OnInit } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'app-release-notes',
  templateUrl: './release-notes.component.html',
  styleUrls: ['./release-notes.component.css']
})
export class ReleaseNotesComponent implements OnInit, OnDestroy {

  constructor(public dialogRef: MatDialogRef<ReleaseNotesComponent>) { }

  ngOnDestroy(): void {
    this.dialogRef.close('check');
  }

  ngOnInit(): void {
  }

}
