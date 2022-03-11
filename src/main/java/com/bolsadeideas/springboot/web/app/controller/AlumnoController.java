package com.bolsadeideas.springboot.web.app.controller;

import com.bolsadeideas.springboot.web.app.DAO.AlumnoDAO;
import com.bolsadeideas.springboot.web.app.entity.Alumno;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/alumnos")
public class AlumnoController {

    @Autowired
    AlumnoDAO alumnoDao;

    @GetMapping("/listar")
    public String listaAlu(Model model) {

        List<Alumno> alumnos = new ArrayList<>();
        alumnos = alumnoDao.getAll();
        model.addAttribute("titulo", "Lista de Alumnos");
        model.addAttribute("mensaje", "Lista de alumnos");
        model.addAttribute("idalumno", "IdAlumno");
        model.addAttribute("nombre", "Nombre");
        model.addAttribute("apellido", "Apellido");
        model.addAttribute("alumnos", alumnos);
        return "alumno-template/listar";
    }

    @GetMapping("/agregar")
    public String agregarAlu(Model model) {
        Alumno alumno = new Alumno();
        model.addAttribute("titulo", "Agregar Alumno");
        model.addAttribute("alumno", alumno);
        model.addAttribute("error", new HashMap<>());
        return "alumno-template/agregar";
    }

    @PostMapping("/agregar")
    public String agregarAluProc(@Valid Alumno alumno, BindingResult result, Model model,
                                 @RequestParam(name="nombre") String nombre,
                                 @RequestParam(name="apellido") String apellido) throws SQLException {
        model.addAttribute("titulo", "Falta datos");
        if(result.hasErrors()) {
            Map<String, String> errores = new HashMap<>();
            result.getFieldErrors().forEach(err ->{
                errores.put(err.getField(), "El campo ".concat(err.getField()).concat(" ").concat(err.getDefaultMessage()));
            });
            model.addAttribute("error", errores);
            return "alumno-template/agregar";
        }
        Alumno alumno2 = new Alumno();
        alumno2=alumnoDao.addAlumnoReturnAlu(nombre, apellido);
        model.addAttribute("idalumno", "IdAlumno");
        model.addAttribute("nombre", "Nombre");
        model.addAttribute("apellido", "Apellido");
        model.addAttribute("titulo", "Alumno Agregado");
        model.addAttribute("alumno", alumno2);
        return "alumno-template/resultado";
    }
}
