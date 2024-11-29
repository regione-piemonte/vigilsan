/*
 * Copyright Regione Piemonte - 2024
 * SPDX-License-Identifier: EUPL-1.2
 */

import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { jwtDecode } from "jwt-decode";
import { lastValueFrom } from 'rxjs';
import { Client, Navigation } from '../Client';
import { HeaderComponent } from '../header/header.component';
import { ErrorHandlerService } from '../main-page/ErrorHandlerService';
import { LoginConstants } from './login-constants';

@Component({
  selector: 'app-login-page',
  templateUrl: './login-page.component.html',
  styleUrls: ['./login-page.component.css']
})
export class LoginPageComponent implements OnInit {

  isSpinEmitter: boolean = false;

  constructor(private client: Client, private header: HeaderComponent, private router: Router,
    private errorHandlerService: ErrorHandlerService) { }


  async ngOnInit() {
    this.isSpinEmitter = true;

    if (this.client.devMode) {
      // LoginConstants.setPasquaVentiquattroResidenzaSantaRita(this.client);
      LoginConstants.setPietraVentitreAslCittaTorino(this.client);

      this.header.makeLinkMenu();
      this.header.ngOnInit();
      this.isSpinEmitter = false;
      this.router.navigate([Navigation.VETRINA], { skipLocationChange: true });
    } else {
      if (this.client.token && this.client.token !== '') {
        /**
         * Getting user data
         */
        let params = {
          token: this.client.token
        }
        const loginObservable = this.client.login(params);
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
              this.header.makeLinkMenu();
              this.header.ngOnInit();
              // if (this.client.loggedUser.profili?.length === 1) {
              this.isSpinEmitter = false;
              this.router.navigate([Navigation.VETRINA], { skipLocationChange: true });
              // }
            }
          })
          .catch(
            error => {
              this.isSpinEmitter = false;
              this.errorHandlerService.handleError(error, 'login');
              this.router.navigate([Navigation.LOGIN_ERROR_PAGE], { skipLocationChange: true });
            }
          );
      } else {
        this.router.navigate([Navigation.LOGIN_ERROR_PAGE], { skipLocationChange: true });
      }
    }
    this.isSpinEmitter = false;
  }

  getDecodedAccessToken(token: string): any {
    try {
      return jwtDecode(token);
    } catch (Error) {
      return null;
    }
  }
}
