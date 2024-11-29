/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsancommon.api.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.Date;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.csi.vigilsan.vigilsancommon.api.persistence.model.ArpeStrutturaDettaglio;
import it.csi.vigilsan.vigilsancommon.api.persistence.model.Comune;
import it.csi.vigilsan.vigilsancommon.api.persistence.model.Ente;
import it.csi.vigilsan.vigilsancommon.api.persistence.model.EnteStruttura;
import it.csi.vigilsan.vigilsancommon.api.persistence.model.RuoloEnteStruttura;
import it.csi.vigilsan.vigilsancommon.api.persistence.model.Struttura;
import it.csi.vigilsan.vigilsancommon.api.service.EnteService;
import it.csi.vigilsan.vigilsancommon.api.service.EnteStrutturaService;
import it.csi.vigilsan.vigilsancommon.api.service.StrutturaNaturaService;
import it.csi.vigilsan.vigilsancommon.api.service.StrutturaService;
import it.csi.vigilsan.vigilsancommon.util.ErrorCodeEnum;
import it.csi.vigilsan.vigilsancommon.util.LogMessageEnum;
import it.csi.vigilsan.vigilsanutil.generic.services.error.RESTErrorUtil;
import it.csi.vigilsan.vigilsanutil.generic.services.error.RESTException;
import it.csi.vigilsan.vigilsanutil.generic.services.log.AuditableWPersistenceApiServiceImpl;
import it.csi.vigilsan.vigilsanutil.generic.util.AuditOperazioneEnum;
import it.csi.vigilsan.vigilsanutil.generic.util.DateFormatEnum;
import it.csi.vigilsan.vigilsanutil.generic.util.DateUtils;

@Service
public class RpviarServiceTransactionalImpl extends AuditableWPersistenceApiServiceImpl {

	static String[] fieldDescription = new String[] { "COD_ASR_TER", "DESC_ASR_TER", "COD_ASR_TIT", "DESC_ASR_TIT",
			"COD_STRUTTURA", "DENOM_STRUTTURA", "COD_NATURA", "NATURA", "COD_ACCREDITAMENTO", "ACCREDITAMENTO",
			"COD_TIPO_STRUTTURA", "DESC_TIPO_STRUTTURA", "COD_TIPO_ASSISTENZA", "DESC_TIPO_ASSISTENZA", "INDIRIZZO",
			"COMUNE", "ISTAT_COMUNE", "CAP", "PROVINCIA", "COORD_SRID", "COORD_X", "COORD_Y", "COD_ATTIVITA",
			"DESC_ATTIVITA", "COD_CLASSE_ATTIVITA", "DESC_CLASSE_ATTIVITA", "COD_TITOLARITA", "DESC_TITOLARITA",
			"COD_SPECIALITA", "DESC_SPECIALITA", "DATA_ATTIVAZIONE_STRUTTURA", "DATA_ATTIVAZIONE_ASSISTENZA",
			"DATA_ATTIVAZIONE_ATTIVITA" };

	static String aslTerritoriale = "ASL_TERR";

	@Autowired
	private EnteService enteService;

	@Autowired
	private StrutturaService strutturaService;

	@Autowired
	private StrutturaNaturaService strutturaNaturaService;

	@Autowired
	private StrutturaAccreditamentoServiceImpl strutturaAccreditamentoService;

	@Autowired
	private ArpeStrutturaTipoServiceImpl arpeStrutturaTipoService;

	@Autowired
	private ArpeAssistenzaTipoServiceImpl arpeAssistenzaTipoService;

	@Autowired
	private ArpeAttivitaServiceImpl arpeAttivitaService;

	@Autowired
	private ArpeAttivitaClasseServiceImpl arpeAttivitaClasseService;

	@Autowired
	private ArpeDisciplinaServiceImpl arpeDisciplinaService;

	@Autowired
	private ComuneServiceImpl comuneService;

