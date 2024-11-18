/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanrilevazioni.api.persistence.mapper;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;

import it.csi.vigilsan.vigilsanrilevazioni.api.generated.dto.ModelParametro;

public class ModelParametroMapper implements RowMapper<Object> {

	List<String> valoriDaEstrarre;
	Map<String, List<String>> result;

	public ModelParametroMapper(List<String> valoriDaEstrarre) {
		this.valoriDaEstrarre = valoriDaEstrarre;
	}

	@Override
	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {

		List<ModelParametro> res = new ArrayList<>();

		ResultSetMetaData rsmd = rs.getMetaData();
		int columnCount = rsmd.getColumnCount();

		for (int i = 1; i <= columnCount; i++) {

			String key = rsmd.getColumnName(i);
			if (valoriDaEstrarre.contains(key)) {
				ModelParametro parametro = new ModelParametro();
				parametro.setChiave(key);
				Object val = rs.getObject(i);
				parametro.setValore(val != null ? val.toString() : null);
				res.add(parametro);
			}
		}

		return null;
	}

}
