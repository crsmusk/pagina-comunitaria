package com.proyecto.proyectofinal.service.interfaces;



import com.proyecto.proyectofinal.model.entities.EmailEntity;

public interface EmailService {
    EmailEntity guardarEmail(EmailEntity email);
    void eliminarEmail(String email);
    EmailEntity buscarEmail(String email);
  
}