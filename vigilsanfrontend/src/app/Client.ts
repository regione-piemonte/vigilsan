/*
 * Copyright Regione Piemonte - 2024
 * SPDX-License-Identifier: EUPL-1.2
 */

import { HttpClient, HttpParams, HttpResponse } from "@angular/common/http";
import { Injectable } from "@angular/core";

import { FormControl } from "@angular/forms";
import { catchError, Observable, throwError } from "rxjs";
import { environment } from "src/environments/environment";
import { RuoloCommissione, SoggettoCommissione } from "./DTO/CommissioneDTO";
import { Condizione } from "./DTO/CondizioneDTO";
import { DettaglioArpe } from "./DTO/DettaglioArpeDTO";
import { Documentazione } from "./DTO/Documentazione";
import { DocumentazioneCompilabile } from "./DTO/DocumentazioneCompilabiliDTO";
import { DocumentazioneCompilata } from "./DTO/DocumentazioniCompilateDTO";
import { MovimentoTipo } from "./DTO/MovimentoTipoDTO";
import { OspiteDetail } from "./DTO/OspiteDetailDTO";
import { Ospite } from "./DTO/OspiteDTO";
import { PostFile, TypeUploadFile } from "./DTO/PostFileDTO";
import { AppuntamentoStato, AppuntamentoTipo, Azione, AzioniPerPratiche, NuovaPraticaPraticaTipo, NuovaPraticaStrutturaTipo, PraticaStato, PraticaTipo, PrescrizioneStato, PrescrizioneTipo } from "./DTO/PraticheVigilanzaDecodificheDTO";
import { MembroSopralluogo, RuoliMembriSopralluogo } from "./DTO/PraticheVigilanzaMembriSopralluoghiDTO";
import { Attivita, PraticheAggiunte, RicercaPratiche, SingolaPraticaDettaglio } from "./DTO/PraticheVigilanzaNuovaPratica";
import { Requisito, RequisitoPratica, RequisitoPraticaLista } from "./DTO/RequisitiDTO";
import { RilevazioniAggiungi } from "./DTO/RilevazioniAggiungiDTO";
import { RilevazioniPerTab } from "./DTO/RilevazioniPerTab";
import { RilevazioniTab } from "./DTO/RilevazioniTabModel";
import { Scadenza } from "./DTO/ScadenzarioDTO";
import { SezioniEntity } from "./DTO/SezioniEntity";
import { StatoOspite } from "./DTO/StatiOspiteDTO";
import { StrutturaScelta } from "./DTO/StrutturaSceltaDTO";
import { ProfiliEntity, User } from "./DTO/User";
import { MenuItem } from "./header/header.component";
import { GenericAppPre } from "./main-page/pratiche-vigilanza/dettaglio-pratica/dettaglio-pratica.component";

type TokenType = {
  "x-access-token": string
}

const enum PathApi {
  login = '/utente/login',
  logout = '/utente/logout',
  getUtente = '/utente',
  // MODULI
  getModulo = '/modulo',
  // TAB RILEVAZIONI
  getListaTabRilevazioni = '/rilevazione/modulo/lista',
  getListaRilevazioniAggiungi = '/rilevazione/compilabile/lista',
  getListaRilevazioniPerTab = '/rilevazione/compilata/lista',
  postRilevazione = '/rilevazione',
  getRilevazioniCSV = '/rilevazione/compilata/lista/csv',
  getRilevazioniCsvStorico = '/rilevazione/storico/csv',
  // FILE
  postFile = '/file',
  getFile = '/file',
  getTypeUploadFile = '/file/content/type/lista',
  // DOCUMENTAZIONE
  getListaDocumentazioni = '/documentazione/modulo/lista',
  getDocumentazionePerListaDocumentazioni = '/documentazione/compilata/lista',
  getDocumentazioniAggiungi = '/documentazione/compilabile/lista',
  postDocumentazione = '/documentazione',
  postVerificaDocumentazione = '/documentazione/verifica',
  postInviaNotificaEmail = '/notifica/gestione/documentale',
  // OSPITI
  getListaOspiti = '/ospite/lista',
  getListaOspitiCSV = '/ospite/lista/csv',
  getDecodificaStatiOspite = '/ospite/stato/lista',
  getOspiteDetail = '/ospite',
  getMovimentoTipo = '/ospite/movimento/tipo/lista',
  getCondizioni = '/ospite/condizioni/lista',
  postMovimento = '/ospite/movimento',
  deleteMovimento = '/ospite/movimento',
  searchSoggetto = '/ospite',
  // DETTAGLIO ARPE
  getDettaglioArpe = '/arpe/struttura/dettaglio',
  // STRUTTURE
  getFlagStrutture = '/struttura/parametro/lista',
  // TAB PRATICHE VIGILANZA
  getListaPraticaTipo = '/pratiche/pratica/tipo/lista',
  getListaPraticaStato = '/pratiche/pratica/stato/lista',
  getListaPrescrizioneTipo = '/pratiche/prescrizione/tipo/lista',
  getListaPrescrizioneStato = '/pratiche/prescrizione/stato/lista',
  getListaAppuntamentoTipo = '/pratiche/appuntamento/tipo/lista',
  getListaAppuntamentoStato = '/pratiche/appuntamento/stato/lista',
  getListaNuovaPraticaStruttura = '/pratiche/nuovapratica/struttura/tipo/lista',
  getListaNuovaPraticaPratica = '/pratiche/nuovapratica/pratica/tipo/lista',
  getListaNuovaPratica = '/pratiche/pratica/inseribili/lista',
  postAddPratica = '/pratiche/pratica',
  postAddPraticaModulo = '/pratiche/pratica/dettaglio/modulo',
  getAzioniPerPratica = '/pratiche/pratica/azione',
  getListaPraticheAggiunte = '/pratiche/pratica/lista',
  getPraticaDettaglio = '/pratiche/pratica',
  getPdfPratica = '/modulo/pdf',
  getZipPratica = '/pratiche/pratica/zip',
  getScadenze = '/pratiche/pratica/dettaglio/scadenze',
  getListaMembriPerSopralluogo = '/pratiche/appuntamento/soggetto/lista',
  getListaRuoliMembroSopralluogo = '/pratiche/appuntamento/soggetto/ruolo/decodifica/lista',
  postAddPartecipante = '/pratiche/appuntamento/soggetto',
  postDeletePartecipante = '/pratiche/appuntamento/soggetto/delete',
  // PRATICHE - COMMISSIONE
  getListaSoggetti = '/ente/soggetto/lista',
  getListaRuoli = '/ente/soggetto/ruolo/decodifica/lista',
  postAddMembro = '/ente/soggetto',
  deliteMembro = '/ente/soggetto/delete',
  // PRATICHE - REQUISITI
  getDecodificaRequisiti = '/pratiche/pratica/requisiti/esito/lista',
  getRequisitiPratica = '/pratiche/pratica/requisiti/lista',
  postRequisito = '/pratiche/pratica/requisiti',
  // STRUTTURA SCELTA
  getListaStrtturaScelta = '/struttura/lista',
  // PROFILO SCELTA
  getCambioProfilo = '/utente/login/profilo',
  // LISTA PROFILI
  getListaProfili = '/profilo/decodifica/lista'
}

