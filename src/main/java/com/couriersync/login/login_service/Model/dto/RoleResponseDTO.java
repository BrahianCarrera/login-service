package com.couriersync.login.login_service.Model.dto;

import java.util.List;

/**
 * DTO para mostrar información completa de roles con sus permisos
 */
public class RoleResponseDTO {
    private Long id;
    private String nombre;
    private String descripcion;
    private List<PermisoDTO> permisos;

    // Constructor
    public RoleResponseDTO(Long id, String nombre, String descripcion, List<PermisoDTO> permisos) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.permisos = permisos;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<PermisoDTO> getPermisos() {
        return permisos;
    }

    public void setPermisos(List<PermisoDTO> permisos) {
        this.permisos = permisos;
    }
}
