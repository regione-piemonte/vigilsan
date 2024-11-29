/*
 * Copyright Regione Piemonte - 2024
 * SPDX-License-Identifier: EUPL-1.2
 */

import { AfterContentChecked, AfterViewInit, ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { lastValueFrom } from 'rxjs';
import { Client, ModuloConfigGruppoCod, Navigation } from 'src/app/Client';
import { SendDocumentazione } from 'src/app/DTO/Documentazione';
import { DocumentazioneCompilabile } from 'src/app/DTO/DocumentazioneCompilabiliDTO';
import { DocumentazioneCompilata } from 'src/app/DTO/DocumentazioniCompilateDTO';
import { RilevazioniAggiungi } from 'src/app/DTO/RilevazioniAggiungiDTO';
import { RilevazioniPerTab } from 'src/app/DTO/RilevazioniPerTab';
import { RilevazioniTab } from 'src/app/DTO/RilevazioniTabModel';
import { CambioStrutturaComponent } from '../cambio-struttura/cambio-struttura.component';
import { DialogAddDocumentazioneComponent } from '../documentazione/dialog-add-documentazione/dialog-add-documentazione.component';
import { ErrorHandlerService } from '../ErrorHandlerService';
import { ModuloCommon } from '../ModuloCommon';
import { TestComponent } from '../test/test.component';
import { DialogAggiungiComponent } from './dialog-aggiungi/dialog-aggiungi.component';
import { ScaricoCsvStoricoComponent } from './scarico-csv-storico/scarico-csv-storico.component';
import { ScaricoCsvComponent } from './scarico-csv/scarico-csv.component';

@Component({
  selector: 'app-rilevazioni',
  templateUrl: './rilevazioni.component.html',
  styleUrls: ['./rilevazioni.component.css', '../../../styles.css']
})
export class RilevazioniComponent implements OnInit, AfterContentChecked, AfterViewInit {

  activeFirst: number = 0;
  active: number = 0;

  showTooltipNoRilevazioniAggiungi: boolean = false;
  listaSezioniToShow: any = null;
  listaVociToShow: any = null;
  numeroRilevazioniToShow: any = null;

  isSpinEmitter: boolean = false;
  isSpinEmitterInformazioni: boolean = false;
  isLoadingData: boolean = false;
  showModuloInformazioni: boolean = false;

  rilevazioneConfigCod: string | null = null;

  documentazioneInSelezione: DocumentazioneCompilata | null = null;

  constructor(public client: Client, public dialog: MatDialog, private cdr: ChangeDetectorRef, public moduloCommon: ModuloCommon,
    private changeDetector: ChangeDetectorRef, private errorHandlerService: ErrorHandlerService, public router: Router,
    private test: TestComponent) { }

  ngAfterViewInit(): void {
    setTimeout(() => {
      this.client.textRoute = 'Rilevazioni';
    }, 0);
  }

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
      await this.getRilevazioniTabInformation();  // Getting Rilevazioni tab information

      // Init lista 3d
      if (this.client.listaTabRilevazioni) {
        const lista: RilevazioniPerTab[] = [];
        for (let i = 0; i < this.client.listaTabRilevazioni.length; i++) {
          this.client.listeRilevazioniPerTab.push(lista);
        }
      }
      if (this.client.listaTabRilevazioni && this.client.listaTabRilevazioni.length > 0) {  // First check if AGGIUNGI button is abilited
        this.onChangeTab(this.client.listaTabRilevazioni[0].moduloConfigCod ? this.client.listaTabRilevazioni[0].moduloConfigCod : '');
      }
      // TAB INFORMAZIONI
      this.isSpinEmitterInformazioni = true;
      this.client.moduloCompilatoToshow = null;
      this.client.moduloToshow = null;
      if (this.router.url === '/main-page/rilevazioni') {
        this.client.resDoc = ModuloConfigGruppoCod.INF;
      } else {
        this.client.resDoc = ModuloConfigGruppoCod.DOC;
      }
      await this.getDocumentazioniLista(); // OTTENGO LE DOCUMENTAZIONI PER LA SELECT INIZIALE
      await this.getDocumentazioniCompilate();
      await this.getDocumentazioniCompilabili();
      if (this.client.listaDocumentazioni) {
        this.logicaFiltroTipologiaDocumentazione();
      }
      this.isSpinEmitterInformazioni = false;
      this.isSpinEmitter = false;
    } else {
      this.client.selectedTab = 'main-page/vetrina';
      this.router.navigate([Navigation.VETRINA], { skipLocationChange: true });
    }
  }

  // TAB INFORMAZIONI-------------------------------------------------------------------------------------
  logicaFiltroTipologiaDocumentazione() {
    if (this.client.listaDocumentazioni) {
      const moduloCodAmmessi = this.client.listaDocumentazioni.map((e) => e.moduloConfigCod);
      if (this.client.listaDocumentazioniCompilate) {
        this.client.listaDocumentazioniCompilate = this.client.listaDocumentazioniCompilate.filter((e) => moduloCodAmmessi.includes(e.modulo.moduloCompilatoRidotto.moduloCod));
        if (this.client.listaDocumentazioniCompilate && this.client.listaDocumentazioniCompilate.length > 0) {
          this.client.moduloCompilatoToshow = this.client.listaDocumentazioniCompilate[0].moduloCompilatoId;
          this.documentazioneInSelezione = this.client.listaDocumentazioniCompilate[0];
          this.showModuloInformazioni = true;
        } else {
          this.showModuloInformazioni = false;
        }
      } else {
        this.showModuloInformazioni = false;
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
          this.isSpinEmitter = false;
          this.errorHandlerService.handleError(error, 'getDocumentazionePerListaDocumentazioni');
        }
      );
  }

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
        this.isSpinEmitterInformazioni = true;

        await this.getDocumentazioniCompilabili(); // OTTENGO LE DOCUMENTAZIONI DA AGGIUNGERE NEI TASTI AGGIUNGI
        await this.getDocumentazioniCompilate(); // Riottengo la lista delle documentazioni per modulo_cod
        this.logicaFiltroTipologiaDocumentazione();
        await this.test.ngOnInit();

        this.client.moduloToshow = null;
        this.client.moduloCompilatoToshow = null;
        this.errorHandlerService.displaySuccessMessage('Documentazione salvata con successo');
        this.isSpinEmitterInformazioni = false;
      } else {
        this.isSpinEmitterInformazioni = false;
        this.client.moduloToshow = null;
        this.client.moduloCompilatoToshow = null;
      }
    }
  }

  async sendDocumentazione() {
    this.isSpinEmitterInformazioni = true;
    this.client.selectedAzioneDialoagAddPratica = null; //Anullo struttura idSwap per altri calcoli
    this.moduloCommon.sendModulo();
    if (!this.client.isValidModulo) {
      this.isSpinEmitterInformazioni = false;
      this.errorHandlerService.displayErrorMessage('ERRORE IN COMPILAZIONE');
      return;
    }

    if (this.documentazioneInSelezione) {
      const documentazioneToSend: SendDocumentazione = {
        documentazione: {
          documentazioneId: this.documentazioneInSelezione.documentazioneId,
          strutturaId: this.documentazioneInSelezione.strutturaId,
          strCatId: this.documentazioneInSelezione.strCatId,
          moduloCompilatoId: this.documentazioneInSelezione.moduloCompilatoId,
          moduloConfigId: this.documentazioneInSelezione.moduloConfigId,
          dataoraDocumentazione: new Date().getTime(),
          occorrenza: this.documentazioneInSelezione.occorrenza
        },
        moduloCompilato: {
          note: this.client.moduloToSend.note,
          modulo: this.client.moduloToSend
        }
      }

      const data = await lastValueFrom(this.client.postSalvaDocumentazione(documentazioneToSend))
        .catch(
          error => {
            this.isSpinEmitterInformazioni = false;
            this.errorHandlerService.handleError(error, 'postSalvaDocumentazione');
          }
        );
      if (data) {
        await this.getDocumentazioniCompilabili(); // OTTENGO LE DOCUMENTAZIONI DA AGGIUNGERE NEI TASTI AGGIUNGI
        await this.getDocumentazioniCompilate(); // Riottengo la lista delle documentazioni per modulo_cod
        this.logicaFiltroTipologiaDocumentazione();
      }
      this.isSpinEmitterInformazioni = false;
      this.errorHandlerService.displaySuccessMessage('Documentazione salvata con successo');
    } else {
      this.isSpinEmitterInformazioni = false;
      this.errorHandlerService.displayErrorMessage('Errore interno');
    }
  }

  // -----------------------------------------------------------------------------------------------------
  ngAfterContentChecked(): void {
    this.changeDetector.detectChanges();
  }

  // Getting Rilevazioni to add in AGGIUNGI button
  async getListaRilevazioniAggiungi(params: any) {
    const listaTabRilevazioniAggiungiObservable = this.client.getListaRilevazioniAggiungi(params);
    await lastValueFrom(listaTabRilevazioniAggiungiObservable)
      .then(data => {
        if (data !== null) {
          this.client.listaTabRilevazioniAggiungi = data;
        }
      })
      .catch(
        error => {
          this.isSpinEmitter = false;
          this.errorHandlerService.handleError(error, 'getListaRilevazioniAggiungi');
        }
      );
  }

  // Getting Rilevazioni tab information
  async getRilevazioniTabInformation() {
    const params = {
      modulo_config_gruppo_cod: ModuloConfigGruppoCod.RIL
    }
    const listaTabRilevazioniObservable = this.client.getListaTabRilevazioni(params);
    await lastValueFrom(listaTabRilevazioniObservable)
      .then(data => {
        if (data !== null) {
          this.client.listaTabRilevazioni = data;
        }
      })
      .catch(
        error => {
          this.isSpinEmitter = false;
          this.errorHandlerService.handleError(error, 'getListaTabRilevazioni');
        }
      );
  }

  // Getting Rilevazioni x tab information
  async getRilevazioniPerTab(index: number) {
    if (this.client.listaTabRilevazioni !== null) {
      let lista: RilevazioniPerTab[] = [];
      let params = {
        modulo_config_cod: this.client.listaTabRilevazioni[index].moduloConfigCod
      }
      const rilevazioniPerTabAggiungiObservable = this.client.getListaRilevazioniPerTab(params);
      await lastValueFrom(rilevazioniPerTabAggiungiObservable)
        .then(data => {
          if (data !== null) {
            lista = data;
            this.client.listeRilevazioniPerTab[this.active] = lista;
          }
        })
        .catch(
          error => {
            this.isSpinEmitter = false;
            this.isLoadingData = false;
            this.errorHandlerService.handleError(error, 'getListaRilevazioniPerTab');
          }
        );
    }
  }

  // Get rileazioni per la tab in update/insert
  async getRilevazioniPerTabUpdate(index: number) {
    if (this.client.listaTabRilevazioni !== null) {
      let lista: RilevazioniPerTab[] = [];
      let params = {
        modulo_config_cod: this.client.listaTabRilevazioni[index].moduloConfigCod
      }
      const rilevazioniPerTabAggiungiObservable = this.client.getListaRilevazioniPerTab(params);
      await lastValueFrom(rilevazioniPerTabAggiungiObservable)
        .then(data => {
          if (data !== null) {
            lista = data;
            this.client.listeRilevazioniPerTab[index] = lista;
            this.onChangeTab(this.client.rilevazioneTipo);
            this.cdr.detectChanges();
            this.isLoadingData = false;
          }
        })
        .catch(
          error => {
            this.isSpinEmitter = false;
            this.isLoadingData = false;
            this.errorHandlerService.handleError(error, 'getListaRilevazioniPerTab');
          }
        );
    }
  }

  async showAddRilevazione(rilevazione: RilevazioniAggiungi, tab: RilevazioniTab) {
    if (!this.showTooltipNoRilevazioniAggiungi) {
      await this.moduloCommon.setDataModify();
      this.client.moduloCompilatoToshow = null;
      this.client.moduloToshow = rilevazione.modulo?.moduloId ? rilevazione.modulo.moduloId : null;
      if (this.client.moduloToshow) {
        let visualizzazione: boolean = false;
        if (this.client.checkAzioni('vigil_ril-ril') === 'W') {
          visualizzazione = true;
        } else {
          visualizzazione = false;
        }
        const dialogRef = this.dialog.open(DialogAggiungiComponent, {
          width: '1200px',
          maxHeight: '90vh',
          panelClass: 'custom-modal',
          data: { rilevazione: rilevazione, tab: tab, visualizzazione: visualizzazione }
        });  // Apro la modal
        const result = await lastValueFrom(dialogRef.afterClosed());
        this.isSpinEmitter = true;
        this.client.moduloToshow = null;
        this.client.moduloCompilatoToshow = null;
        if (result === 'updated') {
          this.isLoadingData = true;
          this.moduloCommon.setDataOnlyRead();
          if (this.rilevazioneConfigCod) {
            let params = {
              modulo_config_cod: this.rilevazioneConfigCod
            }
            await this.getListaRilevazioniAggiungi(params); // Getting Rilevazioni to add in AGGIUNGI button
          }
          await this.getRilevazioniPerTabUpdate(this.active);
        }
        this.isSpinEmitter = false;
      }
    }
  }


  async showDettaglioRilevazione(rilevazione: RilevazioniAggiungi, tab: RilevazioniTab, dettaglio: boolean) {
    // Nel caso di sola visione i campi del modulo devono essere disabilitati
    dettaglio ? this.client.disabilitaCampi = true : this.client.disabilitaCampi = false;
    this.client.moduloToshow = 0;
    this.client.moduloCompilatoToshow = rilevazione.moduloCompilatoId ? rilevazione.moduloCompilatoId : null;
    this.client.modeDettaglio = true;
    if (this.client.moduloCompilatoToshow) {
      let visualizzazione: boolean = false;
      if (this.client.checkAzioni('vigil_ril-ril') === 'W') {
        visualizzazione = true;
      } else {
        visualizzazione = false;
      }
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
        this.isLoadingData = true;
        await this.getRilevazioniPerTabUpdate(this.active);
      }
      this.client.modeDettaglio = false;
      this.isSpinEmitter = false;
    }
  }

  async onChangeTab(rilevazioneTipo: string) {
    this.isLoadingData = true;
    this.showTooltipNoRilevazioniAggiungi = false;

    this.rilevazioneConfigCod = rilevazioneTipo;
    let params = {
      modulo_config_cod: rilevazioneTipo
    }
    await this.getListaRilevazioniAggiungi(params); // Getting Rilevazioni to add in AGGIUNGI button
    await this.getRilevazioniPerTab(this.active); // Getting Rilevazioni compilate per tab

    this.listaVociToShow = null;
    this.listaSezioniToShow = null;
    if (rilevazioneTipo === '') {
      this.showTooltipNoRilevazioniAggiungi = false;
      this.client.listeRilevazioniPerTab[this.active] = [];
      this.listaSezioniToShow = [];
      this.numeroRilevazioniToShow = 0;
      return;
    }

    this.client.rilevazioneTipo = rilevazioneTipo;  // Ottengo il tipo rilevazione per la modal

    // TABELLA RILEVAZIONI PER TAB
    if (this.client.listeRilevazioniPerTab[this.active].length > 0 && this.client.listeRilevazioniPerTab[this.active][0].modulo.moduloCompilatoRidotto) {
      // Nel caso di sezioni, si prende in considerazion la prima rilevazione della lista
      this.listaSezioniToShow = this.client.listeRilevazioniPerTab[this.active][0].modulo.moduloCompilatoRidotto?.sezioni;
      // Nel caso di solo voci inziali, si prende in considerazion la prima rilevazione della lista
      this.listaVociToShow = this.client.listeRilevazioniPerTab[this.active][0].modulo.moduloCompilatoRidotto?.voci;
      // Data/ora delle rilevazioni per tab, lista completa delle rilevazioni per tab
      this.numeroRilevazioniToShow = this.client.listeRilevazioniPerTab[this.active];
    } else {
      this.client.listeRilevazioniPerTab[this.active] = [];
      this.listaSezioniToShow = [];
      this.numeroRilevazioniToShow = 0;
    }
    // TASTO AGGIUNGI
    if (this.client.listaTabRilevazioniAggiungi && this.client.listaTabRilevazioniAggiungi?.length <= 0) {
      this.showTooltipNoRilevazioniAggiungi = true;
    } else {
      this.showTooltipNoRilevazioniAggiungi = false;
    }

    this.isLoadingData = false;
  }

  scaricaCsv() {
    if (this.client.listaTabRilevazioni) {
      this.dialog.open(ScaricoCsvComponent, {
        width: '400px',
        maxHeight: '90vh',
        panelClass: 'custom-modal',
        data: { moduloConfigCod: this.client.listaTabRilevazioni[this.active].moduloConfigCod }
      });  // Apro la modal
    }
  }

  scaricaCsvStorico() {
    if (this.client.listaTabRilevazioni) {
      this.dialog.open(ScaricoCsvStoricoComponent, {
        width: '400px',
        maxHeight: '90vh',
        panelClass: 'custom-modal',
        // data: { moduloConfigCod: this.client.listaTabRilevazioni[this.active].moduloConfigCod }
      });  // Apro la modal
    }
  }

  async onChangeTabInit() {
    this.isSpinEmitterInformazioni = true;
    this.client.moduloCompilatoToshow = null;
    this.client.moduloToshow = null;
    if (this.router.url === '/main-page/rilevazioni') {
      this.client.resDoc = 'resinfo';
    } else {
      this.client.resDoc = 'resdoc';
    }
    await this.getDocumentazioniCompilate();
    if (this.client.listaDocumentazioni) {
      this.logicaFiltroTipologiaDocumentazione();
    }
    this.isSpinEmitterInformazioni = false;
  }
}
