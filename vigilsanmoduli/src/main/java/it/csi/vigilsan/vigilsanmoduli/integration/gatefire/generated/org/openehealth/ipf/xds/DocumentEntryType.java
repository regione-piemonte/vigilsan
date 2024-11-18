/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.org.openehealth.ipf.xds;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlEnumValue;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per DocumentEntryType.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * <p>
 * <pre>
 * &lt;simpleType name="DocumentEntryType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="stable"/>
 *     &lt;enumeration value="on-demand"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "DocumentEntryType")
@XmlEnum
public enum DocumentEntryType {

    @XmlEnumValue("stable")
    STABLE("stable"),
    @XmlEnumValue("on-demand")
    ON_DEMAND("on-demand");
    private final String value;

    DocumentEntryType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static DocumentEntryType fromValue(String v) {
        for (DocumentEntryType c: DocumentEntryType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
