package com.proyecto.proyectofinal.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.proyecto.proyectofinal.model.entities.CiudadEntity;
import com.proyecto.proyectofinal.model.entities.DireccionEntity;
import com.proyecto.proyectofinal.model.idsEmbedded.IdDireccion;
import com.proyecto.proyectofinal.repository.DireccionRepository;
import com.proyecto.proyectofinal.service.interfaces.DireccionService;

@Service
public class DireccionServiceImpl implements DireccionService {

    
    private final DireccionRepository direccionRepository;
 
    private final CiudadServiceImpl ciudadService;    

    public DireccionServiceImpl(DireccionRepository direccionRepository, CiudadServiceImpl ciudadService) {
        this.direccionRepository = direccionRepository;
        this.ciudadService = ciudadService;
    }

    @Transactional
    @Override
    public DireccionEntity guardarDireccion(String direccion,String ciudad) {
        IdDireccion idDireccion = new IdDireccion();
        idDireccion.setDireccion(direccion);
        /*
         *  Se llame el metodo buscar  ciudad para verificar si existe la ciudad
         *  en la base de datos, si no existe se crea una nueva ciudad
         */
        idDireccion.setCiudad(buscarCiudad(ciudad).getNombreCiudad() );
        DireccionEntity direccionGuardada ;
        if (!this.direccionRepository.existsById(idDireccion)){
            direccionGuardada= new DireccionEntity();
            direccionGuardada.setId(idDireccion);
            direccionGuardada.setCiudad(this.buscarCiudad(ciudad));
            return this.direccionRepository.save(direccionGuardada);
        }else{
            direccionGuardada = this.direccionRepository.findById(idDireccion).get();
            return direccionGuardada;
        }
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<DireccionEntity> buscarPorId(IdDireccion id) {
        return direccionRepository.findById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public List<DireccionEntity> buscarPorTexto(String texto) {
        return direccionRepository.buscarDireccion(texto);
    }

    @Transactional(readOnly = true)
    @Override
    public List<DireccionEntity> listarTodas() {
        return direccionRepository.findAll();
    }

    @Override
    public void eliminarDireccion(IdDireccion id) {
        direccionRepository.deleteById(id);
    }

    public CiudadEntity buscarCiudad(String ciudad) {
        Optional<CiudadEntity> ciudadBuscada = ciudadService.buscarPorNombre(ciudad);
        if (ciudadBuscada.isPresent()) {
            return ciudadBuscada.get();
        } else {
            CiudadEntity ciudadNueva = new CiudadEntity();
            ciudadNueva.setNombreCiudad(ciudad);
            ciudadService.guardarCiudad(ciudad);
            return ciudadNueva;
        }
    }
}
