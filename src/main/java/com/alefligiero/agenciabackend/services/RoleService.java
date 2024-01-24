package com.alefligiero.agenciabackend.services;

import com.alefligiero.agenciabackend.domain.role.Role;
import com.alefligiero.agenciabackend.domain.role.RoleRequestDTO;
import com.alefligiero.agenciabackend.domain.role.RoleResponseDTO;
import com.alefligiero.agenciabackend.mappers.RoleMapper;
import com.alefligiero.agenciabackend.repositories.RoleRepository;
import com.alefligiero.agenciabackend.services.exceptions.DatabaseException;
import com.alefligiero.agenciabackend.services.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    @Transactional(readOnly = true)
    public List<RoleResponseDTO> getAllRoles() {
        return roleRepository
                .findAll()
                .stream()
                .map(RoleMapper::toResponseDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public RoleResponseDTO getRoleById(UUID id) {
        return roleRepository
                .findById(id)
                .map(RoleMapper::toResponseDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found id: " + id));
    }

    @Transactional(readOnly = true)
    public RoleResponseDTO getRoleByName(String name) {
        return roleRepository
                .findByName(name)
                .map(RoleMapper::toResponseDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found name: " + name));
    }

    @Transactional
    public RoleResponseDTO createRole(RoleRequestDTO dto) {
        return RoleMapper.toResponseDTO(roleRepository.save(RoleMapper.toEntity(dto)));
    }

    @SneakyThrows
    @Transactional
    public RoleResponseDTO updateRole(UUID id, RoleRequestDTO dto) {
        Role role = roleRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found id: " + id));
        RoleMapper.copyDtoToEntity(dto, role);
        return RoleMapper.toResponseDTO(roleRepository.save(role));

    }

    @SneakyThrows
    @Transactional
    public void deleteRole(UUID id) {
        if (!roleRepository.existsById(id)) {
            throw new ResourceNotFoundException("Role not found id: " + id);
        }
        try {
            roleRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Integrity violation");
        }
    }
}
