# Autos JPA Hibernate

Proyecto de práctica en **Java** utilizando **JPA con Hibernate** para la persistencia de entidades.  
El sistema permite cargar información de autos, incluyendo **Marca**, **Modelo** y **Tipos**, vinculados entre sí, y almacenarlos en una base de datos **H2**.

## 🚀 Tecnologías utilizadas
- Java 17+ (compatible también con 21 o superior)
- JPA / Hibernate
- H2 Database (modo archivo o en memoria)
- Maven
- JUnit 5

## 📂 Estructura del proyecto
- `entity/` → Entidades JPA (`Auto`, `Marca`, `Modelo`, `Tipo`).
- `repository/` → Repositorios con acceso a la base de datos.
- `service/` → Lógica de negocio.
- `resources/` → Archivos de configuración (`application.properties`) y dataset CSV inicial.
- `test/` → Pruebas unitarias con JUnit.

## ⚙️ Configuración
El proyecto utiliza **H2** como base de datos.  
La configuración por defecto guarda los datos en un archivo local (`./data/demo.mv.db`), pero se puede cambiar a modo memoria si se desea.

Archivo `application.properties`:

```properties
spring.datasource.url=jdbc:h2:file:./data/demo;DB_CLOSE_DELAY=-1;AUTO_SERVER=TRUE
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
