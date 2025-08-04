package com.mouritech.collegeadmissionmanagment.collegeadmissionmanagment.serviceimpl;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JWTService {
	
	public static final String secret="N0M4s/KzmuYfzPInBpx4UuBh2A3g8rApEsOjRTmUHkg=";
	public String generateToken(String userName)
	{
		Map<String,Object> claims=new HashMap<>();
		return createToken(claims,userName);
		
	}

	private String createToken(Map<String, Object> claims, String userName) {
		
		return Jwts.builder().
				setClaims(claims).
				setSubject(userName).
				setIssuedAt(new Date(System.currentTimeMillis())).
				setExpiration(new Date(System.currentTimeMillis()+1000*60*30)).
				signWith(getSignKey(),SignatureAlgorithm.HS256).compact();
	}

	private Key getSignKey() {
		byte[] keyBytes=Decoders.BASE64.decode(secret);
		
		return Keys.hmacShaKeyFor(keyBytes);
	}

}
