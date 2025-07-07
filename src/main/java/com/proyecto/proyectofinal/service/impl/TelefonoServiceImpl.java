package com.proyecto.proyectofinal.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.proyecto.proyectofinal.model.entities.TelefonoEntity;
import com.proyecto.proyectofinal.model.idsEmbedded.IdTelefono;
import com.proyecto.proyectofinal.repository.TelefonoRepository;
import com.proyecto.proyectofinal.service.interfaces.TelefonoService;

@Service
public class TelefonoServiceImpl implements TelefonoService {

    @Autowired
    private TelefonoRepository telefonoRepository;

    @Override
    @Transactional
    public TelefonoEntity guardarTelefono(TelefonoEntity telefono) {
        return telefonoRepository.save(telefono);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TelefonoEntity> buscarPorId(IdTelefono id) {
        return telefonoRepository.findById(id);
    }

    

    @Override
    @Transactional(readOnly = true)
    public List<TelefonoEntity> listarTodos() {
        return telefonoRepository.findAll();
    }

    @Override
    @Transactional
    public void eliminarTelefono(IdTelefono id) {
        telefonoRepository.deleteById(id);
    }

    @Override
    public TelefonoEntity buscarPorNumeroTelefono(String numeroTelefono) {
        return telefonoRepository.findByNumeroTelefono(numeroTelefono)
                .orElse(null);
    }

    
}