export enum Navigation {
  LOGIN_ERROR_PAGE = '/login-error-page',
  LOGIN = '/login',
  VETRINA = '/main-page/vetrina',
  MAIN_PAGE = '/main-page',
  MODULO = '/test',
  DETTAGLIO_OSPITE = '/main-page/ospite-detail',
  MOVIMENTI_OSPITE = '/main-page/ospiti-movimenti',
  NEW_OSPITE = '/main-page/ospite-new',
  PRATICA = '/main-page/pratiche-vigilanza',
  DETTAGLIO_PRATICA = '/main-page/dettaglio-pratica',
  ADD_PRATICA = '/main-page/add-pratica',
  ADD_AZIONE = '/main-page/add-azione',
  REQUISITI_PRATICA = '/main-page/requisiti-pratica',
}

export enum ModuloConfigGruppoCod {
  DOC = 'doc',  // Moduli Gestione documentale
  INF = 'inf',  // Moduli informativi strutture
  RIL = 'ril',  // Moduli Rilevazioni
  QST = 'qst',  // Moduli Sondaggi
}
// PDF tasto ASSISTENZA
const NAME_FILE = 'Manuale_Utente_ASR_Applicativo_Vigilsan_V1.pdf';
@Injectable({ providedIn: 'root' })
export class Client {

  // LOGIN AND HTTP CALLS
  // devMode: boolean = true;
  devModeLogin: boolean = false;

  devMode: boolean = false;
  // devModeLogin: boolean = true;

  private _baseUrl: string = environment.endpoint;
  private LINK_ASSISTENZA: string = environment.assistenza;
  private FILE_URL = environment.manuale;
  public GESTRUTTURE_URL = environment.gestruttureUrl;
  private _token: string = '';
  private _tokenApplication: TokenType = {
    "x-access-token": ''
  };
  private _loggedUser: User = {
    profiloId: null,
    soggetto: null,
    struttura: null,
    ente: null,
    ruolo: null,
    applicativo: null,
    profili: null,
    funzioni: null
  };
  selectedProfilo: ProfiliEntity | null = null;
  private _isErrorLogin: boolean = false;
  private _tokenJwt: any;
  private _tokenJwtProfili: any;
  isStruttura: boolean = false;
  isEnte: boolean = false;
  listaProfili: ProfiliEntity[] | null = null;

  //HEADER
  opened: boolean = false;
  menuItems: MenuItem[] = [];
  selectedTab: string = '';
  textRoute: string = '';

  // MODULO
  moduloToshow: number | null = null;
  moduloCompilatoToshow: number | null = null;
  disabilitaCampi: boolean = false;
  isValidModulo: boolean = true;
  erroreModulo: boolean = false;
  warningModulo: boolean = false;
  modeDettaglio: boolean = false;
  modulo: SezioniEntity = {
    moduloId: null,
    moduloCod: null,
    moduloDesc: null,
    moduloOrd: null,
    moduloTitolo: null,
    moduloIdPadre: null,
    validitaInizio: null,
    validitaFine: null,
    dataCreazione: null,
    dataModifica: null,
    dataCancellazione: null,
    utenteCreazione: null,
    utenteModifica: null,
    utenteCancellazione: null,
    sezioni: null,
    voci: null,
    note: null
  };
  moduloToSend: SezioniEntity = {
    moduloId: null,
    moduloCod: null,
    moduloDesc: null,
    moduloOrd: null,
    moduloTitolo: null,
    moduloIdPadre: null,
    validitaInizio: null,
    validitaFine: null,
    dataCreazione: null,
    dataModifica: null,
    dataCancellazione: null,
    utenteCreazione: null,
    utenteModifica: null,
    utenteCancellazione: null,
    sezioni: null,
    voci: null,
    note: null
  };
  idStrutturaSwap: number | null = null;

  // TAB RILEVAZIONI
  private _rilevazioneTipo: string = '';
  private _listaTabRilevazioni: RilevazioniTab[] | null = null; // tab delle rilevazioni
  private _listaTabRilevazioniAggiungi: RilevazioniAggiungi[] | null = null;  // rilevazioni da aggiungere per ogni tab
  private _listaRilevazioniSwap: RilevazioniAggiungi[] | null = null;
  private _listeRilevazioniPerTab: RilevazioniPerTab[][] = [];  // Lista rilevazioni per ogni tab giÃ  aggiunte

  // TAB SONDAGGI
  listaTabSondaggi: RilevazioniTab[] | null = null; // tab delle rilevazioni
  listaTabSondaggiAggiungi: RilevazioniAggiungi[] | null = null;  // rilevazioni da aggiungere per ogni tab
  listeSondaggiPerTab: RilevazioniPerTab[][] = [];  // Lista rilevazioni per ogni tab giÃ  aggiunte

  // TAB DOCUMENTAZIONI
  resDoc: string = 'resdoc';
  documentazioneSelezionata: Documentazione = {
    moduloConfigCod: null,
    moduloConfigDesc: null,
    moduloConfigOrd: null,
    reccount: null,
    docFlgObbligatorio: false
  };
  private _listaDocumentazioni: Documentazione[] | null = null;
  private _listaDocumentazioniCompilabili: DocumentazioneCompilabile[] | null = null;
  private _listaDocumentazioniCompilate: DocumentazioneCompilata[] | null = null;


