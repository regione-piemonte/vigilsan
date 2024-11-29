/*
 * Copyright Regione Piemonte - 2024
 * SPDX-License-Identifier: EUPL-1.2
 */

import { LOCALE_ID, NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';

import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatListModule } from '@angular/material/list';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatToolbarModule } from '@angular/material/toolbar';

import { registerLocaleData } from '@angular/common';
import { HTTP_INTERCEPTORS, HttpClientModule, HttpClientXsrfModule } from '@angular/common/http';
import localeIt from '@angular/common/locales/it';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MomentDateAdapter } from '@angular/material-moment-adapter';
import { MatAutocompleteModule } from '@angular/material/autocomplete';
import { MatCardModule } from '@angular/material/card';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { DateAdapter, MAT_DATE_FORMATS, MAT_DATE_LOCALE, MatNativeDateModule } from '@angular/material/core';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatDialogModule } from '@angular/material/dialog';
import { MatExpansionModule } from '@angular/material/expansion';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatMenuModule } from '@angular/material/menu';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatSelectModule } from '@angular/material/select';
import { MatTableModule } from '@angular/material/table';
import { MatTabsModule } from '@angular/material/tabs';
import { MatTooltipModule } from '@angular/material/tooltip';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { NgxMatTimepickerModule } from 'ngx-mat-timepicker';
import { ToastrModule } from 'ngx-toastr';
import { RequestInterceptor } from 'src/interceptors/request-interceptor';
import { Client } from './Client';
import { ErrorLoginPageComponent } from './error-login-page/error-login-page.component';
import { FooterComponent } from './footer/footer.component';
import { HeaderComponent } from './header/header.component';
import { LoginPageComponent } from './login-page/login-page.component';
import { AdesioneBuonoResidenzialitaComponent } from './main-page/adesione-buono-residenzialita/adesione-buono-residenzialita.component';
import { DettaglioArpeComponent } from './main-page/dettaglio-arpe/dettaglio-arpe.component';
import { DialogAddDocumentazioneComponent } from './main-page/documentazione/dialog-add-documentazione/dialog-add-documentazione.component';
import { DialogDettaglioDocumentazioneComponent } from './main-page/documentazione/dialog-dettaglio-documentazione/dialog-dettaglio-documentazione.component';
import { DocumentazioneComponent } from './main-page/documentazione/documentazione.component';
import { MainPageComponent } from './main-page/main-page.component';
import { ModuloComponent } from './main-page/modulo/modulo.component';
import { ConfirmDeleteComponent } from './main-page/ospiti-movimenti/ospite-detail/confirm-delete/confirm-delete.component';
import { DialogAddMovimentoComponent } from './main-page/ospiti-movimenti/ospite-detail/dialog-add-movimento/dialog-add-movimento.component';
import { OspiteDetailComponent } from './main-page/ospiti-movimenti/ospite-detail/ospite-detail.component';
import { ShowStickiesComponent } from './main-page/ospiti-movimenti/ospite-detail/show-stickies/show-stickies.component';
import { OspiteNewComponent } from './main-page/ospiti-movimenti/ospite-new/ospite-new.component';
import { OspitiMovimentiComponent } from './main-page/ospiti-movimenti/ospiti-movimenti.component';
import { DettaglioPraticaComponent } from './main-page/pratiche-vigilanza/dettaglio-pratica/dettaglio-pratica.component';
import { DialogAddAzioneComponent } from './main-page/pratiche-vigilanza/dialog-add-azione/dialog-add-azione.component';
import { DialogAddPraticaComponent } from './main-page/pratiche-vigilanza/dialog-add-pratica/dialog-add-pratica.component';
import { PraticheVigilanzaComponent } from './main-page/pratiche-vigilanza/pratiche-vigilanza.component';
import { ReleaseNotesComponent } from './main-page/release-notes/release-notes.component';
import { RendicontazioneBuonoResidenzialitaComponent } from './main-page/rendicontazione-buono-residenzialita/rendicontazione-buono-residenzialita.component';
import { DialogAggiungiComponent } from './main-page/rilevazioni/dialog-aggiungi/dialog-aggiungi.component';
import { RilevazioniComponent } from './main-page/rilevazioni/rilevazioni.component';
import { ScaricoCsvStoricoComponent } from './main-page/rilevazioni/scarico-csv-storico/scarico-csv-storico.component';
import { ScaricoCsvComponent } from './main-page/rilevazioni/scarico-csv/scarico-csv.component';
import { TamponiRapidiComponent } from './main-page/tamponi-rapidi/tamponi-rapidi.component';
import { ErrorTestComponent } from './main-page/test/sotto-test/error-test/error-test.component';
import { SottoTestComponent } from './main-page/test/sotto-test/sotto-test.component';
import { TestComponent } from './main-page/test/test.component';
import { CambioStrutturaComponent } from './main-page/cambio-struttura/cambio-struttura.component';
import { CambioProfiloComponent } from './main-page/cambio-profilo/cambio-profilo.component';
import { FullCalendarComponent } from './main-page/full-calendar/full-calendar.component';
import { MatStepperModule } from '@angular/material/stepper';
import { VetrinaComponent } from './vetrina/vetrina.component';
import { NuovoMembroComponent } from './main-page/commissione/nuovo-membro/nuovo-membro.component';
import { ModificaModuloComponent } from './main-page/pratiche-vigilanza/modifica-modulo/modifica-modulo.component';
import { DialogVerificaComponent } from './main-page/documentazione/dialog-verifica/dialog-verifica.component';
import { ProfiloComponent } from './main-page/profilo/profilo.component';
import { CommissioneComponent } from './main-page/commissione/commissione.component';
import { DialogConfermaEliminazioneComponent } from './main-page/commissione/dialog-conferma-eliminazione/dialog-conferma-eliminazione.component';
import { DialogDeleteAttivitaComponent } from './main-page/pratiche-vigilanza/dialog-delete-attivita/dialog-delete-attivita.component';
import { MebroSopralluogoComponent } from './main-page/pratiche-vigilanza/membro-sopralluogo/mebro-sopralluogo.component';
import { DialogEliminaMembroComponent } from './main-page/pratiche-vigilanza/membro-sopralluogo/dialog-elimina-membro/dialog-elimina-membro.component';
import { DialogAggiungiPartecipanteComponent } from './main-page/pratiche-vigilanza/membro-sopralluogo/dialog-aggiungi-partecipante/dialog-aggiungi-partecipante.component';
import { RequisitiPraticheComponent } from './main-page/pratiche-vigilanza/requisiti-pratiche/requisiti-pratiche.component';
import { MatProgressBarModule } from '@angular/material/progress-bar';
import { RequisitoComponent } from './main-page/pratiche-vigilanza/requisiti-pratiche/requisito/requisito.component';
import { SondaggiComponent } from './main-page/sondaggi/sondaggi.component';

