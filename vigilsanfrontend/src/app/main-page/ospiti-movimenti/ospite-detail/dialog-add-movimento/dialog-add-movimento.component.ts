/*
 * Copyright Regione Piemonte - 2024
 * SPDX-License-Identifier: EUPL-1.2
 */

import { Component, OnInit } from '@angular/core';
import { FormControl } from '@angular/forms';
import { MatDialogRef } from '@angular/material/dialog';
import { lastValueFrom } from 'rxjs';
import { Client } from 'src/app/Client';
import { Condizione } from 'src/app/DTO/CondizioneDTO';
import { MovimentoTipo } from 'src/app/DTO/MovimentoTipoDTO';
import { Categorie } from 'src/app/DTO/User';
import { ErrorHandlerService } from 'src/app/main-page/ErrorHandlerService';

@Component({
  selector: 'app-dialog-add-movimento',
  templateUrl: './dialog-add-movimento.component.html',
  styleUrls: ['./dialog-add-movimento.component.css']
})
export class DialogAddMovimentoComponent implements OnInit {
  // autocomplite
  myControl = new FormControl();
  options: string[] = ['One', 'Two', 'Three'];
  optionsFiltered: string[] = ['One', 'Two', 'Three'];
  // filteredOptions: Observable<string[]> | null = null;


  condizioneSelezionata: Condizione | null = null;
  tipoMovimentoSelezionato: MovimentoTipo | null = null;
  categoriaSelezionata: Categorie | null = null;
  dataMovimento: string | null = null;
  note: string | null = null;

  constructor(public client: Client, public dialogRef: MatDialogRef<DialogAddMovimentoComponent>, private errorHandlerService: ErrorHandlerService) { }

  ngOnInit(): void {
    // this.filteredOptions = this.myControl.valueChanges.pipe(
    //   startWith(''),
    //   map((value: string) => this._filter(value)),
    // );
  }

  private _filter(value: string): string[] {
    const filterValue = value.toLowerCase();
    return this.options.filter(option => option.toLowerCase().includes(filterValue));
  }

  onInputChange(event: Event) {
    const inputValue = (event.target as HTMLInputElement).value;
    // Fai qualcosa con il valore dell'input
    console.log('Valore dell\'input:', inputValue);
    this.optionsFiltered = this._filter(inputValue);
  }

  async salvaMovimento() {
    const payload = {
      dataMovimento: this.dataMovimento ? new Date(this.dataMovimento).getTime() : this.dataMovimento,
      ospiteMovimentoTipoId: this.tipoMovimentoSelezionato?.ospiteMovimentoTipoId,
      ospiteCondizioniId: this.condizioneSelezionata?.ospiteCondizioniId,
      strutturaIdOd: null,
      note: this.note,
      soggettoId: this.client.ospiteSelezionato?.soggettoId,
      strutturaCategoriaId: this.categoriaSelezionata ? this.categoriaSelezionata.strutturaCategoriaId : null
    }
    await lastValueFrom(this.client.postMovimento(payload))
      .then(data => {
        this.dialogRef.close('add');
        this.errorHandlerService.displaySuccessMessage('Movimento aggiunto con successo.');
      })
      .catch(
        error => {
          this.errorHandlerService.handleError(error, 'postMovimento');
        }
      );
  }
}
