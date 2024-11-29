/*
 * Copyright Regione Piemonte - 2024
 * SPDX-License-Identifier: EUPL-1.2
 */

import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RequisitiPraticheComponent } from './requisiti-pratiche.component';

describe('RequisitiPraticheComponent', () => {
  let component: RequisitiPraticheComponent;
  let fixture: ComponentFixture<RequisitiPraticheComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RequisitiPraticheComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(RequisitiPraticheComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
