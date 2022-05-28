package com.example.couponsapp.controladores;

import android.content.ContentValues;
import android.content.Context;

import com.example.couponsapp.dbHelper.Control;
import com.example.couponsapp.modelos.Direccion;

public class DireccionControl extends Control {
    public DireccionControl(Context context) {
        super(context);
    }

    public long insertDireccion(Direccion direccion){
        this.abrir();
        long id_res;
        ContentValues current = new ContentValues();
        current.put("MUNICIPIO", direccion.getMunicipio());
        current.put("CALLE", direccion.getCalle());
        current.put("NUMERO_LOCAL", direccion.getNumero_local());
        id_res = db.insert("DIRECCION", null, current);
        this.cerrar();
        return id_res;
    }
}
