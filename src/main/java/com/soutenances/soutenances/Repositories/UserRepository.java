package com.soutenances.soutenances.Repositories;

import com.soutenances.soutenances.Models.Salle;
import com.soutenances.soutenances.Models.User;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Integer> {
	Optional<User> findByUsername(String username);


	List<User> findByuserIDNotIn(List<Integer> UserIds);


	Optional<User>findById(Integer id);

	List<User>findByUserIDNotIn(List<Integer>UserIDs);




}
