package com.example.couponsapp.controladores;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.util.Log;

import com.example.couponsapp.dbHelper.Control;
import com.example.couponsapp.modelos.Usuario;

import java.util.ArrayList;

public class UsuarioControl extends Control {
    public UsuarioControl(Context context) {
        super(context);
    }

    public long insertUsuario(Usuario usuario){
        this.abrir();
        long id_res;
        ContentValues current = new ContentValues();
        current.put("ID_ROL", usuario.getId_rol());
        current.put("ID_RESTAURANTE", usuario.getId_restaurante());
        current.put("USERNAME", usuario.getUsername());
        current.put("PASSWORD", usuario.getPassword());
        current.put("EMAIL", usuario.getEmail());
        current.put("NOMBRE", usuario.getNombre());
        current.put("APELLIDO", usuario.getApellido());
        current.put("TELEFONO", usuario.getTelefono());
        current.put("GOOGLE_USUARIO", usuario.getGoogle_usuario());
        id_res = db.insert("USUARIO", null, current);
        this.cerrar();
        return  id_res;
    }

    public int updateUsuario(Usuario usuario){
        this.abrir();
        int id_res;
        String[] args = {String.valueOf(usuario.getId_usuario())};
        ContentValues current = new ContentValues();
        current.put("ID_ROL", usuario.getId_rol());
        current.put("ID_RESTAURANTE", usuario.getId_restaurante());
        current.put("USERNAME", usuario.getUsername());
        current.put("PASSWORD", usuario.getPassword());
        current.put("EMAIL", usuario.getEmail());
        current.put("NOMBRE", usuario.getNombre());
        current.put("APELLIDO", usuario.getApellido());
        current.put("TELEFONO", usuario.getTelefono());
        current.put("GOOGLE_USUARIO", usuario.getGoogle_usuario());
        id_res = db.update("USUARIO", current, "ID_USUARIO = ?", args);
        this.cerrar();
        return  id_res;
    }

    public int deleteUsuario(int id_usuario){
        this.abrir();
        String[] args = {String.valueOf(id_usuario)};
        int id_del = db.delete("USUARIO", "ID_USUARIO = ?", args);
        this.cerrar();
        return  id_del;
    }

    public ArrayList<Usuario> readAllUsuario(){
        this.abrir();
        ArrayList<Usuario> usuarios = new ArrayList<>();
        Usuario usuario;
        Cursor res = db.rawQuery("SELECT * FROM USUARIO", null);
        if(res.moveToFirst()){
            do {
                usuario = new Usuario(
                        res.getInt(0),
                        res.getInt(1),
                        res.getInt(2),
                        res.getString(3),
                        res.getString(4),
                        res.getString(5),
                        res.getString(6),
                        res.getString(7),
                        res.getString(8),
                        res.getInt(9)
                );
                usuarios.add(usuario);
            }while (res.moveToNext());
        }
        res.close();
        this.cerrar();
        return usuarios;
    }

    public int userExist (String username, String email){
        this.abrir();
        int cant;
        String[] args = {username, email};
        Cursor res = db.rawQuery("SELECT COUNT(ID_USUARIO) FROM USUARIO WHERE USERNAME = ? OR EMAIL = ?", args);
        if (res.moveToFirst()){
            cant = res.getInt(0);
        }
        else{
            cant = 0;
        }
        res.close();
        return cant;
    }

    public Usuario traerUsuario (String username, String password){
        this.abrir();
        Usuario usuario;
        String[] args = {username, password};
        Cursor res = db.rawQuery("SELECT * FROM USUARIO WHERE USERNAME = ? AND PASSWORD = ?", args);
        if (res.moveToFirst()){
            usuario = new Usuario(
                    res.getInt(0),
                    res.getInt(1),
                    res.getInt(2),
                    res.getString(3),
                    res.getString(4),
                    res.getString(5),
                    res.getString(6),
                    res.getString(7),
                    res.getString(8),
                    res.getInt(9)
            );
        }
        else{
            usuario = new Usuario();
        }
        res.close();
        return usuario;
    }

    public int validarUsuario (String username, String password){
        this.abrir();
        int cant;
        String[] args = {username, password};
        Cursor res = db.rawQuery("SELECT COUNT(ID_USUARIO) FROM USUARIO WHERE USERNAME = ? AND PASSWORD = ?", args);
        if (res.moveToFirst()){
            cant = res.getInt(0);
        }
        else{
            cant = 0;
        }
        res.close();
        return cant;
    }

    public int adminExist (int rol){
        this.abrir();
        int cant;
        String[] args = {String.valueOf(rol)};
        Cursor res = db.rawQuery("SELECT COUNT(ID_USUARIO) FROM USUARIO WHERE ID_ROL = ?", args);
        if (res.moveToFirst()){
            cant = res.getInt(0);
        }
        else{
            cant = 0;
        }
        res.close();
        return cant;
    }

    public ArrayList<Usuario> readMany(String apellidos){
        String []args={"%"+apellidos+"%"};
        String []columns = {"ID_USUARIO", "ID_ROL", "ID_RESTAURANTE", "USERNAME", "PASSWORD","EMAIL","NOMBRE","APELLIDO","TELEFONO","GOOGLE_USUARIO"};
        ArrayList <Usuario> usuarios=new ArrayList<>();
        this.abrir();
        Cursor results=db.query("USUARIO",columns,"APELLIDO LIKE ?",args,null,null,null);

        try{
            while(results.moveToNext()){
                usuarios.add(new Usuario(
                        results.getInt(0),
                        results.getInt(1),
                        results.getInt(2),
                        results.getString(3),
                        results.getString(4),
                        results.getString(5),
                        results.getString(6),
                        results.getString(7),
                        results.getString(8),
                        results.getInt(9)));
            }
        }catch (SQLException e){
            Log.e("Error",e.getMessage());
        }
        this.cerrar();
        return usuarios;
    }
}
