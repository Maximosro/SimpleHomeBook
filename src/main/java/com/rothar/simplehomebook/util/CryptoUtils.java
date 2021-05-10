package com.rothar.simplehomebook.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.stereotype.Component;

@Component
public class CryptoUtils {
	
	
	public byte[] encodePass(String pass)  {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-512");
			return md.digest(pass.getBytes(StandardCharsets.UTF_8));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
	}


}
