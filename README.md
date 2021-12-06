# Proyecto Final Seguridad
## By: Jose David Jimenez, Alejandro Martinez y Cristian Lasso




## Features

- Login para administradores y usuarios normales.
- Método de cifrado para las contraseñas (PBKDF2) .
- El administrador puede eliminar usuarios.
- EL administrador puede colocar vacia la contraseña de un usuario.
- El usuario puede cambiar su contraseña
- El usuario puede ver su última hora de ingreso.

## Tech

Tecnologías utilizadas para el desarrollo del proyecto:

- [Java] 
- [JavaFX] 
- [SQL Server]


## Cómo se hizo?

Primero creamos las vistas que ibamos a utilizar en el programa SceneBuilder.

```sh
Son archivos FXML.
Las vistas fueron el login, la vista del administrador y la vista del usuario.
El lenguaje que se utiliza es el de JavaFX
```

Después creamos la base de datos en SQL Server.

```sh
La tabla se llama usuario
Sus atributos son: El id del usuario, su nombre de usuario, su privilegio y su contraseña cifrada.
```
Para finalizar realizamos el código faltante y necesario en Java.
```sh
Se integraron los métodos de cifrado.
Se implemento la conexión entre el programa y la base de datos.
Se hicieron los controladores de las vistas. Además, se hicieron las funciones dentro de cada uno para cumplir con los requerimientos especificados.
Se manejan todas las excepciones posibles dentro del programa.
```

## Dificultades en el desarrollo del proyecto.

1. Configuración de la base de datos.
    
    Fue difícil primero crear la base de datos, porque ningún programa nos queria funcionar. Después se nos complico la conexión, debido a que no sabiamos que error estaba sucediendo.
2. Verificar que los algoritmos de cifrado fueran acorde a lo que buscabamos.
    
    Ya que teniamos que cumplir con unas condiciones dadas, fue difícil la búsqueda de algunos métodos que nos ayudarán al funcionamiento del programa.
3. Pasar datos entre vistas del programa.

    Se nos hizo difícil buscar la forma de pasar unos datos que necesitabamos en las siguientes vistas, ya que estas eran independientes, y esos datos solo se podian conseguir de la primera vista.

## Conclusiones

En conclusión, primeramente queremos hablar sobre las mejoras que se le pueden realizar al proyecto. Por una parte, mejorar las validaciones que realizamos sobre la integridad de los datos, debido a que lo hacemos con unos datos ya registrados en la base de datos y que sabemos como deben ir. Por otra parte, el mejoramiento en términos gráficos de la aplicación, ya que el diseño que maneja actualmente es muy simple. Además, se puede profundizar mejor en el uso de las APIs criptográficas que nos brinda Java. Por último, como reflexiones propias, creemos que este proyecto nos enseño que el querer darle seguridad a los datos de nuestros usuarios es muy importante, y sobre todo, no es tan difícil como se ve en las peliculas o como dicen algunas personas, si se requiere un esfuerzo para verificar algunas cosas pero en general es bastante práctico el implementar seguridad en el código.
