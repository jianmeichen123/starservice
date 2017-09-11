package com.galaxy.im.common;

import java.security.MessageDigest;
import java.security.SecureRandom;

import com.galaxy.im.common.db.IdGenerator;

public class TokenGenerator {
	private TokenGenerator() {
	}

	private static final TokenGenerator instance = new TokenGenerator();
	SecureRandom ran = new SecureRandom();

	public static TokenGenerator getInstance() {
		return instance;
	}

	public String generateToken() {
		String token = System.currentTimeMillis() + ran.nextInt() + "";
		try {
			MessageDigest md5 = MessageDigest.getInstance("md5");
			byte[] md = md5.digest(token.getBytes());
			return Base64Util.encodeBase64(md);
		} catch (Exception e) {
			return IdGenerator.generateId(TokenGenerator.class).toString();
		}
	}
}
