/*
 * Copyright Regione Piemonte - 2024
 * SPDX-License-Identifier: EUPL-1.2
 */

import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OspiteNewComponent } from './ospite-new.component';

describe('OspiteNewComponent', () => {
  let component: OspiteNewComponent;
  let fixture: ComponentFixture<OspiteNewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ OspiteNewComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(OspiteNewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
