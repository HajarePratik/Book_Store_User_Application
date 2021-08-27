package com.bridgelabz.userbookstore.exception;

import org.springframework.http.HttpStatus;

public class UserRegistrationException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public UserRegistrationException(String message)
	{
		super(message);
	}
	public UserRegistrationException(int statusCode, String statusmessage)
	{
		super(statusmessage);
	}
	public UserRegistrationException(String string, HttpStatus ok, Object object, String string2) {
		// TODO Auto-generated constructor stub
	}
	

	

}
