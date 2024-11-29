/*
 * Copyright Regione Piemonte - 2024
 * SPDX-License-Identifier: EUPL-1.2
 */

import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DettaglioArpeComponent } from './dettaglio-arpe.component';

describe('DettaglioArpeComponent', () => {
  let component: DettaglioArpeComponent;
  let fixture: ComponentFixture<DettaglioArpeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DettaglioArpeComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(DettaglioArpeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
