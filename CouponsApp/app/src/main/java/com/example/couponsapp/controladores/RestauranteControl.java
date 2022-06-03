package com.example.couponsapp.controladores;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.couponsapp.dbHelper.Control;
import com.example.couponsapp.modelos.Restaurante;

import java.util.ArrayList;

public class RestauranteControl extends Control {
    public RestauranteControl(Context context) {
        super(context);
    }

    public long insertRestaurante(Restaurante restaurante){
        this.abrir();
        long id_res;
        ContentValues current = new ContentValues();
        current.put("DIRECCION", restaurante.getDireccion());
        current.put("NOMBRE_RESTAURANTE", restaurante.getNombre_restaurante());
        id_res = db.insert("RESTAURANTE", null, current);
        this.cerrar();
        return id_res;
    }

    public ArrayList<Restaurante> traerRestaurante(String id_restaurante){
        this.abrir();
        String[] args = {id_restaurante};
        ArrayList<Restaurante> list = new ArrayList<>();
        Restaurante restaurante;
        //Cursor result = db.rawQuery("SELECT * FROM RESTAURANTE", null);
        Cursor results = db.rawQuery("SELECT RESTAURANTE.ID_RESTAURANTE, RESTAURANTE.DIRECCION, RESTAURANTE.NOMBRE_RESTAURANTE FROM\n" +
                "RESTAURANTE WHERE RESTAURANTE.ID_RESTAURANTE = ?", args);
        if(results.moveToFirst()){
            do {
                restaurante = new Restaurante(
                        results.getInt(0),
                        results.getString(2),
                        results.getString(1)
                );
                list.add(restaurante);
            }while (results.moveToNext());
        }
        results.close();
        this.cerrar();
        return list;
    }

    public ArrayList<Restaurante> all(){
        this.abrir();
        ArrayList<Restaurante> list = new ArrayList<>();
        Restaurante restaurante;
        //Cursor result = db.rawQuery("SELECT * FROM RESTAURANTE", null);
        Cursor results = db.rawQuery("SELECT RESTAURANTE.ID_RESTAURANTE, RESTAURANTE.DIRECCION, RESTAURANTE.NOMBRE_RESTAURANTE FROM\n" +
                "RESTAURANTE", null);
        if(results.moveToFirst()){
            do {
                restaurante = new Restaurante(
                        results.getInt(0),
                        results.getString(2),
                        results.getString(1)
                );
                list.add(restaurante);
            }while (results.moveToNext());
        }
        results.close();
        this.cerrar();
        return list;
    }
}
