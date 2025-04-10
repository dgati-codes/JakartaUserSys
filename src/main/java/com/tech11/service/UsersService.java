package com.tech11.service;


import com.tech11.constant.Role;
import com.tech11.dto.PasswordResetRequest;
import com.tech11.dto.UsersDTO;
import com.tech11.exception.UserException;
import com.tech11.model.Users;
import com.tech11.util.Paginate;


public interface UsersService {
	Paginate<UsersDTO> findAll(int page, int size);

    Users getUserById(Long id) throws UserException;

    Users createUser(Users user);

    Users updateUser(Users user) throws UserException;

    void deleteUser(Long id) throws UserException;
    
    // Addition essential service
    void resetPassword(PasswordResetRequest request) throws UserException;
    void updateUserRole(Long id, Role userRole) throws UserException;
    
 // Stubbed methods
    void deactivateUser(Long id); 
    Users findUserByEmail(String email);
}
