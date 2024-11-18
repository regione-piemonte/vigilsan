/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanmoduli.util;

import java.io.IOException;

import org.apache.pdfbox.Loader;

public enum FileTypeEnum {

	pdf("pdf","pdf") {
	
		@Override
		public Boolean isType(byte[] file) {
			try (var pdf = Loader.loadPDF(file)) {
				pdf.getNumberOfPages();
				return true;
			} catch (IOException e) {
				return false;
			}
		}

	};

	private final String code;
	private final String extension;

	private FileTypeEnum(String code, String extension) {
		this.code = code;
		this.extension = extension;
	}

	public String getCode() {
		return code;
	}

	public abstract Boolean isType(byte[] file);

	public String getExtension() {
		return extension;
	}
}
