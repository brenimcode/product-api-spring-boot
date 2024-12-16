package com.breno.springboot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import com.breno.springboot.models.UserModel;


public interface UserRepository extends JpaRepository<UserModel, String>{
    
    UserDetails findByLogin(String login);
}
