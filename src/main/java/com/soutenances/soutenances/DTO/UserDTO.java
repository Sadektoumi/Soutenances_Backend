package com.soutenances.soutenances.DTO;

import com.soutenances.soutenances.Models.Groupe;
import com.soutenances.soutenances.Models.ModelsEnum.UserRole;


import lombok.Data;

@Data
public class UserDTO {

	private String username;
	private String Name;

	private String lastname;
	private String email;

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	private String password;
	private UserRole role;

	private int groupe_id ;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public UserRole getRole() {
		return role;
	}
	public void setRole(UserRole role) {
		this.role = role;
	}

}
