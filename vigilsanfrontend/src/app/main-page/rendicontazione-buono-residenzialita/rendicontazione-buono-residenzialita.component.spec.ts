/*
 * Copyright Regione Piemonte - 2024
 * SPDX-License-Identifier: EUPL-1.2
 */

import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RendicontazioneBuonoResidenzialitaComponent } from './rendicontazione-buono-residenzialita.component';

describe('RendicontazioneBuonoResidenzialitaComponent', () => {
  let component: RendicontazioneBuonoResidenzialitaComponent;
  let fixture: ComponentFixture<RendicontazioneBuonoResidenzialitaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RendicontazioneBuonoResidenzialitaComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(RendicontazioneBuonoResidenzialitaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
