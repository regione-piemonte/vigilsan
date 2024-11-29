/*
 * Copyright Regione Piemonte - 2024
 * SPDX-License-Identifier: EUPL-1.2
 */

import { Client } from "../Client";

// src/app/utils/utils.ts
export class LoginConstants {

  constructor() {}

  static setPietraVentitreAslCittaTorino(client: Client) {
    client.loggedUser = {
      "profiloId": 5,
      "soggetto": {
        "soggettoId": 194,
        "codiceFiscale": "VNTPTR60S65D604A",
        "nome": "PIETRA",
        "cognome": "VENTITRE",
        "presenteConfiguratore": true,
        "dataNascita": null
      },
      "struttura": null,
      "ente": {
        "enteId": 12,
        "enteCod": "301",
        "enteCodConfiguratore": "010301",
        "enteDesc": "A.S.L. CITTA' DI TORINO",
        "enteTipoId": 2,
        "enteTipo": {
          "enteTipoId": 2,
          "enteTipoCod": "ASL",
          "enteTipoDesc": "A.S.L."
        }
      },
      "ruolo": {
        "ruoloId": 1,
        "ruoloCod": "OAM",
        "ruoloDesc": "Operatore amministrativo"
      },
      "applicativo": {
        "applicativoId": 1,
        "applicativoCod": "VIGILSAN",
        "applicativoDesc": "Modulo vigilanza / residenzialitÃ "
      },
      "profili": [
        {
          "profiloId": 5,
          "profiloCod": "VIGIL_ENTE",
          "profiloDesc": "Operatore commissione di vigilanza"
        },
        {
          "profiloId": 2,
          "profiloCod": "VIGIL_ASL",
          "profiloDesc": "Operatore ASL"
        }
      ],
      "funzioni": [
        {
          "funzione": "vigil_hom",
          "permesso": "R"
        },
        {
          "funzione": "vigil_hom-ent",
          "permesso": "R"
        },
        {
          "funzione": "vigil_hom-str",
          "permesso": "R"
        },
        {
          "funzione": "vigil_hom-str-sel",
          "permesso": "R"
        },
        {
          "funzione": "vigil_ril",
          "permesso": null
        },
        {
          "funzione": "vigil_ril-inf",
          "permesso": null
        },
        {
          "funzione": "vigil_ril-ril",
          "permesso": null
        },
        {
          "funzione": "vigil_ril-csvcovid",
          "permesso": null
        },
        {
          "funzione": "vigil_doc",
          "permesso": "R"
        },
        {
          "funzione": "vigil_osp",
          "permesso": null
        },
        {
          "funzione": "vigil_osp-new",
          "permesso": null
        },
        {
          "funzione": "vigil_osp-mov",
          "permesso": null
        },
        {
          "funzione": "vigil_pra",
          "permesso": "W"
        },
        {
          "funzione": "vigil_pra-filter",
          "permesso": "R"
        },
        {
          "funzione": "vigil_pra-new",
          "permesso": "W"
        },
        {
          "funzione": "vigil_pra-det",
          "permesso": "W"
        },
        {
          "funzione": "vigil_bra",
          "permesso": null
        },
        {
          "funzione": "vigil_brr",
          "permesso": null
        },
        {
          "funzione": "vigil_tmp",
          "permesso": null
        },
        {
          "funzione": "vigil_tst",
          "permesso": "R"
        },
        {
          "funzione": "vigil_pra-scad",
          "permesso": "R"
        },
        {
          "funzione": "vigil_pra-comm",
          "permesso": "R"
        },
      ]
    }
  }

