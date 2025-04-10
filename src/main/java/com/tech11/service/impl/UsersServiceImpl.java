package com.tech11.service.impl;

import com.tech11.constant.Role;
import com.tech11.dto.PasswordResetRequest;
import com.tech11.dto.UsersDTO;
import com.tech11.exception.UserException;
import com.tech11.model.Users;
import com.tech11.repository.UsersRepository;
import com.tech11.service.UsersService;
import com.tech11.util.Paginate;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Transactional
public class UsersServiceImpl implements UsersService {

    private static final Logger LOGGER = Logger.getLogger(UsersServiceImpl.class.getName());

    @Inject
    private UsersRepository usersRepository;

    @Override
    public Paginate<UsersDTO> findAll(int page, int size) {
    	
        List<Users> usersList = usersRepository.findAll(page, size);
        long totalElements = usersRepository.countAll();

        List<UsersDTO> usersDTOList = usersList.stream()
                .map(UsersDTO::new)
                .collect(Collectors.toList());

        return new Paginate<>(usersDTOList, page, size, totalElements);
    }
    
    @Override
    public Users getUserById(Long id) throws UserException {
        LOGGER.log(Level.INFO, "Fetching user by ID: {0}", id);
       
        return usersRepository.findById(id)
                .orElseThrow(() -> new UserException("User not found with ID: " + id));
    }

    @Override
    public Users createUser(Users user) {
        LOGGER.log(Level.INFO, "Creating new user: {0}", user.getEmail());
        
        user.setCreatedOn(LocalDateTime.now());
        user.setUpdatedOn(LocalDateTime.now());
        Users createdUser = usersRepository.save(user);
        
        LOGGER.log(Level.INFO, "User created with ID: {0}", createdUser.getId());
        return createdUser;
    }

    @Override
    public Users updateUser(Users user) throws UserException {
        if (user.getId() == null) {
            LOGGER.log(Level.WARNING, "User ID is null for update");
            throw new UserException("User ID cannot be null for update.");
        }

        LOGGER.log(Level.INFO, "Updating user with ID: {0}", user.getId());
        return usersRepository.findById(user.getId())
                .map(existingUser -> {
                    user.setCreatedOn(existingUser.getCreatedOn());
                    user.setUpdatedOn(LocalDateTime.now());
                    Users updatedUser = usersRepository.save(user);
                
                    LOGGER.log(Level.INFO, "User updated with ID: {0}", updatedUser.getId());
                    return updatedUser;
                })
                .orElseThrow(() -> new UserException("User not found with ID: " + user.getId()));
    }

    @Override
    public void deleteUser(Long id) throws UserException {
        LOGGER.log(Level.INFO, "Deleting user with ID: {0}", id);
       
        if (!usersRepository.existsById(id)) {
            LOGGER.log(Level.WARNING, "User not found with ID: {0}", id);
            throw new UserException("User not found with ID: " + id);
        }
        usersRepository.deleteById(id);
        LOGGER.log(Level.INFO, "User deleted with ID: {0}", id);
    }
    
	@Override
	public void resetPassword(PasswordResetRequest request) throws UserException {
		  LOGGER.log(Level.INFO, "Resetting password for user ID: {0}", request.getId());
	        
	        Users user = usersRepository.findById(request.getId())
	                .orElseThrow(() -> new UserException("User not found with ID: " + request.getId()));

	        
	        if (!user.getPassword().equals(request.getOldPassword())) {
	            throw new UserException("Old password does not match.");
	        }
	              
	        user.setPassword(request.getNewPassword());
	        user.setUpdatedOn(LocalDateTime.now());
	        usersRepository.save(user);
	        	
	}
	@Override
	public void updateUserRole(Long id, Role userRole) throws UserException {
	    LOGGER.log(Level.INFO, "Updating role for user ID: {0} to role: {1}", new Object[]{id, userRole});
	    
	    // Ensure user is found, throw exception if not
	    Users user = usersRepository.findById(id)
	            .orElseThrow(() -> new UserException("User not found with ID: " + id));

	    // Update the role
	    user.setRole(userRole);
	    user.setUpdatedOn(LocalDateTime.now());
	    usersRepository.save(user);

	    LOGGER.log(Level.INFO, "Role successfully updated to {0} for user ID: {1}", new Object[]{userRole, id});
	}


    @Override
    public void deactivateUser(Long id) {
        LOGGER.log(Level.WARNING, "Deactivating user with ID: {0}. This feature is not implemented yet.", id);
        throw new UnsupportedOperationException("This feature is not implemented yet.");
    }

    @Override
    public Users findUserByEmail(String email) {
        LOGGER.log(Level.WARNING, "Finding user by email: {0}. This feature is not implemented yet.", email);
        throw new UnsupportedOperationException("This feature is not implemented yet.");
    }



}
