package com.example.couponsapp.modelos;

public class Direccion {
    private int id_direccion;
    private String ubicacion;


    public Direccion() {
    }

    public Direccion(int id_direccion,  String ubicacion) {
        this.id_direccion = id_direccion;
        this.ubicacion = ubicacion;
    }

    public int getId_direccion() {
        return id_direccion;
    }

    public void setId_direccion(int id_direccion) {
        this.id_direccion = id_direccion;
    }



    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }


}
