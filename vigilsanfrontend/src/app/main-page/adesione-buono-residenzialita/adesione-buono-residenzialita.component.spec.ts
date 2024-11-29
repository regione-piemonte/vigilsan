/*
 * Copyright Regione Piemonte - 2024
 * SPDX-License-Identifier: EUPL-1.2
 */

import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdesioneBuonoResidenzialitaComponent } from './adesione-buono-residenzialita.component';

describe('AdesioneBuonoResidenzialitaComponent', () => {
  let component: AdesioneBuonoResidenzialitaComponent;
  let fixture: ComponentFixture<AdesioneBuonoResidenzialitaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AdesioneBuonoResidenzialitaComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AdesioneBuonoResidenzialitaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
