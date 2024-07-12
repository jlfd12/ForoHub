# ForoHub
Foro Hub creado como parte del Challenge ONE de Alura

![Logo de Alura](https://github.com/jlfd12/Conversor-de-Monedas/assets/67215733/9781ec64-0aac-4605-a1ea-947a25c926fc)

<h1 font-weight="bold">Indice</h1>
<ol>
  <li>Descripción</li>
  <li>Uso</li>
  <li>Características y Demostración</li>
  <li>Herramientas utilizadas</li>
  <li>Desarrollador del proyecto</li>
</ol>

<h1 font-weight="bold">Descripción</h1>
<p>Este proyecto muestra una API Rest de un foro. Con la ayuda de Insomnia (https://insomnia.rest/), una herramienta de interaccion con servicios de API, y MySQL (https://www.mysql.com/) para el manejo de base de datos se puede registrar un topico para un foro, además de actualizarlo, eliminarlo y visualizar alguno en especifico o todos los topicos registrados. Cada topico cuenta con id, titulo, mensaje, curso y fecha de creación. El hacer un request está protegido y requiere autorización por lo que cuenta además con la opción de crear un token de usuario para poder acceder a las funciones.
</p>

<h1>Uso</h1>

<p> 1.- Descargar la carpeta de archivos del repositorio y abrir en el IDE de preferencia, en este caso se usó IntelliJ (https://www.jetbrains.com/es-es/idea/). Descargar ademas MySQL y el administrador de API de su preferencia, por ejemplo, Postman o Insomnia, en este caso se hizo uso de Insomnia. 
En MySQL Workbench crear un nuevo schema con el nombre 'forohubapi' y agregar los datos de usuario y contraseña creados en MySQL a las variables de entorno en las propiedades del sistema de nuestra PC. Dentro de las propiedades de la API se puede visualizar la forma en que están señalados estos datos para agregarlos a las variables de entorno o editarlos. Además de la api key que por defecto está como 123456 pero puede modificarse o borrarse para mayor seguridad:</p>
  <li>spring.application.name=API Foro Hub</li>
  <li>spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver</li>
  <li>spring.datasource.url=jdbc:mysql://localhost:3306/forohubapi</li>
  <li>spring.datasource.username=${DB_USER}</li>
  <li>spring.datasource.password=${DB_PASSWORD}</li>
  <li>spring.datasource.host=${DB_HOST}</li>

  <li>api.security.token.secret=${JWT_SECRET:123456} <-------PUEDEN BORRARSE LOS NÚMEROS PARA MAYOR SEGURIDAD PERO ANTES DEBEN AGREGARSE A LAS VARIABLES DE ENTORNO.</li>
  
<p>
2.- A continuación se inicia el programa y se verifica que haya iniciado correctamente como se puede ver a continuación.
  
  ![logs](https://github.com/user-attachments/assets/8e139c6c-e914-47f2-a7d8-5b2e352e800c)

3.- Una vez iniciado se crea un request POST en Insomnia con el siguiente URL: http://localhost:8080/auth para obtener el token de autorización. En el body se pone en formato json los datos de acceso:
  {
    "email": "alumno@email.com",
    "contrasena": "123456"
  }

se da SEND y se obtiene el token.

  ![auth credenciales](https://github.com/user-attachments/assets/a42d6739-a828-4526-8ae7-c182f47e20ae)

  ![token](https://github.com/user-attachments/assets/0d01a4e5-b0e6-481d-8e5e-2acb50bac22c)

Inicialmente los datos de acceso mostrados son únicos para el funcionamiento de la API. Para agregar más, se crea una migración en la carpeta db.migrations siguiendo el formato de la migraciones anteriores:
  (Vx__insert-into-table-usuarios.sql)
  INSERT INTO `forohubapi`.`usuarios` (`id`, `email`, `contrasena`)
  VALUES ('1', 'alumno@email.com', '$2a$12$xWIsZNoC36wEwFFeuJn7B.Iq1fWCvcv3.Mc2jvF.ZqXInuTBKe9M2'); <----SE MODIFICAN LOS DATOS POR LOS NUEVOS.

La contraseña debe ser encriptada previamente en formato BCrypt para la migración y puede ser ingresada normalmente en el request en Insomnia.

4.- Una vez obtenido el token se puede crear un request de POST con el siguiente URL: http://localhost:8080/topicos, y se agrega el token en el header de auth, eligiendo Bearer Token del menú desplegable. En el body se agregan los siguientes campos en formato json y se agrega la información a registrar en el topico:
  {
     "idUsuario":"",
     "titulo":"",
     "mensaje":"",
     "curso":""
  }

  ![tokenHeader](https://github.com/user-attachments/assets/3b5a7478-e5e6-4c5a-bbcf-977c3292fd70)

  ![topicoRegistrado](https://github.com/user-attachments/assets/c10845d5-054f-431a-9ccc-a85a9ffd1f10)

Todos los campos son obligatorios por lo que el programa nos indicará que faltó alguno.

  ![campoVacio](https://github.com/user-attachments/assets/fa4c78a9-8ed6-4d19-9cb1-ca2546d1826c)

Igualmente si se repite el titulo y mensaje previamente registrados el programa nos lo hará saber.

  ![camposRepetidos](https://github.com/user-attachments/assets/6b3e2006-70d1-40cd-b345-fa880fca7a7c)

Si se hace algun request sin agregar el token el programa nos indicará un error.

  ![AuthToken403](https://github.com/user-attachments/assets/4b485775-ee4a-4454-88b1-831a6e71215b)

5.- Una vez registrados los topicos estos se pueden visualizar mediante un request de GET con el siguiente URL: http://localhost:8080/topicos o http://localhost:8080/topicos/{id} si es que se quiere visualizar alguno en especifico. Se agrega el token al header de auth y se da SEND.

  ![TopicosListados](https://github.com/user-attachments/assets/d75ec4f5-4fdc-4270-9c3a-48e801bd07b7)

6.-Para actualizar un topico se hace un request de PUT con el siguiente URL: http://localhost:8080/topicos{id} con el id del topico a actualizar, se agrega el token al header de auth y se agrega en el body el dato en formato json que se quiera actualizar.

  ![actualizarTopico](https://github.com/user-attachments/assets/f9c0aa2c-9a20-4b1d-901d-0f799553bc5a)

  ![topicoActualizado](https://github.com/user-attachments/assets/49b98b74-d419-441d-a215-916c062d8c73)

7.- Para eliminar alguno se hace un request de DELETE con el URL: http://localhost:8080/topicos/{id} con el id del topico a eliminar y el token en el header de auth.

  ![eliminarTopico](https://github.com/user-attachments/assets/a79d04c4-5f5c-457e-805a-6460d49ae9af)
</p>

<h1>Caracteristicas y Demostración</h1>

<p>Se inicia el programa en intelliJ y una vez inició correctamente se hacen los requests en Insomnia.
  
  ![requests](https://github.com/user-attachments/assets/c3b0cc38-457c-4a12-8bf0-24cc29cdce2d)

Primero se hace el request de POST de autenticación con los datos de usuario para obtener el token:
  {
    "email":"alumno@email.com",
    "contrasena":"123456"
  }

  ![auth credenciales](https://github.com/user-attachments/assets/cfebc91f-54cd-4816-ab9f-3c8c64627226)

  ![token](https://github.com/user-attachments/assets/50a9e232-e6dc-491e-8a24-f39ac859c7ac)

Una vez hecho esto se puede observar en la base de datos que el usuario ha sido registrado.

  ![dbUsuarios](https://github.com/user-attachments/assets/9b5b9837-01c2-4acf-b326-0fc0a2d4892b)

Ahora se crea el request de Registro para registrar los topicos y se agrega el token. Se agrega la información de los topicos y ahora se puede observar en el panel derecho que el topico ha sido registrado e igualmente en la base de datos.

  ![topicoRegistrado](https://github.com/user-attachments/assets/cbefc2c3-aad9-4ee0-9de2-898cc5d69a50)

  ![dbTopicos](https://github.com/user-attachments/assets/5a79c9aa-c377-41fb-b6b1-0728f2d5718b)

Mediante un request de GET y agregando el token al header podemos listar todos los topicos.

  ![TopicosListados](https://github.com/user-attachments/assets/3fe5d92a-4181-496f-9303-4c32179e08ae)

  ![ListarTopicos](https://github.com/user-attachments/assets/bb3a2202-c5c8-4887-8242-45a81f3551a1)

Al actualizar un topico mediante un request de PUT podemos observar nuevamente al listarlos como se actualiza la información.

  ![actualizarTopico](https://github.com/user-attachments/assets/397723fe-af8a-4dd2-a625-ebb0bbfa90af)

  ![topicoActualizado](https://github.com/user-attachments/assets/d2777746-394d-4b12-9122-66aed49db7ed)

Si eliminamos alguno con un request de DELETE al listarlos y dentro de la base de datos podemos observar que el topico desaparece.

  ![topicoEliminado](https://github.com/user-attachments/assets/2365b899-1bf3-4e70-873b-a243ee048740)

  ![dbTopicoEliminado](https://github.com/user-attachments/assets/fa846ad3-cca4-46f4-88c4-ae206340c905)
</p>


<h1 font-weight="bold">Herramientas utilizadas</h1>

![java](https://github.com/jlfd12/Conversor-de-Monedas/assets/67215733/4437322b-70a5-4c58-8842-99f4284a8fab)

JAVA

![download](https://github.com/user-attachments/assets/aecbfccf-1e86-4f04-bd44-6745ff02df19)

SPRING BOOT

![download](https://github.com/user-attachments/assets/c982d376-aefc-4f30-b18e-10c3dfa7ec13)

INTELLI J

![download (1)](https://github.com/user-attachments/assets/f4d57bc1-2a76-401c-86ad-e2f6371ae201)

INSOMNIA

![download (1)](https://github.com/user-attachments/assets/354fdf05-8676-432f-922d-8a3d8452ffb4)

MYSQL


<h1 font-weight="bold">Desarrollador del proyecto</h1>

Joanna L. Fernandez

![joannafd](https://github.com/jlfd12/Conversor-de-Monedas/assets/67215733/2d1de9ef-b76b-44a1-ac8c-2f60107de2f7)


![Badge-Spring](https://github.com/user-attachments/assets/f2601054-7086-49e6-a540-2266cb3e975a)



