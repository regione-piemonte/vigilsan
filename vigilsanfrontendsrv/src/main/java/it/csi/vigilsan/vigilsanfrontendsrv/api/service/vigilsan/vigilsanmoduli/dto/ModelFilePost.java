/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanfrontendsrv.api.service.vigilsan.vigilsanmoduli.dto;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ModelFilePost   {
  private List<String> cfVerificaFirma = new ArrayList<>();
  private String base64File = null;
  private Integer fileTipoId = null;
  private Integer fileContentTypeId = null;
  private String fileName = null;
  private String enteCod = null;
  private String strutturaCod = null;
  private Integer soggettoId = null;

  /**
   **/
  
  @JsonProperty("cfVerificaFirma")
  public List<String> getCfVerificaFirma() {
    return cfVerificaFirma;
  }
  public void setCfVerificaFirma(List<String> cfVerificaFirma) {
    this.cfVerificaFirma = cfVerificaFirma;
  }

  /**
   **/
  
  @JsonProperty("base64File")
  public String getBase64File() {
    return base64File;
  }
  public void setBase64File(String base64File) {
    this.base64File = base64File;
  }

  /**
   **/
  
  @JsonProperty("fileTipoId")
  public Integer getFileTipoId() {
    return fileTipoId;
  }
  public void setFileTipoId(Integer fileTipoId) {
    this.fileTipoId = fileTipoId;
  }

  /**
   **/
  
  @JsonProperty("fileContentTypeId")
  public Integer getFileContentTypeId() {
    return fileContentTypeId;
  }
  public void setFileContentTypeId(Integer fileContentTypeId) {
    this.fileContentTypeId = fileContentTypeId;
  }

  /**
   **/
  
  @JsonProperty("fileName")
  public String getFileName() {
    return fileName;
  }
  public void setFileName(String fileName) {
    this.fileName = fileName;
  }

  /**
   **/
  
  @JsonProperty("enteCod")
  public String getEnteCod() {
    return enteCod;
  }
  public void setEnteCod(String enteCod) {
    this.enteCod = enteCod;
  }

  /**
   **/
  
  @JsonProperty("strutturaCod")
  public String getStrutturaCod() {
    return strutturaCod;
  }
  public void setStrutturaCod(String strutturaCod) {
    this.strutturaCod = strutturaCod;
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
}
