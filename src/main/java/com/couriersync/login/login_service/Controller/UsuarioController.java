package com.couriersync.login.login_service.Controller;

import com.couriersync.login.login_service.Model.dto.UsuarioRequestDTO;
import com.couriersync.login.login_service.Model.entity.Usuario;
import com.couriersync.login.login_service.Service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/usuarios")
@Tag(name = "Usuarios", description = "API para gestión de usuarios del sistema")
@SecurityRequirement(name = "Bearer Authentication")
public class UsuarioController {

    private final UsuarioService usuarioService;

    // Inyección del servicio por constructor
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    // ✅ GET: listar todos los usuarios (requiere permiso USUARIO_MANAGE)
    @GetMapping
    @PreAuthorize("hasAuthority('USUARIO_MANAGE')")
    @Operation(summary = "Listar todos los usuarios", description = "Obtiene la lista completa de usuarios. Requiere permiso USUARIO_MANAGE (solo ADMIN).")
    @ApiResponse(responseCode = "200", description = "Lista de usuarios obtenida exitosamente")
    @ApiResponse(responseCode = "403", description = "No tienes permiso USUARIO_MANAGE para ver usuarios")
    public List<Usuario> getAllUsuarios() {
        return usuarioService.getAllUsuarios();
    }

    // ✅ GET: obtener un usuario por su ID (requiere permiso USUARIO_MANAGE)
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('USUARIO_MANAGE')")
    @Operation(summary = "Obtener usuario por ID", description = "Obtiene los detalles de un usuario específico. Requiere permiso USUARIO_MANAGE (solo ADMIN).")
    @ApiResponse(responseCode = "200", description = "Usuario encontrado")
    @ApiResponse(responseCode = "403", description = "No tienes permiso USUARIO_MANAGE para ver usuarios")
    @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    public ResponseEntity<Usuario> getUsuarioById(@PathVariable Long id) {
        Optional<Usuario> usuario = usuarioService.getUsuarioById(id);
        return usuario.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ✅ GET: obtener un usuario por username (requiere permiso USUARIO_MANAGE)
    @GetMapping("/username/{username}")
    @PreAuthorize("hasAuthority('USUARIO_MANAGE')")
    @Operation(summary = "Obtener usuario por username", description = "Obtiene los detalles de un usuario por su nombre de usuario. Requiere permiso USUARIO_MANAGE (solo ADMIN).")
    @ApiResponse(responseCode = "200", description = "Usuario encontrado")
    @ApiResponse(responseCode = "403", description = "No tienes permiso USUARIO_MANAGE para ver usuarios")
    @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    public ResponseEntity<Usuario> getUsuarioByUsername(@PathVariable String username) {
        Optional<Usuario> usuario = usuarioService.getUsuarioByUsername(username);
        return usuario.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ✅ POST: crear un nuevo usuario (requiere permiso USUARIO_MANAGE)
    @PostMapping
    @PreAuthorize("hasAuthority('USUARIO_MANAGE')")
    @Operation(summary = "Crear nuevo usuario", description = "Crea un nuevo usuario en el sistema. Requiere permiso USUARIO_MANAGE (solo ADMIN puede crear usuarios).")
    @ApiResponse(responseCode = "201", description = "Usuario creado exitosamente")
    @ApiResponse(responseCode = "400", description = "Datos inválidos o usuario ya existe")
    @ApiResponse(responseCode = "403", description = "No tienes permiso USUARIO_MANAGE para crear usuarios")
    public ResponseEntity<?> createUsuario(@RequestBody UsuarioRequestDTO usuarioRequest) {
        try {
            Usuario nuevoUsuario = usuarioService.createUsuario(usuarioRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoUsuario);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // ✅ PUT: actualizar un usuario existente (requiere permiso USUARIO_MANAGE)
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('USUARIO_MANAGE')")
    @Operation(summary = "Actualizar usuario", description = "Actualiza la información de un usuario existente. Requiere permiso USUARIO_MANAGE (solo ADMIN).")
    @ApiResponse(responseCode = "200", description = "Usuario actualizado exitosamente")
    @ApiResponse(responseCode = "400", description = "Datos inválidos")
    @ApiResponse(responseCode = "403", description = "No tienes permiso USUARIO_MANAGE para actualizar usuarios")
    @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    public ResponseEntity<?> updateUsuario(@PathVariable Long id, @RequestBody UsuarioRequestDTO usuarioRequest) {
        try {
            Usuario usuarioActualizado = usuarioService.updateUsuario(id, usuarioRequest);
            return ResponseEntity.ok(usuarioActualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // ✅ DELETE: eliminar un usuario por ID (requiere permiso USUARIO_MANAGE)
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('USUARIO_MANAGE')")
    @Operation(summary = "Eliminar usuario", description = "Elimina un usuario del sistema. Requiere permiso USUARIO_MANAGE (solo ADMIN).")
    @ApiResponse(responseCode = "204", description = "Usuario eliminado exitosamente")
    @ApiResponse(responseCode = "403", description = "No tienes permiso USUARIO_MANAGE para eliminar usuarios")
    @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    public ResponseEntity<Void> deleteUsuario(@PathVariable Long id) {
        if (!usuarioService.getUsuarioById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        usuarioService.deleteUsuario(id);
        return ResponseEntity.noContent().build();
    }
}