  // private _listaDocumentazioniVersioning: DocumentazionePerOpzioneDocumentazione[] | null = null;
  private _listaDocumentazioniPerSelezioneSwap: DocumentazioneCompilata[] | null = null;
  public get listaDocumentazioniPerSelezioneSwap(): DocumentazioneCompilata[] | null {
    return this._listaDocumentazioniPerSelezioneSwap;
  }
  public set listaDocumentazioniPerSelezioneSwap(value: DocumentazioneCompilata[] | null) {
    this._listaDocumentazioniPerSelezioneSwap = value;
  }


  // TAB OSPITI
  ospite: string = '';
  ingressoDal: any = null;
  ingressoAl: any = null;
  uscitaDal: any = null;
  uscitaAl: any = null;
  // dataNascitaOspite: any = null;
  private _listaOspiti: Ospite[] | null = null;
  private _listaStatiOspite: StatoOspite[] | null = null;
  statoOspiteSelezionato: StatoOspite = {
    validitaInizio: null,
    validitaFine: null,
    dataCreazione: null,
    dataModifica: null,
    dataCancellazione: null,
    utenteCreazione: null,
    utenteModifica: null,
    utenteCancellazione: null,
    ospiteStatoId: null,
    ospiteStatoCod: null,
    ospiteStatoDesc: null,
    ospiteStatoOrd: null,
    ospiteStatoHint: null,
    flgPresenza: null,
    flgPosto: null,
  };
  private _ospiteSelezionato: Ospite | null = null;
  // MOVIMENTI
  private _listaMovimentiTipo: MovimentoTipo[] | null = null;
  private _listaCondizioni: Condizione[] | null = null;

  // PRATICHE VIGILANZA
  active: number = 0;
  azioniPerPratiche: AzioniPerPratiche | null = null;
  listaPraticheAggiunte: PraticheAggiunte[] | null = null;
  collectionSize: number = 0;
  pagePraticheVigilanza: number = 1;
  pageSizePraticheVigilanza: number = 5;

  // card pratica
  statoP: PraticaStato[] | null = null;
  tipoP: PraticaTipo[] | null = null;
  selectedStatoP: PraticaStato | null = null;
  selectedTipoP: PraticaTipo | null = null;
  dateTimeP: any = null;
  dateTimeAlP: any = null;
  dateTimeTwoP: any = null;
  dateTimeAlTwoP: any = null;
  timeP = new FormControl();
  timeAlP = new FormControl();
  timeTwoP = new FormControl();
  timeAlTwoP = new FormControl();
  textDateTimeP: string = '';
  textDateTimeAlP: string = '';
  textDateTimeTwoP: string = '';
  textDateTimeAlTwoP: string = '';
  // card prescrizione
  statoUl: PrescrizioneStato[] | null = null;
  tipoUl: PrescrizioneTipo[] | null = null;
  selectedStatoUl: PrescrizioneStato | null = null;
  selectedTipoUl: PrescrizioneTipo | null = null;
  dateTime: any = null;
  dateTimeAl: any = null;
  dateTimeTwo: any = null;
  dateTimeAlTwo: any = null;
  time = new FormControl();
  timeAl = new FormControl();
  timeTwo = new FormControl();
  timeAlTwo = new FormControl();
  textDateTime: string = '';
  textDateTimeAl: string = '';
  textDateTimeTwo: string = '';
  textDateTimeAlTwo: string = '';
  // card sopralluogo
  statoUp: AppuntamentoStato[] | null = null;
  tipoUp: AppuntamentoTipo[] | null = null;
  selectedStatoUp: AppuntamentoStato | null = null;
  selectedTipoUp: AppuntamentoTipo | null = null;
  dateTimeUp: any = null;
  dateTimeAlUp: any = null;
  dateTimeTwoUp: any = null;
  dateTimeAlTwoUp: any = null;
  timeUp = new FormControl();
  timeAlUp = new FormControl();
  timeTwoUp = new FormControl();
  timeAlTwoUp = new FormControl();
  textDateTimeUp: string = '';
  textDateTimeAlUp: string = '';
  textDateTimeTwoUp: string = '';
  textDateTimeAlTwoUp: string = '';
  strutturaEnte: string | null = null;
  // PRATICHE VIGILANZA - DialogAddAzione
  textNumeroPratica: string | null = null;
  textNumeroSopralluogo: string | null = null;
  textNumeroPrescrizione: string | null = null;

  selectedAzioneDialogAddAzione: Azione | null = null;
  noteDialogAddAzione: string = '';
  selectedPraticaDettaglio: null | GenericAppPre = null;

  dataOraDialogAddAzione: any | null = null;
  textDataOraDialogAddAzione: string | null = null;
  timeDataOraDialogAddAzione!: FormControl;


  dataOraInizioDialogAddAzione: any | null = null;
  timeDataOraInizioDialogAddAzione!: FormControl;
  timeDataOraFineDialogAddAzione!: FormControl;
  // PRATICHE VIGILANZA - DialoagAddPratica
  selectedAzioneDialoagAddPratica: Azione | null = null;
  // PRATICHE VIGILANZA - Dettaglio pratica
  praticaDettaglio: SingolaPraticaDettaglio | null = null;
  selectedExistPratica: PraticheAggiunte | null = null;
  // PRATICHE VIGILANZA - Modifica attivita
  attivitaSelected: Attivita | null = null;
  // PRATICHE VIGILANZA - SCADENZIARIO
  listaScadenza: Scadenza[] = [];
  dates: Date[][] = [];
  currentMonth: number = 0;
  currentYear: number = 0;
  years: number[] = [];
  selectedYear: number = 0;
  selectedMode: string = '';
  monthIndex: number = 0;

  // PRATICHE - COMMISSIONE
  listaRuoli: RuoloCommissione[] | null = null;
  ruoloSelected: RuoloCommissione | null = null;
  listaSoggettiCommissione: SoggettoCommissione[] | null = null;

  // PRATICHE - REQUISITI
  listaDecodificaRequisiti: Requisito[] | null = null;
  genericAppPreRequisiti: GenericAppPre | null = null;
  atRequisiti: Attivita | null = null;
  listaRequisitiPraticaToClient: RequisitoPraticaLista[] | null = null;
  reqSelected: RequisitoPraticaLista | null = null;
  isLoadingDataRequisito: boolean = false;
  isSpinEmitterCardDx: boolean = false;

  constructor(private http: HttpClient) { }

