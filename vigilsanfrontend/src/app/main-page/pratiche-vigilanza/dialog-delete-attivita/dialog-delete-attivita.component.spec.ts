/*
 * Copyright Regione Piemonte - 2024
 * SPDX-License-Identifier: EUPL-1.2
 */

import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DialogDeleteAttivitaComponent } from './dialog-delete-attivita.component';

describe('DialogDeleteAttivitaComponent', () => {
  let component: DialogDeleteAttivitaComponent;
  let fixture: ComponentFixture<DialogDeleteAttivitaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DialogDeleteAttivitaComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(DialogDeleteAttivitaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
