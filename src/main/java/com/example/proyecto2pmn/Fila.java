package com.example.proyecto2pmn;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.ArrayList;
import java.util.List;

public class Fila
{
    private final List<SimpleStringProperty> valores;

    Fila(List <String> valores)
    {
        this.valores = valores.stream().map(SimpleStringProperty::new).toList();
    }
    public SimpleStringProperty obtenerValores(int index)
    {
        return valores.get(index);
    }
}
