/*
 * Copyright Regione Piemonte - 2024
 * SPDX-License-Identifier: EUPL-1.2
 */

import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DialogAddPraticaComponent } from './dialog-add-pratica.component';

describe('DialogAddPraticaComponent', () => {
  let component: DialogAddPraticaComponent;
  let fixture: ComponentFixture<DialogAddPraticaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DialogAddPraticaComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(DialogAddPraticaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
