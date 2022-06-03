package com.example.couponsapp.controladores;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.couponsapp.dbHelper.Control;
import com.example.couponsapp.modelos.Direccion;
import com.example.couponsapp.modelos.Restaurante;
import com.example.couponsapp.modelos.TipoCupon;

import java.util.ArrayList;

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

    public ArrayList<Restaurante> traerRestaurante(String id_restaurante){
        this.abrir();
        String[] args = {id_restaurante};
        ArrayList<Restaurante> list = new ArrayList<>();
        Restaurante restaurante;
        Direccion direccion;
        //Cursor result = db.rawQuery("SELECT * FROM RESTAURANTE", null);
        Cursor results = db.rawQuery("SELECT RESTAURANTE.ID_RESTAURANTE, RESTAURANTE.ID_DIRECCION, RESTAURANTE.NOMBRE_RESTAURANTE, DIRECCION.UBICACION  FROM\n" +
                "RESTAURANTE, DIRECCION WHERE RESTAURANTE.ID_DIRECCION = DIRECCION.ID_DIRECCION\n " +
                "AND RESTAURANTE.ID_RESTAURANTE = ?", args);
        if(results.moveToFirst()){
            do {
                direccion = new Direccion(  results.getInt(1),
                        results.getString(3)
                );
                restaurante = new Restaurante(
                        results.getInt(0),
                        results.getString(2),
                        direccion
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
        Direccion direccion;
        //Cursor result = db.rawQuery("SELECT * FROM RESTAURANTE", null);
        Cursor results = db.rawQuery("SELECT RESTAURANTE.ID_RESTAURANTE, RESTAURANTE.ID_DIRECCION, RESTAURANTE.NOMBRE_RESTAURANTE,DIRECCION.UBICACION FROM\n" +
                "RESTAURANTE, DIRECCION WHERE RESTAURANTE.ID_DIRECCION = DIRECCION.ID_DIRECCION", null);
        if(results.moveToFirst()){
            do {
                direccion = new Direccion(  results.getInt(1),
                        results.getString(3)
                );
                restaurante = new Restaurante(
                        results.getInt(0),
                        results.getString(2),
                        direccion
                );
                list.add(restaurante);
            }while (results.moveToNext());
        }
        results.close();
        this.cerrar();
        return list;
    }
}
