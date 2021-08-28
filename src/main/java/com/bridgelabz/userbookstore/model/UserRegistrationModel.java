package com.bridgelabz.userbookstore.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;

import com.bridgelabz.userbookstore.dto.UserRegistrationDTO;

import lombok.Data;
import javax.persistence.Id;

@Entity
@Table(name="UserRegistration")
@Data
public class UserRegistrationModel 
{
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int Id;
	private String firstName;
	private String lastName;
	private String kyc;
	private String dateOfBirth;
	private LocalDate registeredDate;
	private LocalDate updatedDate;
	private String emailId;
	private String password;
	private boolean verify;
	private int otp;
	private LocalDate purchaseDate;
	private LocalDate expiryDate;
	
	public UserRegistrationModel() {
		
	}
	
	public UserRegistrationModel(int Id,UserRegistrationDTO userRegistrationDTO) 
	{
		
		this.firstName = userRegistrationDTO.getFirstName();
		this.lastName = userRegistrationDTO.getLastName();
		this.kyc = userRegistrationDTO.getKyc();
		this.dateOfBirth = userRegistrationDTO.getDateOfBirth();
		this.emailId = userRegistrationDTO.getEmailId();
		this.password = userRegistrationDTO.getPassword();
	}

}
