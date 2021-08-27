package com.bridgelabz.userbookstore.respository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bridgelabz.userbookstore.model.UserRegistrationModel;

public interface UserRegistrationRespository extends JpaRepository<UserRegistrationModel, Integer> {

	Optional<UserRegistrationModel> findByEmailId(String emailId);

}
