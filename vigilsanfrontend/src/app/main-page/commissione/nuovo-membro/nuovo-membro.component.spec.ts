/*
 * Copyright Regione Piemonte - 2024
 * SPDX-License-Identifier: EUPL-1.2
 */

import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NuovoMembroComponent } from './nuovo-membro.component';

describe('NuovoMembroComponent', () => {
  let component: NuovoMembroComponent;
  let fixture: ComponentFixture<NuovoMembroComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ NuovoMembroComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(NuovoMembroComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
