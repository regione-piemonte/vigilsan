/*
 * Copyright Regione Piemonte - 2024
 * SPDX-License-Identifier: EUPL-1.2
 */

import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ModificaModuloComponent } from './modifica-modulo.component';

describe('ModificaModuloComponent', () => {
  let component: ModificaModuloComponent;
  let fixture: ComponentFixture<ModificaModuloComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ModificaModuloComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ModificaModuloComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
