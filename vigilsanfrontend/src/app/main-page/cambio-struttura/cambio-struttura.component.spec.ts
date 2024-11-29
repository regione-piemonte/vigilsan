/*
 * Copyright Regione Piemonte - 2024
 * SPDX-License-Identifier: EUPL-1.2
 */

import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CambioStrutturaComponent } from './cambio-struttura.component';

describe('CambioStrutturaComponent', () => {
  let component: CambioStrutturaComponent;
  let fixture: ComponentFixture<CambioStrutturaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CambioStrutturaComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CambioStrutturaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
