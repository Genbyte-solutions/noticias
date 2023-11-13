Api-Rest con Java Springboot:

-La pagina necesita para funcionar una BD "noticias" instanciada en MySql corriendo en el puerto 3306, con usuario root, contrasena root.
-La api se corre abriendo el proyecto, y corriendo spring-boot run, que la levanta en el puerto localhost:8080.
-La api solo da acceso al puerto en que esta corriendo actualmente el front con vite.
-La api rutea sus metodos en la carpeta controladores.
-Si la BD esta vacia se pueden cargar 10 objetos de cada entidad con la ruta http://localhost:8080/api/iniciar
