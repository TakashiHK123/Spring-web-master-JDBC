package com.bolsadeideas.springboot.web.app.controller;

import com.bolsadeideas.springboot.web.app.DAO.AlumnoDAO;
import com.bolsadeideas.springboot.web.app.entity.Alumno;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
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
                                 @RequestParam(name = "nombre") String nombre,
                                 @RequestParam(name = "apellido") String apellido) throws SQLException {

        if (nombre != "" && apellido != "") {
            alumno = alumnoDao.addAlumnoReturnAlu(nombre, apellido);
            model.addAttribute("idalumno", "IdAlumno");
            model.addAttribute("nombre", "Nombre");
            model.addAttribute("apellido", "Apellido");
            model.addAttribute("titulo", "Alumno Agregado");
            model.addAttribute("alumno", alumno);
            //System.out.println("True");
            return "alumno-template/resultado";
        } else if (result.hasErrors()) {
            Map<String, String> errores = new HashMap<>();
            result.getFieldErrors().forEach(err -> {
                errores.put(err.getField(), "El campo ".concat(err.getField()).concat(" ").concat(err.getDefaultMessage()));
            });
            model.addAttribute("titulo", "Falta datos");
            model.addAttribute("error", errores);
            //System.out.println("False");
            return "alumno-template/agregar";
        }
        return null;
    }

     @GetMapping("/buscar")
     public String buscarAlu(Model model) {
        Alumno alumno = new Alumno();
        model.addAttribute("titulo", "Buscar Alumno");
        model.addAttribute("alumno", alumno);
        model.addAttribute("error", new HashMap<>());
        return "alumno-template/buscar";
    }

    @PostMapping("/buscar")
    public String buscarAlu(@Valid Alumno alumno, BindingResult result, Model model,
                            @RequestParam(name= "idAlumno") int idAlumno) throws SQLException {
        if (idAlumno>0) {
            alumno = alumnoDao.getAlumno(idAlumno);
            if(alumno!=null){
                model.addAttribute("idalumno", "IdAlumno");
                model.addAttribute("nombre", "Nombre");
                model.addAttribute("apellido", "Apellido");
                model.addAttribute("titulo", "Alumno Encontrado");
                model.addAttribute("alumno", alumno);
            }else {
                model.addAttribute("idalumno", "");
                model.addAttribute("nombre", "");
                model.addAttribute("apellido", "");
                model.addAttribute("titulo", "Alumno no se encuentra en la base de datos");
                model.addAttribute("alumno", alumno);
            }
        } else if (result.hasErrors()) {
            Map<String, String> errores = new HashMap<>();
            result.getFieldErrors().forEach(err -> {
                errores.put(err.getField(), "El campo ".concat(err.getField()).concat(" ").concat(err.getDefaultMessage()));
            });
            model.addAttribute("titulo", "Falta datos");
            model.addAttribute("error", errores);
            //System.out.println("False");
            return "alumno-template/buscar";
        }else{
            model.addAttribute("idalumno", "");
            model.addAttribute("nombre", "");
            model.addAttribute("apellido", "");
            model.addAttribute("titulo", "Alumno no se encuentra en la base de datos");
            model.addAttribute("alumno", alumno);
        }
        return "alumno-template/resultado";
    }

    @GetMapping("/eliminar")
    public String eliminarAlu(Model model) {
        Alumno alumno = new Alumno();
        model.addAttribute("titulo", "Eliminar Alumno");
        model.addAttribute("alumno", alumno);
        model.addAttribute("error", new HashMap<>());
        return "alumno-template/eliminar";
    }
    @PostMapping("/eliminar")
    public String eliminarAluProc(@Valid Alumno alumno, BindingResult result, Model model,
                                  @RequestParam(name= "idAlumno") int idAlumno)throws SQLException {
        if (idAlumno > 0) {
            try {
                alumno = alumnoDao.getAlumno(idAlumno);

                if (alumnoDao.deleteAlu(idAlumno) == 0) {
                    model.addAttribute("idAlumno", " ");
                    model.addAttribute("nombre", " ");
                    model.addAttribute("apellido", " ");
                    model.addAttribute("titulo", "El alumno no se puede eliminar esta referido a otra base de datos");
                    model.addAttribute("alumno", alumno);

                } else {
                    if (alumno == null) {
                        model.addAttribute("idAlumno", " ");
                        model.addAttribute("nombre", " ");
                        model.addAttribute("apellido", " ");
                        model.addAttribute("titulo", "El alumno no se encuentra en la base de datos");
                        model.addAttribute("alumno", alumno);
                    } else {
                        model.addAttribute("idAlumno", "IdAlumno");
                        model.addAttribute("nombre", "Nombre");
                        model.addAttribute("apellido", "Apellido");
                        model.addAttribute("titulo", "Alumno Eliminado");
                        model.addAttribute("alumno", alumno);
                    }

                }else if (result.hasErrors()) {
                    Map<String, String> errores = new HashMap<>();
                    result.getFieldErrors().forEach(err -> {
                        errores.put(err.getField(), "El campo ".concat(err.getField()).concat(" ").concat(err.getDefaultMessage()));
                    });
                    model.addAttribute("titulo", "Debe ser numero entero");
                    model.addAttribute("error", errores);
                    return "alumno-template/eliminar";
                }
                return "alumno-template/resultado";
            } catch (EmptyResultDataAccessException ex) {
                model.addAttribute("idAlumno", " ");
                model.addAttribute("nombre", " ");
                model.addAttribute("apellido", " ");
                model.addAttribute("titulo", "El alumno no se encuentra en la base de datos");
                model.addAttribute("alumno", alumno);
                return "alumno-template/resultado";
            }

        }
    }

}
