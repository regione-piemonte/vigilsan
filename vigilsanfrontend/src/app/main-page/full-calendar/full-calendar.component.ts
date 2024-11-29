/*
 * Copyright Regione Piemonte - 2024
 * SPDX-License-Identifier: EUPL-1.2
 */

import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { lastValueFrom } from 'rxjs';
import { Client, Navigation } from 'src/app/Client';
import { Scadenza } from 'src/app/DTO/ScadenzarioDTO';
import { ErrorHandlerService } from '../ErrorHandlerService';

@Component({
  selector: 'app-full-calendar',
  templateUrl: './full-calendar.component.html',
  styleUrls: ['./full-calendar.component.css']
})
export class FullCalendarComponent implements OnInit {

  modes: string[] = ['Mese', 'Settimana'];
  columns: string[] = ["LunedÃ¬", "MartedÃ¬", "MercoledÃ¬", "GiovedÃ¬", "VenerdÃ¬", "Sabato", "Domenica"];
  mesi: string[] = ["Gennaio", "Febbraio", "Marzo", "Aprile", "Maggio", "Giugno", "Luglio", "Agosto", "Settembre", "Ottobre", "Novembre", "Dicembre"];

  highlightedCells: { [key: string]: boolean } = {};

  isSpinEmitter: boolean = false;

  lenghtListaScadenze: number = 0;

  constructor(private errorHandlerService: ErrorHandlerService, public client: Client, private router: Router) { }

  async ngOnInit() {
    // CALENDARIO
    if (this.client.currentMonth === 0) {
      this.client.currentMonth = new Date().getMonth() + 1;
    }
    if (this.client.currentYear === 0) {
      // Calcolo gli anni in modo dinamico
      this.client.currentYear = new Date().getFullYear();
    }
    const differenceYear = this.client.currentYear - 2024;
    if (!isNaN(differenceYear) && differenceYear > 0) {
      for (let i = 0; i < differenceYear; i++) {
        this.client.years.push(2024 + i);
      }
    } else {
      this.client.years.push(2024);
    }

    if (this.client.selectedYear === 0) {
      this.client.selectedYear = this.client.years[0];
    }

    if (this.client.dates.length === 0) {
      // Ottengo le date per il mese corrente
      this.client.dates = this.getCalendarDates(this.client.currentYear, this.client.currentMonth);
    }

    if (this.client.selectedMode === '') {
      // Selected mode initial
      this.client.selectedMode = this.modes[0];
    }

    // SCADENZARIO
    await this.getScadenze(this.client.dates[0][0], this.client.dates[this.client.dates.length - 1][this.client.dates[0].length - 1]);
  }

  // SCADENZARIO
  async getScadenze(dataDa: Date, dataA: Date | null) {
    let params = {
      data_da: `${dataDa.getFullYear()}-${dataDa.getMonth() + 1}-${dataDa.getDate()}`,
      data_a: dataA ? `${dataA.getFullYear()}-${dataA.getMonth() + 1}-${dataA.getDate()}` : null,
    };
    await lastValueFrom(this.client.getScadenze(params))
      .then(data => {
        if (data !== null) {
          this.client.listaScadenza = data;
        }
      })
      .catch(
        error => {
          this.isSpinEmitter = false;
          this.errorHandlerService.handleError(error, 'getScadenze');
        }
      );
  }

  getScadentePerData(data: Date): Scadenza[] | null {
    const year = data.getFullYear();
    const month = data.getMonth() + 1;
    const day = data.getDate();
    const scadenzeFiltrate = this.client.listaScadenza.filter((e) => {
      if (!e.dataoraScadenza) {
        return false; // Se non esiste una data di scadenza, escludi l'elemento
      }
      const dataFiltro = new Date(e.dataoraScadenza);
      const scadenzaYear = dataFiltro.getFullYear();
      const scadenzaMonth = dataFiltro.getMonth() + 1;
      const scadenzaDay = dataFiltro.getDate();
      e.oraDaMostrare = `${dataFiltro.getHours() <= 9 ? '0' + dataFiltro.getHours() : dataFiltro.getHours()}:${dataFiltro.getMinutes() <= 9 ? '0' + dataFiltro.getMinutes() : dataFiltro.getMinutes()}`;
      return scadenzaYear === year && scadenzaMonth === month && scadenzaDay === day;
    });
    this.lenghtListaScadenze = scadenzeFiltrate.length;
    return scadenzeFiltrate;
  }

