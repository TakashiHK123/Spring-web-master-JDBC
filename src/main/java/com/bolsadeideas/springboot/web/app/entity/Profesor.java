package com.bolsadeideas.springboot.web.app.entity;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

public class Profesor implements Serializable {

    @NotNull
    @Min(1)
    @Max(99999999)
    private int idProfesor;
    @NotEmpty
    private String nombre;
    @NotEmpty
    private String apellido;

    public Profesor() {
    }

    public Profesor(@NotNull int idProfesor, String nombre, String apellido) {
        this.idProfesor = idProfesor;
        this.nombre = nombre;
        this.apellido = apellido;
    }

    public int getIdProfesor() {
        return idProfesor;
    }

    public void setIdProfesor(int idProfesor) {
        this.idProfesor = idProfesor;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    @Override
    public String toString() {
        return "Profesor{" +
                "idProfesor=" + idProfesor +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                '}';
    }
}
