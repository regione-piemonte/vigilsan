/*
 * Copyright Regione Piemonte - 2024
 * SPDX-License-Identifier: EUPL-1.2
 */

import { AfterViewInit, Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { lastValueFrom } from 'rxjs';
import { Client, Navigation } from '../Client';
import { ProfiliEntity } from '../DTO/User';
import { ReleaseNotesComponent } from './release-notes/release-notes.component';
@Component({
  selector: 'app-main-page',
  templateUrl: './main-page.component.html',
  styleUrls: ['./main-page.component.css']
})
export class MainPageComponent implements OnInit, AfterViewInit {

  isFeatureEnabled: boolean = false;

  constructor(public router: Router, public client: Client, public dialog: MatDialog) {
  }
  ngAfterViewInit(): void {
    setTimeout(() => {
      this.client.textRoute = 'MainPage';
    }, 0);
  }

  async ngOnInit() {

    if (this.client.tokenApplication['x-access-token'] === '' && !this.client.devMode) {
      this.router.navigate([Navigation.LOGIN_ERROR_PAGE], { skipLocationChange: true });
    }

    // Controllare la variabile nel localStorage
    this.isFeatureEnabled = localStorage.getItem('isFeatureEnabled') === 'true';
    if (!this.isFeatureEnabled) {
      await this.openReleaseNotes();
    }

    this.getProfilo();
  }

  async openReleaseNotes() {
    const dialogRef = this.dialog.open(ReleaseNotesComponent, {
      width: '1200px',
      maxHeight: '90vh',
      panelClass: 'custom-modal',
      // data: { rilevazione: rilevazione, tab: tab }
    });  // Apro la modal
    const result = await lastValueFrom(dialogRef.afterClosed());
    if (result === 'check') {
      localStorage.setItem('isFeatureEnabled', 'true'); // Salva come stringa
    }
  }

  getProfilo(): string {
    const profilo = this.client.loggedUser.profili?.find((e: ProfiliEntity) => e.profiloId === this.client.loggedUser.profiloId);
    if (profilo && profilo.profiloDesc) {
      this.client.selectedProfilo = profilo;
      return profilo.profiloDesc;
    } else {
      return '';
    }
  }

}
