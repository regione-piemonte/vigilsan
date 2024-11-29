/*
 * Copyright Regione Piemonte - 2024
 * SPDX-License-Identifier: EUPL-1.2
 */

import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { DettaglioArpe } from 'src/app/DTO/DettaglioArpeDTO';

@Component({
  selector: 'app-dettaglio-arpe',
  templateUrl: './dettaglio-arpe.component.html',
  styleUrls: ['./dettaglio-arpe.component.css']
})
export class DettaglioArpeComponent implements OnInit {

  listaSettaglioArpe: DettaglioArpe[];
  columns: string[] = ['Tipo struttura', 'Tipo assistenza', 'AttivitÃ ', 'Classe attivitÃ '];

  constructor(@Inject(MAT_DIALOG_DATA) public data: any) {
    ({ listaSettaglioArpe: this.listaSettaglioArpe} = data);
  }

  ngOnInit(): void {
    if (this.listaSettaglioArpe[0].arpeDisciplina) {
      this.columns.push('Disciplina');
    }
  }

}
