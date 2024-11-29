/*
 * Copyright Regione Piemonte - 2024
 * SPDX-License-Identifier: EUPL-1.2
 */

import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DettaglioPraticaComponent } from './dettaglio-pratica.component';

describe('DettaglioPraticaComponent', () => {
  let component: DettaglioPraticaComponent;
  let fixture: ComponentFixture<DettaglioPraticaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DettaglioPraticaComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(DettaglioPraticaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
