package com.proyecto.proyectofinal.service.interfaces;

import java.util.List;
import java.util.Optional;

import com.proyecto.proyectofinal.model.entities.TelefonoEntity;
import com.proyecto.proyectofinal.model.idsEmbedded.IdTelefono;

public interface TelefonoService {
    TelefonoEntity guardarTelefono(TelefonoEntity telefono);
    Optional<TelefonoEntity> buscarPorId(IdTelefono id);
    List<TelefonoEntity> listarTodos();
    void eliminarTelefono(IdTelefono id);
    TelefonoEntity buscarPorNumeroTelefono(String numeroTelefono);
}