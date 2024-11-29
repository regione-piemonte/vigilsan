/*
 * Copyright Regione Piemonte - 2024
 * SPDX-License-Identifier: EUPL-1.2
 */

import {
  HttpEvent,
  HttpHandler,
  HttpHeaders,
  HttpInterceptor,
  HttpRequest,
} from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Client } from 'src/app/Client';
import * as uuid from 'uuid';

@Injectable({ providedIn: 'root' })
export class RequestInterceptor implements HttpInterceptor {

  constructor(private client: Client) { }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const authReq = req.clone(this.client.devMode ? {
      headers: new HttpHeaders({
        'X-Request-Id': uuid.v4(),
        'Shib-Iride-IdentitaDigitale': 'VNTPSQ97E66A971K/PASQUA/VENTIQUATTRO/ACTALIS_EU/20240220100403/16/OCt5xhFeWbPiFx2MGFcBLQ==',
        'Shib-Identita-CodiceFiscale': 'VNTPSQ97E66A971K',
        'X-Codice-Servizio': 'VIGILSAN',
        'X-Forwarded-For': '127.0.0.1',
        'x-access-token':
          'eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJEZXR0YWdsaSBQcm9maWxvIiwiY2YiOiJWTlRQU1E5N0U2NkE5NzFLIiwibmJmIjoxNzI2MjEwMjEyLCJpcCI6IjEyNy4wLjAuMSIsImlzcyI6IlByb2ZpbG8iLCJleHAiOjE3MjYyMjgyMTIsInByb2ZpbG8iOiJ7XCJzZXNzaW9uZUlkXCI6NDQ1MyxcInNvZ2dldHRvSWRcIjoxOTQsXCJydW9sb0lkXCI6MSxcImVudGVJZFwiOjEyLFwicHJvZmlsb0lkXCI6NSxcImFwcGxpY2F0aXZvSWRcIjoxLFwicHJvZmlsaVwiOlt7XCJwcm9maWxvSWRcIjo1fSx7XCJwcm9maWxvSWRcIjoyfV0sXCJhdXRoXCI6W3tcImZcIjpcInZpZ2lsX2hvbVwiLFwicFwiOlwiUlwiLFwiYVwiOltdfSx7XCJmXCI6XCJ2aWdpbF9kb2NcIixcInBcIjpcIlJcIixcImFcIjpbXX0se1wiZlwiOlwidmlnaWxfcHJhXCIsXCJwXCI6XCJXXCIsXCJhXCI6W3tcImZcIjpcInNjYWRcIixcInBcIjpcIlJcIixcImFcIjpbXX0se1wiZlwiOlwiY29tbVwiLFwicFwiOlwiUlwiLFwiYVwiOltdfSx7XCJmXCI6XCJmaWx0ZXJcIixcInBcIjpcIlJcIixcImFcIjpbXX1dfSx7XCJmXCI6XCJ2aWdpbF90c3RcIixcInBcIjpcIlJcIixcImFcIjpbXX1dfSIsImlhdCI6MTcyNjIxMDIxMiwianRpIjoiMjMyNDAxYzUtNzJhZi00ZDMwLWIwYjgtZDc0ZGM2YjU3N2FkIn0._bKZPNBuNGdQIDeOWnGpQzbfN0OZNkBVCmJnW0-uK4Y'
      }),
    } : this.client.devModeLogin ? {
      headers: new HttpHeaders({
        'X-Request-Id': uuid.v4(),
        'Shib-Iride-IdentitaDigitale': 'VNTPSQ97E66A971K/PASQUA/VENTIQUATTRO/ACTALIS_EU/20240220100403/16/OCt5xhFeWbPiFx2MGFcBLQ==',
        'Shib-Identita-CodiceFiscale': 'VNTPSQ97E66A971K',
        'X-Codice-Servizio': 'VIGILSAN',
        'X-Forwarded-For': '127.0.0.1',
        'x-access-token': this.client.tokenApplication['x-access-token'],
      }),
    } : {
      headers: new HttpHeaders({
        'X-Request-Id': uuid.v4(),
        'X-Codice-Servizio': 'VIGILSAN',
        'x-access-token': this.client.tokenApplication['x-access-token'],
      }),
    });

    // console.log('Intercepted HTTP call', authReq);

    return next.handle(authReq);
  }
}
