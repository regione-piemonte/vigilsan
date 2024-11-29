/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsancommon.api.persistence.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.vigilsan.vigilsancommon.api.persistence.model.OspiteStatoUltimo;
import it.csi.vigilsan.vigilsancommon.api.persistence.model.RuoloStruttura;
import it.csi.vigilsan.vigilsancommon.api.persistence.model.SoggettoEstesoLista;
import it.csi.vigilsan.vigilsancommon.api.persistence.model.StrutturaSoggetto;
import it.csi.vigilsan.vigilsancommon.util.ErrorCodeEnum;
import it.csi.vigilsan.vigilsanutil.generic.services.error.RESTErrorUtil;
import it.csi.vigilsan.vigilsanutil.generic.util.DateUtils;

public class SoggettoEstesoMapper<T extends SoggettoEstesoLista> extends SoggettoMapperGeneric implements RowMapper<T> {

	private Class<T> clazz; // Tipo generico T

	// Costruttore che richiede il parametro di tipo Class<T>
	public SoggettoEstesoMapper(Class<T> clazz) {
		this.clazz = clazz;
	}

	@Override
	public T mapRow(ResultSet rs, int rowNum) throws SQLException {

		T res;
		try {
			res = clazz.getDeclaredConstructor().newInstance();
		} catch (Exception e) {
			throw RESTErrorUtil.generateGenericRestException(ErrorCodeEnum.ERRORE_CREAZIONE_OGGETTO_QUERY);
		}
		this.mapRow(rs, res);
		res.setStrutturaCategoriaDesc(rs.getString("struttura_categoria_desc"));
		res.setDataPrimoIngresso(DateUtils.dateFromSqlTimestamp(rs.getTimestamp("data_primo_ingresso")));
		res.setDataUltimaUscita(DateUtils.dateFromSqlTimestamp(rs.getTimestamp("data_ultima_uscita")));
		var modelStrutturaSoggetto = new StrutturaSoggetto();
		modelStrutturaSoggetto.setStrSoggId(rs.getObject("str_sogg_id", Integer.class));
		modelStrutturaSoggetto.setStrutturaId(rs.getObject("struttura_id", Integer.class));
		modelStrutturaSoggetto.setSoggettoId(res.getSoggettoId());
		res.setStrutturaSoggetto(modelStrutturaSoggetto);
		var ruoloStruttura = new RuoloStruttura();
		ruoloStruttura.setRuoloStrutturaSoggettoCod(rs.getString("ruolo_struttura_soggetto_cod"));
		ruoloStruttura.setRuoloStrutturaSoggettoDesc(rs.getString("ruolo_struttura_soggetto_desc"));
		res.setRuoloStruttura(ruoloStruttura);
		var ultimoStato = new OspiteStatoUltimo();
		ultimoStato.setDataMovimento(DateUtils.dateFromSqlTimestamp(rs.getTimestamp("data_movimento")));
		ultimoStato.setOspiteStatoId(rs.getObject("ospite_stato_id", Integer.class));
		ultimoStato.setOspiteStatoCod(rs.getString("ospite_stato_cod"));
		ultimoStato.setOspiteStatoDesc(rs.getString("ospite_stato_desc"));
		res.setUltimoStato(ultimoStato);

		res.setTotalCount(rs.getObject("total_count", Long.class));
		return res;
	}

}