  capitalize(text: string | null): string {
    if (!text) return '';
    return text.toLowerCase().replace(/\b\w/g, (char) => char.toUpperCase());
  }
  checkAzioni(azione: string): string {
    if (this.loggedUser.funzioni && this.loggedUser.funzioni.length > 0) {
      const search = this.loggedUser.funzioni.filter((e) => e.funzione === azione);
      if (search && search.length === 1) {
        if (search[0].permesso === null) {
          return 'null';
        } else {
          return search[0].permesso;
        }
      } else {
        return 'null';
      }
    } else {
      return 'null';
    }
  }
  // PDF ?
  openUrlPdf() {
    window.open(this.FILE_URL + NAME_FILE, '_blank');
  }

  gotoAssistenza() {
    window.open(this.LINK_ASSISTENZA, '_blank');
  }

  azzeraFiltriRicercaPraticheVigilanza() {
    this.strutturaEnte = null;
    this.selectedStatoP = null;
    this.selectedTipoP = null;
    this.dateTimeP = null;
    this.dateTimeAlP = null;
    this.dateTimeTwoP = null;
    this.dateTimeAlTwoP = null;
    this.timeP.patchValue(null);
    this.timeAlP.patchValue(null);
    this.timeTwoP.patchValue(null);
    this.textDateTimeP = '';
    this.timeAlTwoP.patchValue(null);
    this.textDateTimeAlP = '';
    this.textDateTimeTwoP = '';
    this.textDateTimeAlTwoP = '';
    this.selectedStatoUl = null;
    this.selectedTipoUl = null;
    this.dateTime = null;
    this.dateTimeAl = null;
    this.dateTimeTwo = null;
    this.dateTimeAlTwo = null;
    this.time.patchValue(null);
    this.timeAl.patchValue(null);
    this.timeTwo.patchValue(null);
    this.timeAlTwo.patchValue(null);
    this.textDateTime = '';
    this.textDateTimeAl = '';
    this.textDateTimeTwo = '';
    this.textDateTimeAlTwo = '';
    this.selectedStatoUp = null;
    this.selectedTipoUp = null;
    this.dateTimeUp = null;
    this.dateTimeAlUp = null;
    this.dateTimeTwoUp = null;
    this.dateTimeAlTwoUp = null;
    this.timeUp.patchValue(null);
    this.timeAlUp.patchValue(null);
    this.timeTwoUp.patchValue(null);
    this.timeAlTwoUp.patchValue(null);
    this.textDateTimeUp = '';
    this.textDateTimeAlUp = '';
    this.textDateTimeTwoUp = '';
    this.textDateTimeAlTwoUp = '';

    this.dates = [];
    this.currentMonth = 0;
    this.currentYear = 0;
    this.years = [];
    this.selectedYear = 0;
    this.selectedMode = '';
    this.monthIndex = 0;
  }

  // LOGIN -------------------------------------------------------------
  login(params: any): Observable<HttpResponse<User>> {
    //Converti i parametri in un oggetto HttpParams
    let httpParams = new HttpParams();
    if (params) {
      Object.keys(params).forEach((key) => {
        httpParams = httpParams.append(key, params[key]);
      });
    }
    const url = this.baseUrl + PathApi.login;
    return this.http.get<User>(url, { params: httpParams, observe: 'response' });
  }

  logout(): Observable<HttpResponse<any>> {
    let httpParams = new HttpParams();
    const url = this.baseUrl + PathApi.logout;
    return this.http.get<any>(url, { params: httpParams, observe: 'response' });
  }

  // STRUTTURA
  getFlagStrutture(params: any): Observable<{ chiave: string, valore: string }[]> {
    let httpParams = new HttpParams();
    if (params) {
      Object.keys(params).forEach((key) => {
        httpParams = httpParams.append(key, params[key]);
      });
    }
    const url = this.baseUrl + PathApi.getFlagStrutture;
    return this.http.get<{ chiave: string, valore: string }[]>(url, { params: httpParams });
  }
  // MODULI -------------------------------------------------------------
  getModulo(params: any): Observable<SezioniEntity> {
    //Converti i parametri in un oggetto HttpParams
    let httpParams = new HttpParams();
    if (params) {
      Object.keys(params).forEach((key) => {
        httpParams = httpParams.append(key, params[key]);
      });
    }
    const url = this.baseUrl + PathApi.getModulo;
    return this.http.get<SezioniEntity>(url, { params: httpParams });
  }

  // TAB RILEVAZIONI
  getListaTabRilevazioni(params: any): Observable<RilevazioniTab[]> {
    let httpParams = new HttpParams();
    if (params) {
      Object.keys(params).forEach((e) => {
        httpParams = httpParams.append(e, params[e]);
      });
    }
    const url = this.baseUrl + PathApi.getListaTabRilevazioni;
    return this.http.get<RilevazioniTab[]>(url, { params: httpParams });
  }
  getListaRilevazioniAggiungi(params: any): Observable<RilevazioniAggiungi[]> {
    let httpParams = new HttpParams();
    if (params) {
      Object.keys(params).forEach((e) => {
        httpParams = httpParams.append(e, params[e]);
      });
    }
    const url = this.baseUrl + PathApi.getListaRilevazioniAggiungi;
    return this.http.get<RilevazioniAggiungi[]>(url, { params: httpParams });
  }
  getListaRilevazioniPerTab(params: any): Observable<RilevazioniPerTab[]> {
    let httpParams = new HttpParams();
    if (params) {
      Object.keys(params).forEach((e) => {
        httpParams = httpParams.append(e, params[e]);
      });
    }
    const url = this.baseUrl + PathApi.getListaRilevazioniPerTab;
    return this.http.get<RilevazioniPerTab[]>(url, { params: httpParams });
  }
  postRilevazione(payload = {}): Observable<any> {
    let httpParams = new HttpParams();
    const url = this.baseUrl + PathApi.postRilevazione;
    return this.http.post<any>(url, payload, { withCredentials: true, params: httpParams });
  }
  getRilevazioniCSV(params: any): Observable<HttpResponse<Blob>> {
    let httpParams = new HttpParams();
    if (params) {
      Object.keys(params).forEach((e) => {
        httpParams = httpParams.append(e, params[e]);
      });
    }
    const url = this.baseUrl + PathApi.getRilevazioniCSV;
    return this.http.get(url, { params: httpParams, responseType: 'blob', observe: 'response' }).pipe(
      catchError((error) => {
        if (error.error instanceof Blob) {
          return new Observable<HttpResponse<Blob>>((observer) => {
            const reader = new FileReader();
            reader.onload = (e) => {
              try {
                const jsonError = JSON.parse((e.target as FileReader).result as string);
                observer.error(jsonError);
              } catch (e) {
                observer.error(error);
              }
            };
            reader.onerror = () => {
              observer.error(error);
            };
            reader.readAsText(error.error);
          });
        } else {
          return throwError(() => error);
        }
      })
    );
  }
  getRilevazioniCsvStorico(params: any): Observable<HttpResponse<Blob>> {
    let httpParams = new HttpParams();
    if (params) {
      Object.keys(params).forEach((e) => {
        httpParams = httpParams.append(e, params[e]);
      });
    }
    const url = this.baseUrl + PathApi.getRilevazioniCsvStorico;
    return this.http.get(url, { params: httpParams, responseType: 'blob', observe: 'response' }).pipe(
      catchError((error) => {
        if (error.error instanceof Blob) {
          return new Observable<HttpResponse<Blob>>((observer) => {
            const reader = new FileReader();
            reader.onload = (e) => {
              try {
                const jsonError = JSON.parse((e.target as FileReader).result as string);
                observer.error(jsonError);
              } catch (e) {
                observer.error(error);
              }
            };
            reader.onerror = () => {
              observer.error(error);
            };
            reader.readAsText(error.error);
          });
        } else {
          return throwError(() => error);
        }
      })
    );
  }

