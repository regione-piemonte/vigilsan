/*
 * Copyright Regione Piemonte - 2024
 * SPDX-License-Identifier: EUPL-1.2
 */

import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SottoTestComponent } from './sotto-test.component';

describe('SottoTestComponent', () => {
  let component: SottoTestComponent;
  let fixture: ComponentFixture<SottoTestComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SottoTestComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SottoTestComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
