/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.it.csi.gatefire;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per ruolo.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * <p>
 * <pre>
 * &lt;simpleType name="ruolo">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="AAS"/>
 *     &lt;enumeration value="APR"/>
 *     &lt;enumeration value="PSS"/>
 *     &lt;enumeration value="INF"/>
 *     &lt;enumeration value="FAR"/>
 *     &lt;enumeration value="DSA"/>
 *     &lt;enumeration value="DAM"/>
 *     &lt;enumeration value="OAM"/>
 *     &lt;enumeration value="ASS"/>
 *     &lt;enumeration value="TUT"/>
 *     &lt;enumeration value="ING"/>
 *     &lt;enumeration value="GEN"/>
 *     &lt;enumeration value="NOR"/>
 *     &lt;enumeration value="DRS"/>
 *     &lt;enumeration value="RSA"/>
 *     &lt;enumeration value="MRP"/>
 *     &lt;enumeration value="INI"/>
 *     &lt;enumeration value="OGC"/>
 *     &lt;enumeration value="OPI"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "ruolo")
@XmlEnum
public enum Ruolo {

    AAS,
    APR,
    PSS,
    INF,
    FAR,
    DSA,
    DAM,
    OAM,
    ASS,
    TUT,
    ING,
    GEN,
    NOR,
    DRS,
    RSA,
    MRP,
    INI,
    OGC,
    OPI;

    public String value() {
        return name();
    }

    public static Ruolo fromValue(String v) {
        return valueOf(v);
    }

}
