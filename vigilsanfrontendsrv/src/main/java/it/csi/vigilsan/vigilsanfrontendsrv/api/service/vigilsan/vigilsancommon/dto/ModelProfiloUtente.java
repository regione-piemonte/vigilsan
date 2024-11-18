/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanfrontendsrv.api.service.vigilsan.vigilsancommon.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ModelProfiloUtente   {
  private Integer sessioneId = null;
  private Integer soggettoId = null;
  private Integer ruoloId = null;
  private Integer strutturaId = null;
  private Integer enteId = null;
  private Integer profiloId = null;
  private Integer applicativoId = null;
  private List<ModelProfiloUtenteProfili> profili = new ArrayList<ModelProfiloUtenteProfili>();
  private List<ModelAuth> auth = new ArrayList<ModelAuth>();

  /**
   **/
  
  @JsonProperty("sessioneId")
  public Integer getSessioneId() {
    return sessioneId;
  }
  public void setSessioneId(Integer sessioneId) {
    this.sessioneId = sessioneId;
  }

  /**
   **/
  
  @JsonProperty("soggettoId")
  public Integer getSoggettoId() {
    return soggettoId;
  }
  public void setSoggettoId(Integer soggettoId) {
    this.soggettoId = soggettoId;
  }

  /**
   **/
  
  @JsonProperty("ruoloId")
  public Integer getRuoloId() {
    return ruoloId;
  }
  public void setRuoloId(Integer ruoloId) {
    this.ruoloId = ruoloId;
  }

  /**
   **/
  
  @JsonProperty("strutturaId")
  public Integer getStrutturaId() {
    return strutturaId;
  }
  public void setStrutturaId(Integer strutturaId) {
    this.strutturaId = strutturaId;
  }

  /**
   **/
  
  @JsonProperty("enteId")
  public Integer getEnteId() {
    return enteId;
  }
  public void setEnteId(Integer enteId) {
    this.enteId = enteId;
  }

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
  
  @JsonProperty("applicativoId")
  public Integer getApplicativoId() {
    return applicativoId;
  }
  public void setApplicativoId(Integer applicativoId) {
    this.applicativoId = applicativoId;
  }

  /**
   **/
  
  @JsonProperty("profili")
  public List<ModelProfiloUtenteProfili> getProfili() {
    return profili;
  }
  public void setProfili(List<ModelProfiloUtenteProfili> profili) {
    this.profili = profili;
  }

  /**
   **/
  
  @JsonProperty("auth")
  public List<ModelAuth> getAuth() {
    return auth;
  }
  public void setAuth(List<ModelAuth> auth) {
    this.auth = auth;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ModelProfiloUtente modelProfiloUtente = (ModelProfiloUtente) o;
    return Objects.equals(sessioneId, modelProfiloUtente.sessioneId) &&
        Objects.equals(soggettoId, modelProfiloUtente.soggettoId) &&
        Objects.equals(ruoloId, modelProfiloUtente.ruoloId) &&
        Objects.equals(strutturaId, modelProfiloUtente.strutturaId) &&
        Objects.equals(enteId, modelProfiloUtente.enteId) &&
        Objects.equals(profiloId, modelProfiloUtente.profiloId) &&
        Objects.equals(applicativoId, modelProfiloUtente.applicativoId) &&
        Objects.equals(profili, modelProfiloUtente.profili) &&
        Objects.equals(auth, modelProfiloUtente.auth);
  }

  @Override
  public int hashCode() {
    return Objects.hash(sessioneId, soggettoId, ruoloId, strutturaId, enteId, profiloId, applicativoId, profili, auth);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ModelProfiloUtente {\n");
    
    sb.append("    sessioneId: ").append(toIndentedString(sessioneId)).append("\n");
    sb.append("    soggettoId: ").append(toIndentedString(soggettoId)).append("\n");
    sb.append("    ruoloId: ").append(toIndentedString(ruoloId)).append("\n");
    sb.append("    strutturaId: ").append(toIndentedString(strutturaId)).append("\n");
    sb.append("    enteId: ").append(toIndentedString(enteId)).append("\n");
    sb.append("    profiloId: ").append(toIndentedString(profiloId)).append("\n");
    sb.append("    applicativoId: ").append(toIndentedString(applicativoId)).append("\n");
    sb.append("    profili: ").append(toIndentedString(profili)).append("\n");
    sb.append("    auth: ").append(toIndentedString(auth)).append("\n");
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