  static setPasquaVentiquattroResidenzaSantaRita(client: Client) {
    client.loggedUser = {
      profiloId: 1,
      soggetto: {
        soggettoId: 1,
        codiceFiscale: "VNTPSQ97E66A971K",
        nome: "PASQUA",
        cognome: "VENTIQUATTRO",
        presenteConfiguratore: true,
        dataNascita: null
      },
      struttura: {
        strutturaId: 585,
        strutturaCod: "671262",
        strutturaCodConfiguratore: "10535550015@671262",
        strutturaCodArpe: "671262",
        strutturaDesc: "RESIDENZA SANTA RITA",
        strutturaTipoId: 3,
        comuneId: 268,
        indirizzo: "VIA ADA NEGRI 1",
        cap: "10141",
        coordSrid: "32632",
        coordX: "39318400780718300",
        coordY: "4989587928457570",
        strutturaNaturaId: 2,
        strutturaAccreditamentoId: 1,
        validitaInizio: 1565647200000,
        natura: {
          strutturaNaturaId: 2,
          strutturaNaturaCod: "PRIVATA",
          strutturaNaturaCodArpe: "privato",
          strutturaNaturaDesc: "privata"
        },
        asl: [
          {
            enteId: 12,
            enteCod: "301",
            enteCodConfiguratore: "010301",
            enteDesc: "A.S.L. CITTA' DI TORINO",
            enteTipoId: 2
          }
        ],
        comune: {
          comuneId: 268,
          comuneCod: "001272",
          comuneDesc: "TORINO",
          provinciaId: 1,
          provincia: {
            provinciaId: 1,
            provinciaCod: "001",
            provinciaSigla: "TO",
            provinciaDesc: "TORINO",
            regioneId: 1,
            regione: {
              regioneId: 1,
              regioneCod: "010",
              regioneDesc: "PIEMONTE"
            }
          }
        },
        strutturaAccreditamento: {
          strutturaAccreditamentoId: 1,
          strutturaAccreditamentoCod: "ACC_NO",
          strutturaAccreditamentoCodArpe: "Non accreditato",
          strutturaAccreditamentoDesc: "Non Accreditata"
        },
        strutturaTipo: {
          strutturaTipoId: 3,
          strutturaTipoCod: "SAN_RES",
          strutturaTipoDesc: "Struttura sanitaria residenziale"
        },
        strutturaTitolarita: {
          strutturaTitolaritaId: 4610,
          strutturaTitolaritaCod: "2",
          strutturaTitolaritaCodArpe: "2",
          strutturaTitolaritaDesc: "Societa'"
        },
        categorie: [
          {
            validitaInizio: 1705273200000,
            strutturaCategoriaId: 1,
            strutturaCategoriaCod: "SAN_CAVS",
            strutturaCategoriaDesc: "CAVS"
          }
        ]
      },
      ente: null,
      ruolo: {
        ruoloId: 1,
        ruoloCod: "OAM",
        ruoloDesc: "Operatore amministrativo"
      },
      applicativo: {
        applicativoId: 1,
        applicativoCod: "VIGILSAN",
        applicativoDesc: "Modulo vigilanza / residenzialitÃ "
      },
      profili: [
        {
          profiloId: 1,
          profiloCod: "VIGIL_RES",
          profiloDesc: null
        }
      ],
      funzioni: [
        {
          funzione: "vigil_hom",
          permesso: "R"
        },
        {
          funzione: "vigil_hom-ent",
          permesso: "R"
        },
        {
          funzione: "vigil_hom-str",
          permesso: "R"
        },
        {
          funzione: "vigil_hom-str-sel",
          permesso: null
        },
        {
          funzione: "vigil_ril",
          permesso: "W"
        },
        {
          funzione: "vigil_ril-inf",
          permesso: "W"
        },
        {
          funzione: "vigil_ril-ril",
          permesso: "W"
        },
        {
          funzione: "vigil_ril-csvcovid",
          permesso: "R"
        },
        {
          funzione: "vigil_doc",
          permesso: "W"
        },
        {
          funzione: "vigil_osp",
          permesso: "W"
        },
        {
          funzione: "vigil_osp-new",
          permesso: "W"
        },
        {
          funzione: "vigil_osp-mov",
          permesso: "W"
        },
        {
          funzione: "vigil_pra",
          permesso: "W"
        },
        {
          funzione: "vigil_pra-filter",
          permesso: null
        },
        {
          funzione: "vigil_pra-new",
          permesso: null
        },
        {
          funzione: "vigil_pra-det",
          permesso: "W"
        },
        {
          funzione: "vigil_abr",
          permesso: "R"
        },
        {
          funzione: "vigil_rbr",
          permesso: "R"
        },
        {
          funzione: "vigil_tmp",
          permesso: "R"
        }
      ]
    }
  }
}
