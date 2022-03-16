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
                            @RequestParam(name= "idAlumno") String idAlumno) throws SQLException {
        try{
            int idBuscar =Integer.parseInt(idAlumno);
            if (idBuscar>0) {
                try{
                    alumno = alumnoDao.getAlumno(idBuscar);
                    if(alumno!=null){
                        model.addAttribute("idalumno", "IdAlumno");
                        model.addAttribute("nombre", "Nombre");
                        model.addAttribute("apellido", "Apellido");
                        model.addAttribute("titulo", "Alumno Encontrado");
                        model.addAttribute("alumno", alumno);
                    }
                } catch (EmptyResultDataAccessException ex) {
                    Alumno alumno1 =new Alumno();
                    model.addAttribute("idAlumno", " ");
                    model.addAttribute("nombre", " ");
                    model.addAttribute("apellido", " ");
                    model.addAttribute("titulo", "El alumno no se encuentra en la base de datos");
                    model.addAttribute("alumno", alumno1);
                    return "alumno-template/resultado";
                }
                catch (NumberFormatException ex){
                    Map<String, String> errores = new HashMap<>();
                    result.getFieldErrors().forEach(err -> {
                        errores.put(err.getField(), "El campo ".concat(err.getField()).concat(" ").concat(err.getDefaultMessage()));
                    });
                    model.addAttribute("titulo", "Falta datos");
                    model.addAttribute("error", errores);
                    return "alumno-template/buscar";
                }

            } else if (result.hasErrors()) {
                Map<String, String> errores = new HashMap<>();
                result.getFieldErrors().forEach(err -> {
                    errores.put(err.getField(), "El campo ".concat(err.getField()).concat(" ").concat(err.getDefaultMessage()));
                });
                model.addAttribute("titulo", "Falta datos");
                model.addAttribute("error", errores);
                return "alumno-template/buscar";
            }

        }catch (NumberFormatException ex){
            Map<String, String> errores = new HashMap<>();
            result.getFieldErrors().forEach(err -> {
                errores.put(err.getField(), "El campo no puede estar vacio");
            });
            model.addAttribute("titulo", "Falta datos");
            model.addAttribute("error", errores);
            return "alumno-template/eliminar";
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
                                  @RequestParam(name= "idAlumno") String idAlumno) throws SQLException {
        try{
            int id =Integer.parseInt(idAlumno);
            if (id>0) {
                try{
                    alumno = alumnoDao.getAlumno(id);
                    if(alumnoDao.deleteAlu(id)==1){
                        model.addAttribute("idalumno", "IdAlumno");
                        model.addAttribute("nombre", "Nombre");
                        model.addAttribute("apellido", "Apellido");
                        model.addAttribute("titulo", "Alumno Eliminado");
                        model.addAttribute("alumno", alumno);
                    }
                } catch (EmptyResultDataAccessException ex) {
                    Alumno alumno1 =new Alumno();
                    model.addAttribute("idAlumno", " ");
                    model.addAttribute("nombre", " ");
                    model.addAttribute("apellido", " ");
                    model.addAttribute("titulo", "El alumno no se encuentra en la base de datos");
                    model.addAttribute("alumno", alumno1);
                    return "alumno-template/resultado";
                }

            } else if (result.hasErrors()) {
                Map<String, String> errores = new HashMap<>();
                result.getFieldErrors().forEach(err -> {
                    errores.put(err.getField(), "El campo ".concat(err.getField()).concat(" ").concat(err.getDefaultMessage()));
                });
                model.addAttribute("titulo", "Falta datos");
                model.addAttribute("error", errores);
                return "alumno-template/eliminar";
            }
        }catch (NumberFormatException ex){
            Map<String, String> errores = new HashMap<>();
            result.getFieldErrors().forEach(err -> {
                errores.put(err.getField(), "El campo no puede estar vacio");
            });
            model.addAttribute("titulo", "Falta datos");
            model.addAttribute("error", errores);
            return "alumno-template/eliminar";
        }
        return "alumno-template/resultado";
    }

    @GetMapping("/modificar")
    public String modificarAlu(Model model) {
        Alumno alumno = new Alumno();
        model.addAttribute("titulo", "Modificar al Alumno");
        model.addAttribute("alumno", alumno);
        model.addAttribute("error", new HashMap<>());
        return "alumno-template/modificar";
    }
    @PostMapping("/modificar")
    public String modificarAluProc(@Valid Alumno alumno, BindingResult result, Model model,
                                  @RequestParam(name= "idAlumno") String idAlumno,
                                   @RequestParam(name= "nombre") String nombre,
                                   @RequestParam(name= "apellido") String apellido ) throws SQLException {
        try{
            int id =Integer.parseInt(idAlumno);
            if (id>0) {
                try{
                    alumno.setIdAlumno(id);
                    alumno.setNombre(nombre);
                    alumno.setApellido(apellido);
                    int valid = alumnoDao.modify(alumno);
                    if(valid==1){

                        model.addAttribute("idAlumno", "IdAlumno");
                        model.addAttribute("nombre", "Nombre");
                        model.addAttribute("apellido", "Apellido");
                        model.addAttribute("titulo", "Alumno Modificado");
                        model.addAttribute("alumno", alumno);
                    }
                } catch (EmptyResultDataAccessException ex) {
                    Alumno alumno1 =new Alumno();
                    model.addAttribute("idAlumno", " ");
                    model.addAttribute("nombre", " ");
                    model.addAttribute("apellido", " ");
                    model.addAttribute("titulo", "El alumno no se encuentra en la base de datos");
                    model.addAttribute("alumno", alumno1);
                    return "alumno-template/resultado";
                }

            } else if (result.hasErrors()) {
                Map<String, String> errores = new HashMap<>();
                result.getFieldErrors().forEach(err -> {
                    errores.put(err.getField(), "El campo ".concat(err.getField()).concat(" ").concat(err.getDefaultMessage()));
                });
                model.addAttribute("titulo", "Falta datos");
                model.addAttribute("error", errores);
                return "alumno-template/modificar";
            }
        }catch (NumberFormatException ex){
            Map<String, String> errores = new HashMap<>();
            result.getFieldErrors().forEach(err -> {
                errores.put(err.getField(), "El campo no puede estar vacio");
            });
            model.addAttribute("titulo", "Falta datos");
            model.addAttribute("error", errores);
            return "alumno-template/modificar";
        }
        return "alumno-template/resultado";
    }
}
