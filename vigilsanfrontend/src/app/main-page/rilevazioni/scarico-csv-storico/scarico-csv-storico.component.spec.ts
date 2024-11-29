/*
 * Copyright Regione Piemonte - 2024
 * SPDX-License-Identifier: EUPL-1.2
 */

import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ScaricoCsvStoricoComponent } from './scarico-csv-storico.component';

describe('ScaricoCsvStoricoComponent', () => {
  let component: ScaricoCsvStoricoComponent;
  let fixture: ComponentFixture<ScaricoCsvStoricoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ScaricoCsvStoricoComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ScaricoCsvStoricoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
