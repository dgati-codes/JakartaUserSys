package com.tech11.dto;

import com.tech11.constant.Role;

public class RoleUpdateRequest {
	private Long id;
	private Role userRole;

	public RoleUpdateRequest() {
		super();
	}

	public RoleUpdateRequest(Long id, Role userRole) {
		super();
		this.id = id;
		this.userRole = userRole;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Role getUserRole() {
		return userRole;
	}

	public void setUserRole(Role userRole) {
		this.userRole = userRole;
	}

}
