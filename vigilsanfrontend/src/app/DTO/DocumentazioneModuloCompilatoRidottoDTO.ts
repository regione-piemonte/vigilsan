/*
 * Copyright Regione Piemonte - 2024
 * SPDX-License-Identifier: EUPL-1.2
 */

export interface ModuloCompilatoRidotto {
  moduloId: number;
  moduloCod: string;
  moduloDesc: string;
  moduloOrd: string;
  moduloTitolo: string;
  moduloIdPadre: number;
  sezioni: (ModuloCompilatoRidotto)[] | null;
  voci: (VociEntity)[] | null;
}

export interface VociEntity {
  moduloId: number;
  moduloVoceId: number;
  moduloVoceCod: string;
  moduloVoceDesc: string;
  moduloVoceHint: string;
  moduloVoceIdPadre: number;
  moduloVoceTipoDati: string;
  moduloVoceUnitaMisura: string;
  moduloListaId: number;
  fileGruppoId: number;
  valore: string;
  note: string;
  lista: Lista;
  regole?: (RegoleEntity)[] | null;
  fileGruppo: FileGruppo;
  voci?: (string)[] | null;
}
export interface Lista {
  moduloListaCod: string;
  moduloListaDesc: string;
  moduloListaHint: string;
  moduloListaId: number;
  valori?: (ValoriEntity)[] | null;
}
export interface ValoriEntity {
  moduloListaValoreId: number;
  moduloListaValoreCod: string;
  moduloListaValoreDesc: string;
  moduloListaValoreHint: string;
  valore: boolean;
}
export interface RegoleEntity {
  moduloVoceRegolaTipo: string;
  moduloVoceRegolaErrore: string;
}
export interface FileGruppo {
  fileGruppoCod: string;
  fileGruppoDesc: string;
  fileTipi?: (FileTipiEntity)[] | null;
}
export interface FileTipiEntity {
  fileTipoId: number;
  fileTipoCod: string;
  fileTipoDesc: string;
  fileTipoHint: string;
  fileTipoObbligatorio: boolean;
  fileTipoFirmaRichiesta: boolean;
  fileId: number;
}