	@Autowired
	private ArpeStrutturaDettaglioServiceImpl arpeStrutturaDettaglioService;

	@Autowired
	private StrutturaTitolaritaServiceImpl strutturaTitolaritaService;

	@Autowired
	private RuoloEnteStrutturaServiceImpl ruoloEnteStrutturaService;

	@Autowired
	private EnteStrutturaService enteStrutturaService;

	private String shibIdentitaCodiceFiscale = "BATCH_RPVIAR";
	private String DATA_SEPARATOR = ",";
	// 12 giugno 2024 aggiunto gestione date
	private int NUMBER_OF_TOKEN = 33;
	
	@Transactional
	public void batchRipvar(Integer sId, Date dateBatch, String pathFile) throws IOException, Exception {
		File file = new File(pathFile);

		// Controllo il file dei dati in input
		checkTheFile(sId, file);

		// Leggo le righe del file per formare gli oggetti ModelStruttura
		Struttura struttura = leggiFile(sId, file, dateBatch);

		// Modifiche post creazione Struttura
		cancellaStrutture(sId, dateBatch);

		// Modifiche post creazione Struttura Dettaglio
		cancellaStruttureDettaglio(sId, dateBatch);

		// Ricalcolo della struttura
		ricalcolaStruttura(sId);
	}

	private void checkTheFile(Integer sId, File file) throws RESTException, IOException, Exception {
		var methodName = "checkTheFile";
		logDebug(sId, methodName, LogMessageEnum.BEGIN.getMessage());

		try {
			if (!file.exists()) {
				logDebug(sId, methodName, "Il file in input non esiste : " + file.getCanonicalPath());
				logAuditDb(sId, AuditOperazioneEnum.ERROR, "Presenza file",
						ErrorCodeEnum.BATCH_RPVIAR_FILE_NOT_PRESENT.getCode());
				throw RESTErrorUtil.generateGenericRestException(ErrorCodeEnum.BATCH_RPVIAR_FILE_NOT_PRESENT);
			} else if (!file.canRead()) {
				logDebug(sId, methodName, "Il file in input non leggibile : " + file.getCanonicalPath());
				logAuditDb(sId, AuditOperazioneEnum.ERROR, "Lettura file",
						ErrorCodeEnum.BATCH_RPVIAR_FILE_NOT_READABLE.getCode());
				throw RESTErrorUtil.generateGenericRestException(ErrorCodeEnum.BATCH_RPVIAR_FILE_NOT_READABLE);
			}
		} catch (Exception exception) {
			throw exception;
		} finally {
			logDebug(sId, methodName, LogMessageEnum.END.getMessage());
		}
	}

