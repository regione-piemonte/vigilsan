/*
 * Copyright Regione Piemonte - 2024
 * SPDX-License-Identifier: EUPL-1.2
 */

export interface Requisito {
  clreqEsitoId: number | null,
  clreqEsitoCod: string | null,
  clreqEsitoDesc: string | null,
  clreqEsitoOrd: string | null,
}

export interface RequisitoPratica {
  clreqId: number,
  clreqCod: string | null,
  clreqDesc: string | null,
  clreqOrd: string,
  clreqHint: string | null,
  clreqIdPadre: number | null,
  moduloId: number | null,
  flgSelezionato: boolean | null,
  flgSelezionabile: boolean | null,
  flgConforme: boolean,
  checked: boolean
}


export interface RequisitoPraticaLista {
  clreqId: number,
  clreqCod: string | null,
  clreqDesc: string | null,
  clreqOrd: string,
  clreqHint: string | null,
  clreqIdPadre: number | null,
  moduloId: number | null,
  flgSelezionato: boolean | null,
  flgSelezionabile: boolean | null,
  flgConforme: boolean,
  list: RequisitoPraticaLista[],
  mouseHover: boolean,
  isSelected: boolean,
  checked: boolean
}
