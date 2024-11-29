/*
 * Copyright Regione Piemonte - 2024
 * SPDX-License-Identifier: EUPL-1.2
 */

import { AfterViewInit, Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { lastValueFrom } from 'rxjs';
import { Client, ModuloConfigGruppoCod, Navigation } from 'src/app/Client';
import { DocumentazioneCompilabile } from 'src/app/DTO/DocumentazioneCompilabiliDTO';
import { DocumentazioneCompilata } from 'src/app/DTO/DocumentazioniCompilateDTO';
import { CambioStrutturaComponent } from '../cambio-struttura/cambio-struttura.component';
import { ErrorHandlerService } from '../ErrorHandlerService';
import { ModuloCommon } from '../ModuloCommon';
import { DialogAddDocumentazioneComponent } from './dialog-add-documentazione/dialog-add-documentazione.component';
import { DialogDettaglioDocumentazioneComponent } from './dialog-dettaglio-documentazione/dialog-dettaglio-documentazione.component';
import { DialogVerificaComponent } from './dialog-verifica/dialog-verifica.component';

@Component({
  selector: 'app-documentazione',
  templateUrl: './documentazione.component.html',
  styleUrls: ['./documentazione.component.css', '../../../styles.css']
})
export class DocumentazioneComponent implements OnInit, AfterViewInit {

  isSpinEmitter: boolean = false;
  isSpinEmitterSelection: boolean = false;
  showModulo: boolean = false;

  highlightedCells: { [key: string]: boolean } = {};

  constructor(public client: Client, public dialog: MatDialog,
    private moduloCommon: ModuloCommon, public router: Router, private errorHandlerService: ErrorHandlerService) { }

  ngAfterViewInit(): void {
    setTimeout(() => {
      this.client.textRoute = 'Gestione documentale';
    }, 0);
  }


  async ngOnInit() {
    let result: boolean = false;
    if (this.client.isEnte && !this.client.isStruttura) {
      const dialogRef = this.dialog.open(CambioStrutturaComponent, {
        minWidth: '80vh',
        maxHeight: '90vh',
        panelClass: 'custom-modal',
        // data: { rilevazione: rilevazione, tab: tab }
      });  // Apro la modal
      result = await lastValueFrom(dialogRef.afterClosed());
    }
    if (this.client.isStruttura || result) {
      this.client.isStruttura = true;
      this.client.moduloToshow = null;
      this.client.moduloCompilatoToshow = null;

      if (this.router.url === '/main-page/rilevazioni') {
        this.client.resDoc = ModuloConfigGruppoCod.INF;
      } else {
        this.client.resDoc = ModuloConfigGruppoCod.DOC;
      }

      this.isSpinEmitter = true;
      this.showModulo = false;

      await this.getDocumentazioniLista(); // OTTENGO LE DOCUMENTAZIONI PER LA SELECT INIZIALE
      await this.getDocumentazioniCompilate();
      await this.getDocumentazioniCompilabili();

      if (this.client.listaDocumentazioni) {
        this.logicaFiltroTipologiaDocumentazione();
      }

      this.isSpinEmitter = false;
    } else {
      this.client.selectedTab = 'main-page/vetrina';
      this.router.navigate([Navigation.VETRINA], { skipLocationChange: true });
    }
  }

  logicaFiltroTipologiaDocumentazione() {
    if (this.client.listaDocumentazioni) {
      const moduloCodAmmessi = this.client.listaDocumentazioni.map((e) => e.moduloConfigCod);
      if (this.client.listaDocumentazioniCompilate) {
        this.client.listaDocumentazioniCompilate = this.client.listaDocumentazioniCompilate.filter((e) => moduloCodAmmessi.includes(e.modulo.moduloCompilatoRidotto.moduloCod));
      }
      if (this.client.listaDocumentazioniCompilabili) {
        this.client.listaDocumentazioniCompilabili = this.client.listaDocumentazioniCompilabili.filter((e) => moduloCodAmmessi.includes(e.moduloConfigCod));
      }
    }
  }

  // ---- GET API
  async getDocumentazioniCompilabili() {
    const documentazioniAggiungiObservable = this.client.getDocumentazioniCompilabili();
    await lastValueFrom(documentazioniAggiungiObservable)
      .then(data => {
        if (data !== null) {
          this.client.listaDocumentazioniCompilabili = data;
        }
      })
      .catch(
        error => {
          this.isSpinEmitter = false;
          this.errorHandlerService.handleError(error, 'getDocumentazioniAggiungi');
        }
      );
  }

  async getDocumentazioniLista() {
    const params = {
      modulo_config_gruppo_cod: this.client.resDoc
    }
    const documentazioniPerOpzioneObservable = this.client.getListaDocumentazioni(params);
    await lastValueFrom(documentazioniPerOpzioneObservable)
      .then(data => {
        if (data !== null) {
          this.client.listaDocumentazioni = data;
        }
      })
      .catch(
        error => {
          this.isSpinEmitter = false;
          this.errorHandlerService.handleError(error, 'getListaDocumentazioni');
        }
      );
  }

  async getDocumentazioniCompilate() {
    const documentazioniPerSceltaObservable = this.client.getDocumentazioniCompilate();
    await lastValueFrom(documentazioniPerSceltaObservable)
      .then(data => {
        if (data !== null) {
          this.client.listaDocumentazioniCompilate = data; // Lista completa delle documentazioni compilata per modulo_cod
        }
      })
      .catch(
        error => {
          this.client.moduloCompilatoToshow = null;
          this.client.moduloToshow = null;
          this.isSpinEmitterSelection = false;
          this.errorHandlerService.handleError(error, 'getDocumentazionePerListaDocumentazioni');
        }
      );
  }

  // ------------------------------------------------------------------------------

  async showAddDocumentazione(doc: DocumentazioneCompilabile) {
    this.moduloCommon.setDataModify(); // Metto il modulo in modifica
    if (doc.modulo.moduloId) {
      const moduloId: number = doc.modulo.moduloId;
      this.client.moduloToshow = moduloId;
      const dialogRef = this.dialog.open(DialogAddDocumentazioneComponent, {
        width: '1200px',
        maxHeight: '90vh',
        panelClass: 'custom-modal',
        data: { documento: doc, title: doc.moduloConfigDesc }
      });  // Apro la modal

      let result: string = '';
      await lastValueFrom(dialogRef.afterClosed())
        .then((response) => result = response)
      if (result === 'updated') {
        this.isSpinEmitterSelection = true;

        await this.getDocumentazioniCompilabili(); // OTTENGO LE DOCUMENTAZIONI DA AGGIUNGERE NEI TASTI AGGIUNGI
        await this.getDocumentazioniCompilate(); // Riottengo la lista delle documentazioni per modulo_cod
        this.logicaFiltroTipologiaDocumentazione();

        this.client.moduloToshow = null;
        this.client.moduloCompilatoToshow = null;
        this.showModulo = true;
        this.isSpinEmitterSelection = false;
        this.errorHandlerService.displaySuccessMessage('Documentazione salvata con successo');
      } else {
        this.client.moduloToshow = null;
        this.client.moduloCompilatoToshow = null;
        this.isSpinEmitterSelection = false;
      }
    }
  }

  async showDettaglioDocumentazione(doc: DocumentazioneCompilata) {
    if (this.client.isErrorLogin) {
      return;
    }
    if (doc.moduloCompilatoId) {
      const moduloId: number = doc.moduloCompilatoId;
      this.client.moduloCompilatoToshow = moduloId;
      const dialogRef = this.dialog.open(DialogDettaglioDocumentazioneComponent, {
        width: '1300px',
        maxHeight: '90vh',
        panelClass: 'custom-modal',
        data: { documento: doc, title: doc.modulo.moduloDesc }
      });  // Apro la modal
      let result: string = '';
      await lastValueFrom(dialogRef.afterClosed())
        .then((response) => result = response)
      if (result === 'updated') {
        this.isSpinEmitterSelection = true;

        await this.getDocumentazioniCompilate(); // Riottengo la lista delle documentazioni per modulo_cod
        this.logicaFiltroTipologiaDocumentazione();

        this.client.moduloToshow = null;
        this.client.moduloCompilatoToshow = null;
        this.showModulo = true;
        this.isSpinEmitterSelection = false;
        this.errorHandlerService.displaySuccessMessage('Documentazione salvata con successo');
      } else {
        this.client.moduloToshow = null;
        this.client.moduloCompilatoToshow = null;
        this.isSpinEmitterSelection = false;
      }
    }
  }

  async showVerifica(doc: DocumentazioneCompilata) {
    if (this.client.isErrorLogin) {
      return;
    }
    const dialogRef = this.dialog.open(DialogVerificaComponent, {
      width: '600px',
      // maxHeight: '90vh',
      // panelClass: 'custom-modal',
      data: { documento: doc }
    });  // Apro la modal
    let result: boolean = false;
    await lastValueFrom(dialogRef.afterClosed())
      .then((response) => result = response)
    if (result) {
      this.isSpinEmitterSelection = true;

      await this.getDocumentazioniCompilate(); // Riottengo la lista delle documentazioni per modulo_cod
      this.logicaFiltroTipologiaDocumentazione();

      this.client.moduloToshow = null;
      this.client.moduloCompilatoToshow = null;
      this.showModulo = true;
      this.isSpinEmitterSelection = false;
      this.errorHandlerService.displaySuccessMessage('Verifica salvata con successo');
    } else {
      this.client.moduloToshow = null;
      this.client.moduloCompilatoToshow = null;
      this.isSpinEmitterSelection = false;
    }
  }

  // Trigger per mettere in modulo in modifica o sola visualizzazione
  modificaDocumentazione() {
    this.isSpinEmitterSelection = true;
    this.client.disabilitaCampi = !this.client.disabilitaCampi;
    this.moduloCommon.disabilitaCampi(!this.client.disabilitaCampi);
    this.isSpinEmitterSelection = false;
  }

  getDescVociCompilate(doc: DocumentazioneCompilata): string[] {
    let descrizioni: string[] = [];
    if (doc.modulo.moduloCompilatoRidotto.voci && doc.modulo.moduloCompilatoRidotto.voci.length > 0) {
      doc.modulo.moduloCompilatoRidotto.voci.forEach((e) => {
        if (e.lista && e.lista.valori && e.lista.valori.length > 0) {
          e.lista.valori.forEach((i) => {
            if (i.valore && descrizioni.length < 3) {
              descrizioni.push(i.moduloListaValoreDesc);
            }
          });
        }
        if (e.valore !== '' && e.valore !== null && descrizioni.length < 3) {
          descrizioni.push(e.valore);
        }
      });
    }
    if (doc.modulo.moduloCompilatoRidotto.sezioni && doc.modulo.moduloCompilatoRidotto.sezioni.length > 0) {
      if (doc.modulo.moduloCompilatoRidotto.sezioni[0].voci && doc.modulo.moduloCompilatoRidotto.sezioni[0].voci.length > 0) {
        doc.modulo.moduloCompilatoRidotto.sezioni[0].voci.forEach((e) => {
          if (e.lista && e.lista.valori && e.lista.valori.length > 0) {
            e.lista.valori.forEach((i) => {
              if (i.valore && descrizioni.length < 3) {
                descrizioni.push(i.moduloListaValoreDesc);
              }
            });
          }
          if (e.valore !== '' && e.valore !== null && descrizioni.length < 3) {
            descrizioni.push(e.valore);
          }
        });
      }
    }
    return descrizioni;
  }

  // Highlighted verifica td
  onMouseEnter(i: number) {
    this.highlightedCells[`${i}`] = true;
  }

  onMouseLeave(i: number) {
    this.highlightedCells[`${i}`] = false;
  }

  isHighlighted(i: number): boolean {
    return !!this.highlightedCells[`${i}`];
  }

  async inviaNotifica() {
    if (this.client.loggedUser && this.client.loggedUser.struttura && this.client.loggedUser.struttura.strutturaId) {
      this.client.isErrorLogin = true;
      let payload = {
        strutturaId: this.client.loggedUser.struttura.strutturaId
      }
      await lastValueFrom(this.client.postInviaNotificaEmail(payload))
        .then(data => {
          if (data) {
            this.client.isErrorLogin = false;
            this.errorHandlerService.displaySuccessMessage('Notifica inviata con successo');
          }
        })
        .catch(
          error => {
            this.client.isErrorLogin = false;
            this.errorHandlerService.handleError(error, 'postInviaNotificaEmail');
          }
        );
      } else {
        this.client.isErrorLogin = false;
        this.errorHandlerService.displayErrorMessage('[INTERNAL-ERROR] - Errore invio notifica causato da loggedUser');
    }
  }


}
