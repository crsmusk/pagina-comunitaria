package com.proyecto.proyectofinal.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.proyectofinal.model.entities.InteresEntity;
import com.proyecto.proyectofinal.repository.InteresRepository;
import com.proyecto.proyectofinal.service.interfaces.InteresService;

@Service
public class InteresServiceImpl implements InteresService {

    
    private final InteresRepository interesRepository;

    public InteresServiceImpl(InteresRepository interesRepository) {
        this.interesRepository = interesRepository;
    }

    @Override
    public InteresEntity guardarInteres(InteresEntity interes) {
        InteresEntity interesGuardar= this.interesRepository.findByInteresIgnoreCase(interes.getInteres());
        if (interesGuardar == null) {
            return this.interesRepository.save(interes);
        } else {
            return interesGuardar;
            
        }
           
    }   
    
    @Override
    public Optional<InteresEntity> buscarPorNombre(String nombre) {
        InteresEntity interes = interesRepository.findByInteresIgnoreCase(nombre);
        if (interes == null) {
            interes= new InteresEntity();
            interes.setInteres(nombre);
            interes = this.interesRepository.save(interes);
        }
        return Optional.ofNullable(interes);
    }

    @Override
    public List<InteresEntity> listarTodos() {
        return interesRepository.findAll();
    }

    
    @Override
    public void eliminarInteres(String nombre) {
        InteresEntity interes = interesRepository.findByInteresIgnoreCase(nombre);
        if (interes != null) {
            interesRepository.delete(interes);
        } 
    }
    
   

}
