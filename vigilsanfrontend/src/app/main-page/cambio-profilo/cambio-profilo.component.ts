/*
 * Copyright Regione Piemonte - 2024
 * SPDX-License-Identifier: EUPL-1.2
 */

import { Component, OnInit } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';
import { lastValueFrom } from 'rxjs';
import { Client } from 'src/app/Client';
import { ProfiliEntity } from 'src/app/DTO/User';
import { ErrorHandlerService } from '../ErrorHandlerService';
import { jwtDecode } from "jwt-decode";


@Component({
  selector: 'app-cambio-profilo',
  templateUrl: './cambio-profilo.component.html',
  styleUrls: ['./cambio-profilo.component.css']
})
export class CambioProfiloComponent implements OnInit {

  columns : string[] = ['Codice', 'Descrizione'];
  isSpinEmitter: boolean = false;

  constructor(public client: Client, public dialogRef: MatDialogRef<CambioProfiloComponent>, private errorHandlerService: ErrorHandlerService) { }

  ngOnInit(): void {
  }

  async cambioProfilo(profilo: ProfiliEntity) {
    this.isSpinEmitter = true;
    let params = {
      // struttura_id: this.client.loggedUser.struttura?.strutturaId,
      // struttura_id: null,
      profiloId: profilo.profiloId
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

}
