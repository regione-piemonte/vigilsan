/*
 * Copyright Regione Piemonte - 2024
 * SPDX-License-Identifier: EUPL-1.2
 */

import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ErrorLoginPageComponent } from './error-login-page/error-login-page.component';
import { LoginPageComponent } from './login-page/login-page.component';
import { DocumentazioneComponent } from './main-page/documentazione/documentazione.component';
import { MainPageComponent } from './main-page/main-page.component';
import { RilevazioniComponent } from './main-page/rilevazioni/rilevazioni.component';
import { TestComponent } from './main-page/test/test.component';
import { AdesioneBuonoResidenzialitaComponent } from './main-page/adesione-buono-residenzialita/adesione-buono-residenzialita.component';
import { RendicontazioneBuonoResidenzialitaComponent } from './main-page/rendicontazione-buono-residenzialita/rendicontazione-buono-residenzialita.component';
import { OspitiMovimentiComponent } from './main-page/ospiti-movimenti/ospiti-movimenti.component';
import { TamponiRapidiComponent } from './main-page/tamponi-rapidi/tamponi-rapidi.component';
import { OspiteDetailComponent } from './main-page/ospiti-movimenti/ospite-detail/ospite-detail.component';
import { OspiteNewComponent } from './main-page/ospiti-movimenti/ospite-new/ospite-new.component';
import { PraticheVigilanzaComponent } from './main-page/pratiche-vigilanza/pratiche-vigilanza.component';
import { DettaglioPraticaComponent } from './main-page/pratiche-vigilanza/dettaglio-pratica/dettaglio-pratica.component';
import { FullCalendarComponent } from './main-page/full-calendar/full-calendar.component';
import { DialogAddPraticaComponent } from './main-page/pratiche-vigilanza/dialog-add-pratica/dialog-add-pratica.component';
import { DialogAddAzioneComponent } from './main-page/pratiche-vigilanza/dialog-add-azione/dialog-add-azione.component';
import { VetrinaComponent } from './vetrina/vetrina.component';
import { ProfiloComponent } from './main-page/profilo/profilo.component';
import { CommissioneComponent } from './main-page/commissione/commissione.component';
import { SondaggiComponent } from './main-page/sondaggi/sondaggi.component';
import { RequisitiPraticheComponent } from './main-page/pratiche-vigilanza/requisiti-pratiche/requisiti-pratiche.component';

const routes: Routes = [
  { path: '', component: LoginPageComponent, pathMatch: 'full' },
  { path: 'login', component: LoginPageComponent },
  { path: 'login-error-page', component: ErrorLoginPageComponent },
  {
    path: 'main-page', component: MainPageComponent, children: [
      { path: 'vetrina', component: VetrinaComponent },
      { path: 'profilo', component: ProfiloComponent },
      { path: 'test', component: TestComponent },
      { path: 'full-calendar', component: FullCalendarComponent },
      { path: 'rilevazioni', component: RilevazioniComponent },
      { path: 'documentazione', component: DocumentazioneComponent },
      { path: 'adesione-buono-residenzialita', component: AdesioneBuonoResidenzialitaComponent },
      { path: 'rendicontazione-buono-residenzialita', component: RendicontazioneBuonoResidenzialitaComponent },
      { path: 'ospiti-movimenti', component: OspitiMovimentiComponent },
      { path: 'ospite-new', component: OspiteNewComponent },
      { path: 'ospite-detail', component: OspiteDetailComponent },
      { path: 'tamponi-rapidi', component: TamponiRapidiComponent },
      { path: 'questionario', component: SondaggiComponent },
      { path: 'commissione', component: CommissioneComponent },
      { path: 'pratiche-vigilanza/:choice', component: PraticheVigilanzaComponent },
      { path: 'dettaglio-pratica', component: DettaglioPraticaComponent },
      { path: 'add-pratica', component: DialogAddPraticaComponent },
      { path: 'add-azione/:choice', component: DialogAddAzioneComponent },
      { path: 'requisiti-pratica', component: RequisitiPraticheComponent },
    ]
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
