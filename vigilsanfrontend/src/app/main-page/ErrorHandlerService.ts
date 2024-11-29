/*
 * Copyright Regione Piemonte - 2024
 * SPDX-License-Identifier: EUPL-1.2
 */

import { Injectable } from "@angular/core";
import { ToastrService } from "ngx-toastr";

@Injectable({
  providedIn: 'root'
})
export class ErrorHandlerService {

  constructor(private toastr: ToastrService) { }

  handleError(error: any, serviceName: string): void {
    console.error('ERROR:', error);
    let errorMessage: string = '';
    if (error.error) {
      errorMessage = error.error ? `[${serviceName}][${error.error.code}], ${error.error.title}` : `[${serviceName}], Errore sconosciuto`;
      console.log(new Date(), errorMessage);
      this.toastr.error(errorMessage);
      return;
    }

    errorMessage = `[${serviceName}][${error.status ? error.status : 'status sconosciuto'}], [${error.code ? error.code : 'codice sconosciuto'}], ${error.title ? error.title : ''}`;
    console.log(new Date(), errorMessage);
    this.toastr.error(errorMessage);
  }

  displayErrorMessage(message: string) {
    if (!message) { return; }
    this.toastr.error(message);
  }
  displaySuccessMessage(message: string) {
    if (!message) { return; }
    this.toastr.success(message);
  }
  displayInfoMessageError(message: string) {
    if (!message) { return; }
    this.toastr.warning(message);
  }

}
