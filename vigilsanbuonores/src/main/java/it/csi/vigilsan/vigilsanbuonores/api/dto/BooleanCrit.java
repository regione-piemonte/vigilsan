/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanbuonores.api.dto;

import java.util.Objects;
import java.util.ArrayList;
import java.util.HashMap;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import jakarta.validation.constraints.*;

public class BooleanCrit   {
  private Boolean eq = null;

  /**
   **/
  
  @JsonProperty("eq")
  public Boolean isEq() {
    return eq;
  }
  public void setEq(Boolean eq) {
    this.eq = eq;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    BooleanCrit booleanCrit = (BooleanCrit) o;
    return Objects.equals(eq, booleanCrit.eq);
  }

  @Override
  public int hashCode() {
    return Objects.hash(eq);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class BooleanCrit {\n");
    
    sb.append("    eq: ").append(toIndentedString(eq)).append("\n");
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
