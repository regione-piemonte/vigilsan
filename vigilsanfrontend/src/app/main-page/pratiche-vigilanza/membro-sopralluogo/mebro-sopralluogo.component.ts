/*
 * Copyright Regione Piemonte - 2024
 * SPDX-License-Identifier: EUPL-1.2
 */

import { Component, Inject, OnInit } from '@angular/core';
import { Client } from 'src/app/Client';
import { ErrorHandlerService } from '../../ErrorHandlerService';
import { MAT_DIALOG_DATA, MatDialog, MatDialogRef } from '@angular/material/dialog';
import { GenericAppPre } from '../dettaglio-pratica/dettaglio-pratica.component';
import { lastValueFrom } from 'rxjs';
import { MembroSopralluogo, RuoliMembriSopralluogo } from 'src/app/DTO/PraticheVigilanzaMembriSopralluoghiDTO';
import { DialogEliminaMembroComponent } from './dialog-elimina-membro/dialog-elimina-membro.component';
import { DialogAggiungiPartecipanteComponent } from './dialog-aggiungi-partecipante/dialog-aggiungi-partecipante.component';

export interface ObjListMembroSopralluogo {
  descrizione: string | null,
  cognome: string | null,
  nome: string | null,
  soggettoId: number | null,
  ruoloAppuntamentoSoggettoId: number | null,
  appuntamentoSoggettoId: number | null,
}

@Component({
  selector: 'app-mebro-sopralluogo',
  templateUrl: './mebro-sopralluogo.component.html',
  styleUrls: ['./mebro-sopralluogo.component.css']
})
export class MebroSopralluogoComponent implements OnInit {

  isSpinEmitter: boolean = false;
  isLoadingAddMembro: boolean = false;
  sopralluogo: GenericAppPre | null = null;

  listaRuoliMembriSopralluogo: RuoliMembriSopralluogo[] = [];

  listaMembriSopralluogo: MembroSopralluogo[] = [];
  columnsMembriSopralluogo: string[] = ['Cognome', 'Nome', 'CF', 'Data Nascita', 'Ruolo Ente', 'Ruolo Soggetto'];

  columnsMembriRuolo: string[] = ['Descrizione', 'Cognome', 'Nome', ''];
  objListMembroSopralluogo: ObjListMembroSopralluogo[] = [];

  constructor(public dialogRef: MatDialogRef<MebroSopralluogoComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any, public client: Client, private errorHandlerService: ErrorHandlerService, public dialog: MatDialog) {
    ({ sopralluogo: this.sopralluogo } = data);
  }

  async ngOnInit() {
    this.isSpinEmitter = true;
    await this.getListaRuoliMembroSopralluogo();
    if (this.sopralluogo && this.sopralluogo.id) {
      await this.getListaMembriPerSopralluogo(this.sopralluogo.id);
    }
    this.listaRuoliMembriSopralluogo.forEach((e) => {
      let newObj: ObjListMembroSopralluogo = {
        descrizione: e.ruoloAppuntamentoSoggettoDesc,
        cognome: null,
        nome: null,
        ruoloAppuntamentoSoggettoId: e.ruoloAppuntamentoSoggettoId,
        soggettoId: null,
        appuntamentoSoggettoId: null
      }
      if (this.listaMembriSopralluogo && this.listaMembriSopralluogo.length > 0) {
        const search = this.listaMembriSopralluogo.filter((e) => e.ruoloAppuntamentoSoggettoId === newObj.ruoloAppuntamentoSoggettoId);
        if (search && search.length > 0) {
          newObj.cognome = search[0].soggetto.cognome;
          newObj.nome = search[0].soggetto.nome;
          newObj.soggettoId = search[0].soggettoId;
          newObj.appuntamentoSoggettoId = search[0].appuntamentoSoggettoId
        }
      }
      this.objListMembroSopralluogo.push(newObj);
    });

    this.isSpinEmitter = false;
  }