	private Struttura leggiFile(Integer sId, File file, Date dateBatch) throws RESTException, IOException, Exception {
		var methodName = "leggiFile";
		logDebug(sId, methodName, LogMessageEnum.BEGIN.getMessage());
		Struttura strutturaToReturn = null;

		try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {

			int indice = 0;
			String line = null;
			while ((line = bufferedReader.readLine()) != null) {
				logDebug(sId, methodName, "Line :" + line);
				if (indice > 0) {
					try {
						strutturaToReturn = parsificaStruttura(sId, line, dateBatch);
					} catch (RESTException restException) {
						logDebug(sId, methodName, "Code error : " + restException.getCode());
						// ERRORI NON BLOCCANTI

						if (ErrorCodeEnum.BATCH_RPVIAR_NUMERO_DI_CAMPI_INCORRETTO.getCode()
								.equals(restException.getCode())) {
							logInfo(sId, methodName, "Numero di campi file arpe incorretto : " + line);
							logAuditDb(sId, AuditOperazioneEnum.ERROR, line,
									ErrorCodeEnum.BATCH_RPVIAR_NUMERO_DI_CAMPI_INCORRETTO.getCode());

							throw restException;
						} else {
							if (ErrorCodeEnum.ENTE_NON_TROVATO.getCode().equals(restException.getCode())) {
								logInfo(sId, methodName, "COD_ASR_TER NON TROVATO: " + line);
								logAuditDb(sId, AuditOperazioneEnum.ERROR, line,
										ErrorCodeEnum.ENTE_NON_TROVATO.getCode());

								throw restException;
							} else {
								if (ErrorCodeEnum.COMUNE_NON_TROVATO.getCode().equals(restException.getCode())) {
									logInfo(sId, methodName, "ISTAT_COMUNE NON TROVATO: " + line);
									logAuditDb(sId, AuditOperazioneEnum.ERROR, line,
											ErrorCodeEnum.COMUNE_NON_TROVATO.getCode());

									throw restException;
								}
							}
						}
					} catch (Exception exception) {
						logError(sId, methodName,
								LogMessageEnum.ECCEZIONE_GENERICA.getMessage() + exception.getMessage(), exception);
						logAuditDb(sId, AuditOperazioneEnum.ERROR, line,
								LogMessageEnum.ECCEZIONE_GENERICA.getMessage() + exception.getMessage());

						throw exception;
					}
				}
				indice++;
			}
		} catch (IOException ex) {
			logError(sId, methodName, "IOException : " + ex.getMessage(), ex);
			throw ex;
		} finally {
			logDebug(sId, methodName, LogMessageEnum.END.getMessage());
		}

		return strutturaToReturn;
	}

