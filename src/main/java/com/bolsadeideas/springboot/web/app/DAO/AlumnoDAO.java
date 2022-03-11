package com.bolsadeideas.springboot.web.app.DAO;

import com.bolsadeideas.springboot.web.app.RowMapper.AlumnoRowMapper;
import com.bolsadeideas.springboot.web.app.SQLErrorCode.CustomSQLErrorCodeTranslator;
import com.bolsadeideas.springboot.web.app.entity.Alumno;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Repository
public class AlumnoDAO {

    private static final String SQL="SELECT * FROM alumnos";
    private static final String SQL_INSERT = "INSERT INTO alumnos (nombre, apellido) VALUES (?, ?)";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private SimpleJdbcInsert simpleJdbcInsert;

    private SimpleJdbcCall simpleJdbcCall;


    //@Autowired
    public void setDataSource(final DataSource dataSource){
        jdbcTemplate = new JdbcTemplate(dataSource);
        final CustomSQLErrorCodeTranslator customSQLErrorCodeTranslator = new CustomSQLErrorCodeTranslator();
        jdbcTemplate.setExceptionTranslator(customSQLErrorCodeTranslator);

        namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        simpleJdbcInsert = new SimpleJdbcInsert(dataSource).withTableName("alumnos");

    }


    public int getCountOfAlumnos() {
        return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM alumnos", Integer.class);
    }


    public List<Alumno> getAll() {
        return jdbcTemplate.query(SQL, new AlumnoRowMapper());
    }

    public int addAlumno(String nombre, String apellido) {
        return jdbcTemplate.update(SQL_INSERT, nombre, apellido);
    }

    public Alumno addAlumnoReturnAlu(String nombre, String apellido){

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, nombre);
            preparedStatement.setString(2, apellido);
            return preparedStatement;
        },keyHolder);
        Integer id = (Integer) keyHolder.getKeys()
                .entrySet().stream()
                .filter(m  -> m.getKey().equalsIgnoreCase("idalumno"))
                .map(Map.Entry::getValue)
                .findFirst().orElse(null);
        Alumno alumno = new Alumno();
        alumno.setIdAlumno(id);
        alumno.setNombre(nombre);
        alumno.setApellido(apellido);
        return alumno;
    }


    public int addAlumnoUsingSimpelJdbcInsert(final Alumno alumno) {
        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("idalumno", alumno.getIdAlumno());
        parameters.put("nombre", alumno.getNombre());
        parameters.put("apellido", alumno.getApellido());
        return simpleJdbcInsert.execute(parameters);
    }

    public Alumno getAlumno(int idalumno) {
        final String sql = "SELECT * FROM alumnos WHERE idalumno = ?";
        return jdbcTemplate.queryForObject(sql, new Object[] { idalumno }, new AlumnoRowMapper());
    }

    public void addEmplyeeUsingExecuteMethod() {
        jdbcTemplate.execute("INSERT INTO alumnos VALUES (6, 'Bill', 'Gates', 'USA')");
    }

    public String getAlumnoUsingMapSqlParameterSource() {
        final SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("idalumno", 1);

        return namedParameterJdbcTemplate.queryForObject("SELECT FIRST_NAME FROM alumnos WHERE ID = :idalumno", namedParameters, String.class);
    }


    public int[] batchUpdateUsingJDBCTemplate(final List<Alumno> alumnos) {
        return jdbcTemplate.batchUpdate("INSERT INTO alumnos VALUES (?, ?)", new BatchPreparedStatementSetter() {

            @Override
            public void setValues(final PreparedStatement preparedStatement, final int i) throws SQLException {
                preparedStatement.setString(1, alumnos.get(i).getNombre());
                preparedStatement.setString(2, alumnos.get(i).getApellido());

            }

            @Override
            public int getBatchSize() {
                return 3;
            }
        });
    }

    public int[] batchUpdateUsingNamedParameterJDBCTemplate(final List<Alumno> Alumnos) {
        final SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch(Alumnos.toArray());
        final int[] updateCounts = namedParameterJdbcTemplate.batchUpdate("INSERT INTO alumnos VALUES (:idalumno, :nombre, :apellido)", batch);
        return updateCounts;
    }

    public Alumno getAlumnoUsingSimpleJdbcCall(int id) {
        SqlParameterSource in = new MapSqlParameterSource().addValue("idalumno", id);
        Map<String, Object> out = simpleJdbcCall.execute(in);

        Alumno alu = new Alumno();
        alu.setNombre((String) out.get("nombre"));
        alu.setApellido((String) out.get("apellido"));

        return alu;
    }


}
