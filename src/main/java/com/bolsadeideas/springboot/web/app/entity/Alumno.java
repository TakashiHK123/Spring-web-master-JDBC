package com.bolsadeideas.springboot.web.app.entity;


import org.springframework.stereotype.Repository;


public class Alumno {

    private int idAlumno;
    private String nombre;
    private String apellido;

    public Alumno() {
    }

    public Alumno(int idAlumno, String nombre, String apellido) {
        this.idAlumno = idAlumno;
        this.nombre = nombre;
        this.apellido = apellido;
    }

    public int getIdAlumno() {
        return idAlumno;
    }

    public void setIdAlumno(int idAlumno) {
        this.idAlumno = idAlumno;
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
        return "Alumno{" +
                "idAlumno=" + idAlumno +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                '}';
    }
}