  // FILE
  postFile(payload = {}): Observable<PostFile> {
    let httpParams = new HttpParams();
    const url = this.baseUrl + PathApi.postFile;
    return this.http.post<PostFile>(url, payload, { withCredentials: true, params: httpParams });
  }
  getFile(params: any): Observable<HttpResponse<Blob>> {
    let httpParams = new HttpParams();
    if (params) {
      Object.keys(params).forEach((e) => {
        httpParams = httpParams.append(e, params[e]);
      });
    }
    const url = this.baseUrl + PathApi.getFile;
    return this.http.get(url, { params: httpParams, responseType: 'blob', observe: 'response' }).pipe(
      catchError((error) => {
        if (error.error instanceof Blob) {
          return new Observable<HttpResponse<Blob>>((observer) => {
            const reader = new FileReader();
            reader.onload = (e) => {
              try {
                const jsonError = JSON.parse((e.target as FileReader).result as string);
                observer.error(jsonError);
              } catch (e) {
                observer.error(error);
              }
            };
            reader.onerror = () => {
              observer.error(error);
            };
            reader.readAsText(error.error);
          });
        } else {
          return throwError(() => error);
        }
      })
    );
  }
  getTypeUploadFile(): Observable<TypeUploadFile[]> {
    let httpParams = new HttpParams();
    const url = this.baseUrl + PathApi.getTypeUploadFile;
    return this.http.get<TypeUploadFile[]>(url, { params: httpParams });
  }
  // DOCUMENTAZIONE
  getListaDocumentazioni(params: any): Observable<Documentazione[]> {
    let httpParams = new HttpParams();
    if (params) {
      Object.keys(params).forEach((e) => {
        httpParams = httpParams.append(e, params[e]);
      });
    }
    const url = this.baseUrl + PathApi.getListaDocumentazioni;
    return this.http.get<Documentazione[]>(url, { params: httpParams });
  }
  getDocumentazioniCompilabili(): Observable<DocumentazioneCompilabile[]> {
    let httpParams = new HttpParams();
    // if (params) {
    //   Object.keys(params).forEach((e) => {
    //     httpParams = httpParams.append(e, params[e]);
    //   });
    // }
    const url = this.baseUrl + PathApi.getDocumentazioniAggiungi;
    return this.http.get<DocumentazioneCompilabile[]>(url, { params: httpParams });
  }
  getDocumentazioniCompilate(): Observable<DocumentazioneCompilata[]> {
    let httpParams = new HttpParams();
    // if (params) {
    //   Object.keys(params).forEach((e) => {
    //     httpParams = httpParams.append(e, params[e]);
    //   });
    // }
    const url = this.baseUrl + PathApi.getDocumentazionePerListaDocumentazioni;
    return this.http.get<DocumentazioneCompilata[]>(url, { params: httpParams });
  }
  postSalvaDocumentazione(payload = {}): Observable<any> {
    let httpParams = new HttpParams();
    const url = this.baseUrl + PathApi.postDocumentazione;
    return this.http.post<any>(url, payload, { params: httpParams });
  }
  postVerificaDocumentazione(payload = {}): Observable<any> {
    let httpParams = new HttpParams();
    const url = this.baseUrl + PathApi.postVerificaDocumentazione;
    return this.http.post<any>(url, payload, { withCredentials: true, params: httpParams });
  }
  postInviaNotificaEmail(payload = {}): Observable<any> {
    let httpParams = new HttpParams();
    const url = this.baseUrl + PathApi.postInviaNotificaEmail;
    return this.http.post<any>(url, payload, { withCredentials: true, params: httpParams });
  }

  // DETTAGLIO ARPE
  getDettaglioArpe(params: any): Observable<DettaglioArpe[]> {
    //Converti i parametri in un oggetto HttpParams
    let httpParams = new HttpParams();
    if (params) {
      Object.keys(params).forEach((key) => {
        httpParams = httpParams.append(key, params[key]);
      });
    }
    const url = this.baseUrl + PathApi.getDettaglioArpe;
    return this.http.get<DettaglioArpe[]>(url, { params: httpParams });
  }

