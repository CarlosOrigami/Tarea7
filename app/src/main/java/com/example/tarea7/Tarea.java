package com.example.tarea7;

public class Tarea {
    private String asignatura;
    private String descripcion;
    private String fecha;
    private String estado;

    // Constructor con 4 parámetros
    public Tarea(String asignatura, String descripcion, String fecha, String estado) {
        this.asignatura = asignatura;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.estado = estado;
    }

    // Constructor con 3 parámetros (por defecto estado como "Pendiente")
    public Tarea(String asignatura, String descripcion, String fecha) {
        this(asignatura, descripcion, fecha, "Pendiente");
    }

    // Getters y Setters
    public String getAsignatura() {
        return asignatura;
    }

    public void setAsignatura(String asignatura) {
        this.asignatura = asignatura;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
