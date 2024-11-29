/*
 * Copyright Regione Piemonte - 2024
 * SPDX-License-Identifier: EUPL-1.2
 */

export interface StrutturaScelta {
  strutturaId: number | null;
  strutturaCod: string | null;
  strutturaCodConfiguratore?: null;
  strutturaCodArpe: string | null;
  strutturaDesc: string | null;
  strutturaTipoId: number | null;
  comuneId: number | null;
  indirizzo: string | null;
  cap: string | null;
  coordSrid: string | null;
  coordX: string | null;
  coordY: string | null;
  strutturaNaturaId: number | null;
  strutturaAccreditamentoId?: number | null;
  strutturaTitolaritaId: number | null | null;
}
