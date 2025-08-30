package com.proyecto.proyectofinal.service.userDetails;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.proyecto.proyectofinal.model.entities.RolEntity;
import com.proyecto.proyectofinal.model.entities.UsuarioEntity;
import com.proyecto.proyectofinal.repository.UsuarioRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepo;

    public CustomUserDetailsService(UsuarioRepository usuarioRepo) {
        this.usuarioRepo = usuarioRepo;
    }


    @Override
    public UserDetails loadUserByUsername(String nickName) throws UsernameNotFoundException {
        UsuarioEntity usuario = usuarioRepo.findByNickNameIgnoreCase(nickName).orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
        List <GrantedAuthority> authorities = new ArrayList<>();
        for (RolEntity rol : usuario.getRoles()) {
            authorities.add(new SimpleGrantedAuthority("ROLE_".concat(rol.getNombreRol())));
        }
        
        return new User(usuario.getNickName(), usuario.getContrasena(), authorities);
    }
    

}