  async getListaRuoliMembroSopralluogo() {
    await lastValueFrom(this.client.getListaRuoliMembroSopralluogo())
      .then(data => {
        if (data !== null) {
          this.listaRuoliMembriSopralluogo = data;
        }
      })
      .catch(
        error => {
          this.isSpinEmitter = false;
          this.errorHandlerService.handleError(error, 'getListaMembri');
        }
      );
  }
  async getListaMembriPerSopralluogo(appuntamentoId: number) {
    let params = {
      appuntamento_id: appuntamentoId
    }
    await lastValueFrom(this.client.getListaMembriPerSopralluogo(params))
      .then(data => {
        if (data !== null) {
          this.listaMembriSopralluogo = data;
        }
      })
      .catch(
        error => {
          this.isSpinEmitter = false;
          this.errorHandlerService.handleError(error, 'getListaMembriPerSopralluogo');
        }
      );
  }

  async showEliminaPartecipante(partecipante: ObjListMembroSopralluogo) {
    const dialogRef = this.dialog.open(DialogEliminaMembroComponent, {
      data: { partecipante: partecipante }
    });  // Apro la modal
    const result = await lastValueFrom(dialogRef.afterClosed());
    if (result) {
      this.isSpinEmitter = true;
      this.objListMembroSopralluogo = [];
      await this.getListaRuoliMembroSopralluogo();
      if (this.sopralluogo && this.sopralluogo.id) {
        await this.getListaMembriPerSopralluogo(this.sopralluogo.id);
      }
      this.listaRuoliMembriSopralluogo.forEach((e) => {
        let newObj: ObjListMembroSopralluogo = {
          descrizione: e.ruoloAppuntamentoSoggettoDesc,
          cognome: null,
          nome: null,
          ruoloAppuntamentoSoggettoId: e.ruoloAppuntamentoSoggettoId,
          soggettoId: null,
          appuntamentoSoggettoId: null,
        }
        if (this.listaMembriSopralluogo && this.listaMembriSopralluogo.length > 0) {
          const search = this.listaMembriSopralluogo.filter((e) => e.ruoloAppuntamentoSoggettoId === newObj.ruoloAppuntamentoSoggettoId);
          if (search && search.length > 0) {
            newObj.cognome = search[0].soggetto.cognome;
            newObj.nome = search[0].soggetto.nome;
            newObj.soggettoId = search[0].soggettoId;
            newObj.appuntamentoSoggettoId = search[0].appuntamentoSoggettoId;
          }
        }
        this.objListMembroSopralluogo.push(newObj);
      });
      this.isSpinEmitter = false;
    }
  }

  async showAddPartecipante(partecipante: ObjListMembroSopralluogo) {
    const dialogRef = this.dialog.open(DialogAggiungiPartecipanteComponent, {
      data: { partecipante: partecipante, sopralluogo: this.sopralluogo }
    });  // Apro la modal
    const result = await lastValueFrom(dialogRef.afterClosed());
    if (result) {
      this.isSpinEmitter = true;
      this.objListMembroSopralluogo = [];
      await this.getListaRuoliMembroSopralluogo();
      if (this.sopralluogo && this.sopralluogo.id) {
        await this.getListaMembriPerSopralluogo(this.sopralluogo.id);
      }
      this.listaRuoliMembriSopralluogo.forEach((e) => {
        let newObj: ObjListMembroSopralluogo = {
          descrizione: e.ruoloAppuntamentoSoggettoDesc,
          cognome: null,
          nome: null,
          ruoloAppuntamentoSoggettoId: e.ruoloAppuntamentoSoggettoId,
          soggettoId: null,
          appuntamentoSoggettoId: null,
        }
        if (this.listaMembriSopralluogo && this.listaMembriSopralluogo.length > 0) {
          const search = this.listaMembriSopralluogo.filter((e) => e.ruoloAppuntamentoSoggettoId === newObj.ruoloAppuntamentoSoggettoId);
          if (search && search.length > 0) {
            newObj.cognome = search[0].soggetto.cognome;
            newObj.nome = search[0].soggetto.nome;
            newObj.soggettoId = search[0].soggettoId;
            newObj.appuntamentoSoggettoId = search[0].appuntamentoSoggettoId;
          }
        }
        this.objListMembroSopralluogo.push(newObj);
      });
      this.isSpinEmitter = false;
    }
  }

}
