/*
 * Copyright Regione Piemonte - 2024
 * SPDX-License-Identifier: EUPL-1.2
 */

import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MebroSopralluogoComponent } from './mebro-sopralluogo.component';

describe('MebroSopralluogoComponent', () => {
  let component: MebroSopralluogoComponent;
  let fixture: ComponentFixture<MebroSopralluogoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MebroSopralluogoComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MebroSopralluogoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
