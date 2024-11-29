/*
 * Copyright Regione Piemonte - 2024
 * SPDX-License-Identifier: EUPL-1.2
 */

import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SondaggiComponent } from './sondaggi.component';

describe('SondaggiComponent', () => {
  let component: SondaggiComponent;
  let fixture: ComponentFixture<SondaggiComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SondaggiComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SondaggiComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
