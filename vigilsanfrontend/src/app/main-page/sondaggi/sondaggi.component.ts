/*
 * Copyright Regione Piemonte - 2024
 * SPDX-License-Identifier: EUPL-1.2
 */

import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { lastValueFrom } from 'rxjs';
import { Client, ModuloConfigGruppoCod, Navigation } from 'src/app/Client';
import { RilevazioniAggiungi } from 'src/app/DTO/RilevazioniAggiungiDTO';
import { RilevazioniPerTab } from 'src/app/DTO/RilevazioniPerTab';
import { RilevazioniTab } from 'src/app/DTO/RilevazioniTabModel';
import { ErrorHandlerService } from '../ErrorHandlerService';
import { ModuloCommon } from '../ModuloCommon';
import { DialogAggiungiComponent } from '../rilevazioni/dialog-aggiungi/dialog-aggiungi.component';
import { CambioStrutturaComponent } from '../cambio-struttura/cambio-struttura.component';
import { Router } from '@angular/router';

@Component({
  selector: 'app-sondaggi',
  templateUrl: './sondaggi.component.html',
  styleUrls: ['./sondaggi.component.css']
})
export class SondaggiComponent implements OnInit {

  isSpinEmitter: boolean = false;
  isLoadingData: boolean = false;
  showTooltipNoRilevazioniAggiungi: boolean = false;
  active: number = 0;
  questionariPerTab: any = null;
  questionarioSezioniToShow: any = null;
  questionarioVociToShow: any = null;

  constructor(public client: Client, private errorHandlerService: ErrorHandlerService, public moduloCommon: ModuloCommon,
    public dialog: MatDialog, public router: Router
  ) { }

