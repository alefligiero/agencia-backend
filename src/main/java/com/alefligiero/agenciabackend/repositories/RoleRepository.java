package com.alefligiero.agenciabackend.repositories;

import com.alefligiero.agenciabackend.domain.role.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Repository
public interface RoleRepository extends JpaRepository<Role, UUID> {

    Optional<Role> findByName(String name);

    Set<Role> findRolesByNameIn(Set<String> names);

}
