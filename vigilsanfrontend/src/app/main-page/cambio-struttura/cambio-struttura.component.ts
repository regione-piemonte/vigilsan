/*
 * Copyright Regione Piemonte - 2024
 * SPDX-License-Identifier: EUPL-1.2
 */

import { Component, OnInit } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';
import { lastValueFrom } from 'rxjs';
import { Client } from 'src/app/Client';
import { ErrorHandlerService } from '../ErrorHandlerService';
import { StrutturaScelta } from 'src/app/DTO/StrutturaSceltaDTO';
import { jwtDecode } from "jwt-decode";
import { Router } from '@angular/router';
import { HeaderComponent } from 'src/app/header/header.component';

export interface MyParamsCambioStruttura {
  filter?: string;
  page_number?: number;
  row_per_page?: number;
  descending?: boolean;
  order_by?: string;
}

@Component({
  selector: 'app-cambio-struttura',
  templateUrl: './cambio-struttura.component.html',
  styleUrls: ['./cambio-struttura.component.css']
})
export class CambioStrutturaComponent implements OnInit {

  struttura: string = '';
  columns: string[] = ['Codice ARPE', 'Denominativo', 'CAP'];
  isSpinEmitter: boolean = false;

  listaSceltaStrutture: StrutturaScelta[] | null = null;

  page: number = 1;
  pageSize: number = 5;
  collectionSize: number = 0;
  descending: boolean = false;
  orderBy: string = 'struttura_desc';

  constructor(public dialogRef: MatDialogRef<CambioStrutturaComponent>, public client: Client,
    private errorHandlerService: ErrorHandlerService) { }

  async ngOnInit() {
    this.isSpinEmitter = true;
    await this.getListaStrtturaScelta();
    this.isSpinEmitter = false;
  }

  async getListaStrtturaScelta() {
    let params: MyParamsCambioStruttura = {};
    if (this.struttura && this.struttura.trim() !== '') {
      params.filter = this.struttura;
    }
    params.page_number = this.page;
    params.row_per_page = this.pageSize;
    params.descending = this.descending;
    params.order_by = this.orderBy;

    await lastValueFrom(this.client.getListaStrtturaScelta(params))
      .then(data => {
        const header = data.headers;
        const collectionSize = header.get('Rows-Number');
        if (collectionSize) {
          this.collectionSize = parseInt(collectionSize);
        }
        if (data.body) {
          this.listaSceltaStrutture = data.body;
        }
      })
      .catch(
        error => {
          this.isSpinEmitter = false;
          this.errorHandlerService.handleError(error, 'getListaNuovaPratica');
        }
      );
  }

  async cambiaStruttura(struttura: StrutturaScelta) {
    this.isSpinEmitter = true;
    let params = {
      struttura_id: struttura.strutturaId,
      profiloId: this.client.loggedUser.profiloId
    }
    const loginObservable = this.client.getCambioProfilo(params);
    await lastValueFrom(loginObservable)
      .then(data => {
        const headers = data.headers; // Access the headers
        const accessToken = headers.get('x-access-token');
        this.client.tokenApplication['x-access-token'] = accessToken !== null ? accessToken : '';
        if (accessToken !== null) {
          this.client.tokenJwt = this.getDecodedAccessToken(accessToken);
          this.client.tokenJwtProfili = JSON.parse(this.client.tokenJwt.profilo);
        }
        if (data.body !== null) {
          this.client.loggedUser = data.body; // Access the body (response data)
          this.isSpinEmitter = false;
          this.dialogRef.close('cambio');
          this.isSpinEmitter = false;
        }
      })
      .catch(
        error => {
          this.dialogRef.close(true);
          this.isSpinEmitter = false;
          this.errorHandlerService.handleError(error, 'login');
        }
      );

  }
  getDecodedAccessToken(token: string): any {
    try {
      return jwtDecode(token);
    } catch (Error) {
      return null;
    }
  }

  async cerca() {
    this.isSpinEmitter = true;
    await this.getListaStrtturaScelta();
    this.isSpinEmitter = false;
  }

  async cercaButton() {
    this.isSpinEmitter = true;
    this.page = 1;
    this.pageSize = 5;
    this.collectionSize = 0;
    this.descending = false;
    await this.getListaStrtturaScelta();
    this.isSpinEmitter = false;
  }
}
