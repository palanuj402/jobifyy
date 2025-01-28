package com.pal.portal.Jobportal.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.pal.portal.Jobportal.entity.Users;
import com.pal.portal.Jobportal.repository.UserRepository;
import com.pal.portal.Jobportal.util.CustomUserDetails;

@Service
public class CustomUserDetailsService implements UserDetailsService{

	private final UserRepository usersRepository;

    @Autowired
    public CustomUserDetailsService(UserRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = usersRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("Could not found user"));
        return new CustomUserDetails(user);
    }
}