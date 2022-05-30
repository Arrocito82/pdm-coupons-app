package com.example.couponsapp.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.example.couponsapp.controladores.UsuarioControl;
import com.example.couponsapp.modelos.Usuario;
import com.example.couponsapp.R;

import java.util.ArrayList;

public class UsuarioAdapter extends BaseAdapter {
    Context context;
    UsuarioControl control;
    LayoutInflater layoutInflater;
    ArrayList<Usuario> items;

    //variables del custom dialog
    EditText username,email, nombre, apellido, password, telefono, rol;

    public UsuarioAdapter(Context context ) {
        this.control = new UsuarioControl(context);
        this.items=control.readAllUsuario();
        this.context=context;
        this.layoutInflater=LayoutInflater.from(context);
    }

    public void crear() {
        /*Usuario targetItem=new Usuario();
        //creando el view del dialogo
        View customDialog=layoutInflater.inflate(R.layout.dialog_usuario, null);

        //construccion del dialogo
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(customDialog)
                .setTitle(R.string.dialog_crear)
                .setPositiveButton(R.string.guardar_usuario_configuracion, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        Dialog dialogView = (Dialog) dialog;
                        //nuevos valores
                        username=dialogView.findViewById(R.id.edittext_codigo_especialidad);
                        email=dialogView.findViewById(R.id.edittext_nombre_especialidad);
                        nombre=dialogView.findViewById(R.id.edittext_nombre_especialidad);
                        apellido=dialogView.findViewById(R.id.edittext_nombre_especialidad);
                        password=dialogView.findViewById(R.id.edittext_nombre_especialidad);
                        telefono=dialogView.findViewById(R.id.edittext_nombre_especialidad);
                        rol=dialogView.findViewById(R.id.edittext_nombre_especialidad);

                        targetItem.setUsername(username.getText().toString());
                        targetItem.setEmail(email.getText().toString());
                        targetItem.setNombre(nombre.getText().toString());
                        targetItem.setApellido(apellido.getText().toString());
                        targetItem.setPassword(password.getText().toString());
                        targetItem.setTelefono(telefono.getText().toString());
                        targetItem.setId_rol(Integer.getInteger(rol.getText()));
                        int result=(int)control.insertUsuario(targetItem);
                        targetItem.setId_usuario(result);
                        if (result>0){
                            addItem(targetItem);
                            notifyDataSetChanged();
                            Toast.makeText(context,R.string.guardado,Toast.LENGTH_LONG).show();
                        }
                    }
                });
        builder.create();
        builder.show();*/

    }

    public  void editar(int position){
        /*item a modificar
        Usuario targetItem=getItem(position);

        //creando el view del dialogo
        View customDialog=layoutInflater.inflate(R.layout.dialog_usuario, null);

        //valores por defecto
        username=customDialog.findViewById(R.id.edittext_codigo_especialidad);
        email=customDialog.findViewById(R.id.edittext_nombre_especialidad);
        nombre=customDialog.findViewById(R.id.edittext_nombre_especialidad);
        apellido=customDialog.findViewById(R.id.edittext_nombre_especialidad);
        password=customDialog.findViewById(R.id.edittext_nombre_especialidad);
        telefono=customDialog.findViewById(R.id.edittext_nombre_especialidad);
        rol=customDialog.findViewById(R.id.edittext_nombre_especialidad);

        //construccion del dialogo
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(customDialog)
                .setTitle(R.string.dialog_editar)
                .setPositiveButton(R.string.guardar_usuario_configuracion, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        Dialog dialogView = (Dialog) dialog;
                        //nuevos valores
                        codigo=dialogView.findViewById(R.id.edittext_codigo_especialidad);
                        nombre=dialogView.findViewById(R.id.edittext_nombre_especialidad);
                        nuevoCodigo=codigo.getText().toString();
                        nuevoNombre=nombre.getText().toString();
                        if (targetItem.getCodigo_especialidad()!=nuevoCodigo){
                            targetItem.setCodigo_especialidad(nuevoCodigo);
                        }
                        if(targetItem.getNombre_especialidad()!=nuevoNombre){
                            targetItem.setNombre_especialidad(nuevoNombre);
                        }

                        int result=control.update(targetItem);
                        boolean isUpdated=result>0;
                        if (isUpdated){
                            items.set(position,targetItem);
                            notifyDataSetChanged();
                            Toast.makeText(context,R.string.guardado,Toast.LENGTH_LONG).show();
                        }
                    }
                });
        builder.create();
        builder.show();*/

    }
    public void eliminar(int position){

        Usuario usuario=getItem(position);
        int result=control.deleteUsuario(usuario.getId_usuario());
        boolean isDeleted=result>0;
        if (isDeleted){
            removeItem(position);
            notifyDataSetChanged();
            Toast.makeText(context,R.string.eliminado,Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(context,R.string.no_eliminado,Toast.LENGTH_LONG).show();

        }
    }
    public void filtrar(String busqueda) {
        items=control.readMany(busqueda);
        notifyDataSetChanged();

    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Usuario getItem(int i) {
        return items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    public void removeItem(int i){
        items.remove(i);
    }
    public void addItem(Usuario item){
        items.add(item);
    }

    @Override
    public View getView(int position, View customView, ViewGroup parent) {
        TextView nombre,username,email, apellido, password, telefono, rol;
        if(customView == null){
            customView = LayoutInflater.from(context).inflate(R.layout.item_usuario,parent,false);
        }else{
            customView=layoutInflater.inflate(R.layout.item_usuario,parent,false);
        }
        nombre=customView.findViewById(R.id.nombre_usuario_crud);
        username=customView.findViewById(R.id.username_crud);
        apellido=customView.findViewById(R.id.apellid_usuario_crud);
        rol=customView.findViewById(R.id.rol_crud);
        nombre.setText(items.get(position).getNombre());
        username.setText(items.get(position).getUsername());
        apellido.setText(items.get(position).getApellido());
        rol.setText(String.valueOf(items.get(position).getId_rol()));
        return customView;
    }
}
