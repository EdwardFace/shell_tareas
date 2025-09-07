package com.edward.tareas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

@SpringBootApplication
public class TareasApplication {

	public static void main(String[] args) throws IOException {
        crearArchivo();
		SpringApplication.run(TareasApplication.class, args);
	}
    private static void crearArchivo() throws IOException {
        File json = new File("datos.json");
        if (!json.exists()){
            String listaInicial = "{ \n \"listaTareas\": [] \n}";
            json.createNewFile();

            FileWriter fw = new FileWriter(json);
            fw.write(listaInicial);
            fw.close();
        }



    }
}
