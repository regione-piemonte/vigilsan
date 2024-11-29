/*
 * Copyright Regione Piemonte - 2024
 * SPDX-License-Identifier: EUPL-1.2
 */

import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DialogAggiungiPartecipanteComponent } from './dialog-aggiungi-partecipante.component';

describe('DialogAggiungiPartecipanteComponent', () => {
  let component: DialogAggiungiPartecipanteComponent;
  let fixture: ComponentFixture<DialogAggiungiPartecipanteComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DialogAggiungiPartecipanteComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(DialogAggiungiPartecipanteComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
