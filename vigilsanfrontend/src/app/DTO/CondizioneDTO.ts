/*
 * Copyright Regione Piemonte - 2024
 * SPDX-License-Identifier: EUPL-1.2
 */

export interface Condizione {
  ospiteCondizioniId: number | null;
  ospiteCondizioniCod: string | null;
  ospiteCondizioniDesc: string | null;
  ospiteCondizioniOrd: string | null;
  ospiteCondizioniHint?: string | null;
}
