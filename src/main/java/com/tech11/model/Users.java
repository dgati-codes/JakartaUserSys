package com.tech11.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import com.tech11.constant.Role;
import com.tech11.constant.Status;
import com.tech11.dto.UsersDTO;

@Entity
@Table(name = "users")
public class Users {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "first_name")
	private String firstname;

	@Column(name = "last_name")
	private String lastname;

	@Column(name = "email")
	private String email;

	@Column(name = "birthday")
	private LocalDate birthday;

	@Column(name = "password")
	private String password;

	@Column(name = "phone_number")
	private String phoneNumber;

	@Column(name = "address")
	private String address;

	@Column(name = "username")
	private String username;

	@Column(name = "profile_picture_url")
	private String profilePictureURL;

	@Enumerated(EnumType.STRING)
	@Column(name = "role")
	private Role role;

	@Enumerated(EnumType.STRING)
	@Column(name = "status")
	private Status status;

	@Column(name = "created_on",updatable = false)
	private LocalDateTime createdOn;

	@Column(name = "updated_on")
	private LocalDateTime updatedOn;

	@Column(name = "last_login")
	private LocalDateTime lastLogin;
	
	public Users() {
    }
	public Users(Long id, String firstname, String lastname, String email, LocalDate birthday, String password,
			String phoneNumber, String address, String username, String profilePictureURL, Role role, Status status,
			LocalDateTime createdOn, LocalDateTime updatedOn, LocalDateTime lastLogin) {
		super();
		this.id = id;
		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
		this.birthday = birthday;
		this.password = password;
		this.phoneNumber = phoneNumber;
		this.address = address;
		this.username = username;
		this.profilePictureURL = profilePictureURL;
		this.role = role;
		this.status = status;
		this.createdOn = createdOn;
		this.updatedOn = updatedOn;
		this.lastLogin = lastLogin;
	}
	public Users(UsersDTO userDto) {	
		this.id = userDto.getId();
		this.firstname = userDto.getFirstname();
		this.lastname = userDto.getLastname();
		this.email = userDto.getEmail();
		this.birthday = userDto.getBirthday();
		this.phoneNumber = userDto.getPhoneNumber();
		this.address = userDto.getAddress();
		this.username = userDto.getUsername();
		this.profilePictureURL = userDto.getProfilePictureURL();
		this.role = userDto.getRole();
		this.status = userDto.getStatus();
		this.createdOn = userDto.getCreatedOn();
		this.updatedOn = userDto.getUpdatedOn();
		this.lastLogin = userDto.getLastLogin();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public LocalDate getBirthday() {
		return birthday;
	}

	public void setBirthday(LocalDate birthday) {
		this.birthday = birthday;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getProfilePictureURL() {
		return profilePictureURL;
	}

	public void setProfilePictureURL(String profilePictureURL) {
		this.profilePictureURL = profilePictureURL;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public LocalDateTime getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(LocalDateTime createdOn) {
		this.createdOn = createdOn;
	}

	public LocalDateTime getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(LocalDateTime updatedOn) {
		this.updatedOn = updatedOn;
	}

	public LocalDateTime getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(LocalDateTime lastLogin) {
		this.lastLogin = lastLogin;
	}
	

}
