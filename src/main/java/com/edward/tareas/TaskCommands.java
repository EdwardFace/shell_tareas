package com.edward.tareas;

import com.edward.tareas.constants.Constantes;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.shell.command.annotation.Command;

import java.io.*;
import java.time.LocalDateTime;
import java.util.logging.Logger;


@Command(command = "task-cli", group = "Comando de task cli")
public class TaskCommands {
    Logger log = Logger.getLogger(String.valueOf(TaskCommands.class));

    @Command(command = "add", description = "Comando para agregar una tarea")
    public String addTask(String description) {
        try {

            StringBuilder sb = cargarListaTareas();

            JSONObject datos = new JSONObject(sb.toString());

            //obtenemos la lista del objeto json
            JSONArray listaTareas = datos.getJSONArray("listaTareas");
            int newId = 0;
            if(listaTareas.isEmpty()) {
                newId++;
            }else {

                JSONObject tarea = listaTareas.getJSONObject(listaTareas.length()-1);
                newId = tarea.getInt("id") + 1;
            }

            //Creamos el nuevo objeto
            JSONObject nuevaTarea = new JSONObject();
            nuevaTarea.put("id", newId);
            nuevaTarea.put("descripcion", description);
            nuevaTarea.put("status", "todo");
            nuevaTarea.put("createAt", LocalDateTime.now().toString());
            nuevaTarea.put("updateAt", "");

            listaTareas.put(nuevaTarea);

            //guardamos en el archivo json
            try (FileWriter fw = new FileWriter(Constantes.NOMBRE_ARCHIVO)) {
                fw.write(datos.toString(4));
                fw.flush();
            }
            return "Tarea añadida exitosamente (ID: %d)".formatted(newId);


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Command( command = "update", description = "Actualiza una tarea dado su id")
    public String updateTask(int id, String newDescription) {
       try {
           log.info("[cli update] Cargando registro del archivo json");
           StringBuilder datosJson = cargarListaTareas();
           JSONObject datos = new JSONObject(datosJson.toString());
           log.info("[cli update] Cargando lista de tareas");
           JSONArray listaTareas = datos.getJSONArray("listaTareas");
           JSONObject tareaABuscar;

           log.info("[cli update] Iniciando busqueda de registro");
           boolean found = false;
           for ( int i = 0; i < listaTareas.length(); i++ ) {
               tareaABuscar = listaTareas.getJSONObject(i);
               if (tareaABuscar.getInt("id") == id){
                   log.info("[cli update] Registro encontrado");
                   tareaABuscar.put("descripcion", newDescription);
                   tareaABuscar.put("updateAt", LocalDateTime.now().toString());
                   found = true;
                   break;
               }
           }
           if (!found) {
               log.warning("[cli update] Registro no encontrado");
                return "No se encontro la tarea %d a actualizar".formatted(id);
           }

           //guardamos en el archivo json
           try (FileWriter fw = new FileWriter(Constantes.NOMBRE_ARCHIVO)) {
               log.info("[cli update] Guardando cambios a archivo json");
               fw.write(datos.toString(4));
               fw.flush();
           }
           return "Tarea %d actualizada correctamente".formatted(id);

       }catch (IOException ex){
           throw new RuntimeException(ex);
       }
    }

    @Command(command = "delete", description = "elimina una tarea dado su id")
    public String deleteTask(int id) {
        try{
            log.info("[cli delete] Cargando registro del archivo json");
            StringBuilder datosJson = cargarListaTareas();
            JSONObject datos = new JSONObject(datosJson.toString());
            log.info("[cli delete] Cargando lista de tareas");
            JSONArray listaTareas = datos.getJSONArray("listaTareas");
            JSONObject tareaABuscar;
            log.info("[cli update] Iniciando busqueda de registro");
            boolean found = false;
            for ( int i = 0; i < listaTareas.length(); i++ ) {
                tareaABuscar = listaTareas.getJSONObject(i);
                if (tareaABuscar.getInt("id") == id){
                    log.info("[cli delete] Registro encontrado");
                    listaTareas.remove(i);
                    found = true;
                    break;
                }
            }
            if (!found) {
                log.warning("[cli delete] Registro no encontrado");
                return "No se encontro la tarea %d a eliminar".formatted(id);
            }

            //guardamos en el archivo json
            try (FileWriter fw = new FileWriter(Constantes.NOMBRE_ARCHIVO)) {
                log.info("[cli update] Guardando cambios a archivo json");
                fw.write(datos.toString(4));
                fw.flush();
            }
            return "Tarea %d eliminada correctamente".formatted(id);
        }catch (IOException ex){
            throw new RuntimeException(ex);
        }


    }

    @Command(command = "list", description = "lista todas las tareas")
    public void listAll(){
        try{
            StringBuilder sb = cargarListaTareas();
            JSONObject datos = new JSONObject(sb.toString());
            log.info("[cli delete] Cargando lista de tareas");
            JSONArray listaTareas = datos.getJSONArray("listaTareas");

            for (int i = 0; i < listaTareas.length(); i++){
                System.out.println("------------------------------------");
                JSONObject tarea = listaTareas.getJSONObject(i);
                System.out.println("Id: "+ tarea.getInt("id"));
                System.out.println("Descripcion: "+ tarea.getString("descripcion"));
                System.out.println("Status: "+ tarea.getString("status"));
                System.out.println("Ultima actualizacion: "+ tarea.getString("updateAt"));
                System.out.println("Creado en: "+ tarea.getString("createAt"));
                System.out.println("------------------------------------");
            }

        }catch (IOException ex){
            throw new RuntimeException(ex);
        }

    }

    @Command(command = "mark-in-progress", description = "Marca una tarea como 'en progreso'")
    public String mark_in_progress(int id){
        try {
            StringBuilder sb = cargarListaTareas();
            JSONObject datos = new JSONObject(sb.toString());
            log.info("[cli mark-in-progress] Cargando lista de tareas");
            JSONArray listaTareas = datos.getJSONArray("listaTareas");
            JSONObject tareaABuscar;
            boolean foundMark = false;
            for ( int i = 0; i < listaTareas.length(); i++ ) {
                tareaABuscar = listaTareas.getJSONObject(i);
                if (tareaABuscar.getInt("id") == id){
                    log.info("[cli mark-in-progress] Registro encontrado");
                    tareaABuscar.put("status", "mark-in-progress");
                    foundMark = true;
                    break;
                }
            }

            if(!foundMark){
                log.warning("[cli mark-in-progress] Registro no encontrado");
                return "No se encontro la tarea %d a actualizar".formatted(id);
            }

            //guardamos en el archivo json
            try (FileWriter fw = new FileWriter(Constantes.NOMBRE_ARCHIVO)) {
                log.info("[cli mark-in-progress] Guardando cambios a archivo json");
                fw.write(datos.toString(4));
                fw.flush();
            }
            return "Tarea %d actualizada correctamente".formatted(id);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Command(command = "mark-done", description = "Marca una tarea como 'en progreso'")
    public String mark_in_done(int id){
        try {
            StringBuilder sb = cargarListaTareas();
            JSONObject datos = new JSONObject(sb.toString());
            log.info("[cli mark-in-done] Cargando lista de tareas");
            JSONArray listaTareas = datos.getJSONArray("listaTareas");
            JSONObject tareaABuscar;
            boolean foundMark = false;
            for ( int i = 0; i < listaTareas.length(); i++ ) {
                tareaABuscar = listaTareas.getJSONObject(i);
                if (tareaABuscar.getInt("id") == id){
                    log.info("[cli mark-in-done] Registro encontrado");
                    tareaABuscar.put("status", "done");
                    foundMark = true;
                    break;
                }
            }

            if(!foundMark){
                log.warning("[cli mark-in-done] Registro no encontrado");
                return "No se encontro la tarea %d a actualizar".formatted(id);
            }

            //guardamos en el archivo json
            try (FileWriter fw = new FileWriter(Constantes.NOMBRE_ARCHIVO)) {
                log.info("[cli mark-in-done] Guardando cambios a archivo json");
                fw.write(datos.toString(4));
                fw.flush();
            }
            return "Tarea %d actualizada correctamente".formatted(id);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private StringBuilder cargarListaTareas() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(Constantes.NOMBRE_ARCHIVO));
        StringBuilder sb = new StringBuilder();
        String line;

        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        br.close();
        return sb;
    }

}
