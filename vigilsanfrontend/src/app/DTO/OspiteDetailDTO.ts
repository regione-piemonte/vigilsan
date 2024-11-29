/*
 * Copyright Regione Piemonte - 2024
 * SPDX-License-Identifier: EUPL-1.2
 */

import { Categorie } from "./User";

export interface OspiteDetail {
  soggettoId: number | null;
  codiceFiscale: string | null;
  nome: string | null;
  cognome: string | null;
  presenteConfiguratore: boolean | null;
  dataNascita: number | null;
  ospiteMovimento?: (OspiteMovimentoDetail)[] | null;
}
export interface OspiteMovimentoDetail {
  ospiteMovimentoId: number | null;
  dataMovimento: number | null;
  strSoggId: number | null;
  ospiteMovimentoTipoId: number | null;
  ospiteCondizioniId?: number | null;
  strutturaIdOd?: number | null;
  note: string | null;
  ospiteMovimentoTipo: OspiteMovimentoTipo | null;
  ospiteCondizioni?: OspiteCondizioni | null;
  strutturaCategoria: Categorie | null;
}
export interface OspiteMovimentoTipo {
  ospiteMovimentoTipoId: number | null;
  ospiteMovimentoTipoCod: string | null;
  ospiteMovimentoTipoDesc: string | null;
  ospiteMovimentoTipoOrd: string | null;
  ospiteMovimentoTipoHint?: string | null;
  ospiteStatoId?: number | null;
  ospiteStato: OspiteStato | null;
}
export interface OspiteStato {
  ospiteStatoId: number | null;
  ospiteStatoCod: string | null;
  ospiteStatoDesc: string | null;
  ospiteStatoOrd: number | null;
  ospiteStatoHint: string | null;
  flgPresenza: boolean | null;
  flgPosto: boolean | null;
}
export interface OspiteCondizioni {
  ospiteCondizioniId: number | null;
  ospiteCondizioniCod: string | null;
  ospiteCondizioniDesc: string | null;
  ospiteCondizioniOrd: string | null;
  ospiteCondizioniHint?: string | null;
}
