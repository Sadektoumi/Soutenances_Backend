package com.soutenances.soutenances.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.soutenances.soutenances.Models.ModelsEnum.UserRole;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="User")
public class User implements UserDetails{
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="UserID")
	private int userID;
	@Column(name="UserName")
	private String username;

	@Column(name="Lastname")
	private String lastname;
	@Column(name="Name")
	private String Name;
	@Column(name="Email")
	private String email;
	@Column(name="Password")
	private String password;
	
	@Column(name="CreatedAt",updatable = false)
	 @CreationTimestamp
	private LocalDateTime createdDate;
	@Column(name="Role")
	@Enumerated(EnumType.STRING)
	private UserRole  role;

	@ManyToOne
	@JoinColumn(name = "groupe_id",nullable = true)
	private Groupe groupe ;


	@OneToMany(mappedBy = "Etudiant_id")
	private Set<Projet> JoinEtudiantProjet;
	@OneToMany(mappedBy = "Encadrant_id")
	private Set<Projet> JoinEncadrantProjet;





	@OneToMany(mappedBy = "userTo")
	@JsonIgnore

    private List<Token> tokens;

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public String getUsername() {
		return username;
	}
	public void setName(String name) {
		this.Name = name;
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
	public LocalDateTime getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}
	public UserRole getRole() {
		return role;
	}
	public void setRole(UserRole role) {
		this.role = role;
	}
	  public List<Token> getTokens() {
	        return tokens;
	    }

	    public void setTokens(List<Token> tokens) {
	        this.tokens = tokens;
	    }

	public Groupe getGroupe() {
		return groupe;
	}

	public void setGroupe(Groupe groupe) {
		this.groupe = groupe;
	}

	@Override

	@JsonIgnore
	public Collection<? extends GrantedAuthority> getAuthorities() {
		 return List.of(new SimpleGrantedAuthority(role.name()));
	}

	
	@Override

	@JsonIgnore
	public boolean isAccountNonExpired() {
		
		return true;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getName() {
		return Name;
	}

	@Override

	@JsonIgnore
	public boolean isAccountNonLocked() {
		
		return true;
	}
	@Override
	@JsonIgnore
	public boolean isCredentialsNonExpired() {
		
		return true;
	}
	@Override

	@JsonIgnore
	public boolean isEnabled() {
		
		return true;
	}
	
	
}
