/*
 * Copyright Regione Piemonte - 2024
 * SPDX-License-Identifier: EUPL-1.2
 */

import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DialogConfermaEliminazioneComponent } from './dialog-conferma-eliminazione.component';

describe('DialogConfermaEliminazioneComponent', () => {
  let component: DialogConfermaEliminazioneComponent;
  let fixture: ComponentFixture<DialogConfermaEliminazioneComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DialogConfermaEliminazioneComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(DialogConfermaEliminazioneComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
