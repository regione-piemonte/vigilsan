/*
 * Copyright Regione Piemonte - 2024
 * SPDX-License-Identifier: EUPL-1.2
 */

export interface DettaglioArpe {
  arpeStrDettId: number | null,
  strutturaId: number | null,
  arpeStrutturaTipoId: number | null,
  arpeAssistenzaTipoId: number | null,
  arpeAttivitaId: number | null,
  arpeAttivitaClasseId: number | null,
  arpeDisciplinaId?: number | null,
  arpeAssistenzaTipo: ArpeAssistenzaTipo;
  arpeAttivitaClasse: ArpeAttivitaClasse;
  arpeAttivita: ArpeAttivita;
  arpeDisciplina?: ArpeDisciplina;
  arpeStrutturaTipo: ArpeStrutturaTipo;
}
export interface ArpeAssistenzaTipo {
  arpeAssistenzaTipoId: number | null,
  arpeAssistenzaTipoCod: string | null,
  arpeAssistenzaTipoDesc: string | null,
}
export interface ArpeAttivitaClasse {
  arpeAttivitaClasseId: number | null,
  arpeAttivitaClasseCod: string | null,
  arpeAttivitaClasseDesc: string | null,
}
export interface ArpeDisciplina {
  arpeDisciplinaId: number | null,
  arpeDisciplinaCod: string | null,
  arpeDisciplinaDesc: string | null
}
export interface ArpeAttivita {
  arpeAttivitaId: number | null,
  arpeAttivitaCod: string | null,
  arpeAttivitaDesc: string | null,
}
export interface ArpeStrutturaTipo {
  arpeStrutturaTipoId: number | null,
  arpeStrutturaTipoCod: string | null,
  arpeStrutturaTipoDesc: string | null,
}
