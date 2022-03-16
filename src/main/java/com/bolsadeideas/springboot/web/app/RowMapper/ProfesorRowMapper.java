package com.bolsadeideas.springboot.web.app.RowMapper;

import com.bolsadeideas.springboot.web.app.entity.Profesor;
import org.springframework.jdbc.core.RowMapper;


import java.sql.ResultSet;
import java.sql.SQLException;

public class ProfesorRowMapper implements RowMapper<Profesor> {
    @Override
    public Profesor mapRow(final ResultSet resultSet, final int rowNum) throws SQLException {
        final Profesor profesor = new Profesor();
        profesor.setIdProfesor(resultSet.getInt("idprofesor"));
        profesor.setNombre(resultSet.getString("nombre"));
        profesor.setApellido(resultSet.getString("apellido"));
        return profesor;
    }
}
