/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanrilevazioni.api.persistence.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import it.csi.vigilsan.vigilsanrilevazioni.api.generated.dto.ModelModuloRidotto;

public class GenericModuloEstesoMapper {

	private GenericModuloEstesoMapper() {
		super();
	}

	protected static void mapRaw(ResultSet rs, ModelModuloRidotto modulo) throws SQLException {
		setParametriModuloCompilato(rs, modulo);
		setModuloId(rs, modulo);
		modulo.setModuloCod(rs.getString("modulo_cod"));
	}

	protected static void setModuloId(ResultSet rs, ModelModuloRidotto modulo) throws SQLException {
		modulo.setModuloId(rs.getObject("modulo_id", Integer.class));
	}

	protected static void setParametriModuloCompilato(ResultSet rs, ModelModuloRidotto modulo) throws SQLException {
		modulo.setModuloDesc(rs.getString("modulo_desc"));
		modulo.setModuloOrd(rs.getString("modulo_ord"));
	}

}