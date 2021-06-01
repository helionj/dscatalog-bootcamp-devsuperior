package com.helion.dscatalog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.helion.dscatalog.entities.User;

public interface UserRepository extends JpaRepository<User, Long>{

	User findByEmail(String email);
}
