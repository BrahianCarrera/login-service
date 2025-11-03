package com.couriersync.login.login_service.Service;

import com.couriersync.login.login_service.Model.dto.PermisoDTO;
import com.couriersync.login.login_service.Model.dto.RoleRequestDTO;
import com.couriersync.login.login_service.Model.dto.RoleResponseDTO;
import com.couriersync.login.login_service.Model.entity.Role;
import com.couriersync.login.login_service.Model.entity.RolPermiso;
import com.couriersync.login.login_service.Repository.RoleRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    // Obtener todos los roles (Entidades - uso interno)
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    // Obtener todos los roles como DTOs (para API)
    public List<RoleResponseDTO> getAllRolesDTO() {
        return roleRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Obtener un rol específico por ID
    public Optional<Role> getRoleById(Long id) {
        return roleRepository.findById(id);
    }

    // Obtener un rol específico por ID como DTO
    public Optional<RoleResponseDTO> getRoleByIdDTO(Long id) {
        return roleRepository.findById(id)
                .map(this::convertToDTO);
    }

    // Crear un rol desde DTO
    public RoleResponseDTO createRole(RoleRequestDTO roleRequestDTO) {
        Role role = new Role();
        role.setNombre(roleRequestDTO.getNombre());
        role.setDescripcion(roleRequestDTO.getDescripcion());
        
        Role savedRole = roleRepository.save(role);
        return convertToDTO(savedRole);
    }

    // Actualizar un rol desde DTO
    public Optional<RoleResponseDTO> updateRole(Long id, RoleRequestDTO roleRequestDTO) {
        return roleRepository.findById(id)
                .map(role -> {
                    role.setNombre(roleRequestDTO.getNombre());
                    role.setDescripcion(roleRequestDTO.getDescripcion());
                    Role updatedRole = roleRepository.save(role);
                    return convertToDTO(updatedRole);
                });
    }

    // Crear o actualizar un rol (método legacy)
    public Role saveRole(Role role) {
        return roleRepository.save(role);
    }

    // Eliminar un rol
    public void deleteRole(Long id) {
        roleRepository.deleteById(id);
    }

    // Método helper: Convertir Role entity a RoleResponseDTO
    private RoleResponseDTO convertToDTO(Role role) {
        List<PermisoDTO> permisosDTO = role.getRolPermisos().stream()
                .map(RolPermiso::getPermiso)
                .map(permiso -> new PermisoDTO(
                        permiso.getIdPermiso(),
                        permiso.getNombre(),
                        permiso.getDescripcion()
                ))
                .collect(Collectors.toList());

        return new RoleResponseDTO(
                role.getIdRol(),
                role.getNombre(),
                role.getDescripcion(),
                permisosDTO
        );
    }
}
