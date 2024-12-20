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
import java.util.List;
import jakarta.validation.constraints.*;

public class IntegerCrit   {
  private Integer eq = null;
  private Integer ne = null;
  private Integer lt = null;
  private Integer lte = null;
  private Integer gt = null;
  private Integer gte = null;
  private List<Integer> in = new ArrayList<Integer>();
  private List<Integer> nin = new ArrayList<Integer>();

  /**
   **/
  
  @JsonProperty("eq")
  public Integer getEq() {
    return eq;
  }
  public void setEq(Integer eq) {
    this.eq = eq;
  }

  /**
   **/
  
  @JsonProperty("ne")
  public Integer getNe() {
    return ne;
  }
  public void setNe(Integer ne) {
    this.ne = ne;
  }

  /**
   **/
  
  @JsonProperty("lt")
  public Integer getLt() {
    return lt;
  }
  public void setLt(Integer lt) {
    this.lt = lt;
  }

  /**
   **/
  
  @JsonProperty("lte")
  public Integer getLte() {
    return lte;
  }
  public void setLte(Integer lte) {
    this.lte = lte;
  }

  /**
   **/
  
  @JsonProperty("gt")
  public Integer getGt() {
    return gt;
  }
  public void setGt(Integer gt) {
    this.gt = gt;
  }

  /**
   **/
  
  @JsonProperty("gte")
  public Integer getGte() {
    return gte;
  }
  public void setGte(Integer gte) {
    this.gte = gte;
  }

  /**
   **/
  
  @JsonProperty("in")
  public List<Integer> getIn() {
    return in;
  }
  public void setIn(List<Integer> in) {
    this.in = in;
  }

  /**
   **/
  
  @JsonProperty("nin")
  public List<Integer> getNin() {
    return nin;
  }
  public void setNin(List<Integer> nin) {
    this.nin = nin;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    IntegerCrit integerCrit = (IntegerCrit) o;
    return Objects.equals(eq, integerCrit.eq) &&
        Objects.equals(ne, integerCrit.ne) &&
        Objects.equals(lt, integerCrit.lt) &&
        Objects.equals(lte, integerCrit.lte) &&
        Objects.equals(gt, integerCrit.gt) &&
        Objects.equals(gte, integerCrit.gte) &&
        Objects.equals(in, integerCrit.in) &&
        Objects.equals(nin, integerCrit.nin);
  }

  @Override
  public int hashCode() {
    return Objects.hash(eq, ne, lt, lte, gt, gte, in, nin);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class IntegerCrit {\n");
    
    sb.append("    eq: ").append(toIndentedString(eq)).append("\n");
    sb.append("    ne: ").append(toIndentedString(ne)).append("\n");
    sb.append("    lt: ").append(toIndentedString(lt)).append("\n");
    sb.append("    lte: ").append(toIndentedString(lte)).append("\n");
    sb.append("    gt: ").append(toIndentedString(gt)).append("\n");
    sb.append("    gte: ").append(toIndentedString(gte)).append("\n");
    sb.append("    in: ").append(toIndentedString(in)).append("\n");
    sb.append("    nin: ").append(toIndentedString(nin)).append("\n");
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
