package com.proyecto.proyectofinal.controller;

import java.io.IOException;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.proyecto.proyectofinal.mappers.MapperDtos;
import com.proyecto.proyectofinal.model.dtos.requestDtos.RequestActividadDTO;
import com.proyecto.proyectofinal.model.dtos.responseDtos.ResponseActividadDTO;
import com.proyecto.proyectofinal.service.interfaces.ActividadService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/actividad")
public class ActividadController {

    @Autowired
    private ActividadService actividadService;
    @Autowired
    private MapperDtos mapper;

    @GetMapping("/buscar")
    public String buscarActividades(@RequestParam String resultado, @RequestParam String opcion, Model model) {
        int opciones = Integer.parseInt(opcion);
        switch (opciones) {
            case 1:
                model.addAttribute("actividades", this.actividadService.buscarPorNombreActividad(resultado));
                return "index";
            case 2:
                model.addAttribute("actividades", this.actividadService.buscarPorCiudad(resultado));
                return "index";
            case 3:
                model.addAttribute("actividades", this.actividadService.buscarPorInteres(resultado));
                return "index";
            case 4:
                model.addAttribute("actividades", this.actividadService.buscarPorParticipante(resultado));
                return "index";
            default:
                model.addAttribute("actividades", this.actividadService.buscarPorCreador(resultado));
                return "index";
        }

    }

    @GetMapping("/guardar")
    public String mostrarFormularioGuardarActividad(Model model) {

        model.addAttribute("actividadDto", new RequestActividadDTO());
        return "crearActividad";
    }

    @GetMapping("/listar-actividades")
    public String mostrarActividades(Model model) {
        model.addAttribute("actividades", this.actividadService.buscarProximasActividades());
        return "index";
    }

    @PostMapping("/guardar-actividad")
    public String guardarActividad(@ModelAttribute("actividadDto") RequestActividadDTO actividadDTO,
            RedirectAttributes redirectAttributes) {
        this.actividadService.guardarActividad(actividadDTO);
        return "index";
    }

    @GetMapping("/borrar-actividad/{fechaCreacion}")
    public String borrarActividad(@PathVariable LocalDateTime fechaCreacion, Model model) {
        this.actividadService.eliminarActividad(fechaCreacion);
        return "redirect:/actividad/listar-actividades";

    }

    @GetMapping("/actualizar/{fechaCreacion}")
    public String actualizarActividad(@PathVariable LocalDateTime fechaCreacion, Model model) {
        ResponseActividadDTO actividad = this.actividadService.buscarPorId(fechaCreacion);
        // se convierte el objeto ResponseActividadDTO a RequestActividadDTO
        RequestActividadDTO actividadDto = this.mapper.responseToRequestActividadDTO(actividad);
        actividadDto.setFechaCreacionTexto(actividad.getFechaCreacion().toString());
        // Asegurarnos de que la fecha de creación se mantenga exactamente igual
        actividadDto.setFechaCreacion(fechaCreacion);
       
        model.addAttribute("actividadDto", actividadDto);
       
        return "actualizarActividad";
    }

    @PostMapping("/actualizar-actividad")
    public String actualizrActividad(@ModelAttribute("actividadDto") RequestActividadDTO actividadDTO,
            RedirectAttributes redirectAttributes) throws IOException {
            LocalDateTime fechaCreacion = LocalDateTime.parse(actividadDTO.getFechaCreacionTexto());
            actividadDTO.setFechaCreacion(fechaCreacion);
             this.actividadService.actualizarActividad(actividadDTO.getFechaCreacion(), actividadDTO);   
            //return "redirect:/actividad/listar-actividades";
           
            return "index";
    }

    @GetMapping("/eliminar-actividad/{fechaCreacion}")
    public String eliminarActividad(@PathVariable LocalDateTime fechaCreacion) {
        this.actividadService.eliminarActividad(fechaCreacion);
        return "redirect:/actividad/listar-actividades";
    }

}