  goToPraticaDettaglio(praticaId: number, praticaTipoId: number) {
    this.client.selectedExistPratica = {
      praticaId: praticaId,
      praticaTipoId: praticaTipoId,
      dataoraApertura: null,
      dataoraChiusura: null,
      enteId: null,
      appuntamenti: [],
      attivita: [],
      prescrizioni: [],
      struttura: {
        comuneId: null,
        cap: null,
        coordSrid: null,
        coordX: null,
        coordY: null,
        strutturaCod: null,
        indirizzo: null,
        strutturaCodArpe: null,
        strutturaDesc: null,
        strutturaId: null,
        strutturaNaturaId: null,
        strutturaTipoId: null,
        strutturaAccreditamentoId: null,
        strutturaTitolaritaId: null,
      },
      strutturaId: null,
      tipo: {
        praticaTipoCod: null,
        praticaTipoDesc: null,
        praticaTipoId: null,
      },
    }
    this.router.navigate([Navigation.DETTAGLIO_PRATICA], { skipLocationChange: true });
  }


  // CALENDARIO
  onMouseEnter(i: number, j: number) {
    this.highlightedCells[`${i}-${j}`] = true;
  }

  onMouseLeave(i: number, j: number) {
    this.highlightedCells[`${i}-${j}`] = false;
  }

  isHighlighted(i: number, j: number): boolean {
    return !!this.highlightedCells[`${i}-${j}`];
  }

  getCalendarDates(year: number, month: number) {
    // Mese in JavaScript va da 0 (gennaio) a 11 (dicembre)
    month = month - 1; // Convertiamo da 1-12 a 0-11

    let dates = [];
    let firstDayOfMonth = new Date(year, month, 1); // Primo giorno del mese
    let firstDayOfWeek = new Date(firstDayOfMonth); // Primo giorno della settimana del primo giono del mese

    // Trova il primo giorno della settimana che include il primo giorno del mese
    while (firstDayOfWeek.getDay() !== 1) {
      firstDayOfWeek.setDate(firstDayOfWeek.getDate() - 1);
    }

    // Aggiungi le date al risultato finchÃ© non copriamo tutto il mese selezionato
    let currentDate = new Date(firstDayOfWeek);
    while (true) {
      let week = [];
      for (let i = 0; i < 7; i++) {
        week.push(new Date(currentDate));
        currentDate.setDate(currentDate.getDate() + 1);
      }
      dates.push(week);
      // Se l'ultima data della settimana corrente Ã¨ nel mese successivo e il giorno della settimana Ã¨ domenica
      if (currentDate.getMonth() > month && currentDate.getDay() === 1) {
        break;
      }
      if (currentDate.getFullYear() > this.client.currentYear || currentDate.getFullYear() < this.client.currentYear) {
        break;
      }
    }
    return dates;
  }

  checkWeek() {
    for (let i = 0; i < this.client.dates[this.client.monthIndex].length; i++) {
      for (let j = 0; j < this.client.dates[this.client.monthIndex].length - i; j++) {
        if (i !== j) {
          if (this.client.dates[this.client.monthIndex][i].getMonth() !== this.client.dates[this.client.monthIndex][j].getMonth()) {
            return true;
          }
        }
      }
    }
    return false;
  }

