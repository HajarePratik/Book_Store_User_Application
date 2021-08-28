package com.bridgelabz.userbookstore.service;

import java.io.File;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bridgelabz.userbookstore.dto.ResponseDTO;
import com.bridgelabz.userbookstore.dto.UserRegistrationDTO;
import com.bridgelabz.userbookstore.exception.UserRegistrationException;
import com.bridgelabz.userbookstore.model.UserRegistrationModel;
import com.bridgelabz.userbookstore.repository.UserRegistrationRepository;
import com.bridgelabz.userbookstore.util.JMSUtil;
import com.bridgelabz.userbookstore.util.TokenUtil;

@Service
public class UserRegistrationService implements IUserRegistrationService {
	
	@Autowired
	private UserRegistrationRepository userRepository;
	
	@Autowired
	ModelMapper modelmapper;
	
	@Override
	public ResponseDTO createUser(UserRegistrationDTO userDTO) 
	{
		Optional<UserRegistrationModel> isUserPresent = userRepository.findByEmailId(userDTO.getEmailId());
		if(!isUserPresent.isPresent())
		{
			UserRegistrationModel createUser = modelmapper.map(userDTO, UserRegistrationModel.class);
			createUser.setRegisteredDate(LocalDate.now());
			Random random = new Random();
			int otp = random.nextInt(999999);
			createUser.setOtp(otp);
			String body = "Hey,Please Verify the OTP for Book Store Registration = " + createUser.getOtp();
			JMSUtil.sendEmail(createUser.getEmailId(), "Please Verify OTP", body);
			System.out.println(body);
			userRepository.save(createUser);
			return new ResponseDTO("User Register Sucessfully", userDTO);
		}
		else
		{
			throw new UserRegistrationException(400,"User is already Register, Please Try with another Email Id");
		}
	}

	@Override
	public ResponseDTO updateUserById(String token,int id, UserRegistrationDTO userDTO) 
	{
		int userId = TokenUtil.decodeToken(token);
		Optional<UserRegistrationModel> isUserPresent = userRepository.findById(userId);
		if (!isUserPresent.isPresent()) 
		{
			isUserPresent.get().setFirstName(userDTO.getFirstName());
			isUserPresent.get().setLastName(userDTO.getLastName());
			isUserPresent.get().setKyc(userDTO.getKyc());
			isUserPresent.get().setDateOfBirth(userDTO.getDateOfBirth());
			isUserPresent.get().setEmailId(userDTO.getEmailId());
			isUserPresent.get().setPassword(userDTO.getPassword());
			isUserPresent.get().setUpdatedDate(LocalDate.now());
			
			userRepository.save(isUserPresent.get());
			return new ResponseDTO("User Updated Successfully", isUserPresent);
		}
		else 
		{
			throw new UserRegistrationException(400,"User is already Register, Please Try with another Email Id");
		}
	}

	@Override
	public ResponseDTO deleteUserById(String token, int id) {
		
		int userId = TokenUtil.decodeToken(token);
		Optional<UserRegistrationModel> isUserPresent = userRepository.findById(userId);
		if (isUserPresent.isPresent()) 
		{
			userRepository.delete(isUserPresent.get());
			return new ResponseDTO("User Deleted Successfully", isUserPresent.get().getId());
		} 
		else 
		{
			throw new UserRegistrationException(400,"User is already Register, Please Try with another Email Id");
		}
	}

	@Override
	public ResponseDTO getUserById(int id) 
	{
		
		Optional<UserRegistrationModel> isUserPresent = userRepository.findById(id);
		return new ResponseDTO("User of this Particular ID :" + id, isUserPresent);
	}

	@Override
	public ResponseDTO getAllUser() 
	{
		
		List<UserRegistrationModel> isUserPresent = userRepository.findAll();
		return new ResponseDTO("List of All Users :", isUserPresent);
	}

