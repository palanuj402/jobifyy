package com.pal.portal.Jobportal.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pal.portal.Jobportal.entity.Users;

public interface UserRepository extends JpaRepository<Users, Integer>{
	
	Optional<Users> findByEmail(String email);

}