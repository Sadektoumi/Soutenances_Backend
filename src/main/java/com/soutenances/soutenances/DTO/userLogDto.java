package com.soutenances.soutenances.DTO;

import lombok.Data;

@Data
public class userLogDto {
private String username;



private String password;

public String getUsername() {
	return username;
}

public String getPassword() {
	return password;
}

}
