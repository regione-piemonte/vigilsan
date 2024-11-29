/*
 * Copyright Regione Piemonte - 2024
 * SPDX-License-Identifier: EUPL-1.2
 */

import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OspiteDetailComponent } from './ospite-detail.component';

describe('OspiteDetailComponent', () => {
  let component: OspiteDetailComponent;
  let fixture: ComponentFixture<OspiteDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ OspiteDetailComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(OspiteDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
