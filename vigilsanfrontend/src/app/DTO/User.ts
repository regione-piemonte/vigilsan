/*
 * Copyright Regione Piemonte - 2024
 * SPDX-License-Identifier: EUPL-1.2
 */


export interface User {
  profiloId: number | null;
  soggetto: Soggetto | null;
  struttura: Struttura | null;
  ente?: Ente | null;
  ruolo: Ruolo | null;
  applicativo: Applicativo | null;
  profili?: (ProfiliEntity)[] | null;
  funzioni: Funzione[] | null;
}

export interface Funzione {
  funzione: string;
  permesso: string | null;
}

export interface Ente {
  enteId: number | null;
  enteCod: string | null;
  enteCodConfiguratore: string | null;
  enteDesc: string | null;
  enteTipoId: number | null;
  enteTipo: EnteTipo | null;
}
export interface EnteTipo {
  enteTipoId: number | null;
  enteTipoCod: string | null;
  enteTipoDesc: string | null;
}

export interface Soggetto {
  soggettoId: number | null;
  codiceFiscale: string | null;
  nome: string | null;
  cognome: string | null;
  presenteConfiguratore: boolean;
  dataNascita: number | null;
}
// STRUTTURA --------------------------------------------------------------------
export interface Struttura {
  strutturaId: number | null;
  strutturaCod: string | null;
  strutturaCodConfiguratore: string | null;
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
  strutturaAccreditamentoId: number | null;
  validitaInizio: number;
  natura: Natura | null;
  asl: Asl[] | null;
  comune: Comune | null;
  strutturaAccreditamento: StrutturaAccreditamento | null;
  strutturaTipo: StrutturaTipo | null;
  strutturaTitolarita: StrutturaTitolarita | null;
  categorie: Categorie[] | null;
}

export interface StrutturaTitolarita {
  strutturaTitolaritaId: number | null;
  strutturaTitolaritaCod: string | null;
  strutturaTitolaritaCodArpe: string | null;
  strutturaTitolaritaDesc: string | null;
}
export interface Natura {
  strutturaNaturaId: number;
  strutturaNaturaCod: string;
  strutturaNaturaCodArpe: string;
  strutturaNaturaDesc: string;
}

export interface Asl {
  enteId: number | null;
  enteCod: string | null;
  enteCodConfiguratore: string | null;
  enteDesc: string | null;
  enteTipoId: number | null;
}

export interface Comune {
  comuneId: number | null;
  comuneCod: string | null;
  comuneDesc: string | null;
  provinciaId: number | null;
  provincia: {
    provinciaId: number | null;
    provinciaCod: string | null;
    provinciaSigla: string | null;
    provinciaDesc: string | null;
    regioneId: number | null;
    regione: {
      regioneId: number | null;
      regioneCod: string | null;
      regioneDesc: string | null;
    }
  }
}
export interface StrutturaAccreditamento {
  utenteCancellazione?: string | null;
  strutturaAccreditamentoId: number | null;
  strutturaAccreditamentoCod: string | null;
  strutturaAccreditamentoCodArpe: string | null;
  strutturaAccreditamentoDesc: string | null;
}

export interface StrutturaTipo {
  strutturaTipoId: number | null;
  strutturaTipoCod: string | null;
  strutturaTipoDesc: string | null;
}
export interface Categorie {
  validitaInizio: number;
  strutturaCategoriaId: number | null;
  strutturaCategoriaCod: string | null;
  strutturaCategoriaDesc: string | null;
}
// --------- --------------------------------------------------------------------


export interface Ruolo {
  ruoloId: number | null;
  ruoloCod: string | null;
  ruoloDesc: string | null;
}
export interface Applicativo {
  applicativoId: number | null;
  applicativoCod: string | null;
  applicativoDesc: string | null;
}
export interface ProfiliEntity {
  profiloId: number | null;
  profiloCod: string | null;
  profiloDesc?: string | null;
}
