/*
 * Copyright Regione Piemonte - 2024
 * SPDX-License-Identifier: EUPL-1.2
 */

import { AfterViewInit, Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { lastValueFrom } from 'rxjs';
import { Client, Navigation } from 'src/app/Client';
import { Ospite } from 'src/app/DTO/OspiteDTO';
import { ErrorHandlerService } from '../../ErrorHandlerService';

@Component({
  selector: 'app-ospite-new',
  templateUrl: './ospite-new.component.html',
  styleUrls: ['./ospite-new.component.css']
})
export class OspiteNewComponent implements OnInit, AfterViewInit {

  active: number = 0;
  isInCaricamento: boolean = false;
  cf: string | null = null;

  constructor(public client: Client, private router: Router, private errorHandlerService: ErrorHandlerService) { }

  ngAfterViewInit(): void {
    setTimeout(() => {
      this.client.textRoute = 'Nuovo Ospite';
    }, 0);
  }

  ngOnInit(): void {
  }

  goBack() {
    this.router.navigate([Navigation.MOVIMENTI_OSPITE], { skipLocationChange: true });
  }

  removeSpaces() {
    this.cf = this.cf ? this.cf.replace(/\s+/g, '') : this.cf;
  }

  async cerca() {
    this.isInCaricamento = true;
    this.client.isErrorLogin = true;
    const payload = {
      codiceFiscale: this.cf
    }
    await lastValueFrom(this.client.searchSoggetto(payload))
      .then(data => {
        this.client.isErrorLogin = false;
        this.isInCaricamento = false;
        this.errorHandlerService.displaySuccessMessage('Soggetto trovato.');
        let ospite: Ospite = data;
        this.client.ospiteSelezionato = ospite;
        this.router.navigate([Navigation.DETTAGLIO_OSPITE], { skipLocationChange: true });
      })
      .catch(
        error => {
          this.client.isErrorLogin = false;
          this.isInCaricamento = false;
          this.errorHandlerService.handleError(error, 'searchSoggetto');
        }
      );
  }


  // eraseData() {
  //   this.client.dataNascitaOspite = null;
  // }
}
