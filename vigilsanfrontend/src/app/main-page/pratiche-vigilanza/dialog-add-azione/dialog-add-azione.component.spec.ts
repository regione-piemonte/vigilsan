/*
 * Copyright Regione Piemonte - 2024
 * SPDX-License-Identifier: EUPL-1.2
 */

import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DialogAddAzioneComponent } from './dialog-add-azione.component';

describe('DialogAddAzioneComponent', () => {
  let component: DialogAddAzioneComponent;
  let fixture: ComponentFixture<DialogAddAzioneComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DialogAddAzioneComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(DialogAddAzioneComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
