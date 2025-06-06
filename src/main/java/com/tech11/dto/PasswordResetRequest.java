package com.tech11.dto;

public class PasswordResetRequest {
	private Long id;
	private String oldPassword;
	private String newPassword;
	
	public PasswordResetRequest() {}

	public PasswordResetRequest(Long id, String oldPassword, String newPassword) {
		this.id = id;
		this.oldPassword = oldPassword;
		this.newPassword = newPassword;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	
}
