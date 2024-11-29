/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsancommon.api.persistence.model;

import java.util.Date;

public class OspiteMovimentoForModifica {

	private Boolean flgValido;
	private Long seq;
	private Date dataMovimento;
	private Integer ospiteMovimentoTipoId;
	private String ospiteMovimentoTipoDesc;
	private String ospiteMovimentoTipoOrd;
	private Integer ospiteStatoId;
	
	public Boolean getFlgValido() {
		return flgValido;
	}
	public void setFlgValido(Boolean flgValido) {
		this.flgValido = flgValido;
	}
	public Date getDataMovimento() {
		return dataMovimento;
	}
	public void setDataMovimento(Date dataMovimento) {
		this.dataMovimento = dataMovimento;
	}
	public Integer getOspiteMovimentoTipoId() {
		return ospiteMovimentoTipoId;
	}
	public void setOspiteMovimentoTipoId(Integer ospiteMovimentoTipoId) {
		this.ospiteMovimentoTipoId = ospiteMovimentoTipoId;
	}
	public String getOspiteMovimentoTipoDesc() {
		return ospiteMovimentoTipoDesc;
	}
	public void setOspiteMovimentoTipoDesc(String ospiteMovimentoTipoDesc) {
		this.ospiteMovimentoTipoDesc = ospiteMovimentoTipoDesc;
	}
	public String getOspiteMovimentoTipoOrd() {
		return ospiteMovimentoTipoOrd;
	}
	public void setOspiteMovimentoTipoOrd(String ospiteMovimentoTipoOrd) {
		this.ospiteMovimentoTipoOrd = ospiteMovimentoTipoOrd;
	}
	public Integer getOspiteStatoId() {
		return ospiteStatoId;
	}
	public void setOspiteStatoId(Integer ospiteStatoId) {
		this.ospiteStatoId = ospiteStatoId;
	}
	public Long getSeq() {
		return seq;
	}
	public void setSeq(Long seq) {
		this.seq = seq;
	}
}
