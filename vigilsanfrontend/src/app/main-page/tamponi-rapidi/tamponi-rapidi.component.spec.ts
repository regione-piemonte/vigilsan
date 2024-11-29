/*
 * Copyright Regione Piemonte - 2024
 * SPDX-License-Identifier: EUPL-1.2
 */

import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TamponiRapidiComponent } from './tamponi-rapidi.component';

describe('TamponiRapidiComponent', () => {
  let component: TamponiRapidiComponent;
  let fixture: ComponentFixture<TamponiRapidiComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TamponiRapidiComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(TamponiRapidiComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
