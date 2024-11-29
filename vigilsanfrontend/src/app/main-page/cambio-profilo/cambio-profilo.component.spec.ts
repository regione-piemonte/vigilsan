/*
 * Copyright Regione Piemonte - 2024
 * SPDX-License-Identifier: EUPL-1.2
 */

import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CambioProfiloComponent } from './cambio-profilo.component';

describe('CambioProfiloComponent', () => {
  let component: CambioProfiloComponent;
  let fixture: ComponentFixture<CambioProfiloComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CambioProfiloComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CambioProfiloComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
