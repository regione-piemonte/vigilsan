# Prodotto
**VIGILSAN (Rilevazioni e Vigilanza sanitaria)**
## Versione
2.0.0

## Descrizione del prodotto

La piattaforma VIGILSAN (Piattaforma Regionale della Residenzialità) è uno strumento web che consente agli operatori del settore di avere accesso a uno strumento innovativo e completo per condividere con le strutture regionali le rilevazioni dei posti letto disponibili.
 
Questo permette una rapida collocazione dei pazienti che, dopo un ricovero ospedaliero, necessitano di cure in strutture specializzate come quelle per anziani, disabili, minori, disabili psichiatrici e persone con dipendenze.

La possibilità di avere una visione aggiornata e precisa dei posti letto disponibili consente di ottimizzare la gestione delle risorse e di garantire un'assistenza tempestiva e adeguata ai pazienti. 

Elenco macro-funzionalità:
*	**Gestione del Profilo**: sezione che visualizza i dati anagrafici e il profilo applicativo dell’utente che ha effettuato l’accesso; consente inoltre di accedere ai dati anagrafici censiti in ARPE – Archivio regionale dei punti di erogazione, della struttura per la quale l’utente intende operare.

*	**Gestione documentale**: sezione che consente ai referenti delle strutture residenziali e semi-residenziali di raccogliere la documentazione relativa alle strutture di competenza per consentire alla Commissione di Vigilanza di effettuare le verifiche durante le visite ispettive.

*	**Rilevazioni**: sezione che consente agli operatori delle strutture residenziali e semi-residenziali di procedere alla registrazione delle informazioni di base e delle rilevazioni giornaliere e/o settimanali dei posti occupati e a disposizione per i propri ospiti.

*	**Questionari**: sezione che consente al settore regionale competente di procedere periodicamente alla raccolta di informazioni dalle strutture residenziali e semi-residenziali, per meglio indirizzare le attività di programmazione degli interventi nell’area socio-sanitaria.

*	**Ospiti**: sezione che consente alle strutture residenziali di registrare i dati anagrafici e i movimenti dei propri ospiti.

*	**Adesione**: sezione che consente a strutture residenziali a carattere socio-sanitario e socio-assistenziale di persone non autosufficienti di registrare la propria adesione al bando relativo il buono residenzialità per la copertura di parte delle spese sostenute dalle famiglie per l'ospitalità in regime privatistico.

*	**Rendicontazione**: sezione che consente ad una struttura residenziale a carattere socio-sanitario e socio-assistenziale di procedere al caricamento delle rendicontazioni attestanti l’effettivo utilizzo del buono residenzialità per i propri ospiti destinatari della misura.

*	**Pratiche di vigilanza**: sezione rivolta ai referenti delle commissioni di vigilanza delle ASL per pianificare i sopralluoghi presso le strutture e gestirne gli esiti condividendo i verbali con i referenti delle strutture e gestendo eventuali feedback.


Elenco componenti:

* [VIGILSANFRONTEND](vigilsanfrontend) Componente frontend
* [VIGILSANFRONTENDSRV](vigilsanfrontendsrv) Servizi per frontend
* [VIGILSANBATCHSRV](vigilsanbatchsrv) Servizi per procedure schedulate (ETL) 
* [VIGILSANEXTSRV](vigilsanextsrv) Servizi per fruitori esterni
* [VIGILSANPRATICHE](vigilsanpratiche) Servizi per gestione vigilanza, pratiche e appuntamenti 
* [VIGILSANMODULI](vigilsanmoduli) Servizi per gestione moduli e file
* [VIGILSANBUONORES](vigilsanbuonores) Servizi  per gestione adesioni, rendicontazione e buono welfare 
* [VIGILSANRILEVAZIONI](vigilsanrilevazioni) Servizi per gestione documentazione e rilevazione 
* [VIGILSANCOMMON](vigilsancommon) Servizi per gestione utenti, soggetti, strutture, gestione accessi, enti gestori e autorizzazione 
* [VIGILSANUTIL](vigilsanutil) Libreria per funzionalita comuni 

## Configurazioni iniziali

Si rimanda ai readme delle singole componenti

* [VIGILSANFRONTEND](vigilsanfrontend/README.md)
* [VIGILSANFRONTENDSRV](vigilsanfrontendsrv/README.md)
* [VIGILSANEXTSRV](vigilsanextsrv/README.md)
* [VIGILSANBATCHSRV](vigilsanbatchsrv/README.md)
* [VIGILSANPRATICHE](vigilsanpratiche/README.md)
* [VIGILSANMODULI](vigilsanmoduli/README.md)
* [VIGILSANBUONORES](vigilsanbuonores/README.md)
* [VIGILSANRILEVAZIONI](vigilsanrilevazioni/README.md)
* [VIGILSANCOMMON](vigilsancommon/README.md)
* [VIGILSANUTIL](vigilsanutil/README.md)

## Prerequisiti di sistema

**Server Web**:
Apache 2.4.58

**Framework** :
Angular versione 13.3.11

**Application Server**:
Spring Boot environment version 3.2.4 

on jdk environment

OpenJDK Runtime Environment Temurin-17.0.8.1+1 (build 17.0.8.1+1)

OpenJDK 64-Bit Server VM Temurin-17.0.8.1+1 (build 17.0.8.1+1, mixed mode, sharing)

**Tipo di database**:
PostgreSQL v15.4

Dipendenze elencate nella cartella docs/wsdl

## Versioning

Per il versionamento del software si usa la tecnica Semantic Versioning (http://semver.org).

## Authors

* [Guido Coutandin](https://github.com/guido-coutandin)
* Laura Moscatelli
* [Alessandro Trombotto](https://github.com/alessandro-trombotto)

## Copyrights

“© Copyright Regione Piemonte – 2024”

## License

SPDX-License-Identifier: inserire il codice SPDX delle licenza
Vedere il file LICENSE per i dettagli in SPDX-License-Identifier: EUPL-1.2-or-later.
