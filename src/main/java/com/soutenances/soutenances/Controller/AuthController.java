package com.soutenances.soutenances.Controller;

import com.soutenances.soutenances.DTO.AuthenticationResponse;
import com.soutenances.soutenances.DTO.UserDTO;
import com.soutenances.soutenances.DTO.userLogDto;
import com.soutenances.soutenances.Service.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/api/auth")
public class AuthController {
	private final AuthenticationService AuthService;
	public AuthController(AuthenticationService authser)
	{
		this.AuthService=authser;
	}
	@PostMapping("/register")
	public ResponseEntity<AuthenticationResponse> regitser(@RequestBody UserDTO req)
	{
		return ResponseEntity.ok(AuthService.Resgister(req));
	}
	 @PostMapping("/login")
	    public ResponseEntity<AuthenticationResponse> login(
	            @RequestBody userLogDto request
	    ) {
	        return ResponseEntity.ok(AuthService.Login(request));
	    }
}
