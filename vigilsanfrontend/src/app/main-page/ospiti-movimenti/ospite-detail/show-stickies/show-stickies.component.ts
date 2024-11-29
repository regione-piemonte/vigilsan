/*
 * Copyright Regione Piemonte - 2024
 * SPDX-License-Identifier: EUPL-1.2
 */

import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';

@Component({
  selector: 'app-show-stickies',
  templateUrl: './show-stickies.component.html',
  styleUrls: ['./show-stickies.component.css']
})
export class ShowStickiesComponent implements OnInit {

  note: string = '';

  constructor(@Inject(MAT_DIALOG_DATA) public data: any) {
    ({ note: this.note} = data);
  }

  ngOnInit(): void {
  }

}
