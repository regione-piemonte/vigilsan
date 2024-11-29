/*
 * Copyright Regione Piemonte - 2024
 * SPDX-License-Identifier: EUPL-1.2
 */

import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OspitiMovimentiComponent } from './ospiti-movimenti.component';

describe('OspitiMovimentiComponent', () => {
  let component: OspitiMovimentiComponent;
  let fixture: ComponentFixture<OspitiMovimentiComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ OspitiMovimentiComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(OspitiMovimentiComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
