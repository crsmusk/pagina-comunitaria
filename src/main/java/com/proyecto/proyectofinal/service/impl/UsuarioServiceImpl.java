package com.proyecto.proyectofinal.service.impl;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.proyecto.proyectofinal.mappers.MapperUsuarioResponse;
import com.proyecto.proyectofinal.model.dtos.requestDtos.RequestUsuarioDTO;
import com.proyecto.proyectofinal.model.dtos.responseDtos.ResponseUsuarioDTO;
import com.proyecto.proyectofinal.model.entities.EmailEntity;
import com.proyecto.proyectofinal.model.entities.InteresEntity;
import com.proyecto.proyectofinal.model.entities.RolEntity;
import com.proyecto.proyectofinal.model.entities.TelefonoEntity;
import com.proyecto.proyectofinal.model.entities.UsuarioEntity;
import com.proyecto.proyectofinal.model.idsEmbedded.IdTelefono;
import com.proyecto.proyectofinal.repository.UsuarioRepository;
import com.proyecto.proyectofinal.service.interfaces.UsuarioService;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Value("${file.upload-dir.fotos}")
    String direccion;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RolServiceImpl rolService;

    @Autowired
    private TelefonoServiceImpl telefonoService;

    @Autowired
    private InteresServiceImpl interesService;

    @Autowired
    private MapperUsuarioResponse mapper;

    @Autowired
    private EmailServiceImpl emailService;

    

    @Transactional
    @Override
    public UsuarioEntity guardarUsuario(RequestUsuarioDTO usuario) {

        UsuarioEntity usuarioEntity = new UsuarioEntity();
        usuarioEntity.setCedula(usuario.getCedula());
        usuarioEntity.setNombreUsuario(usuario.getNombreUsuario());
        usuarioEntity.setApellidoUsuario(usuario.getApellidoUsuario());
        usuarioEntity.setContrasena(usuario.getContrasena());
        usuarioEntity.setNickName(usuario.getNickName());

        List<EmailEntity> emails = new ArrayList<>();
        if (usuario.getEmails() != null) {
            for (String email : usuario.getEmails()) {
                EmailEntity emailEntity = new EmailEntity();
                emailEntity.setEmail(email);
                emailEntity.setUsuario(usuarioEntity);
                emails.add(emailEntity);
            }
        }
        usuarioEntity.setEmails(emails);

        List<RolEntity> roles = buscarRoles(usuario.getRoles());
        usuarioEntity.setRoles(roles);

        // se llama al metodo guardarFoto para guardar la foto de perfil
        usuarioEntity.setFotoPerfilReferencia(guardarFoto(usuario.getFotoPerfil()));

        // se llama al metodo optenerIntereses para obtener los intereses del usuario
        usuarioEntity.setIntereses(optenerIntereses(usuario.getIntereses()));

        usuarioEntity = usuarioRepository.save(usuarioEntity);

        List<TelefonoEntity> telefonos = new ArrayList<>();
        if (usuario.getTelefonos() != null) {
            for (String numeroTelefono : usuario.getTelefonos()) {
                TelefonoEntity telefonoEntity = new TelefonoEntity();
                IdTelefono idTelefono = new IdTelefono(numeroTelefono, usuarioEntity.getCedula());
                telefonoEntity.setId(idTelefono);
                telefonoEntity.setUsuario(usuarioEntity);
                telefonos.add(telefonoService.guardarTelefono(telefonoEntity));
            }
        }
        usuarioEntity.setTelefonos(telefonos);

        return usuarioEntity;

    }

    @Transactional(readOnly = true)
    @Override
    public ResponseUsuarioDTO buscarPorCedula(String cedula) {
        return this.mapper.requestToResponse(this.usuarioRepository.findById(cedula).orElse(null));
    }

    @Transactional(readOnly = true)
    @Override
    public ResponseUsuarioDTO buscarPorNombreUsuario(String nombreUsuario) {
        return this.mapper.requestToResponse(this.usuarioRepository.findByNombreUsuario(nombreUsuario).get());
    }

    @Transactional(readOnly = true)
    @Override
    public List<ResponseUsuarioDTO> buscarPorInteres(String interes) {

        return this.mapper.requestsToResponses(this.usuarioRepository.buscarPorInteres(interes));
    }

    @Transactional(readOnly = true)
    @Override
    public List<ResponseUsuarioDTO> listarTodos() {
        return this.mapper.requestsToResponses(this.usuarioRepository.findAll());
    }

    @Override
    public void eliminarUsuario(String cedula) {
        if (this.usuarioRepository.existsById(cedula)) {
            this.usuarioRepository.deleteById(cedula);
            String rutaPortada = this.usuarioRepository.findById(cedula).get().getFotoPerfilReferencia();
            // Eliminar la foto de perfil del sistema de archivos
            if (rutaPortada != null) {
                String nombreArchivo = rutaPortada.substring(rutaPortada.lastIndexOf("/") + 1);
                File archivo = new File(direccion + File.separator + nombreArchivo);
                if (archivo.exists()) {
                    archivo.delete();
                }
            }
        }
    }

    public List<RolEntity> buscarRoles(List<String> roles) {
        List<RolEntity> rolesEncontrados = new ArrayList<>();
        for (String rol : roles) {
            Optional<RolEntity> rolEncontrado = this.rolService.buscarPorNombre(rol);
            if (rolEncontrado.isPresent()) {
                rolesEncontrados.add(rolEncontrado.get());
            }
        }

        return rolesEncontrados;

    }

    public String guardarFoto(MultipartFile archivo) {
        if (archivo == null || archivo.isEmpty()) {
            return null;
        }
        try {
            File directorio = new File(direccion);
            if (!directorio.exists()) {
                directorio.mkdirs();
            }

            String nombreOriginal = archivo.getOriginalFilename();
            String extension = nombreOriginal.substring(nombreOriginal.lastIndexOf("."));
            String nombreArchivo = System.currentTimeMillis() + extension;

            Path path = Paths.get(direccion, nombreArchivo);
            Files.write(path, archivo.getBytes());

            return "/fotos/" + nombreArchivo;
        } catch (Exception e) {
            System.out.println("aqui esta el error: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public List<InteresEntity> optenerIntereses(List<String> intereses) {
        List<InteresEntity> interesesEncontrados = new ArrayList<>();
        for (String interes : intereses) {
            Optional<InteresEntity> interesEncontrado = this.interesService.buscarPorNombre(interes);
            if (interesEncontrado.isPresent()) {
                interesesEncontrados.add(interesEncontrado.get());
                System.out.println("interes encontrado: " + interesEncontrado.get().getInteres());
            }
        }

        return interesesEncontrados;
    }

    @Override
    public UsuarioEntity buscarPorId(String id) {
        return this.usuarioRepository.findById(id).orElse(null);
    }

    @Override
    public ResponseUsuarioDTO buscarPorNickname(String nickName) {
        return this.mapper.requestToResponse(this.usuarioRepository.findByNickNameIgnoreCase(nickName).get());
    }

    @Override
    public boolean existePorNickName(String nickName) {
        return this.usuarioRepository.existsByNickNameIgnoreCase(nickName);
    }

    @Override
    @Transactional
    public void actualizarUsuario(RequestUsuarioDTO usuario) {
        UsuarioEntity usuarioEntity = this.usuarioRepository.findById(usuario.getCedula()).orElse(null);

        usuarioEntity.setNombreUsuario(usuario.getNombreUsuario());
        usuarioEntity.setApellidoUsuario(usuario.getApellidoUsuario());
        usuarioEntity.setContrasena(usuario.getContrasena());
        usuarioEntity.setNickName(usuario.getNickName());

        usuarioEntity.getEmails().clear();
        List<EmailEntity> emails = new ArrayList<>();
        if (usuario.getEmails() != null) {
            for (String email : usuario.getEmails()) {
                if (email != null && !email.isBlank()) {
                    EmailEntity emailEntity = emailService.buscarEmail(email);
                    emailEntity.setUsuario(usuarioEntity);
                    emails.add(emailEntity);
                }
            }
        }
        usuarioEntity.getEmails().addAll(emails);

        List<RolEntity> roles = buscarRoles(usuario.getRoles());
        usuarioEntity.setRoles(roles);

        // se llama al metodo guardarFoto para guardar la foto de perfil
        usuarioEntity.setFotoPerfilReferencia(guardarFoto(usuario.getFotoPerfil()));

        // se llama al metodo optenerIntereses para obtener los intereses del usuario
        usuarioEntity.setIntereses(optenerIntereses(usuario.getIntereses()));

        usuarioEntity = usuarioRepository.save(usuarioEntity);
        
        // Actualizar los teléfonos del usuario
        // Primero, eliminamos los teléfonos antiguos
        usuarioEntity.getTelefonos().clear();
        // Luego, agregamos los nuevos teléfonos
        List<TelefonoEntity> telefonos = new ArrayList<>();
        if (usuario.getTelefonos() != null) {
            for (String numeroTelefono : usuario.getTelefonos()) {
                if (telefonoService.buscarPorNumeroTelefono(numeroTelefono) != null) {
                    // Si el teléfono ya existe, lo actualizamos
                    TelefonoEntity telefonoExistente = telefonoService.buscarPorNumeroTelefono(numeroTelefono);
                    telefonoExistente.setUsuario(usuarioEntity);
                    telefonos.add(telefonoExistente);
                    continue;
                    
                }
                TelefonoEntity telefonoEntity = new TelefonoEntity();
                IdTelefono idTelefono = new IdTelefono(numeroTelefono, usuarioEntity.getCedula());
                telefonoEntity.setId(idTelefono);
                telefonoEntity.setUsuario(usuarioEntity);
                telefonos.add(telefonoService.guardarTelefono(telefonoEntity));
            }
        }
        // Asignar la lista de teléfonos al usuario
        usuarioEntity.getTelefonos().addAll(telefonos);

        // Guardar los cambios en la base de datos
        this.usuarioRepository.save(usuarioEntity);

    }

}
