/*
 * Copyright Regione Piemonte - 2024
 * SPDX-License-Identifier: EUPL-1.2
 */

export interface PostFile {
  fileId: number | null;
  fileTipoId: number | null;
  fileName: string | null;
  fileSize: number | null;
  fileContentType: string | null;
  filePath: string | null;
  cfFirmaVerificata: string | null;
  validitaInizio: string | null;
  validitaFine: string | null;
  dataCreazione: string | null;
  dataModifica: string | null;
  dataCancellazione: string | null;
  utenteCreazione: string | null;
  utenteModifica: string | null;
  utenteCancellazione: string | null;
}


export interface TypeUploadFile {
  fileContentTypeId: number | null,
  fileContentTypeCod: string | null,
  fileContentTypeDesc: string | null,
  fileContentTypeOrd: string | null,
  fileContentTypeHint: string | null,
  fileContentTypeMime: string | null,
  fileContentTypeExt: string | null,
}
