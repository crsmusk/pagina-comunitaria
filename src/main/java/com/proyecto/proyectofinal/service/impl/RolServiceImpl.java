package com.proyecto.proyectofinal.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.proyecto.proyectofinal.model.entities.RolEntity;
import com.proyecto.proyectofinal.repository.RolRepository;
import com.proyecto.proyectofinal.service.interfaces.RolService;

@Service
public class RolServiceImpl implements RolService {

    
    
    private final RolRepository repositorio;

    public RolServiceImpl(RolRepository repositorio) {
        this.repositorio = repositorio;
    }

    //se usara las transacciones para la consistencia de los datos
    @Transactional
    @Override
    public RolEntity guardarRol(RolEntity rol) {
        /*  
         * se busca el rol por su nombre, si no existe se crea uno nuevo
         * si existe se retorna el rol existente
         * esto es para evitar duplicados en la base de datos
         */
        RolEntity rolGuardado = 
        this.repositorio.findById(rol.getNombreRol())
        .orElse(this.repositorio.save(rol));
        return rolGuardado;
    }

    //se usara las transacciones en readOnly para mejorar el rendimiento
    @Transactional(readOnly = true)
    @Override
    public Optional<RolEntity> buscarPorNombre(String nombreRol) {
        return this.repositorio.findById(nombreRol);
    }
    
    @Transactional(readOnly = true)
    @Override
    public List<RolEntity> listarTodos() {
        return this.repositorio.findAll();
    }

    @Override
    public void eliminarRol(String nombreRol) {
        /*
         * se busca el rol por su nombre, si existe se elimina
         */
        if (this.repositorio.existsById(nombreRol)) {
            this.repositorio.deleteById(nombreRol);
        }
    }


}
