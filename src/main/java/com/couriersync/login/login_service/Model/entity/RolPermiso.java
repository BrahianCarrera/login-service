package com.couriersync.login.login_service.Model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
@Table(name = "rol_permiso")
public class RolPermiso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_rol_permiso")
    private Long idRolPermiso;

    // Muchos a uno con roles
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_rol", nullable = false)
    @JsonBackReference("rol-rolpermisos")
    private Role rol;

    // Muchos a uno con permisos
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_permiso", nullable = false)
    @JsonBackReference("permiso-rolpermisos")
    private Permiso permiso;

    // Getters y Setters
    public Long getIdRolPermiso() {
        return idRolPermiso;
    }

    public void setIdRolPermiso(Long idRolPermiso) {
        this.idRolPermiso = idRolPermiso;
    }

    public Role getRol() {
        return rol;
    }

    public void setRol(Role rol) {
        this.rol = rol;
    }

    public Permiso getPermiso() {
        return permiso;
    }

    public void setPermiso(Permiso permiso) {
        this.permiso = permiso;
    }
}
