/*
 * Copyright Regione Piemonte - 2024
 * SPDX-License-Identifier: EUPL-1.2
 */

import { AfterViewInit, Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { lastValueFrom } from 'rxjs';
import { Client, Navigation } from 'src/app/Client';
import { DettaglioArpe } from 'src/app/DTO/DettaglioArpeDTO';
import { ProfiliEntity } from 'src/app/DTO/User';
import { HeaderComponent } from 'src/app/header/header.component';
import { CambioProfiloComponent } from '../cambio-profilo/cambio-profilo.component';
import { CambioStrutturaComponent } from '../cambio-struttura/cambio-struttura.component';
import { DettaglioArpeComponent } from '../dettaglio-arpe/dettaglio-arpe.component';
import { ErrorHandlerService } from '../ErrorHandlerService';
import { ReleaseNotesComponent } from '../release-notes/release-notes.component';

@Component({
  selector: 'app-profilo',
  templateUrl: './profilo.component.html',
  styleUrls: ['./profilo.component.css']
})
export class ProfiloComponent implements OnInit, AfterViewInit {

  nome: string = '';
  cognome: string = '';
  struttura: string = '';
  ruolo: string = '';
  asr: string = '';
  collocazione: string = '';
  // collocazioneTwo: string = '';

  isSpinEmitter: boolean = false;
  panelOpenState: boolean = true;
  loadDettaglioArpe: boolean = false;

  constructor(public router: Router, public client: Client, public dialog: MatDialog,
    private errorHandlerService: ErrorHandlerService, private header: HeaderComponent) {
  }

  ngAfterViewInit(): void {
    setTimeout(() => {
      this.client.textRoute = 'Profilo';
    }, 0);
  }

  ngOnInit(): void {
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

  delay(ms: number) {
    return new Promise(resolve => setTimeout(resolve, ms));
  }

  redirectToModuloTest() {
    this.router.navigate([Navigation.MODULO], { skipLocationChange: true });
  }

  redirectToModuloLogin() {
    this.router.navigate([Navigation.LOGIN], { skipLocationChange: true });
  }

  async openDettaglioArpe() {
    this.loadDettaglioArpe = true;
    let listaSettaglioArpe: DettaglioArpe[];
    const params = {
      struttura_id: this.client.loggedUser.struttura?.strutturaId
    }
    await lastValueFrom(this.client.getDettaglioArpe(params))
      .then(data => {
        listaSettaglioArpe = data;
        this.loadDettaglioArpe = false;
        const dialogRef = this.dialog.open(DettaglioArpeComponent, {
          panelClass: 'custom-modal',
          data: { listaSettaglioArpe: listaSettaglioArpe }
        });  // Apro la modal
        // dialogRef.afterClosed().subscribe(result => {});
      })
      .catch(
        error => {
          this.loadDettaglioArpe = false;
          this.isSpinEmitter = false;
          this.errorHandlerService.handleError(error, 'getDettaglioArpe');
          return;
        }
      );
  }

  openCambioStruttura() {
    const dialogRef = this.dialog.open(CambioStrutturaComponent, {
      minWidth: '80vh',
      maxHeight: '90vh',
      panelClass: 'custom-modal'
    });  // Apro la modal
    dialogRef.afterClosed().subscribe(result => {
      if (result === 'cambio') {
        this.header.makeLinkMenu();
        this.header.ngOnInit();
        this.ngOnInit();
        this.errorHandlerService.displaySuccessMessage('Cambio struttura avvenuto con successo!');
      }
    });
  }

  openCambioProfilo() {
    const dialogRef = this.dialog.open(CambioProfiloComponent, {
      minWidth: '80vh',
      maxHeight: '90vh',
      panelClass: 'custom-modal'
    });  // Apro la modal
    dialogRef.afterClosed().subscribe(result => {
      if (result === 'cambio') {
        this.header.makeLinkMenu();
        this.header.ngOnInit();
        this.ngOnInit();
        this.errorHandlerService.displaySuccessMessage('Cambio profilo avvenuto con successo!');
      }
    });
  }
}
