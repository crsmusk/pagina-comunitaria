package com.proyecto.proyectofinal.service.interfaces;

import java.util.List;
import java.util.Optional;


import com.proyecto.proyectofinal.model.dtos.requestDtos.RequestUsuarioDTO;
import com.proyecto.proyectofinal.model.dtos.responseDtos.ResponseUsuarioDTO;
import com.proyecto.proyectofinal.model.entities.UsuarioEntity;

public interface UsuarioService {
    UsuarioEntity guardarUsuario(RequestUsuarioDTO usuario);
    ResponseUsuarioDTO buscarPorCedula(String cedula);
    ResponseUsuarioDTO buscarPorNombreUsuario(String nombreUsuario);
    List<ResponseUsuarioDTO> buscarPorInteres(String interes);
    List<ResponseUsuarioDTO> listarTodos();
    UsuarioEntity buscarPorId(String id);
    void eliminarUsuario(String cedula);
    ResponseUsuarioDTO buscarPorNickname(String nickName);
    //boolean existePorCedula(String cedula);
    boolean existePorNickName(String nickName);
    void actualizarUsuario(RequestUsuarioDTO usuario);
    
}