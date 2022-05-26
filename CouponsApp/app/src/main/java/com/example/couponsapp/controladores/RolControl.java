package com.example.couponsapp.controladores;

import android.content.ContentValues;
import android.content.Context;

import com.example.couponsapp.dbHelper.Control;
import com.example.couponsapp.modelos.Rol;

public class RolControl extends Control {
    public RolControl(Context context) {
        super(context);
    }

    public long insertRol(Rol rol){
        this.abrir();
        long id_res;
        ContentValues current = new ContentValues();
        current.put("NOMBRE_ROL", rol.getNombre_rol());
        id_res = db.insert("ROL", null, current);
        this.cerrar();
        return  id_res;
    }
}