  // OSPITI
  getListaOspiti(params: any): Observable<HttpResponse<Ospite[]>> {
    //Converti i parametri in un oggetto HttpParams
    let httpParams = new HttpParams();
    if (params) {
      Object.keys(params).forEach((key) => {
        httpParams = httpParams.append(key, params[key]);
      });
    }
    const url = this.baseUrl + PathApi.getListaOspiti;
    return this.http.get<Ospite[]>(url, { params: httpParams, observe: 'response' });
  }
  getDecodificaStatiOspite(): Observable<StatoOspite[]> {
    let httpParams = new HttpParams();
    const url = this.baseUrl + PathApi.getDecodificaStatiOspite;
    return this.http.get<StatoOspite[]>(url, { params: httpParams });
  }
  getListaOspitiCSV(params: any): Observable<HttpResponse<Blob>> {
    let httpParams = new HttpParams();
    if (params) {
      Object.keys(params).forEach((e) => {
        httpParams = httpParams.append(e, params[e]);
      });
    }
    const url = this.baseUrl + PathApi.getListaOspitiCSV;
    return this.http.get(url, { params: httpParams, responseType: 'blob', observe: 'response' }).pipe(
      catchError((error) => {
        if (error.error instanceof Blob) {
          return new Observable<HttpResponse<Blob>>((observer) => {
            const reader = new FileReader();
            reader.onload = (e) => {
              try {
                const jsonError = JSON.parse((e.target as FileReader).result as string);
                observer.error(jsonError);
              } catch (e) {
                observer.error(error);
              }
            };
            reader.onerror = () => {
              observer.error(error);
            };
            reader.readAsText(error.error);
          });
        } else {
          return throwError(() => error);
        }
      })
    );
  }
  searchSoggetto(payload = {}): Observable<any> {
    let httpParams = new HttpParams();
    const url = this.baseUrl + PathApi.searchSoggetto;
    return this.http.post<any>(url, payload, { withCredentials: true, params: httpParams });
  }

  // OSPITE DETAIL
  getOspiteDetail(params: any): Observable<OspiteDetail> {
    //Converti i parametri in un oggetto HttpParams
    let httpParams = new HttpParams();
    if (params) {
      Object.keys(params).forEach((key) => {
        httpParams = httpParams.append(key, params[key]);
      });
    }
    const url = this.baseUrl + PathApi.getOspiteDetail;
    return this.http.get<OspiteDetail>(url, { params: httpParams });
  }
  getMovimentoTipo(): Observable<MovimentoTipo[]> {
    let httpParams = new HttpParams();
    const url = this.baseUrl + PathApi.getMovimentoTipo;
    return this.http.get<MovimentoTipo[]>(url, { params: httpParams });
  }
  getCondizioni(): Observable<Condizione[]> {
    let httpParams = new HttpParams();
    const url = this.baseUrl + PathApi.getCondizioni;
    return this.http.get<Condizione[]>(url, { params: httpParams });
  }
  postMovimento(payload = {}): Observable<any> {
    let httpParams = new HttpParams();
    const url = this.baseUrl + PathApi.postMovimento;
    return this.http.post<any>(url, payload, { withCredentials: true, params: httpParams });
  }
  deleteMovimento(id: any): Observable<any> {
    const url = this.baseUrl + PathApi.deleteMovimento + '/' + id;
    return this.http.delete<OspiteDetail>(url);
  }

  // TAB PRATICHE VIGILANZA
  getListaPraticaTipo(): Observable<PraticaTipo[]> {
    let httpParams = new HttpParams();
    const url = this.baseUrl + PathApi.getListaPraticaTipo;
    return this.http.get<PraticaTipo[]>(url, { params: httpParams });
  }
  getListaPraticaStato(): Observable<PraticaStato[]> {
    let httpParams = new HttpParams();
    const url = this.baseUrl + PathApi.getListaPraticaStato;
    return this.http.get<PraticaStato[]>(url, { params: httpParams });
  }
  getListaPrescrizioneTipo(): Observable<PrescrizioneTipo[]> {
    let httpParams = new HttpParams();
    const url = this.baseUrl + PathApi.getListaPrescrizioneTipo;
    return this.http.get<PrescrizioneTipo[]>(url, { params: httpParams });
  }
  getListaPrescrizioneStato(): Observable<PrescrizioneStato[]> {
    let httpParams = new HttpParams();
    const url = this.baseUrl + PathApi.getListaPrescrizioneStato;
    return this.http.get<PrescrizioneStato[]>(url, { params: httpParams });
  }
  getListaAppuntamentoTipo(): Observable<AppuntamentoTipo[]> {
    let httpParams = new HttpParams();
    const url = this.baseUrl + PathApi.getListaAppuntamentoTipo;
    return this.http.get<AppuntamentoTipo[]>(url, { params: httpParams });
  }
  getListaAppuntamentoStato(): Observable<AppuntamentoStato[]> {
    let httpParams = new HttpParams();
    const url = this.baseUrl + PathApi.getListaAppuntamentoStato;
    return this.http.get<AppuntamentoStato[]>(url, { params: httpParams });
  }

