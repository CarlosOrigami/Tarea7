package com.example.tarea7;

public class Tarea {

    private String asignatura;
    private String descripcion;
    private String fecha;

    // Constructor
    public Tarea(String asignatura, String descripcion, String fecha) {
        this.asignatura = asignatura;
        this.descripcion = descripcion;
        this.fecha = fecha;
    }

    // Getters
    public String getAsignatura() {
        return asignatura;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getFecha() {
        return fecha;
    }

    // Setters
    public void setAsignatura(String asignatura) {
        this.asignatura = asignatura;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