	private Struttura parsificaStruttura(Integer sId, String line, Date dateBatch)
			throws RESTException, IOException, Exception {
		var methodName = "parsificaStruttura";
		logDebug(sId, methodName, LogMessageEnum.BEGIN.getMessage());

		Struttura strutturaToReturn = null;

		logDebug(sId, methodName, "Line prima parsing :" + line);
		try (Reader reader = new StringReader(line); CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT)) {
			var campiStruttura = new String[0];
			for (CSVRecord csvRecord : csvParser) {
				campiStruttura = csvRecord.toList().toArray(new String[0]);
			}

			logDebug(sId, methodName, "Number of token split :" + campiStruttura.length);
			logDebug(sId, methodName, "Number of token :" + NUMBER_OF_TOKEN);

			if (campiStruttura.length != NUMBER_OF_TOKEN) {
				throw RESTErrorUtil.generateGenericRestException(ErrorCodeEnum.BATCH_RPVIAR_NUMERO_DI_CAMPI_INCORRETTO);
			}

			strutturaToReturn = checkData(sId, campiStruttura, dateBatch);

			return strutturaToReturn;

		} finally {
			logDebug(sId, methodName, LogMessageEnum.END.getMessage());
		}
	}

	private Struttura checkData(Integer sId, String[] campiStruttura, Date dateBatch) throws Exception {
		var methodName = "checkData";
		logDebug(sId, methodName, LogMessageEnum.BEGIN.getMessage());

		// Oggetto per la gestione della tavola vigil_t_struttura
		Struttura strutturaToManage = new Struttura();

		// Oggetto per la gestione della tavola vigil_r_arpe_struttura_dettaglio
		ArpeStrutturaDettaglio arpeStrutturaDettaglio = new ArpeStrutturaDettaglio();

		// Oggetto per la gestione della tavola vigil
		EnteStruttura enteStrutturaToManage = null;

		Ente aslCheck = null;
		try {

			logDebug(sId, methodName, "Campi Struttura passato : " + campiStruttura);

			// check codice ente (COD_ASR_TER) posizione 0, se l'ente non è presente, esco
			// con errore
			try {
				aslCheck = enteService.getEnteAslByEnteCod(getFieldInArray(sId, campiStruttura, 0));
// da definire dove mettere l'asl sulla struttura
			} catch (RESTException enteException) {
				if (ErrorCodeEnum.ENTE_NON_TROVATO.getCode().equals(enteException.getCode())) {
					logError(sId, methodName, fieldDescription[0] + " non trovato", enteException);
				}
				throw enteException;
			}

			// i campi in posizione 1 (DESC_ASR_TER) ,2 (COD_ASR_TIT) ,3 (DESC_ASR_TIT) non
			// vengono utilizzati
			// check codice istat (ISTAT_COMUNE) posizione 16, se il comune non è presente,
			// esco con errore
			try {
				Comune comuneCheck = comuneService.getComuneByComuneCod(getFieldInArray(sId, campiStruttura, 16));
				logDebug(sId, methodName, "COMUNE ID : " + comuneCheck.getComuneId());
				strutturaToManage.setComuneId(comuneCheck.getComuneId());
			} catch (RESTException comuneException) {
				if (ErrorCodeEnum.COMUNE_NON_TROVATO.getCode().equals(comuneException.getCode())) {
					logError(sId, methodName, fieldDescription[16] + " non trovato", comuneException);
				}
				throw comuneException;
			}

			// check natura della struttura (COD_NATURA) posizione 6, se il codice della
			// natura non esiste, lo creo
			try {
				// Gestione Struttura Natura
				String codStrutturaNatura = getFieldInArray(sId, campiStruttura, 6);
				if (isInsertable(sId, codStrutturaNatura)) {
					Integer strutturaNaturaId = strutturaNaturaService.gestioneStrutturaNatura(codStrutturaNatura,
							getFieldInArray(sId, campiStruttura, 7), shibIdentitaCodiceFiscale);
					logDebug(sId, methodName, fieldDescription[6] + " id : " + strutturaNaturaId.intValue());
					strutturaToManage.setStrutturaNaturaId(strutturaNaturaId);
				}
			} catch (Exception naturaException) {
				logError(sId, methodName, "Errore nella gestione della natura della struttura", naturaException);
				throw naturaException;
			}

			// check accreditamento della struttura (COD_ACCREDITAMENTO) posizione 8, se il
			// codice dell'accreditamento è null non lo verifico
			// se non lo trovo sulla base dati, lo creo
			try {
				String codAccreditamento = getFieldInArray(sId, campiStruttura, 8);
				if (isInsertable(sId, codAccreditamento)) {
					Integer strutturaAccreditamentoId = strutturaAccreditamentoService.gestioneStrutturaAccreditamento(
							codAccreditamento, getFieldInArray(sId, campiStruttura, 9), shibIdentitaCodiceFiscale);
					logDebug(sId, methodName, fieldDescription[8] + " id : " + strutturaAccreditamentoId.intValue());
					strutturaToManage.setStrutturaAccreditamentoId(strutturaAccreditamentoId);
				}
			} catch (Exception accreditamentoException) {
				logError(sId, methodName, "Errore nella gestione dell'accreditamento della struttura",
						accreditamentoException);
				throw accreditamentoException;
			}

			// check tipo della struttura (COD_TIPO_STRUTTURA) posizione 10, se il codice
			// struttura tipo è null non lo verifico
			// se il codice del tipo struttura non esiste, lo creo
			try {
				String codStrutturaTipo = getFieldInArray(sId, campiStruttura, 10);
				if (isInsertable(sId, codStrutturaTipo)) {
					Integer tipoStrutturaId = arpeStrutturaTipoService.gestioneArpeStrutturaTipo(codStrutturaTipo,
							getFieldInArray(sId, campiStruttura, 11), shibIdentitaCodiceFiscale);
					logDebug(sId, methodName, fieldDescription[10] + " id : " + tipoStrutturaId.intValue());
					// Valorizzo l'id della struttura tipo sull'oggetto di gestione struttura
					// dettaglio
					arpeStrutturaDettaglio.setArpeStrutturaTipoId(tipoStrutturaId);
				}
			} catch (Exception tipoStrutturaException) {
				logError(sId, methodName, "Errore nella gestione del tipo della struttura", tipoStrutturaException);
				throw tipoStrutturaException;
			}

			// check tipo assistenza (COD_TIPO_ASSISTENZA) posizione 12, se il codice tipo
			// assistenza è null non lo verifico
			// se il codice del tipo assistenza non esiste, lo creo
			try {
				// Gestione Assistenza Tipo Assistenza
				String codAssistenzaTipo = getFieldInArray(sId, campiStruttura, 12);
				if (isInsertable(sId, codAssistenzaTipo)) {
					Integer tipoAssistenzaId = arpeAssistenzaTipoService.gestioneArpeAssistenzaTipo(codAssistenzaTipo,
							getFieldInArray(sId, campiStruttura, 13), shibIdentitaCodiceFiscale);
					logDebug(sId, methodName, fieldDescription[12] + " id : " + tipoAssistenzaId.intValue());
					// Valorizzo l'id della assistenza tipo sull'oggetto di gestione struttura
					// dettaglio
					arpeStrutturaDettaglio.setArpeAssistenzaTipoId(tipoAssistenzaId);
				}
			} catch (Exception tipoAssistenzaException) {
				logError(sId, methodName, "Errore nella gestione del tipo dell'assistenza", tipoAssistenzaException);
				throw tipoAssistenzaException;
			}

			// check attivita (COD_ATTIVITA) posizione 22, se il codice dell'attivita è null
			// non lo verifico
			// se il codice dell'attivita non esiste, lo creo
			try {
				// Gestione Attivita
				String codAttivita = getFieldInArray(sId, campiStruttura, 22);
				if (isInsertable(sId, codAttivita)) {
					Integer attivitaId = arpeAttivitaService.gestioneArpeAttivita(codAttivita,
							getFieldInArray(sId, campiStruttura, 23), shibIdentitaCodiceFiscale);
					logDebug(sId, methodName, fieldDescription[22] + " id : " + attivitaId.intValue());
					// Valorizzo l'id della attivita sull'oggetto di gestione struttura dettaglio
					arpeStrutturaDettaglio.setArpeAttivitaId(attivitaId);
				}
			} catch (Exception attivitaException) {
				logError(sId, methodName, "Errore nella gestione dell'attivita", attivitaException);
				throw attivitaException;
			}

			// check attivita classe (COD_CLASSE_ATTIVITA) posizione 24, se il codice
			// dell'attivita classe è null non lo verifico
			// se il codice dell'attivita classe non esiste, lo creo
			try {
				// Gestione Attivita Classe
				String codAttivitaClasse = getFieldInArray(sId, campiStruttura, 24);
				if (isInsertable(sId, codAttivitaClasse)) {
					Integer attivitaClasseId = arpeAttivitaClasseService.gestioneArpeAttivitaClasse(codAttivitaClasse,
							getFieldInArray(sId, campiStruttura, 25), shibIdentitaCodiceFiscale);
					logDebug(sId, methodName, fieldDescription[24] + " id : " + attivitaClasseId.intValue());
					// Valorizzo l'id della attivita classe sull'oggetto di gestione struttura
					// dettaglio
					arpeStrutturaDettaglio.setArpeAttivitaClasseId(attivitaClasseId);
				}
			} catch (Exception attivitaClasseException) {
				logError(sId, methodName, "Errore nella gestione dell'attivita classe", attivitaClasseException);
				throw attivitaClasseException;
			}

			// check titolarita della struttura (COD_TITOLARITA) posizione 26, se il codice
			// titolarita è null non lo verifico
			// se il codice della titolarita non esiste, lo creo
			try {
				String codTitolarita = getFieldInArray(sId, campiStruttura, 26);
				if (isInsertable(sId, codTitolarita)) {
					Integer strutturaTitolaritaId = strutturaTitolaritaService.gestioneStrutturaTitolarita(
							codTitolarita, getFieldInArray(sId, campiStruttura, 27), shibIdentitaCodiceFiscale);
					logDebug(sId, methodName, fieldDescription[26] + " id : " + strutturaTitolaritaId.intValue());
					strutturaToManage.setStrutturaTitolaritaId(strutturaTitolaritaId);
				}
			} catch (Exception titolaritaException) {
				logError(sId, methodName, "Errore nella gestione della titolarita della struttura ",
						titolaritaException);
				throw titolaritaException;
			}

			// check disciplina (COD_DISCIPLINA) posizione 28, se il codice disciplina è
			// null non lo verifico
			// se il codice della disciplina non esiste, lo creo
			try {
				// Gestione Disciplina
				String codDisciplina = getFieldInArray(sId, campiStruttura, 28);
				if (isInsertable(sId, codDisciplina)) {
					Integer disciplinaId = arpeDisciplinaService.gestioneArpeDisciplina(codDisciplina,
							getFieldInArray(sId, campiStruttura, 29), shibIdentitaCodiceFiscale);
					logDebug(sId, methodName, fieldDescription[28] + " id : " + disciplinaId.intValue());
					arpeStrutturaDettaglio.setArpeDisciplinaId(disciplinaId);
				}
			} catch (Exception disciplinaException) {
				logError(sId, methodName, "Errore nella gestione della disciplina ", disciplinaException);
				throw disciplinaException;
			}

			// Setto i campi comuni per update ed insert
			strutturaToManage.setCap(getFieldInArray(sId, campiStruttura, 17));
			strutturaToManage.setCoordSrid(getFieldInArray(sId, campiStruttura, 19));
			strutturaToManage.setCoordX(getFieldInArray(sId, campiStruttura, 20));
			strutturaToManage.setCoordY(getFieldInArray(sId, campiStruttura, 21));
			strutturaToManage.setIndirizzo(getFieldInArray(sId, campiStruttura, 14));
			strutturaToManage.setStrutturaDesc(getFieldInArray(sId, campiStruttura, 5));

			Struttura strutturaManaged = null;
			Integer arpeStrutturaDettaglioManaged = null;
			RuoloEnteStruttura ruoloEnteStrutturaManaged = null;

			// Gestione struttura
			try {
				strutturaManaged = strutturaService.gestioneStruttura(sId, strutturaToManage,
						getFieldInArray(sId, campiStruttura, 4), shibIdentitaCodiceFiscale);
				logDebug(sId, methodName, fieldDescription[4] + " inserted : " + strutturaManaged.getStrutturaId());
			} catch (Exception exception) {
				logError(sId, methodName, "Errore nella gestione della struttura", exception);
				throw exception;
			} finally {
				logDebug(sId, methodName, LogMessageEnum.END.getMessage());
			}

			// Gestione arpe struttura dettaglio
			try {
				// Valorizzo l'id della struttura sull'oggetto di gestione struttura dettaglio
				arpeStrutturaDettaglio.setStrutturaId(strutturaManaged.getStrutturaId());
				arpeStrutturaDettaglio.setUtenteCreazione(shibIdentitaCodiceFiscale);

				// 20240612 aggiungo date sulla struttura dettaglio
				String dataAttivazioneStruttura = getFieldInArray(sId, campiStruttura, 30);
				if (isInsertable(sId, dataAttivazioneStruttura)) {
					arpeStrutturaDettaglio.setArpeDataAttivazioneStruttura(
							DateUtils.parsetDate(dataAttivazioneStruttura, DateFormatEnum.ARPE_DATE_FORMAT));
				}

				String dataAttivazioneAssistenza = getFieldInArray(sId, campiStruttura, 31);
				if (isInsertable(sId, dataAttivazioneAssistenza)) {
					arpeStrutturaDettaglio.setArpeDataAttivazioneAssistenza(
							DateUtils.parsetDate(dataAttivazioneAssistenza, DateFormatEnum.ARPE_DATE_FORMAT));
				}

				String dataAttivazioneAttivita = getFieldInArray(sId, campiStruttura, 32);
				if (isInsertable(sId, dataAttivazioneAttivita)) {
					arpeStrutturaDettaglio.setArpeDataAttivazioneAttivita(
							DateUtils.parsetDate(dataAttivazioneAttivita, DateFormatEnum.ARPE_DATE_FORMAT));
				}

				arpeStrutturaDettaglioManaged = arpeStrutturaDettaglioService.gestioneArpeStrutturaDettaglio(sId,
						arpeStrutturaDettaglio, dateBatch, shibIdentitaCodiceFiscale);
			} catch (Exception exception) {
				logError(sId, methodName,
						"Errore nella gestione della struttura dettaglio " + arpeStrutturaDettaglio.getStrutturaId(),
						exception);
				throw exception;
			} finally {
				logDebug(sId, methodName, LogMessageEnum.END.getMessage());
			}

			// Gestione ruolo ente struttura
			try {
				// Cerco l'id del ruolo ente struttura
				ruoloEnteStrutturaManaged = ruoloEnteStrutturaService.getRuoloEnteStrutturaCodByRuoloCod(sId,
						aslTerritoriale, shibIdentitaCodiceFiscale);
				if (ruoloEnteStrutturaManaged != null) {
					logDebug(sId, methodName,
							" STRUTTURA ID INSERTED : " + ruoloEnteStrutturaManaged.getRuoloEnteStrutturaId());
				}
			} catch (Exception exception) {
				logError(sId, methodName,
						"Errore nella gestione del ruolo ente struttura " + arpeStrutturaDettaglio.getStrutturaId(),
						exception);
				throw exception;
			} finally {
				logDebug(sId, methodName, LogMessageEnum.END.getMessage());
			}

			// Gestione ente struttura
			try {
				// Ente Struttura
				enteStrutturaToManage = new EnteStruttura();
				enteStrutturaToManage.setEnteId(Integer.valueOf(aslCheck.getEnteId()));
				enteStrutturaToManage.setStrutturaId(strutturaManaged.getStrutturaId());
				enteStrutturaToManage.setRuoloEnteStrutturaId(ruoloEnteStrutturaManaged.getRuoloEnteStrutturaId());
				enteStrutturaToManage.setUtenteCreazione(shibIdentitaCodiceFiscale);
				enteStrutturaService.gestioneEnteStruttura(sId, enteStrutturaToManage, dateBatch);
			} catch (Exception exception) {
				logError(sId, methodName,
						"Errore nella gestione dell'ente struttura " + enteStrutturaToManage.getStrutturaId(),
						exception);
				throw exception;
			} finally {
				logDebug(sId, methodName, LogMessageEnum.END.getMessage());
			}

		} finally {
			logDebug(sId, methodName, LogMessageEnum.END.getMessage());
		}

		return strutturaToManage;
	}

	/**
	 * Gestione della cancellazione logica dalla tavola vigil_t_struttura
	 * 
	 * @param sId       Session Id
	 * @param dateBatch data inizio elaborazione
	 * @throws Exception
	 */
	private void cancellaStrutture(Integer sId, Date dateBatch) throws Exception {
		var methodName = "cancellaStrutture";
		logDebug(sId, methodName, LogMessageEnum.BEGIN.getMessage());

		try {
			strutturaService.cancellaStrutture(sId, dateBatch);

		} catch (Exception exception) {
			logError(sId, methodName, "Errore nella gestione della cancellazione delle strutture ", exception);
			throw exception;
		} finally {
			logDebug(sId, methodName, LogMessageEnum.END.getMessage());
		}
	}

	/**
	 * Gestione della cancellazione logica dalla tavola
	 * vigil_r_arpe_struttura_dettaglio
	 * 
	 * @param sId       Session Id
	 * @param dateBatch data inizio elaborazione
	 * @throws Exception
	 */
	private void cancellaStruttureDettaglio(Integer sId, Date dateBatch) throws Exception {
		var methodName = "cancellaStruttureDettaglio";
		logDebug(sId, methodName, LogMessageEnum.BEGIN.getMessage());

		try {
			arpeStrutturaDettaglioService.cancellaStruttureDettaglio(sId, dateBatch);

		} catch (Exception exception) {
			logError(sId, methodName, "Errore nella gestione della cancellazione delle strutture dettaglio ",
					exception);
			throw exception;
		} finally {
			logDebug(sId, methodName, LogMessageEnum.END.getMessage());
		}
	}

	/**
	 * Ricalcolo della tipologia della struttura
	 */
	private void ricalcolaStruttura(Integer sId) throws Exception {
		var methodName = "ricalcolaStruttura";
		logDebug(sId, methodName, LogMessageEnum.BEGIN.getMessage());

		try {
			strutturaService.ricalcolaStruttura(sId);

		} catch (Exception exception) {
			logError(sId, methodName, "Errore nella gestione del ricalcolo della tipologia della struttura", exception);
			logAuditDb(sId, AuditOperazioneEnum.ERROR, "Errore nel ricalcolo della tipologia della struttura",
					ErrorCodeEnum.ERRORE_RICALCOLA_STRUTTURA.getCode());
			throw exception;
		} finally {
			logDebug(sId, methodName, LogMessageEnum.END.getMessage());
		}
	}

	/**
	 * Check se il campo passato è valorizzato (diverso da null e da stringa vuota)
	 * 
	 * @param sId     Session Id
	 * @param inField Campo da verificare
	 * @return
	 * @throws Exception
	 */
	private boolean isInsertable(Integer sId, String inField) throws Exception {
		var methodName = "isInsertable";
		logDebug(sId, methodName, LogMessageEnum.BEGIN.getMessage());

		boolean insertable = false;

		if (inField != null && inField.length() > 0) {
			insertable = true;
		}
		return insertable;
	}

	private String getFieldInArray(Integer sId, String[] inArray, int inPosizione) throws Exception {
		var methodName = "getFieldInArray";
		logDebug(sId, methodName, LogMessageEnum.BEGIN.getMessage());

		String fieldToReturn = null;
		try {
			fieldToReturn = inArray[inPosizione];
			logDebug(sId, methodName, fieldDescription[inPosizione] + " campo in posizione [" + inPosizione
					+ "] valore : " + fieldToReturn);

		} catch (Exception exception) {
			logError(sId, methodName, "Errore nella lettura dei campi da scarico da arpe", exception);
			throw exception;
		} finally {
			logDebug(sId, methodName, LogMessageEnum.END.getMessage());
		}
		return fieldToReturn;
	}

	/*
	 * 0 COD_ASR_TER, 1 DESC_ASR_TER, 2 COD_ASR_TIT, 3 DESC_ASR_TIT, 4
	 * COD_STRUTTURA, 5 DENOM_STRUTTURA, 6 COD_NATURA, 7 NATURA, 8
	 * COD_ACCREDITAMENTO, 9 ACCREDITAMENTO, 10 COD_TIPO_STRUTTURA, 11
	 * DESC_TIPO_STRUTTURA, 12 COD_TIPO_ASSISTENZA, 13 DESC_TIPO_ASSISTENZA, 14
	 * INDIRIZZO, 15 COMUNE, 16 ISTAT_COMUNE, 17 CAP, 18 PROVINCIA, 19 COORD_SRID,
	 * 20 COORD_X, 21 COORD_Y, 22 COD_ATTIVITA, 23 DESC_ATTIVITA, 24
	 * COD_CLASSE_ATTIVITA, 25 DESC_CLASSE_ATTIVITA, 26 COD_TITOLARITA, 27
	 * DESC_TITOLARITA, 28 COD_SPECIALITA, 29 DESC_SPECIALITA 30
	 * DATA_ATTIVAZIONE_STRUTTURA 31 DATA_ATTIVAZIONE_ASSISTENZA 32
	 * DATA_ATTIVAZIONE_ATTIVITA
	 */
}
