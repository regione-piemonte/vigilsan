/*
 * Copyright Regione Piemonte - 2024
 * SPDX-License-Identifier: EUPL-1.2
 */

import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ScaricoCsvComponent } from './scarico-csv.component';

describe('ScaricoCsvComponent', () => {
  let component: ScaricoCsvComponent;
  let fixture: ComponentFixture<ScaricoCsvComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ScaricoCsvComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ScaricoCsvComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
