/*
 * Copyright Regione Piemonte - 2024
 * SPDX-License-Identifier: EUPL-1.2
 */

// This file can be replaced during build by using the `fileReplacements` array.
// `ng build` replaces `environment.ts` with `environment.prod.ts`.
// The list of file replacements can be found in `angular.json`.

export const environment = {
  production: false,
  endpoint: "https://tst-vigilsan.salutepiemonte.it/vigilsanforfree/bff",
  assistenza: "https://dev-assistenzapua.portali.csi.it/#/RESIDENZ",
  manuale:"https://tst-vigilsan.salutepiemonte.it/manualivigilsan/",
  gestruttureUrl: "https://int-niv-rupcovid.sdp.csi.it/gestruttureres/#/"
  // esci: "https://tst-buonoresbo.sceltasociale.it/tst-buonoresbo_443sliv2wrup/Shibboleth.sso/Logout",
};

/*
 * For easier debugging in development mode, you can import the following file
 * to ignore zone related error stack frames such as `zone.run`, `zoneDelegate.invokeTask`.
 *
 * This import should be commented out in production mode because it will have a negative impact
 * on performance if an error is thrown.
 */
// import 'zone.js/plugins/zone-error';  // Included with Angular CLI.
