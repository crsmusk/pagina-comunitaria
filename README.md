[![Ask DeepWiki](https://deepwiki.com/badge.svg)](https://deepwiki.com/crsmusk/pagina-comunitaria)





# Página Comunitaria

Una plataforma web comunitaria desarrollada con Spring Boot que permite a los usuarios crear, buscar y participar en actividades locales. La aplicación facilita la conexión entre personas con intereses similares en su comunidad.

## 🚀 Características Principales

- **Gestión de Usuarios**: Registro, autenticación y perfiles personalizables con fotos
- **Gestión de Actividades**: Crear, actualizar y participar en eventos comunitarios
- **Sistema de Búsqueda**: Buscar actividades por nombre, ciudad o interés
- **Sistema de Comentarios**: Interactuar y comentar en las actividades
- **Sistema de Intereses**: Etiquetado y categorización de contenido
- **Autenticación Segura**: Sistema de login con Spring Security

## 🛠️ Tecnologías Utilizadas

### Backend
- **Java 21** 
- **Spring Boot 3.4.5** 
- **Spring Data JPA** - Para persistencia de datos 
- **Spring Security** - Para autenticación y autorización 
- **Spring Boot DevTools** - Para desarrollo 
- **Lombok** - Para reducir código boilerplate 
- **Hibernate Validator** - Para validación de datos  

### Frontend
- **Thymeleaf** - Motor de plantillas 
- **Bootstrap 5.3.0** - Framework CSS 
- **Font Awesome 6.0.0** - Iconografía 

### Base de Datos
- **MySQL** - Base de datos principal 

### Herramientas de Construcción
- **Maven** - Gestión de dependencias y construcción del proyecto  

## 📁 Estructura del Proyecto

```
src/main/java/com/proyecto/proyectofinal/
├── configuration/     # Configuraciones de Spring
├── controller/        # Controladores REST y MVC
├── mappers/          # Mappers para conversión DTO-Entity
├── model/            # Modelos de datos
│   ├── dtos/         # Data Transfer Objects
│   ├── entities/     # Entidades JPA
│   └── idsEmbedded/  # IDs embebidos
├── repository/       # Repositorios de datos
├── service/          # Lógica de negocio
└── ProyectofinalApplication.java  # Clase principal
``` [13](#0-12) 

## 🏗️ Entidades Principales

### Usuario
- **Identificación**: Cédula como clave primaria
- **Perfil**: Nickname único, nombre, apellido, contraseña
- **Contacto**: Múltiples teléfonos y emails
- **Personalización**: Foto de perfil e intereses
- **Seguridad**: Sistema de roles [14](#0-13) 

### Actividad
- **Identificación**: Fecha de creación como clave primaria
- **Información**: Nombre, descripción, capacidad
- **Programación**: Fecha de inicio y ubicación
- **Participación**: Lista de participantes inscritos
- **Categorización**: Intereses asociados
- **Interacción**: Sistema de comentarios [15](#0-14) 

## 🚀 Instalación y Configuración

### Prerrequisitos
- Java 21 o superior
- Maven 3.6+
- MySQL 8.0+
- Git

### Pasos de Instalación

1. **Clonar el repositorio**
```bash
git clone https://github.com/crsmusk/pagina-comunitaria.git
cd pagina-comunitaria
```

2. **Configurar la base de datos**
    - Crear una base de datos MySQL
    - Configurar las credenciales en el archivo de propiedades

3. **Instalar dependencias**
```bash
./mvnw clean install
```

4. **Ejecutar la aplicación**
```bash
./mvnw spring-boot:run
```

La aplicación estará disponible en `http://localhost:8080`

## 📖 Funcionalidades

### Para Usuarios
- **Registro y Login**: Crear cuenta y autenticarse securely
- **Perfil Personalizado**: Configurar información personal, foto e intereses
- **Explorar Actividades**: Navegar por actividades disponibles en la comunidad
- **Búsqueda Avanzada**: Filtrar por nombre, ciudad o interés
- **Participación**: Inscribirse y desinscribirse de actividades
- **Interacción**: Comentar en actividades 

### Para Organizadores
- **Crear Actividades**: Publicar nuevos eventos con detalles completos
- **Gestión**: Actualizar información de actividades creadas
- **Monitoreo**: Ver participantes inscritos
- **Moderación**: Gestionar comentarios

## 🎯 Casos de Uso

1. **Evento Deportivo**: Crear una actividad de fútbol en un parque local
2. **Taller Educativo**: Organizar una clase de cocina comunitaria
3. **Actividad Cultural**: Coordinar visitas a museos o exposiciones
4. **Voluntariado**: Organizar actividades de limpieza o ayuda social
5. **Networking**: Crear meetups para profesionales de la misma área

## 🔐 Seguridad

El proyecto implementa Spring Security con:
- Autenticación basada en sesiones
- Control de acceso por roles
- Protección CSRF
- Validación de datos de entrada 

## 📝 Notas

- El proyecto utiliza Lombok para reducir el código boilerplate en las entidades
- Las validaciones se realizan tanto en el frontend como en el backend
- El sistema de comentarios está integrado directamente con las actividades
- La aplicación soporta múltiples roles de usuario para diferentes niveles de acceso
- Las imágenes de perfil y actividades se manejan mediante referencias a archivos


