/*
 * Copyright Regione Piemonte - 2024
 * SPDX-License-Identifier: EUPL-1.2
 */

export interface MovimentoTipo {
  ospiteMovimentoTipoId: number | null;
  ospiteMovimentoTipoCod: string | null;
  ospiteMovimentoTipoDesc: string | null;
  ospiteMovimentoTipoOrd: string | null;
  ospiteMovimentoTipoHint?: string | null;
  ospiteStatoId?: number | null;
  ospiteMovimentoTipoConfig?: (OspiteMovimentoTipoConfig)[] | null;
}
export interface OspiteMovimentoTipoConfig {
  ospiteMovimentoTipoCfgId: number | null;
  ospiteMovimentoTipoId: number | null;
  ospiteStatoId?: number | null;
}
