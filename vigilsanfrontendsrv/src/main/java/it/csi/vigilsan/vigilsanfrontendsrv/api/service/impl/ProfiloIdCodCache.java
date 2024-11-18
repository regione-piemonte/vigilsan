/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanfrontendsrv.api.service.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.checkerframework.checker.units.qual.K;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import it.csi.vigilsan.vigilsanfrontendsrv.api.generated.dto.ModelProfilo;
import it.csi.vigilsan.vigilsanfrontendsrv.api.service.vigilsan.vigilsancommon.VigilsanCommonGetApiEnum;
import it.csi.vigilsan.vigilsanfrontendsrv.api.service.vigilsan.vigilsancommon.dto.ModelProfiloUtente;
import it.csi.vigilsan.vigilsanfrontendsrv.util.OkhttpCallUtils;
import it.csi.vigilsan.vigilsanfrontendsrv.util.ProfiloCodEnum;
import it.csi.vigilsan.vigilsanutil.bff.service.vigilsan.impl.VigilsanServiceAbstract;
import jakarta.annotation.PostConstruct;
import jakarta.ws.rs.core.HttpHeaders;
import okhttp3.Headers;

@Service
public class ProfiloIdCodCache {
	private LoadingCache<Integer, ProfiloCodEnum> cache;


	@Autowired
	private VigilsanServiceAbstract vigilsanCommonService;
	
	@PostConstruct
	public void init() {
		cache = CacheBuilder.newBuilder().expireAfterWrite(1, TimeUnit.HOURS).build(new CacheLoader<>() {
			@Override
			public ProfiloCodEnum load(Integer key) throws Exception {
				throw new UnsupportedOperationException("Utilizza get(K key, V value) per caricare i valori.");

			}
		});
	}

	public ProfiloCodEnum get(ModelProfiloUtente profiloUtente, HttpHeaders httpHeaders) throws ExecutionException, IOException {
		ProfiloCodEnum cachedValue = cache.getIfPresent(profiloUtente.getProfiloId());
		if (cachedValue == null) {
			var headers = OkhttpCallUtils.convertJakartaToOkHttpHeadersWhitoutAccept(httpHeaders);
			ModelProfilo profilo = getProfilo(profiloUtente.getProfiloId(), profiloUtente.getSessioneId(),
					headers);
			ProfiloCodEnum value = ProfiloCodEnum.valueOf(profilo.getProfiloCod());
			cache.put(profilo.getProfiloId(), value);
			return value;
		}
		return cachedValue;
	}


	protected ModelProfilo getProfilo(Integer profiloId, Integer sId, Headers headers) throws IOException {
		var qp = new HashMap<String, Object>();
		OkhttpCallUtils.setQueryParam("profilo_id", profiloId, qp);
		return vigilsanCommonService.getGenericGet(VigilsanCommonGetApiEnum.GET_PROFILO, headers, qp, sId);
	}
	

	public void invalidate(K key) {
		cache.invalidate(key);
	}

	public Map<Integer, ProfiloCodEnum> asMap() {
		return cache.asMap();
	}
}
