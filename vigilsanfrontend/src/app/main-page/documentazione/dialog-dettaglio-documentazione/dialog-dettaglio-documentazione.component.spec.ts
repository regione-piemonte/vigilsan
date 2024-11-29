/*
 * Copyright Regione Piemonte - 2024
 * SPDX-License-Identifier: EUPL-1.2
 */

import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DialogDettaglioDocumentazioneComponent } from './dialog-dettaglio-documentazione.component';

describe('DialogDettaglioDocumentazioneComponent', () => {
  let component: DialogDettaglioDocumentazioneComponent;
  let fixture: ComponentFixture<DialogDettaglioDocumentazioneComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DialogDettaglioDocumentazioneComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(DialogDettaglioDocumentazioneComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
