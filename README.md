# Talk Social Network

Una red social desarrollada con Spring Boot y Thymeleaf que permite a los usuarios conectarse, compartir contenido y comunicarse.



https://github.com/user-attachments/assets/08a01fa8-881f-4146-892f-3a4290a2d3b5


## Tecnologías

### Backend
- Java Spring Boot
- MySQL
- Maven

### Frontend
- Thymeleaf
- HTML/CSS
- JavaScript
- RichText Editor (jQuery)

## Características

- Sistema de autenticación de usuarios
- Publicación de contenido con editor de texto enriquecido
- Sistema de carga de archivos
- Gestión de perfiles de usuario
- Interacciones sociales (likes, comentarios, etc.)

## Estructura del Proyecto

```
├── .mvn/wrapper/          # Configuración Maven Wrapper
├── src/                   # Código fuente
├── uploads/               # Almacenamiento de archivos
├── Rich-Text-Editor/      # Editor de texto enriquecido
├── pom.xml               # Dependencias Maven
└── sqlBD.sql             # Script de base de datos
```

## Requisitos

- Java 11 o superior
- MySQL
- Maven

## Instalación

1. Clonar el repositorio
```bash
git clone https://github.com/axelj123/RedSocialVallejo.git
```

2. Configurar la base de datos
```bash
mysql -u root -p < sqlBD.sql
```

3. Ejecutar el proyecto
```bash
mvn spring-boot:run
```

## Licencia

[MIT](https://choosealicense.com/licenses/mit/)
