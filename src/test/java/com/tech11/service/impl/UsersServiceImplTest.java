package com.tech11.service.impl;

import com.tech11.constant.Role;
import com.tech11.constant.Status;
import com.tech11.dto.PasswordResetRequest;
import com.tech11.dto.UsersDTO;
import com.tech11.exception.UserException;
import com.tech11.model.Users;
import com.tech11.repository.UsersRepository;
import com.tech11.util.Paginate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UsersServiceImplTest {

    @Mock
    private UsersRepository usersRepository;

    @InjectMocks
    private UsersServiceImpl usersService;

    private Users user;

    @BeforeEach
    public void setUp() {
        user = new Users();
        user.setId(1L);
        user.setFirstname("David");
        user.setLastname("Gati");
        user.setEmail("gatidavid2012@gmail.com");
        user.setBirthday(LocalDate.of(1990, 6, 15));
        user.setPassword("password");
        user.setPhoneNumber("+233-24-123-4567");
        user.setAddress("Spintex Road, Accra");
        user.setUsername("davidgati");
        user.setProfilePictureURL("http://example.com/david-profile.jpg");
        user.setRole(Role.USER);
        user.setStatus(Status.ACTIVE);
        user.setCreatedOn(LocalDateTime.now());
        user.setUpdatedOn(LocalDateTime.now());
        user.setLastLogin(LocalDateTime.now());
    }

    @Test
    public void testFindAll() {
        int page = 1;
        int size = 2;
        List<Users> mockUsersList = Arrays.asList(user, 
            new Users(2L, "Abena", "Asare", "abena.asare@example.com", null, null, null, null, null, null, null, null, null, null, null));

        when(usersRepository.findAll(page, size)).thenReturn(mockUsersList);
        when(usersRepository.countAll()).thenReturn(2L);

        Paginate<UsersDTO> result = usersService.findAll(page, size);

        assertEquals(2, result.getTotalElements());
        assertEquals(2, result.getContent().size());
        assertEquals("David", result.getContent().get(0).getFirstname());
        assertEquals("Abena", result.getContent().get(1).getFirstname());
    }

    @Test
    public void testGetUserById() throws UserException {
        when(usersRepository.findById(1L)).thenReturn(Optional.of(user));

        Users result = usersService.getUserById(1L);

        assertEquals(user, result);
    }

    @Test
    public void testGetUserByIdNotFound() {
        when(usersRepository.findById(1L)).thenReturn(Optional.empty());

        UserException exception = assertThrows(UserException.class, () -> {
            usersService.getUserById(1L);
        });

        assertEquals("User not found with ID: 1", exception.getMessage());
    }

    @Test
    public void testCreateUser() {
        when(usersRepository.save(user)).thenReturn(user);

        Users result = usersService.createUser(user);

        assertEquals(user, result);
    }

    @Test
    public void testUpdateUser() throws UserException {
        Users updatedUser = new Users();
        updatedUser.setId(1L);
        updatedUser.setFirstname("David");
        updatedUser.setLastname("Gati Updated");
        updatedUser.setEmail("gatidavid2012@gmail.com");

        when(usersRepository.findById(1L)).thenReturn(Optional.of(user));
        when(usersRepository.save(updatedUser)).thenReturn(updatedUser);

        Users result = usersService.updateUser(updatedUser);

        assertEquals("Gati Updated", result.getLastname());
    }

    @Test
    public void testUpdateUserNotFound() {
        when(usersRepository.findById(1L)).thenReturn(Optional.empty());

        UserException exception = assertThrows(UserException.class, () -> {
            usersService.updateUser(user);
        });

        assertEquals("User not found with ID: 1", exception.getMessage());
    }

    @Test
    public void testDeleteUser() throws UserException {
        when(usersRepository.existsById(1L)).thenReturn(true);
        doNothing().when(usersRepository).deleteById(1L);

        usersService.deleteUser(1L);

        verify(usersRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testDeleteUserNotFound() {
        when(usersRepository.existsById(1L)).thenReturn(false);

        UserException exception = assertThrows(UserException.class, () -> {
            usersService.deleteUser(1L);
        });

        assertEquals("User not found with ID: 1", exception.getMessage());
    }
    @Test
    public void testResetPassword() throws UserException {
       
        Users user = new Users();
        user.setId(1L);
        user.setPassword("password");

       
        when(usersRepository.findById(1L)).thenReturn(Optional.of(user));

       
        PasswordResetRequest resetRequest = new PasswordResetRequest(1L, "password", "newPassword");
        usersService.resetPassword(resetRequest);

        // Verify save and password update
        verify(usersRepository).save(user);
        assertEquals("newPassword", user.getPassword());

        // Case where old password does not match
        UserException exception = assertThrows(UserException.class, () -> {
            PasswordResetRequest wrongPasswordRequest = new PasswordResetRequest(1L, "wrongOldPassword", "newPassword");
            usersService.resetPassword(wrongPasswordRequest);
        });
        assertEquals("Old password does not match.", exception.getMessage());

        // Case where user is not found
        when(usersRepository.findById(1L)).thenReturn(Optional.empty());
        UserException notFoundException = assertThrows(UserException.class, () -> {
            PasswordResetRequest notFoundRequest = new PasswordResetRequest(1L, "password", "newPassword");
            usersService.resetPassword(notFoundRequest);
        });
        assertEquals("User not found with ID: 1", notFoundException.getMessage());
    }

    @Test
    public void testUpdateUserRole() throws UserException {
        when(usersRepository.findById(1L)).thenReturn(Optional.of(user));
        when(usersRepository.save(user)).thenReturn(user);

        usersService.updateUserRole(1L, Role.ADMIN);

        verify(usersRepository).save(user);
        assertEquals(Role.ADMIN, user.getRole());
    }
}
