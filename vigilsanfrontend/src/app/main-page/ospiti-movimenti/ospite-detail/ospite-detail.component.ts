/*
 * Copyright Regione Piemonte - 2024
 * SPDX-License-Identifier: EUPL-1.2
 */

import { AfterViewInit, Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { lastValueFrom } from 'rxjs';
import { Client, Navigation } from 'src/app/Client';
import { OspiteDetail } from 'src/app/DTO/OspiteDetailDTO';
import { ErrorHandlerService } from '../../ErrorHandlerService';
import { ConfirmDeleteComponent } from './confirm-delete/confirm-delete.component';
import { DialogAddMovimentoComponent } from './dialog-add-movimento/dialog-add-movimento.component';
import { ShowStickiesComponent } from './show-stickies/show-stickies.component';

@Component({
  selector: 'app-ospite-detail',
  templateUrl: './ospite-detail.component.html',
  styleUrls: ['./ospite-detail.component.css']
})
export class OspiteDetailComponent implements OnInit, AfterViewInit {
  // Emitters
  isSpinEmitterOspitiDetail: boolean = false;

  classLabelFilter: string = 'text-nowrap m-0 p-0 font-weight-bold display-4';
  classLabelFilterLead: string = 'text-nowrap m-0 p-0 lead display-4';

  // Tabs
  active: number = 0;
  // Tabs MOVIMENTI
  columns: string[] = ['Data', 'Tipo movimento', 'Condizioni', 'Stato', 'Categoria', 'Azioni'];

  // Tabs RENDICONTAZIONI

  // Tabs TAMPONI

  // Ospite
  idOspite: number | null = null;
  ospiteDetail: OspiteDetail | null = null;

  constructor(public client: Client, private errorHandlerService: ErrorHandlerService, private router: Router, public dialog: MatDialog) { }

  ngAfterViewInit(): void {
    setTimeout(() => {
      this.client.textRoute = 'Dettaglio Ospite';
    }, 0);
  }

  async ngOnInit() {
    if (this.client.ospiteSelezionato) {
      this.idOspite = this.client.ospiteSelezionato.soggettoId;
      await this.getOspiteDetail();
      await this.getMovimentoTipo();
      await this.getCondizioni();
      if (this.ospiteDetail && this.ospiteDetail?.ospiteMovimento && this.ospiteDetail?.ospiteMovimento?.length <= 0) {
        this.aggiungiMovimento();
      } else if (!this.ospiteDetail || !this.ospiteDetail?.ospiteMovimento) {
        this.aggiungiMovimento();
      }
    } else {
      this.router.navigate([Navigation.MOVIMENTI_OSPITE], { skipLocationChange: true });
      this.errorHandlerService.displayErrorMessage('Nessun ospite selezionato');
    }
  }

  goBack() {
    this.router.navigate([Navigation.MOVIMENTI_OSPITE], { skipLocationChange: true });
  }

  async getOspiteDetail() {
    this.isSpinEmitterOspitiDetail = true;
    const params = {
      soggetto_id: this.idOspite
    }
    await lastValueFrom(this.client.getOspiteDetail(params))
      .then(data => {
        if (data !== null) {
          this.ospiteDetail = data;
          this.isSpinEmitterOspitiDetail = false;
        }
      })
      .catch(
        error => {
          this.isSpinEmitterOspitiDetail = false;
          this.errorHandlerService.displayErrorMessage('API ERROR: ' + 'getOspiteDetail');
          this.errorHandlerService.displayErrorMessage('API ERROR: ' + error.message);
        }
      );
  }

  async getMovimentoTipo() {
    this.isSpinEmitterOspitiDetail = true;
    await lastValueFrom(this.client.getMovimentoTipo())
      .then(data => {
        if (data !== null) {
          this.client.listaMovimentiTipo = data;
          this.isSpinEmitterOspitiDetail = false;
        }
      })
      .catch(
        error => {
          this.isSpinEmitterOspitiDetail = false;
          this.errorHandlerService.displayErrorMessage('API ERROR: ' + 'getMovimentoTipo');
          this.errorHandlerService.displayErrorMessage('API ERROR: ' + error.message);
        }
      );
  }

  async getCondizioni() {
    this.isSpinEmitterOspitiDetail = true;
    await lastValueFrom(this.client.getCondizioni())
      .then(data => {
        if (data !== null) {
          this.client.listaCondizioni = data;
          this.isSpinEmitterOspitiDetail = false;
        }
      })
      .catch(
        error => {
          this.isSpinEmitterOspitiDetail = false;
          this.errorHandlerService.displayErrorMessage('API ERROR: ' + 'getContizioni');
          this.errorHandlerService.displayErrorMessage('API ERROR: ' + error.message);
        }
      );
  }

  aggiungiMovimento() {
    const dialogRef = this.dialog.open(DialogAddMovimentoComponent, {
      width: '600px',
      maxHeight: '90vh',
      panelClass: 'custom-modal'
    });  // Apro la modal
    dialogRef.afterClosed().subscribe(result => {
      if (result === 'add') {
        this.isSpinEmitterOspitiDetail = true;
        this.getOspiteDetail();
      }
    });
  }

  async eliminaMovimento(id: number | null) {
    if (!id) {
      this.errorHandlerService.displayErrorMessage('API ERROR: ' + 'deleteMovimento');
      return;
    }
    const dialogRef = this.dialog.open(ConfirmDeleteComponent, {
      width: '600px',
      maxHeight: '90vh',
      panelClass: 'custom-modal',
      data: { id: id }
    });  // Apro la modal
    dialogRef.afterClosed().subscribe(result => {
      if (result === 'delete') {
        this.isSpinEmitterOspitiDetail = true;
        this.getOspiteDetail();
      }
    });
  }

  showStickies(note: string | null) {
    if (!note) {
      this.errorHandlerService.displayErrorMessage('Errore nell\'apertura delle note');
      return;
    }
    this.dialog.open(ShowStickiesComponent, {
      width: '600px',
      maxHeight: '90vh',
      panelClass: 'custom-modal',
      data: { note: note }
    });
  }
}
