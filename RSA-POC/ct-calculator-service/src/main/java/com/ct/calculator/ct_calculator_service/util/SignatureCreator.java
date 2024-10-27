package com.ct.calculator.ct_calculator_service.util;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Security;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemReader;
import org.springframework.stereotype.Component;

@Component
public class SignatureCreator {
	
	private static final Logger LOGGER = LogManager.getLogger(SignatureCreator.class);
	
	private static final String PRIVATE_KEY_PATH = "./src/main/resources/private_key.pem";

	public PrivateKey getPrivate() throws IOException {
		LogMessage.debug(LOGGER, "getPrivate started:" + PRIVATE_KEY_PATH);
		
		String key = new String(Files.readAllBytes(Paths.get(PRIVATE_KEY_PATH)), Charset.defaultCharset());
		LogMessage.log(LOGGER, "Read file successfully!!! PRIVATE_KEY_PATH:" + PRIVATE_KEY_PATH);
		
		if (key.contains("-----BEGIN PRIVATE KEY-----")) {
			LogMessage.debug(LOGGER, "inside IF BEGIN PRIVATE KEY");
			
			String privateKeyPEM = key.replace("-----BEGIN PRIVATE KEY-----", "").replaceAll(System.lineSeparator(), "")
					.replace("-----END PRIVATE KEY-----", "");
			byte[] encoded = Base64.getDecoder().decode(privateKeyPEM);
			KeyFactory keyFactory;
			try {
				keyFactory = KeyFactory.getInstance("RSA");
				PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(encoded);
				
				LogMessage.debug(LOGGER, "IN IF About to generate & return privateKey");
				return keyFactory.generatePrivate(keySpec);
			} catch (InvalidKeySpecException e) {
				e.printStackTrace();
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
		} else {
			LogMessage.debug(LOGGER, "inside ELSE");
			
			Security.addProvider(new BouncyCastleProvider());
			try {
				KeyFactory factory = KeyFactory.getInstance("RSA");
				File privateKey = new File(PRIVATE_KEY_PATH);
				FileReader keyReader = new FileReader(privateKey);
				
				PemReader pemReader = new PemReader(keyReader);
				PemObject pemObject = pemReader.readPemObject();
				byte[] content = pemObject.getContent();
				
				LogMessage.debug(LOGGER, "Read File PRIVATE_KEY_PATH:" + PRIVATE_KEY_PATH);
				
				
				PKCS8EncodedKeySpec privKeySpec = new PKCS8EncodedKeySpec(content);
				pemReader.close();
				
				LogMessage.debug(LOGGER, "IN ELSE About to generate & return privateKey");
				
				return (RSAPrivateKey) factory.generatePrivate(privKeySpec);
			}catch (Exception e){
				e.printStackTrace();
			} finally {
				
			}
		}

		return null;
	}

	public String generateSignature(String requestBody) throws Exception {
		PrivateKey privateKey = getPrivate();
		Signature signature = Signature.getInstance("SHA256withRSA");
		signature.initSign(privateKey);
		signature.update(requestBody.getBytes(StandardCharsets.UTF_8));
		byte[] digitalSignature = signature.sign();
		return Base64.getEncoder().encodeToString(digitalSignature);
	}
	
//	public static void main(String[] args) throws Exception {
//		System.out.println(generateSignature("get|api2.ct.com/merchant/providers?countrycode=lt&pageno=1&pagesize=1000|"));
//	}
}