  getListaNuovaPraticaStruttura(): Observable<NuovaPraticaStrutturaTipo[]> {
    let httpParams = new HttpParams();
    const url = this.baseUrl + PathApi.getListaNuovaPraticaStruttura;
    return this.http.get<NuovaPraticaStrutturaTipo[]>(url, { params: httpParams });
  }
  getListaNuovaPraticaPratica(): Observable<NuovaPraticaPraticaTipo[]> {
    let httpParams = new HttpParams();
    const url = this.baseUrl + PathApi.getListaNuovaPraticaPratica;
    return this.http.get<NuovaPraticaPraticaTipo[]>(url, { params: httpParams });
  }
  getListaNuovaPratica(params: any): Observable<HttpResponse<RicercaPratiche[]>> {
    //Converti i parametri in un oggetto HttpParams
    let httpParams = new HttpParams();
    if (params) {
      Object.keys(params).forEach((key) => {
        httpParams = httpParams.append(key, params[key]);
      });
    }
    const url = this.baseUrl + PathApi.getListaNuovaPratica;
    return this.http.get<RicercaPratiche[]>(url, { params: httpParams, observe: 'response' });
  }
  postAddPratica(payload = {}): Observable<any> {
    let httpParams = new HttpParams();
    const url = this.baseUrl + PathApi.postAddPratica;
    return this.http.post<any>(url, payload, { withCredentials: true, params: httpParams });
  }
  postAddPraticaModulo(payload = {}): Observable<any> {
    let httpParams = new HttpParams();
    const url = this.baseUrl + PathApi.postAddPraticaModulo;
    return this.http.post<any>(url, payload, { withCredentials: true, params: httpParams });
  }
  getAzioniPerPratica(params: any): Observable<AzioniPerPratiche> {
    //Converti i parametri in un oggetto HttpParams
    let httpParams = new HttpParams();
    if (params) {
      Object.keys(params).forEach((key) => {
        httpParams = httpParams.append(key, params[key]);
      });
    }
    const url = this.baseUrl + PathApi.getAzioniPerPratica;
    return this.http.get<AzioniPerPratiche>(url, { params: httpParams });
  }
  getListaPraticheAggiunte(params: any): Observable<HttpResponse<PraticheAggiunte[]>> {
    //Converti i parametri in un oggetto HttpParams
    let httpParams = new HttpParams();
    if (params) {
      Object.keys(params).forEach((key) => {
        httpParams = httpParams.append(key, params[key]);
      });
    }
    const url = this.baseUrl + PathApi.getListaPraticheAggiunte;
    return this.http.get<PraticheAggiunte[]>(url, { params: httpParams, observe: 'response' });
  }
  getPraticaDettaglio(params: any): Observable<SingolaPraticaDettaglio> {
    //Converti i parametri in un oggetto HttpParams
    let httpParams = new HttpParams();
    if (params) {
      Object.keys(params).forEach((key) => {
        httpParams = httpParams.append(key, params[key]);
      });
    }
    const url = this.baseUrl + PathApi.getPraticaDettaglio;
    return this.http.get<SingolaPraticaDettaglio>(url, { params: httpParams });
  }
  getPdfPratica(params: any): Observable<HttpResponse<Blob>> {
    let httpParams = new HttpParams();
    if (params) {
      Object.keys(params).forEach((e) => {
        httpParams = httpParams.append(e, params[e]);
      });
    }
    const url = this.baseUrl + PathApi.getPdfPratica;
    return this.http.get(url, { params: httpParams, responseType: 'blob', observe: 'response' }).pipe(
      catchError((error) => {
        if (error.error instanceof Blob) {
          return new Observable<HttpResponse<Blob>>((observer) => {
            const reader = new FileReader();
            reader.onload = (e) => {
              try {
                const jsonError = JSON.parse((e.target as FileReader).result as string);
                observer.error(jsonError);
              } catch (e) {
                observer.error(error);
              }
            };
            reader.onerror = () => {
              observer.error(error);
            };
            reader.readAsText(error.error);
          });
        } else {
          return throwError(() => error);
        }
      })
    );
  }
  getZipPratica(params: any): Observable<HttpResponse<Blob>> {
    let httpParams = new HttpParams();
    if (params) {
      Object.keys(params).forEach((e) => {
        httpParams = httpParams.append(e, params[e]);
      });
    }
    const url = this.baseUrl + PathApi.getZipPratica;
    return this.http.get(url, { params: httpParams, responseType: 'blob', observe: 'response' }).pipe(
      catchError((error) => {
        if (error.error instanceof Blob) {
          return new Observable<HttpResponse<Blob>>((observer) => {
            const reader = new FileReader();
            reader.onload = (e) => {
              try {
                const jsonError = JSON.parse((e.target as FileReader).result as string);
                observer.error(jsonError);
              } catch (e) {
                observer.error(error);
              }
            };
            reader.onerror = () => {
              observer.error(error);
            };
            reader.readAsText(error.error);
          });
        } else {
          return throwError(() => error);
        }
      })
    );
  }
  // PRATICHE VIGILANZA - SCADENZARIO
  getScadenze(params: any): Observable<Scadenza[]> {
    let httpParams = new HttpParams();
    if (params) {
      Object.keys(params).forEach((key) => {
        httpParams = httpParams.append(key, params[key]);
      });
    }
    const url = this.baseUrl + PathApi.getScadenze;
    return this.http.get<Scadenza[]>(url, { params: httpParams });
  }
  // PRATICHE VIGILANZA - MEMBRI SOPRALLUOGHI
  getListaMembriPerSopralluogo(params: any): Observable<MembroSopralluogo[]> {
    let httpParams = new HttpParams();
    if (params) {
      Object.keys(params).forEach((key) => {
        httpParams = httpParams.append(key, params[key]);
      });
    }
    const url = this.baseUrl + PathApi.getListaMembriPerSopralluogo;
    return this.http.get<MembroSopralluogo[]>(url, { params: httpParams });
  }
  getListaRuoliMembroSopralluogo(): Observable<RuoliMembriSopralluogo[]> {
    let httpParams = new HttpParams();
    const url = this.baseUrl + PathApi.getListaRuoliMembroSopralluogo;
    return this.http.get<RuoliMembriSopralluogo[]>(url, { params: httpParams });
  }
  postAddPartecipante(payload = {}): Observable<any> {
    let httpParams = new HttpParams();
    const url = this.baseUrl + PathApi.postAddPartecipante;
    return this.http.post<any>(url, payload, { withCredentials: true, params: httpParams });
  }
  postDeletePartecipante(payload = {}): Observable<any> {
    let httpParams = new HttpParams();
    const url = this.baseUrl + PathApi.postDeletePartecipante;
    return this.http.post<any>(url, payload, { withCredentials: true, params: httpParams });
  }

  // PRATICHE - COMMISSIONE
  getListaSoggettiCommmissione(): Observable<SoggettoCommissione[]> {
    let httpParams = new HttpParams();
    const url = this.baseUrl + PathApi.getListaSoggetti;
    return this.http.get<SoggettoCommissione[]>(url, { params: httpParams });
  }
  getListaRuoli(): Observable<RuoloCommissione[]> {
    let httpParams = new HttpParams();
    const url = this.baseUrl + PathApi.getListaRuoli;
    return this.http.get<RuoloCommissione[]>(url, { params: httpParams });
  }
  postAddMembro(payload = {}): Observable<any> {
    let httpParams = new HttpParams();
    const url = this.baseUrl + PathApi.postAddMembro;
    return this.http.post<any>(url, payload, { withCredentials: true, params: httpParams });
  }
  deliteMembro(payload = {}): Observable<any> {
    let httpParams = new HttpParams();
    const url = this.baseUrl + PathApi.deliteMembro;
    return this.http.post<any>(url, payload, { withCredentials: true, params: httpParams });
  }

  // PRATICHE - REQUISITI
  getDecodificaRequisiti(): Observable<Requisito[]> {
    let httpParams = new HttpParams();
    const url = this.baseUrl + PathApi.getDecodificaRequisiti;
    return this.http.get<Requisito[]>(url, { params: httpParams });
  }
  getRequisitiPratica(params: any): Observable<RequisitoPratica[]> {
    let httpParams = new HttpParams();
    if (params) {
      Object.keys(params).forEach((key) => {
        httpParams = httpParams.append(key, params[key]);
      });
    }
    const url = this.baseUrl + PathApi.getRequisitiPratica;
    return this.http.get<RequisitoPratica[]>(url, { params: httpParams });
  }
  postRequisito(payload = {}): Observable<any> {
    let httpParams = new HttpParams();
    const url = this.baseUrl + PathApi.postRequisito;
    return this.http.post<any>(url, payload, { withCredentials: true, params: httpParams });
  }

