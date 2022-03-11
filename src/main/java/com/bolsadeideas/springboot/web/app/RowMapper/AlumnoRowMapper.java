package com.bolsadeideas.springboot.web.app.RowMapper;

import com.bolsadeideas.springboot.web.app.entity.Alumno;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class AlumnoRowMapper implements RowMapper<Alumno> {

    @Override
    public Alumno mapRow(final ResultSet resultSet, final int rowNum) throws SQLException {

        final Alumno alumno = new Alumno();
        alumno.setIdAlumno(resultSet.getInt("idalumno"));
        alumno.setNombre(resultSet.getString("nombre"));
        alumno.setApellido(resultSet.getString("apellido"));

        return alumno;
    }

}
