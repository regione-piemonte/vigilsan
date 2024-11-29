/*
 * Copyright Regione Piemonte - 2024
 * SPDX-License-Identifier: EUPL-1.2
 */

import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RilevazioniComponent } from './rilevazioni.component';

describe('RilevazioniComponent', () => {
  let component: RilevazioniComponent;
  let fixture: ComponentFixture<RilevazioniComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RilevazioniComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(RilevazioniComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
