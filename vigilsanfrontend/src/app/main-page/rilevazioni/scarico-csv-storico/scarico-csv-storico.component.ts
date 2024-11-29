/*
 * Copyright Regione Piemonte - 2024
 * SPDX-License-Identifier: EUPL-1.2
 */

import { Component, Inject, OnInit } from '@angular/core';
import { lastValueFrom } from 'rxjs';
import { MtParamsCSV, ScaricoCsvComponent } from '../scarico-csv/scarico-csv.component';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { Client } from 'src/app/Client';
import { ErrorHandlerService } from '../../ErrorHandlerService';
import { FormControl, Validators } from '@angular/forms';


export interface MtParamsCsvStorico {
  data_rilevazione_da?: string,
  data_rilevazione_a?: string,
  tipo?: string
}

@Component({
  selector: 'app-scarico-csv-storico',
  templateUrl: './scarico-csv-storico.component.html',
  styleUrls: ['./scarico-csv-storico.component.css']
})
export class ScaricoCsvStoricoComponent implements OnInit {
  // moduloConfigCod: string | null = null;

  dataDa = new FormControl();
  dataA = new FormControl();
  tipo = new FormControl('', [Validators.required]);
  listaTipi: string[] = ['POSTI', 'PERSONALE', 'TAMPONI', 'OSPITI', 'DECESSI', 'VISITATORI'];

  loadCsv: boolean = false;

  constructor(@Inject(MAT_DIALOG_DATA) public data: any, public client: Client, private errorHandlerService: ErrorHandlerService,
    public dialogRef: MatDialogRef<ScaricoCsvComponent>) {
  }

  ngOnInit(): void {
  }


  async scaricaCsv() {
    this.loadCsv = true;
    let params: MtParamsCsvStorico = {};
    if (this.dataDa.value) {
      const dateDa = new Date(this.dataDa.value);
      const yearDa = dateDa.getFullYear();
      const monthDa = dateDa.getMonth() + 1;
      const dayDa = dateDa.getDate();
      params.data_rilevazione_da = `${yearDa}-${monthDa}-${dayDa}`;
    }
    if (this.dataA.value) {
      const dateA = new Date(this.dataA.value);
      const yearA = dateA.getFullYear();
      const monthA = dateA.getMonth() + 1;
      const dayA = dateA.getDate();
      params.data_rilevazione_a = `${yearA}-${monthA}-${dayA}`;
    }
    if (this.tipo.value) {
      params.tipo = this.tipo.value;
    }

    await lastValueFrom(this.client.getRilevazioniCsvStorico(params))
    .then(blob => {
        const headers = blob.headers;
        if (blob.headers) {
          const fileNameHeader = headers.get('Content-Disposition');
          if (fileNameHeader) {
            const parts = fileNameHeader.split(';');
            const partsSwap = parts[1].split('=');
            const fileName = partsSwap[1].replace(/"/g, '');
            if (blob.body) {
              let fileURL = URL.createObjectURL(blob.body);
              const a = document.createElement('a');
              document.body.appendChild(a);
              a.style.display = 'none';
              a.href = fileURL;
              a.download = fileName;
              a.click();
              window.URL.revokeObjectURL(fileURL);
              document.body.removeChild(a);
            }
          }
        }
        this.loadCsv = false;
        this.dialogRef.close();
      })
      .catch(
        error => {
          this.loadCsv = false;
          this.errorHandlerService.handleError(error, 'getRilevazioniCsvStorico');
          this.dialogRef.close();
        }
      );
  }

}