  async ngOnInit() {
    let result: boolean = false;
    if (this.client.isEnte && !this.client.isStruttura) {
      const dialogRef = this.dialog.open(CambioStrutturaComponent, {
        width: '1200px',
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
      this.isSpinEmitter = true;
      await this.getRilevazioniTabInformation();
      if (this.client.listaTabSondaggi && this.client.listaTabSondaggi.length > 0) {
        this.active = 0;
        await this.onChangeTab(this.client.listaTabSondaggi[0].moduloConfigCod);
      }
      this.isSpinEmitter = false;
    } else {
      this.client.selectedTab = 'main-page/vetrina';
      this.router.navigate([Navigation.VETRINA], { skipLocationChange: true });
    }
  }


  async getRilevazioniTabInformation() {
    this.client.listaTabSondaggi = null;
    const params = {
      modulo_config_gruppo_cod: ModuloConfigGruppoCod.QST
    }
    const listaTabRilevazioniObservable = this.client.getListaTabRilevazioni(params);
    await lastValueFrom(listaTabRilevazioniObservable)
      .then(data => {
        if (data !== null) {
          this.client.listaTabSondaggi = data;
        }
      })
      .catch(
        error => {
          this.isSpinEmitter = false;
          this.errorHandlerService.handleError(error, 'getListaTabRilevazioni - sondaggi');
        }
      );
  }

  async getListaRilevazioniAggiungi(params: any) {
    const listaTabRilevazioniAggiungiObservable = this.client.getListaRilevazioniAggiungi(params);
    await lastValueFrom(listaTabRilevazioniAggiungiObservable)
      .then(data => {
        if (data !== null) {
          this.client.listaTabSondaggiAggiungi = data;
        }
      })
      .catch(
        error => {
          this.isSpinEmitter = false;
          this.errorHandlerService.handleError(error, 'getListaRilevazioniAggiungi - sondaggi');
        }
      );
  }

  // Getting Rilevazioni x tab information
  async getRilevazioniPerTab(index: number) {
    if (this.client.listaTabSondaggi !== null) {
      let lista: RilevazioniPerTab[] = [];
      let params = {
        modulo_config_cod: this.client.listaTabSondaggi[index].moduloConfigCod
      }
      const rilevazioniPerTabAggiungiObservable = this.client.getListaRilevazioniPerTab(params);
      await lastValueFrom(rilevazioniPerTabAggiungiObservable)
        .then(data => {
          if (data !== null) {
            lista = data;
            this.client.listeSondaggiPerTab[this.active] = lista;
          }
        })
        .catch(
          error => {
            this.isSpinEmitter = false;
            this.errorHandlerService.handleError(error, 'getListaRilevazioniPerTab - sondaggi');
          }
        );
    }
  }


  async onChangeTab(questionarioTipo: string) {
    if (!questionarioTipo || questionarioTipo === '') {
      this.errorHandlerService.displayErrorMessage('[FE - onChangeTab] - Errore interno');
      return;
    }

    this.isLoadingData = true;
    this.showTooltipNoRilevazioniAggiungi = false;
    let params = {
      modulo_config_cod: questionarioTipo
    }
    await this.getListaRilevazioniAggiungi(params); // Getting Questionari to add in AGGIUNGI button
    await this.getRilevazioniPerTab(this.active); // Getting Questionari compilate per tab
    // TABELLA RILEVAZIONI PER TAB
    if (this.client.listeSondaggiPerTab[this.active].length > 0 && this.client.listeSondaggiPerTab[this.active][0].modulo.moduloCompilatoRidotto) {
      // Nel caso di sezioni, si prende in considerazion la prima rilevazione della lista
      this.questionarioSezioniToShow = this.client.listeSondaggiPerTab[this.active][0].modulo.moduloCompilatoRidotto?.sezioni;
      // Nel caso di solo voci inziali, si prende in considerazion la prima rilevazione della lista
      this.questionarioVociToShow = this.client.listeSondaggiPerTab[this.active][0].modulo.moduloCompilatoRidotto?.voci;
      // Data/ora delle rilevazioni per tab, lista completa delle rilevazioni per tab
      this.questionariPerTab = this.client.listeSondaggiPerTab[this.active];
    } else {
      this.client.listeRilevazioniPerTab[this.active] = [];
      // this.listaSezioniToShow = [];
      this.questionarioSezioniToShow = null;
      this.questionarioVociToShow = null;
      this.questionariPerTab = [];
    }
    // TASTO AGGIUNGI
    if (this.client.listaTabSondaggiAggiungi && this.client.listaTabSondaggiAggiungi?.length <= 0) {
      this.showTooltipNoRilevazioniAggiungi = true;
    } else {
      this.showTooltipNoRilevazioniAggiungi = false;
    }

    this.isLoadingData = false;
  }

  async showAddRilevazione(rilevazione: RilevazioniAggiungi, tab: RilevazioniTab) {
    if (!rilevazione || !tab) {
      this.errorHandlerService.displayErrorMessage('[FE - showAddRilevazione] - Errore interno');
      return;
    }
    if (!this.showTooltipNoRilevazioniAggiungi && this.client.listaTabSondaggi) {
      await this.moduloCommon.setDataModify();
      this.client.moduloCompilatoToshow = null;
      this.client.moduloToshow = rilevazione.modulo?.moduloId ? rilevazione.modulo.moduloId : null;

      let visualizzazione: boolean = false;
      if (this.client.checkAzioni('vigil_qst') === 'W') {
        visualizzazione = true;
      } else {
        visualizzazione = false;
      }

      if (this.client.moduloToshow) {
        this.client.modeDettaglio = false;
        const dialogRef = this.dialog.open(DialogAggiungiComponent, {
          width: '1200px',
          maxHeight: '90vh',
          panelClass: 'custom-modal',
          data: { rilevazione: rilevazione, tab: tab, visualizzazione: visualizzazione }
        });  // Apro la modal
        const result = await lastValueFrom(dialogRef.afterClosed());
        this.isSpinEmitter = true;
        this.client.disabilitaCampi = false;
        this.client.moduloCompilatoToshow = null;
        this.client.moduloToshow = null;
        if (result === 'updated') {
          await this.onChangeTab(this.client.listaTabSondaggi[this.active].moduloConfigCod);
        }
        this.isSpinEmitter = false;
      }
    }
  }

  async showDettaglioRilevazione(rilevazione: RilevazioniAggiungi, tab: RilevazioniTab, dettaglio: boolean) {
    if (!rilevazione || !tab || !dettaglio) {
      this.errorHandlerService.displayErrorMessage('[FE - showDettaglioRilevazione] - Errore interno');
      return;
    }
    // Nel caso di sola visione i campi del modulo devono essere disabilitati
    dettaglio ? this.client.disabilitaCampi = true : this.client.disabilitaCampi = false;
    this.client.moduloToshow = 0;
    this.client.moduloCompilatoToshow = rilevazione.moduloCompilatoId ? rilevazione.moduloCompilatoId : null;
    this.client.modeDettaglio = true;

    let visualizzazione: boolean = false;
    if (this.client.checkAzioni('vigil_qst') === 'W') {
      visualizzazione = true;
    } else {
      visualizzazione = false;
    }

    if (this.client.moduloCompilatoToshow && this.client.listaTabSondaggi) {
      const dialogRef = this.dialog.open(DialogAggiungiComponent, {
        width: '1200px',
        maxHeight: '90vh',
        panelClass: 'custom-modal',
        data: { rilevazione: rilevazione, tab: tab, visualizzazione: visualizzazione }
      });  // Apro la modal
      const result = await lastValueFrom(dialogRef.afterClosed());
      this.isSpinEmitter = true;
      this.client.disabilitaCampi = false;
      this.client.moduloCompilatoToshow = null;
      this.client.moduloToshow = null;
      if (result === 'updated') {
        await this.onChangeTab(this.client.listaTabSondaggi[this.active].moduloConfigCod);
      }
      this.client.modeDettaglio = false;
      this.isSpinEmitter = false;
    }
  }
}