  // STRUTTURA SCELTA
  getListaStrtturaScelta(params: any): Observable<HttpResponse<StrutturaScelta[]>> {
    //Converti i parametri in un oggetto HttpParams
    let httpParams = new HttpParams();
    if (params) {
      Object.keys(params).forEach((key) => {
        httpParams = httpParams.append(key, params[key]);
      });
    }
    const url = this.baseUrl + PathApi.getListaStrtturaScelta;
    return this.http.get<StrutturaScelta[]>(url, { params: httpParams, observe: 'response' });
  }
  // PROFILO SCELTA
  getCambioProfilo(params: any): Observable<HttpResponse<User>> {
    //Converti i parametri in un oggetto HttpParams
    let httpParams = new HttpParams();
    if (params) {
      Object.keys(params).forEach((key) => {
        httpParams = httpParams.append(key, params[key]);
      });
    }
    const url = this.baseUrl + PathApi.getCambioProfilo;
    return this.http.get<User>(url, { params: httpParams, observe: 'response' });
  }

  // LISTA PROFILI
  getListaProfili(): Observable<ProfiliEntity[]> {
    let httpParams = new HttpParams();
    const url = this.baseUrl + PathApi.getListaProfili;
    return this.http.get<ProfiliEntity[]>(url, { params: httpParams });
  }

  // GETTERS AND SETTERS -------------------------------------------------
  public get baseUrl(): string {
    return this._baseUrl;
  }
  public set baseUrl(value: string) {
    this._baseUrl = value;
  }

  public get token(): string {
    return this._token;
  }
  public set token(value: string) {
    this._token = value;
  }

  public get tokenApplication(): TokenType {
    return this._tokenApplication;
  }
  public set tokenApplication(value: TokenType) {
    this._tokenApplication = value;
  }

  public get loggedUser(): User {
    return this._loggedUser;
  }
  public set loggedUser(value: User) {
    this._loggedUser = value;
  }

  public get isErrorLogin(): boolean {
    return this._isErrorLogin;
  }
  public set isErrorLogin(value: boolean) {
    this._isErrorLogin = value;
  }

  public get tokenJwt(): any {
    return this._tokenJwt;
  }
  public set tokenJwt(value: any) {
    this._tokenJwt = value;
  }
  public get tokenJwtProfili(): any {
    return this._tokenJwtProfili;
  }
  public set tokenJwtProfili(value: any) {
    this._tokenJwtProfili = value;
  }

  public get rilevazioneTipo(): string {
    return this._rilevazioneTipo;
  }
  public set rilevazioneTipo(value: string) {
    this._rilevazioneTipo = value;
  }

  public get listaTabRilevazioni(): RilevazioniTab[] | null {
    return this._listaTabRilevazioni;
  }
  public set listaTabRilevazioni(value: RilevazioniTab[] | null) {
    this._listaTabRilevazioni = value;
  }

  public get listaTabRilevazioniAggiungi(): RilevazioniAggiungi[] | null {
    return this._listaTabRilevazioniAggiungi;
  }
  public set listaTabRilevazioniAggiungi(value: RilevazioniAggiungi[] | null) {
    this._listaTabRilevazioniAggiungi = value;
  }

  public get listaRilevazioniSwap(): RilevazioniAggiungi[] | null {
    return this._listaRilevazioniSwap;
  }
  public set listaRilevazioniSwap(value: RilevazioniAggiungi[] | null) {
    this._listaRilevazioniSwap = value;
  }

  public get listeRilevazioniPerTab(): RilevazioniPerTab[][] {
    return this._listeRilevazioniPerTab;
  }
  public set listeRilevazioniPerTab(value: RilevazioniPerTab[][]) {
    this._listeRilevazioniPerTab = value;
  }

  public get listaDocumentazioni(): Documentazione[] | null {
    return this._listaDocumentazioni;
  }
  public set listaDocumentazioni(value: Documentazione[] | null) {
    this._listaDocumentazioni = value;
  }

  public get listaDocumentazioniCompilabili(): DocumentazioneCompilabile[] | null {
    return this._listaDocumentazioniCompilabili;
  }
  public set listaDocumentazioniCompilabili(value: DocumentazioneCompilabile[] | null) {
    this._listaDocumentazioniCompilabili = value;
  }

  // public get listaDocumentazioniVersioning(): DocumentazionePerOpzioneDocumentazione[] | null {
  //   return this._listaDocumentazioniVersioning;
  // }
  // public set listaDocumentazioniVersioning(value: DocumentazionePerOpzioneDocumentazione[] | null) {
  //   this._listaDocumentazioniVersioning = value;
  // }

  public get listaDocumentazioniCompilate(): DocumentazioneCompilata[] | null {
    return this._listaDocumentazioniCompilate;
  }
  public set listaDocumentazioniCompilate(value: DocumentazioneCompilata[] | null) {
    this._listaDocumentazioniCompilate = value;
  }

  public get ospiteSelezionato(): Ospite | null {
    return this._ospiteSelezionato;
  }
  public set ospiteSelezionato(value: Ospite | null) {
    this._ospiteSelezionato = value;
  }

  public get listaOspiti(): Ospite[] | null {
    return this._listaOspiti;
  }
  public set listaOspiti(value: Ospite[] | null) {
    this._listaOspiti = value;
  }

  public get listaStatiOspite(): StatoOspite[] | null {
    return this._listaStatiOspite;
  }
  public set listaStatiOspite(value: StatoOspite[] | null) {
    this._listaStatiOspite = value;
  }

  public get listaMovimentiTipo(): MovimentoTipo[] | null {
    return this._listaMovimentiTipo;
  }
  public set listaMovimentiTipo(value: MovimentoTipo[] | null) {
    this._listaMovimentiTipo = value;
  }

  public get listaCondizioni(): Condizione[] | null {
    return this._listaCondizioni;
  }
  public set listaCondizioni(value: Condizione[] | null) {
    this._listaCondizioni = value;
  }
}
