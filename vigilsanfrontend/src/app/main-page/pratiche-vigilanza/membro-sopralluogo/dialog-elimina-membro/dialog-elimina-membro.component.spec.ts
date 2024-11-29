/*
 * Copyright Regione Piemonte - 2024
 * SPDX-License-Identifier: EUPL-1.2
 */

import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DialogEliminaMembroComponent } from './dialog-elimina-membro.component';

describe('DialogEliminaMembroComponent', () => {
  let component: DialogEliminaMembroComponent;
  let fixture: ComponentFixture<DialogEliminaMembroComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DialogEliminaMembroComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(DialogEliminaMembroComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
