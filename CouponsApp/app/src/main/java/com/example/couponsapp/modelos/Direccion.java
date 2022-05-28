package com.example.couponsapp.modelos;

public class Direccion {
    private int id_direccion;
    private String municipio;
    private String calle;
    private String numero_local;

    public Direccion() {
    }

    public Direccion(int id_direccion, String municipio, String calle, String numero_local) {
        this.id_direccion = id_direccion;
        this.municipio = municipio;
        this.calle = calle;
        this.numero_local = numero_local;
    }

    public int getId_direccion() {
        return id_direccion;
    }

    public void setId_direccion(int id_direccion) {
        this.id_direccion = id_direccion;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getNumero_local() {
        return numero_local;
    }

    public void setNumero_local(String numero_local) {
        this.numero_local = numero_local;
    }
}
