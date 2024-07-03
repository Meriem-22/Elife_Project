package com.elife.MiniProject.businiss.services;


import org.springframework.security.core.Authentication;

import com.elife.MiniProject.dao.entities.User;
import com.elife.MiniProject.exceptions.DuplicateUserException;
import com.elife.MiniProject.web.dto.AuthenticationUserDTO;

public interface AuthenticationService {
   
   // User register(User user) throws DuplicateUserException;
   AuthenticationUserDTO login(Authentication authentication);
}