const MY_DATE_FORMATS = {
  parse: {
    dateInput: 'DD/MM/YYYY', // Formato di input
  },
  display: {
    dateInput: 'DD/MM/YYYY', // Formato di visualizzazione
    monthYearLabel: 'MMMM YYYY',
    dateA11yLabel: 'LL',
    monthYearA11yLabel: 'MMMM YYYY'
  }
};

// Registra il locale italiano
registerLocaleData(localeIt);

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    FooterComponent,
    MainPageComponent,
    TestComponent,
    LoginPageComponent,
    ErrorLoginPageComponent,
    RilevazioniComponent,
    DocumentazioneComponent,
    DialogAggiungiComponent,
    AdesioneBuonoResidenzialitaComponent,
    RendicontazioneBuonoResidenzialitaComponent,
    OspitiMovimentiComponent,
    TamponiRapidiComponent,
    ModuloComponent,
    SottoTestComponent,
    ErrorTestComponent,
    DialogAddDocumentazioneComponent,
    DettaglioArpeComponent,
    OspiteDetailComponent,
    DialogAddMovimentoComponent,
    ConfirmDeleteComponent,
    ShowStickiesComponent,
    OspiteNewComponent,
    ScaricoCsvComponent,
    ReleaseNotesComponent,
    DialogDettaglioDocumentazioneComponent,
    ScaricoCsvStoricoComponent,
    PraticheVigilanzaComponent,
    DialogAddPraticaComponent,
    DialogAddAzioneComponent,
    DettaglioPraticaComponent,
    CambioStrutturaComponent,
    CambioProfiloComponent,
    FullCalendarComponent,
    VetrinaComponent,
    NuovoMembroComponent,
    ModificaModuloComponent,
    DialogVerificaComponent,
    ProfiloComponent,
    CommissioneComponent,
    DialogConfermaEliminazioneComponent,
    DialogDeleteAttivitaComponent,
    MebroSopralluogoComponent,
    DialogEliminaMembroComponent,
    DialogAggiungiPartecipanteComponent,
    RequisitiPraticheComponent,
    RequisitoComponent,
    SondaggiComponent
  ],
  imports: [
    HttpClientModule,
    HttpClientXsrfModule.withOptions({
      cookieName: 'XSRF-TOKEN',
      headerName: 'X-XSRF-TOKEN',
    }),
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MatButtonModule,
    MatSidenavModule,
    MatToolbarModule,
    MatIconModule,
    MatListModule,
    MatCheckboxModule,
    MatMenuModule,
    FormsModule,
    ReactiveFormsModule,
    NgbModule,
    MatFormFieldModule,
    MatInputModule,
    MatSelectModule,
    MatCardModule,
    MatDatepickerModule,
    MatTabsModule,
    MatNativeDateModule,
    MatExpansionModule,
    MatDialogModule,
    MatTooltipModule,
    MatProgressSpinnerModule,
    MatTableModule,
    MatPaginatorModule,
    MatAutocompleteModule,
    MatStepperModule,
    MatProgressBarModule,
    BrowserAnimationsModule, // required animations module
    ToastrModule.forRoot({
      timeOut: 4000,
      positionClass: 'toast-bottom-center',
      preventDuplicates: true,
      // closeButton: true,
      progressBar: true,
      countDuplicates: true,
    }), // ToastrModule added
    NgxMatTimepickerModule
  ],
  providers: [
    Client,
    { provide: HTTP_INTERCEPTORS, useClass: RequestInterceptor, multi: true },
    { provide: DateAdapter, useClass: MomentDateAdapter },
    { provide: MAT_DATE_FORMATS, useValue: MY_DATE_FORMATS },
    { provide: MAT_DATE_LOCALE, useValue: 'it' }, // Imposta la lingua italiana
    { provide: LOCALE_ID, useValue: 'it' } // Usa 'it' per l'italiano
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
