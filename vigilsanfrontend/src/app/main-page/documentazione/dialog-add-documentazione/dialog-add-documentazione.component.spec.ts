/*
 * Copyright Regione Piemonte - 2024
 * SPDX-License-Identifier: EUPL-1.2
 */

import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DialogAddDocumentazioneComponent } from './dialog-add-documentazione.component';

describe('DialogAddDocumentazioneComponent', () => {
  let component: DialogAddDocumentazioneComponent;
  let fixture: ComponentFixture<DialogAddDocumentazioneComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DialogAddDocumentazioneComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(DialogAddDocumentazioneComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
