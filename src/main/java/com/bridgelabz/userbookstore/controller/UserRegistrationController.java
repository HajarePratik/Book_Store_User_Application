package com.bridgelabz.userbookstore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bridgelabz.userbookstore.dto.ResponseDTO;
import com.bridgelabz.userbookstore.dto.UserRegistrationDTO;
import com.bridgelabz.userbookstore.service.IUserRegistrationService;

import io.swagger.annotations.ApiOperation;

@RestController
public class UserRegistrationController 
{
	
	@Autowired(required = true)
	private IUserRegistrationService registrationService;
	
	@GetMapping("/getalluser")
	public ResponseEntity<ResponseDTO> getAllUsers() {
		ResponseDTO respDTO = registrationService.getAllUser();
		return new ResponseEntity<ResponseDTO>(respDTO, HttpStatus.OK);
	}

	@GetMapping("/getuser/{token}/{userid}")
	public ResponseEntity<ResponseDTO> getUser(@PathVariable String token,@PathVariable int userid)
	{
		ResponseDTO respDTO = registrationService.getUserById(userid);
		return new ResponseEntity<ResponseDTO>(respDTO, HttpStatus.OK);
	}
	
	@PostMapping("/createuser")
	public ResponseEntity<ResponseDTO> createUser(@RequestBody UserRegistrationDTO userDTO)
	{
		ResponseDTO userData = registrationService.createUser(userDTO);
		ResponseDTO resDTO = new ResponseDTO("Create User Details Sucessfully :"+userData, userDTO);
		return new ResponseEntity<ResponseDTO>(resDTO,HttpStatus.OK);
	}
	
	@PutMapping("/updateuser/{token}/{userid}")
	public ResponseEntity<ResponseDTO> updateUser(@PathVariable String token,@PathVariable int userid, @RequestBody UserRegistrationDTO userDTO){
		ResponseDTO respDTO = registrationService.updateUserById(token,userid, userDTO);
		return new ResponseEntity<ResponseDTO>(respDTO, HttpStatus.OK);
	}

	@DeleteMapping("/deleteuser/{token}/{userid}")
	public ResponseEntity<ResponseDTO> deleteUser(@PathVariable String token, @PathVariable int userid) {
		ResponseDTO respDTO = registrationService.deleteUserById(token, userid);
		return new ResponseEntity<ResponseDTO>(respDTO, HttpStatus.OK);
	}
	
	@PostMapping("/loginuser")
	public ResponseEntity<ResponseDTO> loginUser(@RequestParam String email,@RequestParam String password)
	{
		ResponseDTO respDTO = registrationService.loginUser(email, password);
		return new ResponseEntity<ResponseDTO>(respDTO, HttpStatus.OK);
	}

	@PostMapping("/forgotpassword")
	public ResponseEntity<ResponseDTO> forgotPassword(@RequestParam String email)
	{
		ResponseDTO respDTO = registrationService.forgotPassword(email);
		return new ResponseEntity<ResponseDTO>(respDTO, HttpStatus.OK);
	}
	
	@GetMapping("/verifyemail/{token}")
	public Boolean verifyEmail(@PathVariable String token) 
	{
		return registrationService.verify(token);
	}

	@GetMapping("/verifyotp/{token}")
	public ResponseDTO verifyOtp(@PathVariable String token, @RequestParam int otp) 
	{
		return registrationService.verifyOtp(token, otp);
	}
	
	@PutMapping("/purchasesubscription/{token}")
	public ResponseEntity<ResponseDTO> purchaseDate(@PathVariable String token)
	{
		ResponseDTO respDTO = registrationService.purchaseDate(token);
		return new ResponseEntity<ResponseDTO>(respDTO, HttpStatus.OK);
	}
	@PostMapping(value = "/uploadkyc/{token}", consumes = { "multipart/form-data" })
	@ApiOperation(value = "Upload Documents", response = ResponseDTO.class)
	public ResponseEntity<ResponseDTO> kycFile(@PathVariable String token,@RequestParam("kycFile") MultipartFile kycFile)
	{
		ResponseDTO respDTO = registrationService.documents(token, kycFile);
		return new ResponseEntity<ResponseDTO>(respDTO, HttpStatus.OK);
	}
	@GetMapping("/getuserid/{token}")
	public int getUserId(@PathVariable String token)
	{
		return registrationService.getUserId(token);
	}

}
