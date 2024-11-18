/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanfrontendsrv.api.service.vigilsan.vigilsancommon.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ModelProfiloUtenteProfili   {
  private Integer profiloId = null;
  private List<String> funzione = new ArrayList<String>();

  /**
   **/
  
  @JsonProperty("profiloId")
  public Integer getProfiloId() {
    return profiloId;
  }
  public void setProfiloId(Integer profiloId) {
    this.profiloId = profiloId;
  }

  /**
   **/
  
  @JsonProperty("funzione")
  public List<String> getFunzione() {
    return funzione;
  }
  public void setFunzione(List<String> funzione) {
    this.funzione = funzione;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ModelProfiloUtenteProfili modelProfiloUtenteProfili = (ModelProfiloUtenteProfili) o;
    return Objects.equals(profiloId, modelProfiloUtenteProfili.profiloId) &&
        Objects.equals(funzione, modelProfiloUtenteProfili.funzione);
  }

  @Override
  public int hashCode() {
    return Objects.hash(profiloId, funzione);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ModelProfiloUtenteProfili {\n");
    
    sb.append("    profiloId: ").append(toIndentedString(profiloId)).append("\n");
    sb.append("    funzione: ").append(toIndentedString(funzione)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}
