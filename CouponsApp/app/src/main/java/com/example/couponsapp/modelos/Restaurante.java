package com.example.couponsapp.modelos;

public class Restaurante {
    private int id_restaurante;
    private String nombre_restaurante;
    private Direccion direccion;

    public Restaurante() {
    }

    public Restaurante(int id_restaurante, String nombre_restaurante, Direccion direccion) {
        this.id_restaurante = id_restaurante;
        this.nombre_restaurante = nombre_restaurante;
        this.direccion = direccion;
    }

    public int getId_restaurante() {
        return id_restaurante;
    }

    public void setId_restaurante(int id_restaurante) {
        this.id_restaurante = id_restaurante;
    }

    public String getNombre_restaurante() {
        return nombre_restaurante;
    }

    public void setNombre_restaurante(String nombre_restaurante) {
        this.nombre_restaurante = nombre_restaurante;
    }

    public Direccion getDireccion() {
        return direccion;
    }

    public void setDireccion(Direccion direccion) {
        this.direccion = direccion;
    }
}
