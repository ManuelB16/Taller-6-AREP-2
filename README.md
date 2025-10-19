## Taller 6 Seguridad 

## Descripción del laboratorio
En este laboratorio vamos a garantizar unos criterios de seguridad para porteger la aplicación que elaboramos y desplegamos en el laboratorio anterior.

---
### Prerrequisitos 🧰

* [Maven](https://maven.apache.org/): Es una herramienta de comprensión y gestión de proyectos de software. Basado en el concepto de modelo de objetos de proyecto (POM), Maven puede gestionar la construcción, los informes y la documentación de un proyecto desde una pieza de información central.
* [Git](https://learn.microsoft.com/es-es/devops/develop/git/what-is-git): Es un sistema de control de versiones distribuido, lo que significa que un clon local del proyecto es un repositorio de control de versiones completo. Estos repositorios locales plenamente funcionales permiten trabajar sin conexión o de forma remota con facilidad.

---
 
### Tecnologías usadas 👨‍💻

* Frontend: HTML, JavaScript (Fetch API para comunicación con el backend).
* Backend: Spring Boot (API RESTful con JPA/Hibernate).
* Base de Datos: MySQL (gestión de datos con persistencia).
* Despliegue: AWS (2 EC2 para backend y base de datos).

---

## Disposición del directorio de archivos 🗂️

```text
LAB5AREP/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── edu/eci/arep/lab5arep/Backend/
│   │   │       ├── controller/
│   │   │       │   └── PropertyController.java
│   │   │       ├── exception/
│   │   │       │   └── ResourceNotFoundException.java
│   │   │       ├── model/
│   │   │       │   └── Property.java
│   │   │       ├── repository/
│   │   │       │   └── PropertyRepository.java
│   │   │       ├── service/
│   │   │       │   └── PropertyService.java
│   │   │       └── Server.java
│   │   └── resources/
│   │       ├── www/
│   │       │   ├── agregar.html
│   │       │   ├── buscar.html
│   │       │   ├── editar.html
│   │       │   └── index.html
│   │       ├── script.js
│   │       ├── styles.css
│   │       └── application.properties
├── test/
├── target/
├── .gitignore
├── .gitattributes
├── docker-compose.yml
├── Dockerfile
└── pom.xml
```

| Carpeta / Archivo                                                                      | Descripción                                          |
| -------------------------------------------------------------------------------------- | ---------------------------------------------------- |
| `src/main/java/edu/eci/arep/lab5arep/Backend/controller/PropertyController.java`       | Controlador principal del CRUD de propiedades        |
| `src/main/java/edu/eci/arep/lab5arep/Backend/exception/ResourceNotFoundException.java` | Manejo de excepciones para recursos no encontrados   |
| `src/main/java/edu/eci/arep/lab5arep/Backend/model/Property.java`                      | Entidad que representa una propiedad inmobiliaria    |
| `src/main/java/edu/eci/arep/lab5arep/Backend/repository/PropertyRepository.java`       | Interfaz de persistencia con JPA                     |
| `src/main/java/edu/eci/arep/lab5arep/Backend/service/PropertyService.java`             | Lógica de negocio para el manejo de propiedades      |
| `src/main/java/edu/eci/arep/lab5arep/Backend/Server.java`                              | Punto de entrada del backend con Spring Boot         |
| `src/main/resources/www/*.html`                                                        | Vistas del frontend (agregar, buscar, editar, index) |
| `src/main/resources/script.js`                                                         | Lógica de interacción del frontend con el backend    |
| `src/main/resources/styles.css`                                                        | Estilos del frontend                                 |
| `src/main/resources/application.properties`                                            | Configuración del backend (DB, puertos, etc.)        |
| `docker-compose.yml`                                                                   | Configuración para despliegue con Docker             |
| `Dockerfile`                                                                           | Imagen del backend                                   |
| `pom.xml`                                                                              | Dependencias y configuración del proyecto Maven      |
| `.gitignore`, `.gitattributes`                                                         | Archivos de control de Git                           |
| `test/`, `target/`                                                                     | Carpeta de pruebas y compilación                     |


---

### Arquitectura 💻

### 💻 Arquitectura del Sistema

La aplicación está diseñada bajo una arquitectura **segura, modular y escalable**, desplegada en **AWS**.  
Se organiza en **tres capas principales**, cada una con responsabilidades bien definidas:

- **🖥️ Servidor Apache (Frontend):**  
  Encargado de servir los archivos estáticos (**HTML, CSS y JavaScript**) al cliente.  
  Proporciona la interfaz de usuario y gestiona la comunicación con el backend mediante solicitudes HTTP seguras (HTTPS).

- **⚙️ Spring Boot (Backend):**  
  Implementa la lógica de negocio y expone **APIs RESTful** para las operaciones del sistema.  
  Procesa las peticiones del frontend, se conecta con la base de datos y aplica medidas de seguridad como autenticación y cifrado.

- **🗄️ Base de Datos MySQL:**  
  Gestiona la persistencia de la información.  
  Almacena los datos de usuarios, propiedades y demás entidades del sistema, garantizando integridad y disponibilidad.


### 🧩 Componentes

#### 1️⃣ Cliente Web
- Se comunica con el backend a través de **AJAX**.  
- Es servido mediante un **servidor Apache**.  
- El servidor Apache se aloja en una **instancia EC2**.  
- Configurado con **TLS (Let's Encrypt)** para conexiones seguras mediante **HTTPS**.  
- El frontend se sirve desde la ruta: `/var/www/html`.

#### 2️⃣ Spring Boot Backend
- Implementado en una **instancia EC2** separada.  
- Expone **APIs REST** en el puerto **8080**.  
- Se conecta con la **base de datos MySQL**.  
- Configurado con **HTTPS** utilizando un certificado **PKCS12 (.p12)**.

#### 3️⃣ Base de Datos MySQL
- Ejecutada en una **instancia EC2 dedicada**.  
- Solo accesible desde el **backend**, controlado mediante **Security Groups** de AWS.

### 🛡️ Estrategias de Seguridad en AWS

#### 1️⃣ Seguridad en la Red

- **Apache**
  - Permite tráfico **HTTP/HTTPS (puertos 80 y 443)** desde cualquier IP (`0.0.0.0/0`).  
  - No permite conexiones directas desde el backend o la base de datos.

- **Backend**
  - Solo acepta conexiones en el **puerto 8080** provenientes del servidor Apache.  
  - Acceso restringido mediante **Security Group**, limitado al SG del frontend.

- **Base de Datos**
  - Acepta conexiones únicamente en el **puerto 3306** desde el backend.  
  - No posee IP pública, siendo accesible solo dentro de la **VPC de AWS**.

---

#### 2️⃣ Certificados SSL/TLS

- **Apache:** Configurado con **Let’s Encrypt (Certbot)** para conexiones HTTPS seguras.  
- **Backend:** Utiliza un certificado **PKCS12 (.p12)** generado con `keytool` para tráfico seguro en el puerto **8443**.

---

#### 3️⃣ Autenticación

- **Spring Security:** Configurado con credenciales definidas en `application.properties`.  
  El inicio de sesión se realiza mediante un **formulario HTML** en el frontend, garantizando autenticación segura.

### Instalación e instrucciones de despliegue 🚀​🌐​

1) Debemos clonar el repositorio
```
git clone https://github.com/ManuelB16/Taller-6-AREP-2
```
2) Una vez clonamos, accedemos al directorio
```
cd Lab6_Arep
```

## Evidencias

Configuracion del aplication properties

<img width="1510" height="417" alt="image" src="https://github.com/user-attachments/assets/f0e2b99f-04a3-414f-9c38-046f68b9ad68" />


Se configuro un dominio sobre las ips publicas de las intancias en una aplicacion llamada DUCKDNS

<img width="843" height="312" alt="image" src="https://github.com/user-attachments/assets/3cbebdc6-ad3f-42d9-8154-6495f4e13c14" />


se hizo una prueba en local, para garantizar que todo estuviera funcionando correctamente antes de realizar el despliegue


https://github.com/user-attachments/assets/1f176e89-a359-4ec0-a1c9-dde3f8b61567

Se configuraron 3 instancias a  las cuales se les asigno una ip elastica, para evitar cambios cuando volviamos a cargar AWS

![image](https://github.com/user-attachments/assets/b9e37bdc-bdd0-4fe4-b1ce-9186f5ef2f16)

Se cargan los archivos dentro del servidor de apache

![Captura de pantalla 2025-03-13 164409](https://github.com/user-attachments/assets/1a7c7eb6-6370-4a28-99c9-4ff98074783b)

verificamos con la herramienta nslookup

![image](https://github.com/user-attachments/assets/3ed16a07-00fc-41fa-a8df-dff4292cf46c)

verificamos 

![Captura de pantalla 2025-03-13 164756](https://github.com/user-attachments/assets/e353d61f-420f-4b0e-a643-9cac159854fd)

se descargar certbot para garantizar conexiones seguras 

![image](https://github.com/user-attachments/assets/b09d83d9-70f9-4bbc-ac35-3b0f6fa8ef3a)

se crea un virtual host para servir el fronten en la ruta asignada 

![image](https://github.com/user-attachments/assets/4e270091-ff5a-4f71-b014-c0fc0e8d9a07)

obtenemos el certificado

![image](https://github.com/user-attachments/assets/e0dd89fd-bbf5-4d5c-aaee-1d736f6b2a33)

verificamos en el navegador 

![image](https://github.com/user-attachments/assets/10c9d5ab-a3d6-4cb3-b760-8e5d9786ee90)

![image](https://github.com/user-attachments/assets/95d2fa6a-b727-41a1-abf8-fbb13a68df2b)



se sube el archivo .jar de la aplicación al backend y se corre

![Captura de pantalla 2025-03-13 203203](https://github.com/user-attachments/assets/ffa32331-a97e-4226-a16c-750a4ffce4a9)

se crean los usuarios con privilegios en la base de datos 

![image](https://github.com/user-attachments/assets/b5fe3e99-07a3-42ff-8bac-01f187e9bbdf)

Nota: Tuve problemas con el despliegue y las conexiones entre base de datos y backend
No se proporciona video. Gracias 

### Construido con

* [Maven](https://maven.apache.org/): Es una herramienta de comprensión y gestión de proyectos de software. Basado en el concepto de modelo de objetos de proyecto (POM), Maven puede gestionar la construcción, los informes y la documentación de un proyecto desde una pieza de información central.

* [Git](https://learn.microsoft.com/es-es/devops/develop/git/what-is-git): Es un sistema de control de versiones distribuido, lo que significa que un clon local del proyecto es un repositorio de control de versiones completo. Estos repositorios locales plenamente funcionales permiten trabajar sin conexión o de forma remota con facilidad.

* [GitHub](https://platzi.com/blog/que-es-github-como-funciona/): Es una plataforma de alojamiento, propiedad de Microsoft, que ofrece a los desarrolladores la posibilidad de crear repositorios de código y guardarlos en la nube de forma segura, usando un sistema de control de versiones llamado Git.

* [Java -17](https://www.cursosaula21.com/que-es-java/): Es un lenguaje de programación y una plataforma informática que nos permite desarrollar aplicaciones de escritorio, servidores, sistemas operativos y aplicaciones para dispositivos móviles, plataformas IoT basadas en la nube, televisores inteligentes, sistemas empresariales, software industrial, etc.

* [JavaScript](https://universidadeuropea.com/blog/que-es-javascript/): Es un lenguaje de programación de scripts que se utiliza fundamentalmente para añadir funcionalidades interactivas y otros contenidos dinámicos a las páginas web.

* [HTML](https://aulacm.com/que-es/html-significado-definicion/): Es un lenguaje de marcado de etiquetas que se utiliza para crear y estructurar contenido en la web. Este lenguaje permite definir la estructura y el contenido de una página web mediante etiquetas y atributos que indican al navegador cómo mostrar la información.

* [CSS](https://www.hostinger.co/tutoriales/que-es-css): Es un lenguaje que se usa para estilizar elementos escritos en un lenguaje de marcado como HTML.

* [Visual Studio Code](https://openwebinars.net/blog/que-es-visual-studio-code-y-que-ventajas-ofrece/): Es un editor de código fuente desarrollado por Microsoft. Es software libre y multiplataforma, está disponible para Windows, GNU/Linux y macOS.

## Autor

* **[Manuel Felipe Barrera](https://github.com/ManuelB16)**

## Licencia
**©** Manuel Felipe Barrera. Estudiante de Ingeniería de Sistemas de la Escuela Colombiana de Ingeniería Julio Garavito