	@Override
	public ResponseDTO loginUser(String email, String password) 
	{
		Optional<UserRegistrationModel> isUserPresent = userRepository.findByEmailId(email);
		if (isUserPresent.isPresent()) 
		{
			if (isUserPresent.get().getEmailId().equals(email) && isUserPresent.get().getPassword().equals(password)) 
			{
				String token = TokenUtil.createToken(isUserPresent.get().getId());
				return new ResponseDTO("Login is Sucessfully", "Token :" + token);
			}
			else 
			{
				throw new UserRegistrationException(400,"Please check Email Id or Password, Retry");
			}
		} 
		else 
		{
			throw new UserRegistrationException(400,"User is already Register, Please Try with another Email Id");
		}
	}

	@Override
	public ResponseDTO forgotPassword(String email) 
	{
		
		Optional<UserRegistrationModel> isUserPresent = userRepository.findByEmailId(email);
		if (isUserPresent.isPresent()) 
		{
			if (isUserPresent.get().getEmailId().equals(email)) 
			{
				String body = "http://localhost:9090/resetpassword/"+ TokenUtil.createToken(isUserPresent.get().getId());
				JMSUtil.sendEmail(isUserPresent.get().getEmailId(), "Reset Password", body);
				return new ResponseDTO("Password is reset", body);
			} 
			else 
			{
				throw new UserRegistrationException(400,"Email id is incorrect");
			}
		}
		else 
		{
			throw new UserRegistrationException(400,"User is already Register, Please Try with another Email Id");
		}
	}

	@Override
	public Boolean verify(String token) 
	{
		int userId = TokenUtil.decodeToken(token);
		Optional<UserRegistrationModel> isUserPresent = userRepository.findById(userId);
		if (isUserPresent != null)
		{
			return true;
			
		} 
		else 
		{
			return false;
		}
	}

	@Override
	public ResponseDTO verifyOtp(String token, int otp) {
		
		int userId = TokenUtil.decodeToken(token);
		Optional<UserRegistrationModel> isUserPresent = userRepository.findById(userId);
		if (isUserPresent.isPresent()) 
		{
			if (isUserPresent.get().getOtp() == otp) 
			{
				isUserPresent.get().setVerify(true);
				userRepository.save(isUserPresent.get());
				return new ResponseDTO("OTP is Verifed", isUserPresent);
			} 
			else 
			{
				throw new UserRegistrationException(400,"OTP is incorrect");
			}
		} 
		else
		{
			throw new UserRegistrationException(400,"User is already Register, Please Try with another Email Id");
		}
	}

	@Override
	public ResponseDTO purchaseDate(String token) 
	{
	
		int userId = TokenUtil.decodeToken(token);
		Optional<UserRegistrationModel> isUserPresent = userRepository.findById(userId);
		if (isUserPresent.isPresent()) 
		{
			LocalDate today = LocalDate.now();
			isUserPresent.get().setPurchaseDate(LocalDate.now());
			isUserPresent.get().setExpiryDate(today.plusYears(1));
			String body = "Dear,"+isUserPresent.get().getFirstName()+"You have Purchase the Book for 1 Year Subscription";
			JMSUtil.sendEmail(isUserPresent.get().getEmailId(), "Get a 1 Year Subscription for Book", body);
			userRepository.save(isUserPresent.get());
			return new ResponseDTO("User Purchased Book is Successfully","ExpiryDate : " +isUserPresent.get().getExpiryDate());
		}
		else 
		{
			throw new UserRegistrationException(400,"User is already Register, Please Try with another Email Id");
		}
	}

	@Override
	public ResponseDTO documents(String token, MultipartFile kycFile)
	{
		
		int userId = TokenUtil.decodeToken(token);
		Optional<UserRegistrationModel> isUserPresent = userRepository.findById(userId);
		if (isUserPresent.isPresent()) 
		{
			File kyc = new File(kycFile.getOriginalFilename());
			String panpath = kyc.getAbsolutePath();
			isUserPresent.get().setKyc(panpath);
			userRepository.save(isUserPresent.get());
			return new ResponseDTO("File is Successfully uploaded", isUserPresent.get());
		} 
		else 
		{
			throw new UserRegistrationException(400,"User is already Register, Please Try with another Email Id");
		}
	}
}
