/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.org.openehealth.ipf.xds;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlEnumValue;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per AvailabilityStatus.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * <p>
 * <pre>
 * &lt;simpleType name="AvailabilityStatus">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="Approved"/>
 *     &lt;enumeration value="Deprecated"/>
 *     &lt;enumeration value="Submitted"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "AvailabilityStatus")
@XmlEnum
public enum AvailabilityStatus {

    @XmlEnumValue("Approved")
    APPROVED("Approved"),
    @XmlEnumValue("Deprecated")
    DEPRECATED("Deprecated"),
    @XmlEnumValue("Submitted")
    SUBMITTED("Submitted");
    private final String value;

    AvailabilityStatus(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static AvailabilityStatus fromValue(String v) {
        for (AvailabilityStatus c: AvailabilityStatus.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
