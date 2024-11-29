package com.soutenances.soutenances.Service;

import com.soutenances.soutenances.DTO.AuthenticationResponse;
import com.soutenances.soutenances.DTO.UserDTO;
import com.soutenances.soutenances.DTO.userLogDto;
import com.soutenances.soutenances.Models.Token;
import com.soutenances.soutenances.Models.User;
import com.soutenances.soutenances.Repositories.TokenRepository;
import com.soutenances.soutenances.Repositories.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthenticationService {

	private final AuthenticationManager authenticationManager;
	private final UserRepository userrepos;
    private final PasswordEncoder passwordEncoder;
    private final JWTService jwtService;
    private final TokenRepository TokenRepo;
	public AuthenticationService(AuthenticationManager authenticationManager, UserRepository user,
			PasswordEncoder passwordEncoder,JWTService jwts,TokenRepository tr) {
		this.authenticationManager = authenticationManager;
		this.userrepos = user;
		this.passwordEncoder = passwordEncoder;
		this.jwtService=jwts;
		this.TokenRepo=tr;
	} 
public AuthenticationResponse Resgister(UserDTO userDTO)
{
	 if (userrepos.findByUsername(userDTO.getUsername()).isPresent()) {
         return new AuthenticationResponse( null,"User already exists");
     }
	 User user= new User();

	 user.setUsername(userDTO.getUsername());
	 user.setName(userDTO.getName());
	 user.setLastname(userDTO.getLastname());
	 user.setEmail(userDTO.getEmail());
	 user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
	 user.setRole(userDTO.getRole());

     String jwt = jwtService.generateToken(user);

	 user=userrepos.save(user);
	  saveUserToken(jwt, user);
	 return new AuthenticationResponse(jwt,"User saved Successfully");

}
	public AuthenticationResponse Login(userLogDto logDto)
	{


		try
		{
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(logDto.getUsername(),
	                logDto.getPassword()
	                )
				);
			 User user = userrepos.findByUsername(logDto.getUsername()).orElseThrow();
		     String jwt = jwtService.generateToken(user);

		        revokeAllTokenByUser(user);
		        saveUserToken(jwt, user);

		//	SecurityContextHolder.getContext().setAuthentication(auth);
			return new AuthenticationResponse(jwt,"Login successfully");
		}catch (BadCredentialsException e) {
	        return new AuthenticationResponse(null,"Invalid username or password");
	    } catch (Exception e) {
	        return new AuthenticationResponse(null,"Login error: " + e.getMessage());
	    }

	        
	}
	 private void revokeAllTokenByUser(User user) {
	        List<Token> validTokens = TokenRepo.findAllTokensByUser(user.getUserID());
	        if(validTokens.isEmpty()) {
	            return;
	        }

	        validTokens.forEach(t-> {
	            t.setLoggedOut(true);
	        });

	        TokenRepo.saveAll(validTokens);
	    }
	   private void saveUserToken(String jwt, User user) {
	        Token token = new Token();
	        token.setToken(jwt);
	        token.setLoggedOut(false);
	        token.setUser(user);
	        TokenRepo.save(token);
	    }
}

