package com.alefligiero.agenciabackend.controllers;

import com.alefligiero.agenciabackend.domain.user.*;
import com.alefligiero.agenciabackend.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<Page<UserResponseDTO>> getAllUsers(
            @PageableDefault(size = 12, sort = "firstName", direction = Sort.Direction.ASC)
            Pageable pageable,
            @RequestParam(required = false, defaultValue = "")
            String search) {

        return ResponseEntity.ok(userService.getAllUsers(pageable, search));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable UUID id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody @Valid UserCreateDTO dto) {
        UserResponseDTO user = userService.createUser(dto);
        URI uri = URI.create("/api/v1/users/" + user.id());

        return ResponseEntity.created(uri).body(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> updateUser(@PathVariable UUID id, @RequestBody @Valid UserEditDTO dto) {
        return ResponseEntity.ok(userService.updateUser(id, dto));
    }

    @PutMapping("/{id}/update-email")
    public ResponseEntity<UserResponseDTO> updateUserEmail(@PathVariable UUID id, @RequestBody @Valid UserEditEmailDTO dto) {
        return ResponseEntity.ok(userService.updateUserEmail(id, dto));
    }

    @PutMapping("/{id}/update-password")
    public ResponseEntity<Void> updateUserPassword(@PathVariable UUID id, @RequestBody @Valid UserEditPasswordDTO dto) {
        boolean isUpdated = userService.updateUserPassword(id, dto);
        return isUpdated ? ResponseEntity.ok().build() : ResponseEntity.unprocessableEntity().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
