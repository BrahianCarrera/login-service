package com.couriersync.login.login_service.Model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * DTO para crear o actualizar roles
 */
public class RoleRequestDTO {
    
    @NotBlank(message = "El nombre del rol es obligatorio")
    @Size(max = 50, message = "El nombre no puede exceder 50 caracteres")
    private String nombre;
    
    @Size(max = 255, message = "La descripción no puede exceder 255 caracteres")
    private String descripcion;

    // Constructor vacío
    public RoleRequestDTO() {
    }

    // Constructor con parámetros
    public RoleRequestDTO(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    // Getters y Setters
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
}
