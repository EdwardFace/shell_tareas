package com.edward.tareas;

import com.edward.tareas.constants.Constantes;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.shell.command.annotation.CommandScan;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

@SpringBootApplication
@CommandScan
public class TareasApplication {

	public static void main(String[] args) throws IOException {
        crearArchivo();
		SpringApplication.run(TareasApplication.class, args);
	}
    private static void crearArchivo() throws IOException {
        File json = new File(Constantes.NOMBRE_ARCHIVO);
        if (json.createNewFile()){
            String listaInicial = "{ \n \"listaTareas\": [] \n}";
            FileWriter fw = new FileWriter(json);
            fw.write(listaInicial);
            fw.close();
        }



    }
}
