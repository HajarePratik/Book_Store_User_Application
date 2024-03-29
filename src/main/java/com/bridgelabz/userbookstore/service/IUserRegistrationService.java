package com.bridgelabz.userbookstore.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bridgelabz.userbookstore.dto.ResponseDTO;
import com.bridgelabz.userbookstore.dto.UserRegistrationDTO;

@Service
public interface IUserRegistrationService 
{
	
	ResponseDTO getUserById(int userid);

	ResponseDTO getAllUser();

	ResponseDTO createUser(UserRegistrationDTO userDTO);

	ResponseDTO updateUserById(String token,int userid, UserRegistrationDTO userDTO);
	
	ResponseDTO deleteUserById(String token, int userid);

	ResponseDTO loginUser(String email, String password);

	ResponseDTO forgotPassword(String email);

	Boolean verify(String token);

	ResponseDTO verifyOtp(String token, int otp);

	ResponseDTO purchaseDate(String token);
	
	ResponseDTO documents(String token, MultipartFile kycFile);

	int getUserId(String token);
}
