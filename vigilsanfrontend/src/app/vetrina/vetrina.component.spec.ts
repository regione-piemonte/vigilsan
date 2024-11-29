/*
 * Copyright Regione Piemonte - 2024
 * SPDX-License-Identifier: EUPL-1.2
 */

import { ComponentFixture, TestBed } from '@angular/core/testing';

import { VetrinaComponent } from './vetrina.component';

describe('VetrinaComponent', () => {
  let component: VetrinaComponent;
  let fixture: ComponentFixture<VetrinaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ VetrinaComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(VetrinaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
