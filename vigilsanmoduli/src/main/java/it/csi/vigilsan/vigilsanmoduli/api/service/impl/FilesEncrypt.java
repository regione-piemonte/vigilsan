/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanmoduli.api.service.impl;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import it.csi.vigilsan.vigilsanmoduli.util.ErrorCodeEnum;
import it.csi.vigilsan.vigilsanutil.generic.services.error.RESTErrorUtil;
import it.csi.vigilsan.vigilsanutil.generic.services.log.AuditableWPersistenceApiServiceImpl;

@Service
public class FilesEncrypt extends AuditableWPersistenceApiServiceImpl {

    private static final int IV_LENGTH = 12;
	private static final String ALGORITHM = "AES/GCM/NoPadding";
    private static final String TRANSFORMATION = "AES";

	@Value("${encrypt.key}")
	private String keyEncrypt;
	// AES KEY size only 16,24, 32
	
    public void encrypt(Integer sId, byte[] inputFile, Path filePath)  {
    	final var methodName = "encrypt";
        try {
            var secureRandom = new SecureRandom();
            var iv = new byte[IV_LENGTH]; // Initialization vector size for GCM mode is 12 bytes
            secureRandom.nextBytes(iv);

	        var cipher = Cipher.getInstance(ALGORITHM);
	        SecretKey secretKey = new SecretKeySpec(keyEncrypt.getBytes(), TRANSFORMATION);
	        var parameterSpec = new GCMParameterSpec(128, iv);
	        cipher.init(Cipher.ENCRYPT_MODE, secretKey, parameterSpec);
	        
	        try (var outputStream = new FileOutputStream(filePath.toFile())) {
	            outputStream.write(iv);
	            outputStream.write(cipher.doFinal(inputFile));
	        }
		} catch (GeneralSecurityException e) {
			logError(sId, methodName, "GeneralSecurityException: " + e.getMessage(), e);
			throw RESTErrorUtil.generateGenericRestException(ErrorCodeEnum.ERRORE_ENCRYPT_FILE_SECURITY_EXC);
		} catch (IOException e) {
			logError(sId, methodName, "IOException: " + e.getMessage(), e);
			throw RESTErrorUtil.generateGenericRestException(ErrorCodeEnum.ERRORE_ENCRYPT_FILE_IOE_EXC);
		}
    }

    public byte[] decrypt(Integer sId, Path filePath)  {
    	final var methodName = "decrypt";
        try {
	        var fileBytes = Files.readAllBytes(filePath);
	        var iv = Arrays.copyOfRange(fileBytes, 0, IV_LENGTH);
	        
	        var cipher = Cipher.getInstance(ALGORITHM);
	        SecretKey secretKey = new SecretKeySpec(keyEncrypt.getBytes(), TRANSFORMATION);
	        var parameterSpec = new GCMParameterSpec(128, iv);
	        cipher.init(Cipher.DECRYPT_MODE, secretKey, parameterSpec);

            return cipher.doFinal(getCryptedFileFromFile(fileBytes));
		} catch (GeneralSecurityException e) {
			logError(sId, methodName, "GeneralSecurityException: " + e.getMessage(), e);
			throw RESTErrorUtil.generateGenericRestException(ErrorCodeEnum.ERRORE_DECRYPT_FILE_SECURITY_EXC);
		} catch (IOException e) {
			logError(sId, methodName, "IOException: " + e.getMessage(), e);
			throw RESTErrorUtil.generateGenericRestException(ErrorCodeEnum.ERRORE_DECRYPT_FILE_IOE_EXC);
		}
    }

	private byte[] getCryptedFileFromFile(byte[] fileBytes) {
		var lunghezzaArrayFinale = fileBytes.length - IV_LENGTH;
		var arrayFinale = new byte[fileBytes.length - IV_LENGTH];
		System.arraycopy(fileBytes, IV_LENGTH, arrayFinale, 0, lunghezzaArrayFinale);
		return arrayFinale;
	}
}


