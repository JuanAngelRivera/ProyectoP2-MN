package com.example.proyecto2pmn;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.Scene;

public class VentanaError extends Stage
{
    Scene escena;
    VBox vbox;
    public void crearUI(String texto)
    {
        Label titulo = new Label("¡Error!");
        Label contenido = new Label();
        if(texto != null)
            contenido.setText(texto);
        else
            contenido = new Label("Hay un error en la toma de las ecuaciones\nverifica que esté escrita correctamente");
        vbox = new VBox(titulo, contenido);
        escena = new Scene(vbox);
    }

    public VentanaError(String texto)
    {
        crearUI(texto);
        this.setTitle("Error");
        this.setResizable(false);
        this.setScene(escena);
        this.show();
    }
}
