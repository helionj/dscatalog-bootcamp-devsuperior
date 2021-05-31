package com.helion.dscatalog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.helion.dscatalog.entities.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

}
