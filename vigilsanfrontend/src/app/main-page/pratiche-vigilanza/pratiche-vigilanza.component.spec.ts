/*
 * Copyright Regione Piemonte - 2024
 * SPDX-License-Identifier: EUPL-1.2
 */

import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PraticheVigilanzaComponent } from './pratiche-vigilanza.component';

describe('PraticheVigilanzaComponent', () => {
  let component: PraticheVigilanzaComponent;
  let fixture: ComponentFixture<PraticheVigilanzaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PraticheVigilanzaComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PraticheVigilanzaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
