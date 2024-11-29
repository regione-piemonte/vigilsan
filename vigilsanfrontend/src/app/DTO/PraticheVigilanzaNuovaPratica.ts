/*
 * Copyright Regione Piemonte - 2024
 * SPDX-License-Identifier: EUPL-1.2
 */

import { Azione } from './PraticheVigilanzaDecodificheDTO';
import { SezioniEntity } from "./SezioniEntity";

// AGGIUNTA NUOVA PRATICA
export interface NewPratica {
  praticaId?: number | null;
  strutturaId: number | null;
  praticaTipoId: number | null;
  praticaDettaglio: PraticaDettaglio | null;
  moduloCompilato?: {
    note: string | null;
    modulo: SezioniEntity | null;
  }
  validitaInizio?: number | null;
  validitaFine?: number | null;
  praticaNumero: string | null;
}
export interface PraticaDettaglio {
  praticaDetId?: number | null;
  praticaId?: number | null;
  prescrizioneId?: number | null;
  appuntamentoId?: number | null;
  azioneId?: number | null;
  dataoraAzione: number | null;
  moduloCompilatoId?: number | null;
  note?: string | null;
  profiloId?: number | null;
  flgScadenza?: boolean | null;
  profiloIdScadenza?: number | null;
  dataoraInizio: number | null;
  dataoraFine: number | null;
  prescrizioneNumero: string | null;
  appuntamentoNumero: string | null;
}

// PRATICHE DA AGGIUNGERE
export interface RicercaPratiche {
  praticaId?: number | null;
  enteId: number | null;
  strutturaId: number | null;
  praticaTipoId: number | null;
  dataoraApertura?: null;
  dataoraChiusura?: null;
  praticaTipo: PraticaTipo | null;
  azioneIniziale: Azione | null;
  ente: Ente | null;
  struttura: Struttura | null;
  moduloId: number | null;
}
export interface PraticaTipo {
  praticaTipoId: number | null;
  praticaTipoCod: string | null;
  praticaTipoDesc: string | null;
}
// export interface AzioneIniziale {
//   azioneId: number | null;
//   azioneCod: string | null;
//   azioneDesc: string | null;
// }
export interface Ente {
  enteId: number | null;
  enteCod: string | null;
  enteCodConfiguratore: string | null;
  enteDesc: string | null;
  enteTipoId: number | null;
}
export interface Struttura {
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
  strutturaTitolaritaId: number | null;
}

// PRATICHE GIA' AGGIUNTE
export interface PraticheAggiunte {
  praticaId: number | null,
  praticaTipoId: number | null,
  enteId: number | null,
  strutturaId: number | null,
  dataoraApertura: number | null,
  dataoraChiusura: number | null,
  appuntamenti: Appuntamenti[],
  prescrizioni: Prescrizioni[],
  attivita: Attivita[],
  tipo: Tipo,
  struttura: Struttura,
  moduloId?: number | null;
  moduloCompilatoId?: number | null;
}

export interface Appuntamenti {
  appuntamentoId: number,
  appuntamentoTipoId: number,
  praticaId: number | null,
  dataoraInizio: number | null,
  dataoraFine: number | null,
  dataoraApertura: number | null,
  dataoraChiusura: number | null,
  appuntamentoStato: {
    appuntamentoStatoId: number | null,
    appuntamentoStatoCod: string | null,
    appuntamentoStatoDesc: string | null,
    appuntamentoStatoFinale: boolean | null
  },
  appuntamentoTipo: {
    appuntamentoTipoId: number | null,
    appuntamentoTipoCod: string | null,
    appuntamentoTipoDesc: string | null
  },
  azioneDesc: string,
  moduloCompilatoId: number | null;
  moduloId: number | null;
  praticaDetIdCheck?: number | null;
  checked?: boolean;
  appuntamentoNumero: string | null;
}
export interface Prescrizioni {
  prescrizioneId: number,
  prescrizioneTipoId: number,
  praticaId: number | null,
  dataoraApertura: number | null,
  dataoraChiusura: number | null,
  prescrizioneStato: {
    prescrizioneStatoId: number | null,
    prescrizioneStatoCod: string | null,
    prescrizioneStatoDesc: string | null,
    prescrizioneStatoFinale: boolean | null
  },
  prescrizioneTipo: {
    prescrizioneTipoId: number | null,
    prescrizioneTipoCod: string | null,
    prescrizioneTipoDesc: string | null
  },
  azioneDesc: string,
  moduloId: number | null;
  moduloCompilatoId: number | null;
  praticaDetIdCheck?: number | null;
  checked?: boolean;
  prescrizioneNumero: string | null;
}
export interface Attivita {
  praticaId: number | null,
  praticaDetId: number | null,
  praticaStatoId: number | null,
  prescrizioneId: number | null,
  prescrizioneStatoId: number | null,
  appuntamentoId: number | null,
  appuntamentoStatoId: number | null,
  azioneId: number | null,
  dataoraAzione: number | null,
  moduloCompilatoId: number | null,
  moduloId: number | null,
  note: string | null,
  profiloId: number | null,
  flgScadenza: boolean | null,
  profiloIdScadenza: number | null,
  praticaStato: {
    praticaStatoId: number | null,
    praticaStatoCod: string | null,
    praticaStatoDesc: string | null,
    praticaStatoFinale: boolean | null
  },
  appuntamentoStato: {
    appuntamentoStatoId: number | null,
    appuntamentoStatoCod: string | null,
    appuntamentoStatoDesc: string | null,
    appuntamentoStatoFinale: boolean | null
  },
  prescrizioneStato: {
    prescrizioneStatoId: number | null,
    prescrizioneStatoCod: string | null,
    prescrizioneStatoDesc: string | null,
    prescrizioneStatoFinale: boolean | null
  },
  azioneDesc: string,
  iconToUse?: string,
  flgClreqEsito: boolean | null
}
export interface Tipo {
  praticaTipoId: number | null,
  praticaTipoCod: string | null,
  praticaTipoDesc: string | null
}


// PRATICA DETTAGLIO
export interface SingolaPraticaDettaglio {
  validitaInizio: number | null,
  validitaFine: number | null,
  praticaId: number | null,
  praticaTipoId: number | null,
  enteId: number | null,
  strutturaId: number | null,
  dataoraApertura: number | null,
  dataoraChiusura: number | null,
  appuntamenti: Appuntamenti[],
  prescrizioni: Prescrizioni[],
  attivita: Attivita[],
  tipo: Tipo,
  struttura: StrutturaPraticaDettaglio,
  ente: EntePraticaDettaglio,
  moduloCompilatoId: number | null;
  moduloId: number | null;
  checked?: boolean;
  praticaDetIdCheck?: number | null;
  praticaNumero: string | null;
}

export interface EntePraticaDettaglio {
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

export interface StrutturaPraticaDettaglio {
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
