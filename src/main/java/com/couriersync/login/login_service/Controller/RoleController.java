package com.couriersync.login.login_service.Controller;

import com.couriersync.login.login_service.Model.dto.RoleRequestDTO;
import com.couriersync.login.login_service.Model.dto.RoleResponseDTO;
import com.couriersync.login.login_service.Service.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
@Tag(name = "Roles", description = "API para gestión de roles y permisos")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    /**
     * Obtener todos los roles con sus permisos
     */
    @GetMapping
    @Operation(summary = "Listar todos los roles", description = "Obtiene la lista completa de roles con sus permisos asociados")
    @ApiResponse(responseCode = "200", description = "Lista de roles obtenida exitosamente")
    public ResponseEntity<List<RoleResponseDTO>> getAllRoles() {
        List<RoleResponseDTO> roles = roleService.getAllRolesDTO();
        return ResponseEntity.ok(roles);
    }

    /**
     * Obtener un rol específico por ID
     */
    @GetMapping("/{id}")
    @Operation(summary = "Obtener rol por ID", description = "Obtiene los detalles de un rol específico incluyendo sus permisos")
    @ApiResponse(responseCode = "200", description = "Rol encontrado")
    @ApiResponse(responseCode = "404", description = "Rol no encontrado")
    public ResponseEntity<RoleResponseDTO> getRoleById(@PathVariable Long id) {
        return roleService.getRoleByIdDTO(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Crear un nuevo rol
     */
    @PostMapping
    @Operation(summary = "Crear nuevo rol", description = "Crea un nuevo rol en el sistema")
    @ApiResponse(responseCode = "201", description = "Rol creado exitosamente")
    @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
    public ResponseEntity<RoleResponseDTO> createRole(@Valid @RequestBody RoleRequestDTO roleRequestDTO) {
        RoleResponseDTO createdRole = roleService.createRole(roleRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdRole);
    }

    /**
     * Actualizar un rol existente
     */
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar rol", description = "Actualiza la información de un rol existente")
    @ApiResponse(responseCode = "200", description = "Rol actualizado exitosamente")
    @ApiResponse(responseCode = "404", description = "Rol no encontrado")
    @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
    public ResponseEntity<RoleResponseDTO> updateRole(
            @PathVariable Long id,
            @Valid @RequestBody RoleRequestDTO roleRequestDTO) {
        return roleService.updateRole(id, roleRequestDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Eliminar un rol
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar rol", description = "Elimina un rol del sistema")
    @ApiResponse(responseCode = "204", description = "Rol eliminado exitosamente")
    @ApiResponse(responseCode = "404", description = "Rol no encontrado")
    public ResponseEntity<Void> deleteRole(@PathVariable Long id) {
        if (roleService.getRoleById(id).isPresent()) {
            roleService.deleteRole(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}

