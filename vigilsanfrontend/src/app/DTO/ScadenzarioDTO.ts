/*
 * Copyright Regione Piemonte - 2024
 * SPDX-License-Identifier: EUPL-1.2
 */

export interface Scadenza {
  dataoraScadenza: number | null,
  flgScadenza: string | null,
  scadenzaDesc: string | null,
  praticaId: number | null,
  prescrizioneId: number | null,
  appuntamentoId: number | null,
  enteId: number | null,
  enteCod: string | null,
  enteDesc: string | null,
  strutturaId: number | null,
  strutturaCod: string | null,
  strutturaCodArpe: string | null,
  strutturaDesc: string | null,
  praticaTipoId: number | null,
  praticaTipoCod: string | null,
  praticaTipoDesc: string | null,
  dataoraApertura: number | null,
  dataoraChiusura: number | null,
  prescrizioneTipoId: number | null,
  prescrizioneTipoCod: string | null,
  prescrizioneTipoDesc: string | null,
  appuntamentoTipoId: number | null,
  appuntamentoTipoCod: string | null,
  appuntamentoTipoDesc: string | null,
  oraDaMostrare?: string | null,
}
