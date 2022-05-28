package com.example.couponsapp.controladores;

import android.content.ContentValues;
import android.content.Context;

import com.example.couponsapp.dbHelper.Control;
import com.example.couponsapp.modelos.Restaurante;

public class RestauranteControl extends Control {
    public RestauranteControl(Context context) {
        super(context);
    }

    public long insertRestaurante(Restaurante restaurante){
        this.abrir();
        long id_res;
        ContentValues current = new ContentValues();
        current.put("ID_DIRECCION", restaurante.getDireccion().getId_direccion());
        current.put("NOMBRE_RESTAURANTE", restaurante.getNombre_restaurante());
        id_res = db.insert("RESTAURANTE", null, current);
        this.cerrar();
        return id_res;
    }
}
