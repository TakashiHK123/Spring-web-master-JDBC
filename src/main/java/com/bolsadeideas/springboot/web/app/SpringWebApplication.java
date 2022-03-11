package com.bolsadeideas.springboot.web.app;

import com.bolsadeideas.springboot.web.app.DAO.AlumnoDAO;
import com.bolsadeideas.springboot.web.app.RowMapper.AlumnoRowMapper;
import com.bolsadeideas.springboot.web.app.entity.Alumno;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;


import java.util.ArrayList;
import java.util.List;

@SpringBootApplication

public class SpringWebApplication{
	//private static final String SQL="SELECT * FROM alumnos";
	//@Autowired
	//private JdbcTemplate jdbcTemplate;

	public static void main(String[] args){
		SpringApplication.run(SpringWebApplication.class, args);
	}
	public void run(String... args) {

		System.out.println("TRUE");
	}


}
