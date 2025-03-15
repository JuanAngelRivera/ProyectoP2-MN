package com.example.proyecto2pmn;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.Scene;

public class Info extends Stage
{
    Scene escena;
    VBox vbox;
    public void crearUI()
    {
        Label contenido = new Label("Programa creado por alumnos del Tecnológico Nacional de México en Celaya para la" +
                " materia de métodos numéricos\nIntegrantes:\n-Arriaga Vázquez Mariana\n-González Cervantes Esteban\n" +
                "-Hernández Luna Diego Leonardo\n-Rivera Torres Juan Angel\n-Cruz Silva Paniagua Jennifer María");
        contenido.setWrapText(true);
        vbox = new VBox(contenido);
        escena = new Scene(vbox, 500, 500);
    }

    public Info()
    {
        crearUI();
        this.setTitle("Información del programa");
        this.setResizable(false);
        this.setScene(escena);
        this.show();
    }
}
