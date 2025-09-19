
# CLI de gestión de tareas con spring shell

He contruido una interfaz de linea de comandos para gestionar tareas usando la dependencia de spring shell y el filesystem de java para la lectura y escritura en archivo json


## Autores

- [@EdwardFace](https://www.github.com/EdwardFace)


## Documentación

[Documentation](https://spring.io/projects/spring-shell)


## Herramientas utilizadas

- Spring boot 3.5.5
- Java 17
- Gradle
- Git para control de versiones


## Ejemplo de uso



```shell
  # Comando para Crear una tarea
  task-cli add "Buy groceries"
  # Output: Tarea añadida exitosamente (ID: 1)

  # Actualizando una tarea por su id y cambiando su descripción
  task-cli update 1 "Buy groceries and cook dinner"
  # Eliminando una tarea dado su id
  task-cli delete 1

  # Marcando una tarea dado su id
  task-cli mark-in-progress 1
  task-cli mark-done 1

  # Listando todas las tareas
  task-cli list

  # Listando las tareas por status
  task-cli list done
  task-cli list todo
  task-cli list in-progress
```



