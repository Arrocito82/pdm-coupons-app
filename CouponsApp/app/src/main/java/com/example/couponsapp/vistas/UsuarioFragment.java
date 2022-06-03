package com.example.couponsapp.vistas;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.couponsapp.R;
import com.example.couponsapp.adapter.RolAdapter;
import com.example.couponsapp.controladores.UsuarioControl;
import com.example.couponsapp.modelos.Rol;
import com.example.couponsapp.modelos.Usuario;

/**
 * Este fragmento tiene por proposito registrar y editar usuario
 */
public class UsuarioFragment extends Fragment {

    int id_usuario;
    UsuarioControl usuarioControl;
    RolAdapter rolAdapter;
    Usuario usuario;
    boolean isNew,isAdmin;
    EditText usernameEditText,passwordEditText,passwordEditText2,emailEditText,nombreEditText,apellidosEditText,telefono;
    EditText passwordConfirmation1,passwordConfirmation2;
    Button guardarBtn,cambiarContrasenaBtn;
    Spinner rolSpinner;
    int updatedCount=0;

    public UsuarioFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        usuarioControl=new UsuarioControl(getContext());
        rolAdapter=new RolAdapter(getContext());
        if (getArguments() != null) {
            isNew=getArguments().getBoolean("is_new",false);
            isAdmin=getArguments().getBoolean("is_admin",false);
            if(isNew){
                usuario=new Usuario();
            }else{
                id_usuario=getArguments().getInt("id_usuario");
                usuario=usuarioControl.readUsuario(id_usuario);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_perfil, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //inicializacion de botones de guardar y cambiar contrasena
        cambiarContrasenaBtn=view.findViewById(R.id.button_cambiar_contrasena);
        guardarBtn=view.findViewById(R.id.btn_guardar_usuario_configuracion);

        //recuperando los editText del view
        usernameEditText=view.findViewById(R.id.perfil_de_usuario_username);
        emailEditText=view.findViewById(R.id.perfil_de_usuario_email);
        passwordEditText=view.findViewById(R.id.perfil_de_usuario_contrasena);
        nombreEditText=view.findViewById(R.id.perfil_de_usuario_nombre);
        apellidosEditText=view.findViewById(R.id.perfil_de_usuario_apellidos);
        telefono=view.findViewById(R.id.perfil_de_usuario_telefono);
        rolSpinner=view.findViewById(R.id.perfil_spinner_rol);


        if(isNew){
            cambiarContrasenaBtn.setVisibility(View.GONE);
            passwordEditText2 = view.findViewById(R.id.perfil_de_usuario_contrasena2);
            passwordEditText.setEnabled(true);
            usernameEditText.setEnabled(true);
            rolSpinner.setVisibility(View.VISIBLE);
            rolSpinner.setAdapter(rolAdapter);
            passwordEditText2.setVisibility(View.VISIBLE);


        }else{
            //asignando valores por defecto si se esta editando el perfil
            usernameEditText.setText(usuario.getUsername());
            emailEditText.setText(usuario.getEmail());
            passwordEditText.setText(usuario.getPassword());
            nombreEditText.setText(usuario.getNombre());
            apellidosEditText.setText(usuario.getApellido());
            telefono.setText(usuario.getTelefono());
            rolSpinner.setVisibility(View.VISIBLE);
            rolSpinner.setAdapter(rolAdapter);
            if(isAdmin){
                usernameEditText.setEnabled(true);
            }
        }


        //agregando un event listener para cuando da clic en guardar
        guardarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usuario.setNombre(nombreEditText.getText().toString());
                usuario.setApellido(apellidosEditText.getText().toString());
                usuario.setEmail(emailEditText.getText().toString());
                usuario.setTelefono(telefono.getText().toString());

                if(isNew){
                    if(passwordEditText.getText().toString().equals(passwordEditText2.getText().toString())){
                        Rol selectedRol=(Rol)rolSpinner.getSelectedItem();
                        usuario.setPassword(passwordEditText.getText().toString());
                        usuario.setUsername(usernameEditText.getText().toString());
                        usuario.setId_rol(selectedRol.getId_rol());

                        //aqui se guarda el usuario
                        updatedCount=(int)usuarioControl.insertUsuario(usuario);
                        if(updatedCount>0){
                            Toast.makeText(getContext(),R.string.guardado,Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(getContext(),R.string.invalido_username,Toast.LENGTH_LONG).show();
                        }
                    }else{
                        Toast.makeText(getContext(),R.string.contrasena_no_coincide,Toast.LENGTH_LONG).show();

                    }
                }else{
                    if(isAdmin){
                        usuario.setUsername(usernameEditText.getText().toString());
                    }
                    updatedCount=usuarioControl.updateUsuario(usuario);
                    if(updatedCount==1){
                        Toast.makeText(getContext(),"Guardado!",Toast.LENGTH_LONG).show();
                    }
                }

            }
        });
        if(!isNew){
            cambiarContrasenaBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    LayoutInflater inflater = requireActivity().getLayoutInflater();
                    builder.setView(inflater.inflate(R.layout.cambiar_contrasena, null))
                            .setPositiveButton(R.string.guardar_usuario_configuracion, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int id) {
                                    Dialog dialogView = (Dialog) dialog;
                                    passwordConfirmation1=(EditText) dialogView.findViewById(R.id.cambiar_contrasena_confirmacion_1);
                                    passwordConfirmation2=(EditText) dialogView.findViewById(R.id.cambiar_contrasena_confirmacion_2);
                                    Boolean isValid=cambiarContrasena();
                                    if(isValid){
                                        Toast.makeText(getContext(),"Guardado!",Toast.LENGTH_LONG).show();
                                    }else{
                                        Toast.makeText(getContext(),"Contraseñas no coinciden. ¡Intentelo de nuevo!",Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                    builder.setTitle(R.string.cambiar_contrasena);
                    builder.create();
                    builder.show();

                }
            });
        }



    }
    private Boolean cambiarContrasena(){
        String confirmacionClave1,confirmacionClave2;
        Boolean isValid=false;
        int updatedPasswordCount;
        confirmacionClave1=passwordConfirmation1.getText().toString();
        confirmacionClave2=passwordConfirmation2.getText().toString();
        if(confirmacionClave1.equals(confirmacionClave2)){
            usuario.setPassword(confirmacionClave1);
            updatedPasswordCount=usuarioControl.updateUsuario(usuario);
            isValid=updatedPasswordCount==1;
        }
        return isValid;
    }

}