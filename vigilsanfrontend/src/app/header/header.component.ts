/*
 * Copyright Regione Piemonte - 2024
 * SPDX-License-Identifier: EUPL-1.2
 */

import { AfterViewInit, Component, Injectable, OnInit, ViewChild } from '@angular/core';
import { MatSidenav } from '@angular/material/sidenav';
import { NavigationExtras, Router } from '@angular/router';
import { lastValueFrom } from 'rxjs';
import { Client } from '../Client';
import { Funzione } from '../DTO/User';
import { ErrorHandlerService } from '../main-page/ErrorHandlerService';

export type MenuItem = {
  icon: string;
  label: string;
  labelTwo?: string;
  route?: string;
  href?: string;
  text?: string;
}
const LOGIN = 'login';

enum AuthFirstLevel {
  TEST = 'vigil_tst',
  HOME = 'vigil_hom',
  RILEVAZIONI = 'vigil_ril',
  ARCHIVIO_DOCUMENTALE = 'vigil_doc',
  OSPITI = 'vigil_osp',
  COMMISSIONE = 'vigil_com',
  PRATICHE_VIGILANZA = 'vigil_pra',
  ADESIONE_BUONO_RESIDENZIALITA = 'vigil_bra',
  RENDICONTAZIONE_BUONO_RESIDENZIALITA = 'vigil_brr',
  TAMPONI_RAPIDI = 'vigil_tmp',
  QUESTIONARI = 'vigil_qst',
}

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
@Injectable({
  providedIn: 'root',
})
export class HeaderComponent implements OnInit, AfterViewInit {

  @ViewChild('sidenav') sidenav!: MatSidenav;

  nome: string = '';
  cognome: string = '';
  struttura: string = '';
  ruolo: string = '';

  constructor(public client: Client, public router: Router, private errorHandlerService: ErrorHandlerService) { }

  ngAfterViewInit(): void {
    if (!this.client.devMode && this.client.tokenApplication['x-access-token'] !== '') {
      this.makeLinkMenu();
    }
  }

  async ngOnInit() {
    this.client.selectedTab = 'main-page/vetrina';
    if (this.client.loggedUser.soggetto) {
      this.nome = this.client.loggedUser.soggetto?.nome || '';
      this.cognome = this.client.loggedUser.soggetto?.cognome || '';
    }
    if (this.client.loggedUser.struttura) {
      this.struttura = this.client.loggedUser.struttura?.strutturaCod + ' ' + this.client.loggedUser.struttura?.strutturaDesc;
      this.client.isStruttura = true;
    } else {
      this.client.isStruttura = false;
    }
    if (this.client.loggedUser.ente) {
      this.struttura = this.client.loggedUser.ente?.enteDesc ? this.client.loggedUser.ente?.enteDesc : '';
      this.client.isEnte = true;
    } else {
      this.client.isEnte = false;
    }
    if (this.client.loggedUser.ruolo) {
      this.ruolo = this.client.loggedUser.ruolo?.ruoloDesc || '';
    }
  }

  async goLogout() {
    const logoutObservable = this.client.logout();
    await lastValueFrom(logoutObservable)
      .then(data => {
        const link = data.body.redirectUrl;
        if (link !== null) {
          window.location.replace(link);
        }
      })
      .catch(
        error => {
          this.errorHandlerService.handleError(error, 'logout');
        }
      );
  }

  navigate(itemHref: string): void {
    this.client.selectedTab = itemHref;
    if (itemHref === 'main-page/pratiche-vigilanza/scadenziario') {
      this.client.azzeraFiltriRicercaPraticheVigilanza();
    }
    const navigationExtras: NavigationExtras = { skipLocationChange: true };
    this.router.navigate([itemHref], navigationExtras);
  }

