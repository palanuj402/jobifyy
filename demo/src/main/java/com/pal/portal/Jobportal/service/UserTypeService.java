package com.pal.portal.Jobportal.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pal.portal.Jobportal.entity.UsersType;
import com.pal.portal.Jobportal.repository.UserTypeRepository;

@Service
public class UserTypeService {

	private final UserTypeRepository usersTypeRepository;

    public UserTypeService(UserTypeRepository usersTypeRepository) {
        this.usersTypeRepository = usersTypeRepository;
    }

    public List<UsersType> getAll() {
        return usersTypeRepository.findAll();
    }
}