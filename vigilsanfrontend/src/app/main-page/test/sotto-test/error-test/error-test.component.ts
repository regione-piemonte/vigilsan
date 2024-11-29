/*
 * Copyright Regione Piemonte - 2024
 * SPDX-License-Identifier: EUPL-1.2
 */

import { Component, Input, OnInit } from '@angular/core';
import { FileTipiEntity, VociEntity } from 'src/app/DTO/SezioniEntity';
import { TypeModule, TypeRules } from '../../test.component';

@Component({
  selector: 'app-error-test',
  templateUrl: './error-test.component.html',
  styleUrls: ['./error-test.component.css']
})
export class ErrorTestComponent implements OnInit {

  @Input() voce: VociEntity | null = null;
  @Input() file?: FileTipiEntity | null = null;

  typeRules: typeof TypeRules = TypeRules;
  enum: typeof TypeModule = TypeModule;

  constructor() { }

  ngOnInit(): void {
  }

  takeError(typeError: string) {
    if (this.voce?.regole) {
      let error = this.voce.regole.find((e) => e.moduloVoceRegolaExec === typeError);
      if (error) {
        return error.moduloVoceRegolaErrore;
      }
    }
    return '';
  }

  // checkFile(): boolean {
  //   if (this.file?.fileTipoObbligatorio && !this.file.fileId) {
  //     return true;
  //   }
  //   return false;
  // }

}
