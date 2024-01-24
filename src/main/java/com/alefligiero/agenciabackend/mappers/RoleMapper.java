package com.alefligiero.agenciabackend.mappers;

import com.alefligiero.agenciabackend.domain.role.Role;
import com.alefligiero.agenciabackend.domain.role.RoleRequestDTO;
import com.alefligiero.agenciabackend.domain.role.RoleResponseDTO;


public class RoleMapper {

    public static RoleResponseDTO toResponseDTO(Role role) {
        return RoleResponseDTO.builder()
                .id(role.getId())
                .name(role.getName())
                .build();
    }

    public static Role toEntity(RoleRequestDTO dto) {
        return Role.builder()
                .name(dto.name())
                .build();
    }

    public static void copyDtoToEntity(RoleRequestDTO dto, Role role) {
        role.setName(dto.name());
    }

}
