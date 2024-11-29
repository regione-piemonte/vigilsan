/*
 * Copyright Regione Piemonte - 2024
 * SPDX-License-Identifier: EUPL-1.2
 */

import { AfterViewInit, Component, Inject, OnInit } from '@angular/core';
import { FormControl } from '@angular/forms';
import { lastValueFrom } from 'rxjs';
import { Client } from 'src/app/Client';
import { Ospite } from 'src/app/DTO/OspiteDTO';
import { ErrorHandlerService } from '../../ErrorHandlerService';
import { DateUtilities } from 'src/app/dateUtilities';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { SoggettoCommissione } from 'src/app/DTO/CommissioneDTO';

@Component({
  selector: 'app-nuovo-membro',
  templateUrl: './nuovo-membro.component.html',
  styleUrls: ['./nuovo-membro.component.css']
})
export class NuovoMembroComponent implements OnInit, AfterViewInit {

  isSpinEmitter: boolean = false;
  isLoadingNewMembro: boolean = false;
  soggettoTrovato: boolean = false;

  cf: string | null = null;
  cognome: string | null = null;
  nome: string | null = null;

  // DATE
  public formatTimePicket: number = 24;

  dataNascita: any;
  inizioValidita: any;
  fineValidita: any;


  membro: SoggettoCommissione | null = null;

  constructor(public client: Client, private errorHandlerService: ErrorHandlerService,
    public dialogRef: MatDialogRef<NuovoMembroComponent>, @Inject(MAT_DIALOG_DATA) public data: any) {
    if (data) {
      ({ membro: this.membro } = data);
    }
  }
  ngAfterViewInit(): void {
    setTimeout(() => {
      this.client.textRoute = 'Nuovo Membro Commissione';
    }, 0);
  }

  ngOnInit(): void {
    this.isSpinEmitter = true;
    if (this.membro && this.membro.soggetto) {
      this.soggettoTrovato = true;
      this.cognome = this.membro.soggetto.cognome;
      this.nome = this.membro.soggetto.nome;
      if (this.membro.soggetto.dataNascita) {
        const dataNascita = new Date(this.membro.soggetto.dataNascita);
        this.dataNascita = {
          year: dataNascita.getFullYear(),
          month: dataNascita.getMonth() + 1,
          day: dataNascita.getDate()
        }
      }
      if (this.membro.validitaInizio) {
        const dataInizioValidita = new Date(this.membro.validitaInizio);
        this.inizioValidita = {
          year: dataInizioValidita.getFullYear(),
          month: dataInizioValidita.getMonth() + 1,
          day: dataInizioValidita.getDate()
        }
      }
      if (this.membro.validitaFine) {
        const dataFineValidita = new Date(this.membro.validitaFine);
        this.fineValidita = {
          year: dataFineValidita.getFullYear(),
          month: dataFineValidita.getMonth() + 1,
          day: dataFineValidita.getDate()
        }
      }
      if (this.client.listaRuoli) {
        const search = this.client.listaRuoli.find(ruolo => ruolo.ruoloEnteSoggettoId === (this.membro && this.membro.ruoloEnteSoggetto ? this.membro.ruoloEnteSoggetto.ruoloEnteSoggettoId : 0));
        if (search) {
          this.client.ruoloSelected = search;
        }
      }
    } else {
      this.soggettoTrovato = false;
    }
    this.isSpinEmitter = false;
  }

  async sendNewMembro() {
    this.isSpinEmitter = true;
    interface MyParams {
      enteSoggId?: number | null;
      soggettoId: number | null;
      ruoloEnteSoggettoId: number | null;
      validitaInizio: number | null;
      validitaFine: number | null;
    }
    if (this.client.ruoloSelected && this.client.ruoloSelected.ruoloEnteSoggettoId) {
      let ospiteSelezionato;
      let enteSoggId = null;
      if (this.client.ospiteSelezionato) {
        ospiteSelezionato = this.client.ospiteSelezionato ? this.client.ospiteSelezionato.soggettoId : null;
      } else if (this.membro) {
        ospiteSelezionato = this.membro.soggettoId ? this.membro.soggettoId : null;
        enteSoggId = this.membro.enteSoggId ? this.membro.enteSoggId : null;
      }
      let params: MyParams = {
        soggettoId: ospiteSelezionato ? ospiteSelezionato : null,
        ruoloEnteSoggettoId: this.client.ruoloSelected && this.client.ruoloSelected.ruoloEnteSoggettoId ? this.client.ruoloSelected.ruoloEnteSoggettoId : null,
        validitaInizio: this.inizioValidita ? DateUtilities.getDateMsFromDatePicker(this.inizioValidita) : null,
        validitaFine: this.fineValidita ? DateUtilities.getDateMsFromDatePicker(this.fineValidita) : null
      }
      if (enteSoggId) {
        params.enteSoggId = enteSoggId;
      }
      const loginObservable = this.client.postAddMembro(params);
      const data = await lastValueFrom(loginObservable)
        .catch(
          error => {
            this.isSpinEmitter = false;
            this.errorHandlerService.handleError(error, 'postAddMembro');
          }
        );
      if (data) {
        this.dialogRef.close(true);
      }
    }
    this.isSpinEmitter = false;
  }

  async cerca() {
    this.isSpinEmitter = true;
    this.soggettoTrovato = false;
    const payload = {
      codiceFiscale: this.cf
    }
    await lastValueFrom(this.client.searchSoggetto(payload))
      .then(data => {
        this.errorHandlerService.displaySuccessMessage('Soggetto trovato.');
        let ospite: Ospite = data;
        this.client.ospiteSelezionato = ospite;
        this.cognome = this.client.ospiteSelezionato.cognome;
        this.nome = this.client.ospiteSelezionato.nome;
        if (this.client.ospiteSelezionato.dataNascita) {
          const dataNascita = new Date(this.client.ospiteSelezionato.dataNascita);
          this.dataNascita = {
            year: dataNascita.getFullYear(),
            month: dataNascita.getMonth() + 1,
            day: dataNascita.getDate()
          }
        }
        this.soggettoTrovato = true;
      })
      .catch(
        error => {
          this.soggettoTrovato = false;
          this.errorHandlerService.handleError(error, 'searchSoggetto');
        }
      );
    this.isSpinEmitter = false;
  }

  eraseData(type: string) {
    switch (type) {
      case 'dataNascita':
        this.dataNascita = null;
        break;
      case 'inizioValidita':
        this.inizioValidita = null;
        break;
      case 'fineValidita':
        this.fineValidita = null;
        break;
      default:
        break;
    }
  }
}
