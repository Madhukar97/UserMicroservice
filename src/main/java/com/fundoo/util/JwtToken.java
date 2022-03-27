package com.fundoo.util;

import java.util.HashMap;
import java.util.Map;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.Verification;

public class JwtToken {

	private final String SECRET_KEY = "secret";

	public String createToken (String username, int id) {
		Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);

		Map<String, Object> payloadClaims = new HashMap<>();
		payloadClaims.put("username", username);
		payloadClaims.put("id", id);

		String token = JWT.create()
			        .withPayload(payloadClaims)
			        .sign(algorithm);

		return token;
	}

	public String decodeTokenUsername(String token) {
		int id;
		String username;
	           //for verification algorithm
	           Verification verification = null;
		try {
		verification = JWT.require(Algorithm.HMAC256(SECRET_KEY));
		} catch (IllegalArgumentException  e) {
			e.printStackTrace();
		}
		JWTVerifier jwtverifier=verification.build();
		//to decode token
		DecodedJWT decodedjwt=jwtverifier.verify(token);

		Map<String, Claim> claims = decodedjwt.getClaims();
		Claim claim = claims.get("id");
		id=claim.asInt();
		Claim claim2 = claims.get("username");
		username = claim2.asString();
		return username;
	}

	public int decodeTokenUserId(String token) {
		int id;
		String username;
	           //for verification algorithm
	           Verification verification = null;
		try {
		verification = JWT.require(Algorithm.HMAC256(SECRET_KEY));
		} catch (IllegalArgumentException  e) {
			e.printStackTrace();
		}
		JWTVerifier jwtverifier=verification.build();
		//to decode token
		DecodedJWT decodedjwt=jwtverifier.verify(token);

		Map<String, Claim> claims = decodedjwt.getClaims();
		Claim claim = claims.get("id");
		id=claim.asInt();
		Claim claim2 = claims.get("username");
		username = claim2.asString();
		return id;
	}

}
