/*
 * Copyright Regione Piemonte - 2024
 * SPDX-License-Identifier: EUPL-1.2
 */

export interface Ospite {
  soggettoId: number | null;
  codiceFiscale: string | null;
  nome: string | null;
  cognome: string | null;
  presenteConfiguratore: boolean;
  strutturaSoggetto: StrutturaSoggetto;
  ruoloStruttura: RuoloStruttura;
  ultimoStato: UltimoStato;
  dataPrimoIngresso: number | null;
  dataUltimaUscita: number | null;
  dataNascita: number | null;
  strutturaCategoriaDesc: string | null;
}
export interface StrutturaSoggetto {
  strSoggId: number | null;
  strutturaId: number | null;
  soggettoId: number | null;
  ruoloStrutturaSoggettoId?: number | null;
}
export interface RuoloStruttura {
  ruoloStrutturaSoggettoId?: number | null;
  ruoloStrutturaSoggettoCod: string | null;
  ruoloStrutturaSoggettoDesc: string | null;
}
export interface UltimoStato {
  ospiteStatoId: number | null;
  ospiteStatoCod: string | null;
  ospiteStatoDesc: string | null;
  ospiteStatoOrd?: string | null;
  ospiteStatoHint?: string | null;
  flgPresenza?: boolean | null;
  flgPosto?: boolean | null;
  dataMovimento: number | null;
}
