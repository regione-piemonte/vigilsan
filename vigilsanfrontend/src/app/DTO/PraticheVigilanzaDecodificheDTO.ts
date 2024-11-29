/*
 * Copyright Regione Piemonte - 2024
 * SPDX-License-Identifier: EUPL-1.2
 */

export interface PraticaTipo {
  praticaTipoId: number | null,
  praticaTipoCod: string | null,
  praticaTipoDesc: string | null
}
export interface PraticaStato {
  praticaStatoId: number | null,
  praticaStatoCod: string | null,
  praticaStatoDesc: string | null,
  praticaStatoFinale: boolean | null
}
export interface PrescrizioneTipo {
  prescrizioneTipoId: number | null,
  prescrizioneTipoCod: string | null,
  prescrizioneTipoDesc: string | null
}
export interface PrescrizioneStato {
  prescrizioneStatoId: number | null,
  prescrizioneStatoCod: string | null,
  prescrizioneStatoDesc: string | null,
  prescrizioneStatoFinale: boolean | null
}
export interface AppuntamentoTipo {
  appuntamentoTipoId: number | null,
  appuntamentoTipoCod: string | null,
  appuntamentoTipoDesc: string | null
}
export interface AppuntamentoStato {
  appuntamentoStatoId: number | null,
  appuntamentoStatoCod: string | null,
  appuntamentoStatoDesc: string | null,
  appuntamentoStatoFinale: boolean | null
}

export interface NuovaPraticaStrutturaTipo {
  strutturaTipoId: number | null,
  strutturaTipoCod: string | null,
  strutturaTipoDesc: string | null
}
export interface NuovaPraticaPraticaTipo {
  praticaTipoId: number | null,
  praticaTipoCod: string | null,
  praticaTipoDesc: string | null
}

// GetAzioniPerPratiche
// export interface AzioniPerPratiche {
//   pratica: Pratica[] | null,
//   prescrizione: Prescrizione[] | null,
//   appuntamento: Appuntamento[] | null
// }
export interface AzioniPerPratiche {
  pratica: Azione[] | null,
  prescrizione: Azione[] | null,
  appuntamento: Azione[] | null
}
export interface Azione {
  azioneId: number | null,
  azioneCod: string | null,
  azioneDesc: string | null,
  prescrizioneTipoId: number | null,
  appuntamentoTipoId: number | null,
  azioneIniziale: boolean | null,
  azioneInizialePratica: boolean,
  azioneInizialePrescrizione: boolean,
  azioneInizialeAppuntamento: boolean,
  statoIdLista: number[]
}