  async previusMonth() {
    this.isSpinEmitter = true;
    if (this.client.selectedMode === 'Mese') {
      this.client.currentMonth -= 1;
      this.client.dates = this.getCalendarDates(this.client.selectedYear, this.client.currentMonth);
      // SCADENZARIO
      await this.getScadenze(this.client.dates[0][0], this.client.dates[this.client.dates.length - 1][this.client.dates[0].length - 1]);
    } else if (this.client.selectedMode === 'Settimana') {
      if (this.client.monthIndex > 0) {
        this.client.monthIndex--;
      } else {
        this.client.currentMonth -= 1;
        this.client.dates = this.getCalendarDates(this.client.selectedYear, this.client.currentMonth);
        this.client.monthIndex = this.client.dates.length - 1;
        if (this.checkWeek()) {
          this.client.monthIndex = this.client.dates.length - 2;
        }
        // SCADENZARIO
        await this.getScadenze(this.client.dates[0][0], this.client.dates[this.client.dates.length - 1][this.client.dates[0].length - 1]);
        this.isSpinEmitter = false;
        return;
      }
    }
    this.isSpinEmitter = false;
  }

  async nextMonth() {
    this.isSpinEmitter = true;
    if (this.client.selectedMode === 'Mese') {
      this.client.currentMonth += 1;
      this.client.dates = this.getCalendarDates(this.client.selectedYear, this.client.currentMonth);
      // SCADENZARIO
      await this.getScadenze(this.client.dates[0][0], this.client.dates[this.client.dates.length - 1][this.client.dates[0].length - 1]);
    } else if (this.client.selectedMode === 'Settimana') {
      if (this.client.monthIndex < 4) {
        this.client.monthIndex++;
      } else {
        this.client.currentMonth += 1;
        this.client.dates = this.getCalendarDates(this.client.selectedYear, this.client.currentMonth);
        this.client.monthIndex = 0;
        if (this.checkWeek()) {
          this.client.monthIndex = 1;
        }
        // SCADENZARIO
        await this.getScadenze(this.client.dates[0][0], this.client.dates[this.client.dates.length - 1][this.client.dates[0].length - 1]);
        this.isSpinEmitter = false;
        return;
      }
    }
    this.isSpinEmitter = false;
  }

  async onSelectedMonth(month: string) {
    this.isSpinEmitter = true;
    this.client.monthIndex = 0;
    const index = this.mesi.indexOf(month);
    this.client.currentMonth = index + 1;
    this.client.dates = this.getCalendarDates(this.client.selectedYear, this.client.currentMonth);
    // SCADENZARIO
    await this.getScadenze(this.client.dates[0][0], this.client.dates[this.client.dates.length - 1][this.client.dates[0].length - 1]);
    this.isSpinEmitter = false;
  }

  onSelectedYear(year: number) {
  }

  onSelectedMode(mode: string) {
    this.isSpinEmitter = true;
    this.client.selectedMode = mode;
    this.isSpinEmitter = false;
  }

  isToday(data: Date) {
    const today = new Date();
    return data.getDate() === today.getDate() &&
      data.getMonth() === today.getMonth() &&
      data.getFullYear() === today.getFullYear();
  }

  lastFirstDay(data: Date) {
    if (data.getMonth() + 1 !== this.client.currentMonth) {
      if (data.getDate() === 1) {
        return this.mesi[data.getMonth()].slice(0, 3);
      }
      const nextMonth = new Date(data.getFullYear(), data.getMonth() + 1, 1); // Primo giorno del mese successivo
      const lastDayOfMonth = new Date(nextMonth.getTime() - 1); // Ultimo giorno del mese corrente
      if (data.getDate() === lastDayOfMonth.getDate()) {
        return this.mesi[data.getMonth()].slice(0, 3);
      }
    }
    return '';
  }

  async goToToday() {
    this.isSpinEmitter = true;
    const today = new Date();
    this.client.currentMonth = today.getMonth() + 1;
    this.client.dates = this.getCalendarDates(this.client.selectedYear, this.client.currentMonth);
    // SCADENZARIO
    await this.getScadenze(this.client.dates[0][0], this.client.dates[this.client.dates.length - 1][this.client.dates[0].length - 1]);
    if (this.client.selectedMode === 'Settimana') {
      for (let i = 0; i < this.client.dates.length; i++) {
        for (let j = 0; j < this.client.dates[i].length; j++) {
          if (today.getDate() === this.client.dates[i][j].getDate()) {
            this.client.monthIndex = i;
          }
        }
      }
    }
    this.isSpinEmitter = false;
  }

}
