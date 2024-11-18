/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanfrontendsrv.api.service.vigilsan.vigilsancommon.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ModelAuth   {
  private String f = null;
  private String p = null;
  private List<ModelAuth> a = new ArrayList<ModelAuth>();

  /**
   **/
  
  @JsonProperty("f")
  public String getF() {
    return f;
  }
  public void setF(String f) {
    this.f = f;
  }

  /**
   **/
  
  @JsonProperty("p")
  public String getP() {
    return p;
  }
  public void setP(String p) {
    this.p = p;
  }

  /**
   **/
  
  @JsonProperty("a")
  public List<ModelAuth> getA() {
    return a;
  }
  public void setA(List<ModelAuth> a) {
    this.a = a;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ModelAuth modelAuth = (ModelAuth) o;
    return Objects.equals(f, modelAuth.f) &&
        Objects.equals(p, modelAuth.p) &&
        Objects.equals(a, modelAuth.a);
  }

  @Override
  public int hashCode() {
    return Objects.hash(f, p, a);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ModelAuth {\n");
    
    sb.append("    f: ").append(toIndentedString(f)).append("\n");
    sb.append("    p: ").append(toIndentedString(p)).append("\n");
    sb.append("    a: ").append(toIndentedString(a)).append("\n");
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
