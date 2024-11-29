package com.soutenances.soutenances.Repositories;

import com.soutenances.soutenances.Models.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Integer> {
	 @Query("""
			 select t from Token t inner join User u on t.userTo.id = u.id
			 where t.userTo.id = :userId and t.loggedOut = false
			 """)
			     List<Token> findAllTokensByUser(Integer userId);

			     Optional<Token> findByToken(String token);

			     
}
