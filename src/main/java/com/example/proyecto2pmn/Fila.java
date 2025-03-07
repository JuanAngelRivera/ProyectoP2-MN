package com.example.proyecto2pmn;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.ArrayList;
import java.util.List;

public class Fila
{
    private final List<SimpleStringProperty> valores;

    Fila(String[] valores)
    {
        this.valores = new ArrayList<>();
        for (int i = 0; i < valores.length; i++)
        {
            this.valores.add(new SimpleStringProperty(valores[i]));
        }
    }
}