  makeLinkMenu() {
    if (this.client.loggedUser.funzioni) {
      this.client.menuItems = [];
      this.client.menuItems.push({
        icon: 'bi bi-house vigilsan-icon-style',
        label: 'Home',
        route: 'main-page/vetrina',
        href: ''
      });
      let search = this.client.loggedUser.funzioni.find((e: Funzione) => e.funzione === AuthFirstLevel.HOME);
      if (search && search.permesso) {
        this.client.menuItems.push({
          icon: 'bi bi-person-rolodex vigilsan-icon-style',
          label: 'Profilo',
          route: 'main-page/profilo',
          href: '',
          text: `Sezione che visualizza i dati anagrafici e il profilo applicativo dellâutente che ha effettuato lâaccesso;
          consente inoltre di accedere ai dati anagrafici censiti in ARPE â Archivio regionale dei punti di erogazione,
          della struttura per la quale lâutente intende operare.`
        });
      }
      search = this.client.loggedUser.funzioni.find((e: Funzione) => e.funzione === AuthFirstLevel.ARCHIVIO_DOCUMENTALE);
      if (search && search.permesso) {
        this.client.menuItems.push({
          icon: 'bi bi-folder vigilsan-icon-style',
          label: 'Gestione documentale',
          route: 'main-page/documentazione',
          href: '',
          text: `Sezione che consente ai referenti delle strutture residenziali e semi-residenziali di raccogliere la documentazione relativa alle strutture di competenza per consentire alla Commissione di Vigilanza di effettuare le verifiche durante le visite ispettive.`,
        });
      }
      search = this.client.loggedUser.funzioni.find((e: Funzione) => e.funzione === AuthFirstLevel.RILEVAZIONI);
      if (search && search.permesso) {
        this.client.menuItems.push({
          icon: 'bi bi-bar-chart-line vigilsan-icon-style',
          label: 'Rilevazioni',
          route: 'main-page/rilevazioni',
          href: '',
          text: `Sezione che consente agli operatori delle strutture residenziali e semi-residenziali di procedere alla registrazione delle informazioni di base e delle rilevazioni giornaliere e/o settimanali dei posti occupati e a disposizione per i propri ospiti.`,
        });
      }
      search = this.client.loggedUser.funzioni.find((e: Funzione) => e.funzione === AuthFirstLevel.QUESTIONARI);
      if (search && search.permesso) {
        this.client.menuItems.push({
          icon: 'bi bi-card-list vigilsan-icon-style',
          label: 'Questionari',
          route: 'main-page/questionario',
          href: '',
          text: `Sezione che consente al settore regionale competente di procedere periodicamente alla raccolta
                  di informazioni dalle strutture residenziali e semi-residenziali, per meglio indirizzare le attivitÃ 
                  di programmazione degli interventi nellâarea socio-sanitaria.`,
        });
      }
      search = this.client.loggedUser.funzioni.find((e: Funzione) => e.funzione === AuthFirstLevel.OSPITI);
      if (search && search.permesso) {
        this.client.menuItems.push({
          icon: 'bi bi-people vigilsan-icon-style',
          label: 'Ospiti',
          route: 'main-page/ospiti-movimenti',
          href: '',
          text: `Sezione che consente alle strutture residenziali di registrare i dati anagrafici e i movimenti dei propri ospiti. `,
        });
      }
      search = this.client.loggedUser.funzioni.find((e: Funzione) => e.funzione === AuthFirstLevel.ADESIONE_BUONO_RESIDENZIALITA);
      if (search && search.permesso) {
        this.client.menuItems.push({
          icon: 'bi bi-ticket-detailed vigilsan-icon-style',
          label: 'Adesione',
          labelTwo: 'buono ResidenzialitÃ ',
          route: '',
          href: this.client.GESTRUTTURE_URL + 'gestione_rsa/residenze',
          text: `Sezione che consente a strutture residenziali a carattere socio-sanitario e socio-assistenziale di persone non autosufficienti di registrare la propria adesione al bando relativo il buono residenzialitÃ  per la copertura di parte delle spese sostenute dalle famiglie per l'ospitalitÃ  in regime privatistico.`,
        });
      }
      search = this.client.loggedUser.funzioni.find((e: Funzione) => e.funzione === AuthFirstLevel.RENDICONTAZIONE_BUONO_RESIDENZIALITA);
      if (search && search.permesso) {
        this.client.menuItems.push({
          icon: 'bi bi-ticket-perforated vigilsan-icon-style',
          label: 'Rendicontazione',
          labelTwo: 'buono ResidenzialitÃ ',
          route: '',
          href: this.client.GESTRUTTURE_URL + 'gestione_rsa/buono',
          text: `Sezione che consente ad una struttura residenziale a carattere socio-sanitario e socio-assistenziale di procedere al caricamento delle rendicontazioni attestanti lâeffettivo utilizzo del buono residenzialitÃ  per i propri ospiti destinatari della misura.`,
        });
      }
      search = this.client.loggedUser.funzioni.find((e: Funzione) => e.funzione === AuthFirstLevel.TAMPONI_RAPIDI);
      if (search && search.permesso) {
        this.client.menuItems.push({
          icon: 'bi bi-virus2 vigilsan-icon-style',
          label: 'Tamponi rapidi',
          route: '',
          href: this.client.GESTRUTTURE_URL + 'strutture',
          text: `Sezione che consente agli operatori delle strutture residenziali di procedere al caricamento degli esiti dei test rapidi antigenici effettuati ai propri ospiti per la rilevazione di infezione da SARS-CoV-2.`,
        });
      }
      search = this.client.loggedUser.funzioni.find((e: Funzione) => e.funzione === AuthFirstLevel.COMMISSIONE);
      if (search && search.permesso) {
        this.client.menuItems.push({
          icon: 'bi bi-people vigilsan-icon-style',
          label: 'Commissione vigilanza',
          route: 'main-page/commissione',
          href: '',
          text: `Sezione rivolta ai referenti delle ASL per procedere alla composizione delle commissioni di vigilanza impegnate nelle attivitÃ  di ispezione in loco e da remoto delle strutture residenziali e semi-residenziali di competenza.`,
        });
      }
      search = this.client.loggedUser.funzioni.find((e: Funzione) => e.funzione === AuthFirstLevel.PRATICHE_VIGILANZA);
      if (search && search.permesso) {
        this.client.menuItems.push({
          icon: 'bi bi-filter-square vigilsan-icon-style',
          label: 'Pratiche vigilanza',
          route: 'main-page/pratiche-vigilanza/scadenziario',
          href: '',
          text: `Sezione rivolta ai referenti delle commissioni di vigilanza delle ASL per pianificare i sopralluoghi presso le strutture e gestirne gli esiti condividendo i verbali con i referenti delle strutture e gestendo eventuali feedback.`,
        });
      }

      // Aggiungo test in ultimo
      search = this.client.loggedUser.funzioni.find((e: Funzione) => e.funzione === AuthFirstLevel.TEST);
      if (search && search.permesso) {
        this.client.menuItems.push({
          icon: 'bi bi-code vigilsan-icon-style',
          label: 'Test',

          route: 'main-page/test',
          href: ''
        });
      }
    }
  }
}
