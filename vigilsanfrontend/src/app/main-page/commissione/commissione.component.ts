/*
 * Copyright Regione Piemonte - 2024
 * SPDX-License-Identifier: EUPL-1.2
 */

import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { lastValueFrom } from 'rxjs';
import { Client } from 'src/app/Client';
import { NuovoMembroComponent } from './nuovo-membro/nuovo-membro.component';
import { ErrorHandlerService } from '../ErrorHandlerService';
import { SoggettoCommissione } from 'src/app/DTO/CommissioneDTO';
import { DialogConfermaEliminazioneComponent } from './dialog-conferma-eliminazione/dialog-conferma-eliminazione.component';

@Component({
  selector: 'app-commissione',
  templateUrl: './commissione.component.html',
  styleUrls: ['./commissione.component.css']
})
export class CommissioneComponent implements OnInit {

  isSpinEmitter: boolean = false;
  columnsCommissione: string[] = ['CF', 'Cognome', 'Nome', 'ruolo', 'Inizio validitÃ ', 'Fine validitÃ ', ''];

  constructor(public dialog: MatDialog, public router: Router, public client: Client, private errorHandlerService: ErrorHandlerService) { }

  async ngOnInit() {
    this.isSpinEmitter = true;
    await this.getListaRuoli();
    await this.getListaSoggettiCommmissione();
    this.isSpinEmitter = false;
  }

  async showNuovoMebroCommissione() {
    const dialogRef = this.dialog.open(NuovoMembroComponent, {
      minWidth: '800px',
      // minHeight: '800px',
      // panelClass: 'custom-modal',
      // data: { rilevazione: rilevazione, tab: tab }
    });  // Apro la modal
    const result = await lastValueFrom(dialogRef.afterClosed());
    this.client.ospiteSelezionato = null;
    if (result) {
      this.isSpinEmitter = true;
      await this.getListaSoggettiCommmissione();
      this.errorHandlerService.displaySuccessMessage('Membro aggiunto con successo');
      this.isSpinEmitter = false;
    }
  }

  async modificaMembro(membro: SoggettoCommissione) {
    const dialogRef = this.dialog.open(NuovoMembroComponent, {
      minWidth: '800px',
      // minHeight: '800px',
      // panelClass: 'custom-modal',
      data: { membro: membro }
    });  // Apro la modal
    const result = await lastValueFrom(dialogRef.afterClosed());
    this.client.ospiteSelezionato = null;
    if (result) {
      this.isSpinEmitter = true;
      await this.getListaSoggettiCommmissione();
      this.errorHandlerService.displaySuccessMessage('Membro modificato con successo');
      this.isSpinEmitter = false;
    }
  }

  async eliminaMembro(membro: SoggettoCommissione) {
    this.isSpinEmitter = true;
    let params = {
      enteSoggId: membro.enteSoggId
    }
    const loginObservable = this.client.deliteMembro(params);
    const data = await lastValueFrom(loginObservable)
      .catch(
        error => {
          this.isSpinEmitter = false;
          this.errorHandlerService.handleError(error, 'deliteMembro');
        }
      );
    if (data) {
      await this.getListaSoggettiCommmissione();
      this.isSpinEmitter = false;
    }
  }

  async showEliminaMembro(membro: SoggettoCommissione) {
    const dialogRef = this.dialog.open(DialogConfermaEliminazioneComponent, {
      data: { membro: membro }
    });  // Apro la modal
    const result = await lastValueFrom(dialogRef.afterClosed());
    this.client.ospiteSelezionato = null;
    if (result) {
      await this.eliminaMembro(membro);
    }
  }

  async getListaRuoli() {
    if (this.client.listaRuoli === null) {
      await lastValueFrom(this.client.getListaRuoli())
        .then(data => {
          if (data !== null) {
            this.client.listaRuoli = data;
          }
        })
        .catch(
          error => {
            this.isSpinEmitter = false;
            this.errorHandlerService.handleError(error, 'getListaRuoli');
          }
        );
    }
  }
  async getListaSoggettiCommmissione() {
    await lastValueFrom(this.client.getListaSoggettiCommmissione())
      .then(data => {
        if (data !== null) {
          this.client.listaSoggettiCommissione = data;
        }
      })
      .catch(
        error => {
          this.isSpinEmitter = false;
          this.errorHandlerService.handleError(error, 'getListaSoggettiCommmissione');
        }
      );
  }
}
