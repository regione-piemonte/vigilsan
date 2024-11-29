/*
 * Copyright Regione Piemonte - 2024
 * SPDX-License-Identifier: EUPL-1.2
 */

import { AfterViewInit, Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { lastValueFrom } from 'rxjs';
import { Client, Navigation } from 'src/app/Client';
import { DettaglioArpe } from 'src/app/DTO/DettaglioArpeDTO';
import { NuovaPraticaPraticaTipo } from 'src/app/DTO/PraticheVigilanzaDecodificheDTO';
import { Attivita } from 'src/app/DTO/PraticheVigilanzaNuovaPratica';
import { DettaglioArpeComponent } from '../../dettaglio-arpe/dettaglio-arpe.component';
import { ErrorHandlerService } from '../../ErrorHandlerService';
import { ModificaModuloComponent } from '../modifica-modulo/modifica-modulo.component';
import { DialogDeleteAttivitaComponent } from '../dialog-delete-attivita/dialog-delete-attivita.component';
import { PraticheVigilanzaComponent } from '../pratiche-vigilanza.component';
import { MebroSopralluogoComponent } from '../membro-sopralluogo/mebro-sopralluogo.component';
import { RequisitiPraticheComponent } from '../requisiti-pratiche/requisiti-pratiche.component';

export type GenericAppPre = {
  id: number | null,
  tipoId: number | null,
  praticaId: number | null,
  dataoraInizio: number | null,
  dataoraFine: number | null,
  dataoraApertura: number,
  dataoraChiusura: number | null,
  stato: {
    statoId: number | null,
    statoCod: string | null,
    statoDesc: string | null,
    statoFinale: boolean | null
  },
  tipo: {
    tipoId: number | null,
    tipoCod: string | null,
    tipoDesc: string | null
  },
  checked?: boolean,
  moduloCompilatoId: number | null;
  moduloId: number | null;
  praticaDetIdCheck?: number | null;
  isPrescrizione: boolean;
  isSopralluogo: boolean;
  appuntamentoNumero: string | null;
  prescrizioneNumero: string | null;
}

@Component({
  selector: 'app-dettaglio-pratica',
  templateUrl: './dettaglio-pratica.component.html',
  styleUrls: ['./dettaglio-pratica.component.css']
})
export class DettaglioPraticaComponent implements OnInit, AfterViewInit {

  columns: string[] = ['Filtro', 'Tipo', 'Numero', 'Stato', 'Apertura/Chiusura', 'Inizio/Fine', ''];
  columnsAzioni: string[] = ['CV/S', 'Data/ora', 'Numero', 'Azione', 'Modulo'];
  loadDettaglioArpe: boolean = false;
  isSpinEmitter: boolean = false;
  isSpinEmitterTableAttivita: boolean = false;
  isSpinEmitterLoadingPdf: boolean = false;
  isTableLoadingScadenza: boolean = false;
  indexTableLoadingPdf: number | null = null;
  isLoadingDownloadZip: boolean = false;


  listaPraticaTipo: NuovaPraticaPraticaTipo[] = [];
  listAttivitaSwap: Attivita[] | null = null;
  praticaPraticaDettaglio: Attivita | null = null;
  listAppPre: GenericAppPre[] = [];

  constructor(public dialog: MatDialog, private errorHandlerService: ErrorHandlerService, private router: Router,
    public client: Client, public praticaComponent: PraticheVigilanzaComponent) { }

  ngAfterViewInit(): void {
    setTimeout(() => {
      this.client.textRoute = 'Dettaglio Pratica';
    }, 0);
  }

  async ngOnInit() {
    this.isSpinEmitter = true;
    this.client.attivitaSelected = null;
    if (this.client.selectedExistPratica && this.client.selectedExistPratica.praticaTipoId) {
      await this.getAzioniPerPratica(this.client.selectedExistPratica.praticaTipoId);
      this.listAppPre = [];
      await this.getPraticaDettaglio();
      // init checked field
      if (this.client.praticaDettaglio) {
        // Ultima attivitÃ  con flg scadenza a null (pratica)
        let attivitaNoFlg = this.client.praticaDettaglio.attivita.filter((e) => !e.flgScadenza);
        if (attivitaNoFlg && attivitaNoFlg.length > 0) {
          this.praticaPraticaDettaglio = attivitaNoFlg[attivitaNoFlg.length - 1];
        }
        this.client.selectedPraticaDettaglio = null;
        this.client.praticaDettaglio.checked = true;
        if (this.listAppPre.length === 0) {
          this.makeListAppPre();
        }
        this.toggle('PRA', null, this.client.praticaDettaglio.checked);
        this.checkModuloCompilato();
      }
    }
    this.isSpinEmitter = false;
  }

  // LOGICA FILTRI
  makeListAppPre() {
    if (this.client.praticaDettaglio) {
      if (this.client.praticaDettaglio.appuntamenti && this.client.praticaDettaglio.appuntamenti.length > 0) {
        this.client.praticaDettaglio.appuntamenti.forEach((e) => {
          let obj: GenericAppPre = {
            id: null,
            tipoId: null,
            praticaId: null,
            dataoraInizio: null,
            dataoraFine: null,
            dataoraApertura: 0,
            dataoraChiusura: null,
            stato: {
              statoId: null,
              statoCod: null,
              statoDesc: null,
              statoFinale: null
            },
            tipo: {
              tipoId: null,
              tipoCod: null,
              tipoDesc: null
            },
            checked: false,
            moduloId: null,
            moduloCompilatoId: null,
            praticaDetIdCheck: null,
            isPrescrizione: false,
            isSopralluogo: false,
            appuntamentoNumero: null,
            prescrizioneNumero: null
          };
          obj.id = e.appuntamentoId;
          obj.tipoId = e.appuntamentoTipoId;
          obj.praticaId = e.praticaId;
          obj.dataoraInizio = e.dataoraInizio;
          obj.dataoraFine = e.dataoraFine;
          obj.dataoraApertura = e.dataoraApertura ?? 0;
          obj.dataoraChiusura = e.dataoraChiusura;
          obj.stato = {
            statoId: e.appuntamentoStato.appuntamentoStatoId,
            statoCod: e.appuntamentoStato.appuntamentoStatoCod,
            statoDesc: e.appuntamentoStato.appuntamentoStatoDesc,
            statoFinale: e.appuntamentoStato.appuntamentoStatoFinale
          };
          obj.tipo = {
            tipoId: e.appuntamentoTipo.appuntamentoTipoId,
            tipoCod: e.appuntamentoTipo.appuntamentoTipoCod,
            tipoDesc: e.appuntamentoTipo.appuntamentoTipoDesc,
          };
          obj.isPrescrizione = false;
          obj.isSopralluogo = true;
          obj.moduloId = e.moduloId;
          obj.moduloCompilatoId = e.moduloCompilatoId;
          obj.appuntamentoNumero = e.appuntamentoNumero;
          this.listAppPre.push(obj);
        });
      }
      if (this.client.praticaDettaglio.prescrizioni && this.client.praticaDettaglio.prescrizioni.length > 0) {
        this.client.praticaDettaglio.prescrizioni.forEach((e) => {
          let obj: GenericAppPre = {
            id: null,
            tipoId: null,
            praticaId: null,
            dataoraInizio: null,
            dataoraFine: null,
            dataoraApertura: 0,
            dataoraChiusura: null,
            stato: {
              statoId: null,
              statoCod: null,
              statoDesc: null,
              statoFinale: null
            },
            tipo: {
              tipoId: null,
              tipoCod: null,
              tipoDesc: null
            },
            checked: false,
            moduloId: null,
            moduloCompilatoId: null,
            praticaDetIdCheck: null,
            isPrescrizione: false,
            isSopralluogo: false,
            appuntamentoNumero: null,
            prescrizioneNumero: null
          };
          obj.id = e.prescrizioneId;
          obj.tipoId = e.prescrizioneTipoId;
          obj.praticaId = e.praticaId;
          obj.dataoraApertura = e.dataoraApertura ? e.dataoraApertura : 0;
          obj.dataoraChiusura = e.dataoraChiusura;
          obj.stato = {
            statoId: e.prescrizioneStato.prescrizioneStatoId,
            statoCod: e.prescrizioneStato.prescrizioneStatoCod,
            statoDesc: e.prescrizioneStato.prescrizioneStatoDesc,
            statoFinale: e.prescrizioneStato.prescrizioneStatoFinale
          };
          obj.tipo = {
            tipoId: e.prescrizioneTipo.prescrizioneTipoId,
            tipoCod: e.prescrizioneTipo.prescrizioneTipoCod,
            tipoDesc: e.prescrizioneTipo.prescrizioneTipoDesc,
          };
          obj.isPrescrizione = true;
          obj.isSopralluogo = false;
          obj.moduloId = e.moduloId;
          obj.moduloCompilatoId = e.moduloCompilatoId;
          obj.prescrizioneNumero = e.prescrizioneNumero;
          this.listAppPre.push(obj);
        });
      }
      if (this.listAppPre && this.listAppPre.length > 0) {
        this.listAppPre.sort((a, b) => a.dataoraApertura - b.dataoraApertura);
      }
    }
  }

  // GOBACK - AZIONI GENERICHE
  goBack() {
    if (this.client.active === 0) {
      this.router.navigate([Navigation.PRATICA + '/scadenziario'], { skipLocationChange: true });
    } else {
      this.router.navigate([Navigation.PRATICA + '/ricerca_pratiche'], { skipLocationChange: true });
    }
  }
  showArchivioDocumentale() {

  }

  // API CALLS
  async getPraticaDettaglio() {
    if (this.client.selectedExistPratica && this.client.praticaDettaglio === null) {
      let params = {
        pratica_id: this.client.selectedExistPratica.praticaId
      }
      await lastValueFrom(this.client.getPraticaDettaglio(params))
        .then(data => {
          if (data !== null) {
            this.client.praticaDettaglio = data;
            this.makeListAppPre();
          }
        })
        .catch(
          error => {
            this.isSpinEmitter = false;
            this.client.active = 1;
            this.goBack();
            this.praticaComponent.cerca();
            this.errorHandlerService.handleError(error, 'getPraticaDettaglio');
          }
        );
    }
  }

  async visualizzaDettaglioArpe() {
    this.loadDettaglioArpe = true;
    let listaSettaglioArpe: DettaglioArpe[];
    if (this.client.loggedUser.struttura && this.client.loggedUser.struttura.strutturaId) {
      const params = {
        struttura_id: this.client.loggedUser.struttura.strutturaId
      }
      await lastValueFrom(this.client.getDettaglioArpe(params))
        .then(data => {
          listaSettaglioArpe = data;
          this.loadDettaglioArpe = false;
          const dialogRef = this.dialog.open(DettaglioArpeComponent, {
            panelClass: 'custom-modal',
            data: { listaSettaglioArpe: listaSettaglioArpe }
          });  // Apro la modal
          // dialogRef.afterClosed().subscribe(result => {});
        })
        .catch(
          error => {
            this.loadDettaglioArpe = false;
            this.errorHandlerService.handleError(error, 'getDettaglioArpe');
            return;
          }
        );
    } else {
      this.loadDettaglioArpe = false;
      this.errorHandlerService.displayErrorMessage('Errore durante l\'ottenimento dei dati [Dettaglio arpe]');
      return;
    }
  }

  async getAzioniPerPratica(praticaId: number) {
    if (this.client.azioniPerPratiche === null) {
      let params = {
        pratica_tipo_id: praticaId
      }
      await lastValueFrom(this.client.getAzioniPerPratica(params))
        .then(data => {
          if (data !== null) {
            this.client.azioniPerPratiche = data;
          }
        })
        .catch(
          error => {
            this.isSpinEmitter = false;
            this.client.azioniPerPratiche = null;
            this.errorHandlerService.handleError(error, 'getAzioniPerPratica');
          }
        );
    }
  }

  async getListaNuovaPraticaPratica() {
    await lastValueFrom(this.client.getListaNuovaPraticaPratica())
      .then(data => {
        if (data !== null) {
          this.listaPraticaTipo = data;
        }
      })
      .catch(
        error => {
          this.isSpinEmitter = false;
          this.errorHandlerService.handleError(error, 'getListaNuovaPraticaPratica');
        }
      );
  }

  // CONTROLLI - AZIONI TABELLE
  async showAddAzione() {
    this.client.dataOraDialogAddAzione = null;
    this.client.dataOraInizioDialogAddAzione = null;
    this.client.noteDialogAddAzione = '';
    this.client.selectedAzioneDialogAddAzione = null;
    this.client.selectedAzioneDialoagAddPratica = null;
    if (this.client.selectedPraticaDettaglio && this.client.selectedPraticaDettaglio.isSopralluogo) {
      this.router.navigate([Navigation.ADD_AZIONE + '/AttivitÃ APP'], { skipLocationChange: true });
    } else if (this.client.selectedPraticaDettaglio && this.client.selectedPraticaDettaglio.isPrescrizione) {
      this.router.navigate([Navigation.ADD_AZIONE + '/AttivitÃ PRE'], { skipLocationChange: true });
    } else {
      this.router.navigate([Navigation.ADD_AZIONE + '/AttivitÃ PRA'], { skipLocationChange: true });
    }
  }

  async showAddPrescrizione() {
    this.client.dataOraDialogAddAzione = null;
    this.client.dataOraInizioDialogAddAzione = null;
    this.client.noteDialogAddAzione = '';
    this.client.selectedAzioneDialogAddAzione = null;
    this.router.navigate([Navigation.ADD_AZIONE + '/Prescrizione'], { skipLocationChange: true });
  }
  async showAddSopralluogo() {
    this.client.dataOraDialogAddAzione = null;
    this.client.dataOraInizioDialogAddAzione = null;
    this.client.noteDialogAddAzione = '';
    this.client.selectedAzioneDialogAddAzione = null;
    this.router.navigate([Navigation.ADD_AZIONE + '/Sopralluogo'], { skipLocationChange: true });
  }

  checkModuloCompilato() {
    if (this.client.praticaDettaglio) {
      // Take moduloCompilatoId for pratica
      const resultPRA = this.client.praticaDettaglio.attivita.filter((e) => !e.prescrizioneId && !e.appuntamentoId);
      if (resultPRA && resultPRA.length > 0) {
        const resultPRAModuloId = resultPRA.filter((e) => e.praticaId === this.client.praticaDettaglio?.praticaId);
        if (resultPRAModuloId && resultPRAModuloId.length > 0) {
          this.client.praticaDettaglio.praticaDetIdCheck = resultPRAModuloId[0].praticaDetId ? resultPRAModuloId[0].praticaDetId : null;
        }
      }
      // Take moduloCompilatoId for every prescrizione
      const resultPRE = this.client.praticaDettaglio.attivita.filter((e) => e.prescrizioneId !== null && !e.appuntamentoId);
      if (resultPRE && resultPRE.length > 0) {
        this.client.praticaDettaglio.prescrizioni.forEach((pre) => {
          const resultPREModuloId = resultPRE.filter((e) => e.prescrizioneId === pre.prescrizioneId);
          if (resultPREModuloId && resultPREModuloId.length > 0) {
            pre.praticaDetIdCheck = resultPREModuloId[0].praticaDetId ? resultPREModuloId[0].praticaDetId : null;
          }
        })
      }
      // Take moduloCompilatoId for every appuntamento
      const resultAPP = this.client.praticaDettaglio.attivita.filter((e) => !e.prescrizioneId && e.appuntamentoId !== null);
      if (resultAPP && resultAPP.length > 0) {
        this.client.praticaDettaglio.appuntamenti.forEach((app) => {
          const resultAPPModuloId = resultAPP.filter((e) => e.appuntamentoId === app.appuntamentoId);
          if (resultAPPModuloId && resultAPPModuloId.length > 0) {
            app.praticaDetIdCheck = resultAPPModuloId[0].praticaDetId ? resultAPPModuloId[0].praticaDetId : null;
          }
        })
      }
    }
  }

  toggle(choice: string, type: null | GenericAppPre, checked: boolean | undefined) {
    this.isSpinEmitterTableAttivita = true;
    this.client.selectedPraticaDettaglio = type;
    this.client.attivitaSelected = null;
    if (this.client.praticaDettaglio) {
      if (checked) {
        switch (choice) {
          case 'PRA':
            if (this.listAppPre && this.listAppPre.length > 0) {
              this.listAppPre?.forEach((e) => e.checked = false);
            }
            this.listAttivitaSwap = this.client.praticaDettaglio.attivita;
            if (this.client.selectedPraticaDettaglio) {
              this.client.selectedPraticaDettaglio.isPrescrizione = false;
              this.client.selectedPraticaDettaglio.isSopralluogo = false;
            }
            break;
          case 'PRE':
            this.client.praticaDettaglio.checked = false;
            if (this.listAppPre && this.listAppPre.length > 0) {
              this.listAppPre?.forEach((e) => {
                if (type) {
                  if (e.id !== type.id) {
                    e.checked = false;
                  } else {
                    e.checked = true;
                  }
                }
              });
            }
            const resultPRE = this.client.praticaDettaglio.attivita.filter((e) => e.prescrizioneId !== null && !e.appuntamentoId && (e.prescrizioneId === type?.id));
            resultPRE && resultPRE.length > 0 ? this.listAttivitaSwap = resultPRE : this.listAttivitaSwap = null;
            break;
          case 'APP':
            this.client.praticaDettaglio.checked = false;
            if (this.listAppPre && this.listAppPre.length > 0) {
              this.listAppPre?.forEach((e) => {
                if (type) {
                  if (e.id !== type.id) {
                    e.checked = false;
                  } else {
                    e.checked = true;
                  }
                }
              });
            }
            const resultAPP = this.client.praticaDettaglio.attivita.filter((e) => !e.prescrizioneId && e.appuntamentoId !== null && (e.appuntamentoId === type?.id));
            resultAPP && resultAPP.length > 0 ? this.listAttivitaSwap = resultAPP : this.listAttivitaSwap = null;
            break;
          default:
            this.listAttivitaSwap = null;
            this.isSpinEmitterTableAttivita = false;
            return;
        }

        // Get right icon to show
        if (this.listAttivitaSwap && this.listAttivitaSwap.length > 0) {
          let idToUse: number | null;
          this.listAttivitaSwap.forEach((e) => {
            if (e.profiloId && !e.profiloIdScadenza) {
              idToUse = e.profiloId;
            } else if (e.profiloIdScadenza) {
              idToUse = e.profiloIdScadenza;
            }
            if (this.client.listaProfili && idToUse) {
              const searchProfile = this.client.listaProfili.filter((e) => e.profiloId === idToUse);
              if (searchProfile && searchProfile.length === 1) {
                switch (searchProfile[0].profiloCod) {
                  case 'VIGIL_ENTE':
                    e.iconToUse = 'bi bi-bank vigilsan-icon-style ente';
                    break;
                  case 'VIGIL_RES':
                    e.iconToUse = 'bi bi-hospital vigilsan-icon-style struttura';
                    break;
                  default:
                    break;
                }
                idToUse = null;
              } else {
                e.iconToUse = '';
                idToUse = null;
              }
            }
          });
        }
      } else {
        this.listAttivitaSwap = null;
      }
    } else {
      this.listAttivitaSwap = null;
    }
    this.isSpinEmitterTableAttivita = false;
  }

  checkDataScadenza(at: Attivita) {
    if (at.dataoraAzione && at.flgScadenza) {
      if (at.dataoraAzione < new Date().getTime()) {
        return 'S';
      } else {
        return 'V';
      }
    } else {
      return '';
    }
  }

  async showDeleteAttivita(at: Attivita) {
    const dialogRef = this.dialog.open(DialogDeleteAttivitaComponent, {
      data: { at: at }
    });  // Apro la modal
    const result = await lastValueFrom(dialogRef.afterClosed());
    if (result) {
      this.isSpinEmitterTableAttivita = true;
      this.client.praticaDettaglio = null;
      this.listAppPre = [];
      await this.getPraticaDettaglio();
      if (this.client.praticaDettaglio) {
        if (this.client.selectedPraticaDettaglio && this.client.selectedPraticaDettaglio.isPrescrizione) {
          this.client.selectedPraticaDettaglio.checked = true;
          this.toggle('PRE', this.client.selectedPraticaDettaglio, true);
          this.checkModuloCompilato();
        } else if (this.client.selectedPraticaDettaglio && this.client.selectedPraticaDettaglio.isSopralluogo) {
          this.client.selectedPraticaDettaglio.checked = true;
          this.toggle('APP', this.client.selectedPraticaDettaglio, true);
          this.checkModuloCompilato();
        } else {
          this.toggle('PRA', this.client.selectedPraticaDettaglio, true);
          this.checkModuloCompilato();
        }
      }
      this.errorHandlerService.displaySuccessMessage('AttivitÃ  eliminata con successo');
      this.isSpinEmitterTableAttivita = false;
    }
    if (this.client.praticaDettaglio && this.client.praticaDettaglio.attivita.length <= 0) {
      this.client.active = 1;
      this.goBack();
      await this.praticaComponent.cerca();
    }
  }

  showChangeAttivita(at: Attivita) {
    this.client.attivitaSelected = at;
    // if (this.client.selectedPraticaDettaglio) {
    this.router.navigate([Navigation.ADD_AZIONE + '/ModificaAttivita'], { skipLocationChange: true });
    // }
  }

  getNumeroAttivita(at: Attivita): string | null{
    if (!at) {
      this.errorHandlerService.displayErrorMessage('Errore in ricerzione attivitÃ ');
      return null;
    }
    if (this.client.praticaDettaglio) {
      const searchSopralluogo = this.client.praticaDettaglio.appuntamenti.filter((e) => e.appuntamentoId === at.appuntamentoId);
      if (searchSopralluogo && searchSopralluogo.length > 0) {
        return searchSopralluogo[0].appuntamentoNumero;
      }
      const searchPrescrizone = this.client.praticaDettaglio.prescrizioni.filter((e) => e.prescrizioneId === at.prescrizioneId);
      if (searchPrescrizone && searchPrescrizone.length > 0) {
        return searchPrescrizone[0].prescrizioneNumero;
      }
      return '';
    } else {
      return null;
    }
  }

  // CAMBIO MODULO
  async changeModulo(moduloId: number | null, moduloCompilatoId: number | null, praticaDetIdCheck: number | null, at: Attivita | null) {
    if (this.client.selectedExistPratica && this.client.selectedExistPratica.struttura) {
      this.client.idStrutturaSwap = this.client.selectedExistPratica.struttura.strutturaId;
      this.client.moduloToshow = null;
      this.client.moduloCompilatoToshow = null;
      // this.client.moduloCompilatoToshow = moduloId;
      const dialogRef = this.dialog.open(ModificaModuloComponent, {
        width: '1300px',
        maxHeight: '90vh',
        panelClass: 'custom-modal',
        data: { title: 'Modifica documento', praticaDetId: praticaDetIdCheck, moduloId: moduloId, moduloCompilatoId: moduloCompilatoId, at: at }
      });  // Apro la modal
      let result: boolean = false;
      result = await lastValueFrom(dialogRef.afterClosed());
      if (result) {
        this.isSpinEmitter = true;
        await this.getPraticaDettaglio();
        // init checked field
        if (this.client.praticaDettaglio) {
          this.client.selectedPraticaDettaglio = null;
          this.client.praticaDettaglio.checked = true;
          this.toggle('PRA', null, this.client.praticaDettaglio.checked);
          if (this.client.praticaDettaglio.appuntamenti && this.client.praticaDettaglio.appuntamenti.length > 0) {
            this.client.praticaDettaglio.appuntamenti.forEach((e) => e.checked = false);
          }
          if (this.client.praticaDettaglio.prescrizioni && this.client.praticaDettaglio.prescrizioni.length > 0) {
            this.client.praticaDettaglio.prescrizioni.forEach((e) => e.checked = false);
          }
          this.checkModuloCompilato();
        }
        this.client.moduloToshow = null;
        this.client.moduloCompilatoToshow = null;
        this.isSpinEmitter = false;
        this.errorHandlerService.displaySuccessMessage('Documento salvata con successo');
      } else {
        this.client.moduloToshow = null;
        this.client.moduloCompilatoToshow = null;
      }
    }
  }

  async getPdf(moduloDetId: number | null, index: number | null, isTableLoadingScadenza: boolean) {
    this.indexTableLoadingPdf = index;
    this.isTableLoadingScadenza = isTableLoadingScadenza;
    if (!moduloDetId) {
      this.errorHandlerService.displayErrorMessage('Errore nel recupero del modulo');
      return;
    }
    this.isSpinEmitterLoadingPdf = true;
    const params = {
      modulo_compilato_id: moduloDetId
    }
    await lastValueFrom(this.client.getPdfPratica(params))
      .then(blob => {
        const headers = blob.headers;
        if (blob.headers) {
          const fileNameHeader = headers.get('Content-Disposition');
          if (fileNameHeader) {
            const parts = fileNameHeader.split(';');
            const partsSwap = parts[1].split('=');
            const fileName = partsSwap[1].replace(/"/g, '');
            if (blob.body) {
              let fileURL = URL.createObjectURL(blob.body);
              window.open(fileURL, '_blank');
            }
          }
        }
        this.indexTableLoadingPdf = null;
        this.isTableLoadingScadenza = false;
        this.isSpinEmitterLoadingPdf = false;
      })
      .catch(
        error => {
          this.indexTableLoadingPdf = null;
          this.isTableLoadingScadenza = false;
          this.isSpinEmitterLoadingPdf = false;
          this.errorHandlerService.handleError(error, 'getPdf');
        }
      );
    this.isSpinEmitterLoadingPdf = false;
  }

  async downloadZipPratiche() {
    if (!this.client.praticaDettaglio || !this.client.praticaDettaglio.praticaId) {
      this.errorHandlerService.displayErrorMessage('Errore interno della pratica');
      return;
    }
    this.isLoadingDownloadZip = true;
    this.client.isErrorLogin = true;
    const params = {
      pratica_id: this.client.praticaDettaglio?.praticaId
    }
    await lastValueFrom(this.client.getZipPratica(params))
      .then(blob => {
        const headers = blob.headers;
        if (blob.headers) {
          const fileNameHeader = headers.get('Content-Disposition');
          if (fileNameHeader) {
            const parts = fileNameHeader.split(';');
            const partsSwap = parts[1].split('=');
            const fileName = partsSwap[1].replace(/"/g, '');
            if (blob.body) {
              let fileURL = URL.createObjectURL(blob.body);
              const a = document.createElement('a');
              document.body.appendChild(a);
              a.style.display = 'none';
              a.href = fileURL;
              a.download = fileName;
              a.click();
              window.URL.revokeObjectURL(fileURL);
              document.body.removeChild(a);
            }
          }
        }
        this.client.isErrorLogin = false;
        this.isLoadingDownloadZip = false;
      })
      .catch(
        error => {
          this.client.isErrorLogin = false;
          this.isLoadingDownloadZip = false;
          this.errorHandlerService.handleError(error, 'getZipPratica');
        }
      );
    this.client.isErrorLogin = false;
    this.isLoadingDownloadZip = false;
  }

  async showPartecipanti(sopralluogo: GenericAppPre) {
    const dialogRef = this.dialog.open(MebroSopralluogoComponent, {
      data: { sopralluogo: sopralluogo }
    });  // Apro la modal
    const result = await lastValueFrom(dialogRef.afterClosed());
    if (result) {
    }
  }

  async showRequisiti(genericAppPreRequisiti: GenericAppPre | null, atRequisiti: Attivita | null) {
    this.client.genericAppPreRequisiti = genericAppPreRequisiti;
    this.client.atRequisiti = atRequisiti;
    this.router.navigate([Navigation.REQUISITI_PRATICA], { skipLocationChange: true });
  }

}
