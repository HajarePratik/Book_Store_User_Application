package com.bridgelabz.userbookstore.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bridgelabz.userbookstore.model.UserRegistrationModel;

@Repository
public interface UserRegistrationRepository extends JpaRepository<UserRegistrationModel, Integer> {

	Optional<UserRegistrationModel> findByEmailId(String emailId);

}
