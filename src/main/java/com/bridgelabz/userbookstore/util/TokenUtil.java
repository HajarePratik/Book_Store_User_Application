package com.bridgelabz.userbookstore.util;

import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.auth0.jwt.interfaces.Verification;


@Component
public class TokenUtil
{
	public final static String TOKEN_SECRET="Pratik";

	public static  String createToken(int userId)   
	{
		      try 
		      {
			       Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
			        
			       String token = JWT.create().withClaim("user_id", userId)
			       .sign(algorithm);
			        
			       return token;
		      }
		      catch (Exception exception) 
		      {
		    	   exception.printStackTrace();
		      } 
		      return null;
	}

	public static int decodeToken(String token)
	{
			int userId;
           //for verification algorithm
           Verification verification = null;
           try
           {
        	   	verification = JWT.require(Algorithm.HMAC256(TOKEN_SECRET));
           } 
           catch (IllegalArgumentException  e) 
           {

        	   e.printStackTrace();
           }
           JWTVerifier jwtverifier=verification.build();
           //to decode token
           DecodedJWT decodedjwt=jwtverifier.verify(token);

           Claim claim=decodedjwt.
           getClaim("user_id");
           userId=claim.asInt();    
           return userId;
     
	}

	
}
