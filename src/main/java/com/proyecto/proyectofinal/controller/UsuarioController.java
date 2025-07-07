package com.proyecto.proyectofinal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.proyecto.proyectofinal.model.dtos.requestDtos.RequestUsuarioDTO;
import com.proyecto.proyectofinal.service.impl.InteresServiceImpl;
import com.proyecto.proyectofinal.service.interfaces.UsuarioService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private InteresServiceImpl interesService;

    @GetMapping("/registro")
    public String mostrarFormularioRegistro(Model model) {
        model.addAttribute("usuarioDto", new RequestUsuarioDTO());
         model.addAttribute("interesesDisponibles", interesService.listarTodos());
        return "crearUsuario";
    }

    @GetMapping("/perfil-creador/{cedula}")
    public String mostrarPerfilCreador(@PathVariable String cedula, Model model) {
        model.addAttribute("usuario", this.usuarioService.buscarPorCedula(cedula));

        return "infoUsuarioCreador";
    }

    @GetMapping("/perfil-normal/{nickName}")
    public String mostrarPerfilNormal(@PathVariable String nickName, Model model) {
        model.addAttribute("usuario", this.usuarioService.buscarPorNickname(nickName));

        return "infoUsuarioNormal";
    }

    @PostMapping("/guardar")
    public String guardarUsuario(@ModelAttribute("usuarioDto") RequestUsuarioDTO usuarioDto,
            RedirectAttributes redirectAttributes, Model model) {
        if (usuarioService.existePorNickName(usuarioDto.getNickName())) {
            model.addAttribute("usuarioDto", usuarioDto);
            model.addAttribute("errorNickName", "El nickname ya está en uso, por favor elige otro.");
            return "crearUsuario";
        }
        this.usuarioService.guardarUsuario(usuarioDto);

        return "redirect:/actividad/listar-actividades";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/borrar-usuario/{cedula}")
    public String borrarUsuario(@PathVariable String cedula, Model model) {
        this.usuarioService.eliminarUsuario(cedula);
        return "redirect:/actividad/listar-actividades";
    }
    
    @GetMapping("/actualizar/{cedula}")
    public String mostrarFormularioActualizarUsuario(@PathVariable String cedula, Model model) {
        model.addAttribute("usuarioDto",new RequestUsuarioDTO());
        return "actualizarUsuario";
    }
    @PostMapping("/actualizar-usuario")
    public String actualizarUsuario(@ModelAttribute("usuarioDto") RequestUsuarioDTO usuarioDto,
            RedirectAttributes redirectAttributes, Model model, HttpServletRequest request, HttpServletResponse response) {
        if (usuarioService.existePorNickName(usuarioDto.getNickName())) {
            model.addAttribute("usuarioDto", usuarioDto);
            model.addAttribute("errorNickName", "El nickname ya está en uso, por favor elige otro.");
            return "actualizarUsuario";
        }
        this.usuarioService.actualizarUsuario(usuarioDto);
        logoutUsuario(request, response);
        return "redirect:/actividad/listar-actividades";
    }


    public void logoutUsuario(HttpServletRequest request, HttpServletResponse response) {
    SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
    logoutHandler.logout(request, response, SecurityContextHolder.getContext().getAuthentication());
   }
}