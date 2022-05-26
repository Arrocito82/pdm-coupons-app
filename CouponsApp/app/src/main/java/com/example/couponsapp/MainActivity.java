package com.example.couponsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.couponsapp.controladores.PermisoControl;
import com.example.couponsapp.controladores.RolControl;
import com.example.couponsapp.controladores.RolPermisoControl;
import com.example.couponsapp.controladores.UsuarioControl;
import com.example.couponsapp.dbHelper.Control;
import com.example.couponsapp.modelos.Permiso;
import com.example.couponsapp.modelos.Rol;
import com.example.couponsapp.modelos.RolPermiso;
import com.example.couponsapp.modelos.Usuario;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Control ctr = new Control(this);
    RolControl rolControl = new RolControl(this);
    PermisoControl permisoControl = new PermisoControl(this);
    RolPermisoControl rolPermisoControl = new RolPermisoControl(this);
    UsuarioControl usuarioControl = new UsuarioControl(this);

    /*
    *
    * Elementos de la UI
    *
    * */
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    ImageView google_btn;

    EditText username, password;
    Button loginbtn;
    String user, pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        crearTablas();
        if(usuarioControl.adminExist(1) == 0){
            llenarUsuarios();
        }

        //obtener datos de inicio de sesión sin google
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        loginbtn = (Button) findViewById(R.id.loginbtn);

        //Obtener datos inciales
        google_btn = findViewById(R.id.google_btn);
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(this,gso);
        GoogleSignInAccount cuenta = GoogleSignIn.getLastSignedInAccount(this);
        if(cuenta!=null){
            navigateToInicioActivity();
        }

        //iniciar sesión sin google
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user = username.getText().toString();
                pass = password.getText().toString();
                if(user.equals("") || pass.equals("")){
                    Toast.makeText(MainActivity.this, "Ingresar los campos", Toast.LENGTH_SHORT).show();
                }
                else{
                    if(usuarioControl.validarUsuario(user, pass) != 0){
                        Intent intent = new Intent(MainActivity.this, InicioActivity.class);
                        intent.putExtra("username", user);
                        intent.putExtra("password", pass);
                        MainActivity.this.startActivity(intent);
                    }
                    else {
                        Toast.makeText(MainActivity.this, "Ha ingresado mal usuario o contraseña", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        //Iniciar sesión
        google_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inicioSesion();
            }
        });
    }


    void inicioSesion(){
        Intent inicioSesionIntent = gsc.getSignInIntent();
        startActivityForResult(inicioSesionIntent, 1000);
    }

    void iniciarSesionNoGoogle(){

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1000){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                task.getResult(ApiException.class);
                navigateToInicioActivity();
            } catch (ApiException e) {
                Toast.makeText(getApplicationContext(), "Ocurrio un error", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void navigateToInicioActivity(){
        finish();
        Intent inte = new Intent(this, InicioActivity.class);
        startActivity(inte);
    }

    public void crearTablas(){
        ctr.abrir();
        ctr.cerrar();
    }

    public void llenarUsuarios(){
        long rolAdmin = rolControl.insertRol(new Rol(1, "Admin")); //1
        long rolEncargado = rolControl.insertRol(new Rol(2, "Encargado")); //2
        long rolCliente = rolControl.insertRol(new Rol(3, "Cliente"));  //3

        long permisoAdmin = permisoControl.insertPermiso(new Permiso(1, "Gestionar usuario"));
        long permisoEncargado = permisoControl.insertPermiso(new Permiso(2, "Gestionar cupon"));
        long permisoCliente = permisoControl.insertPermiso(new Permiso(3, "Canjear cupon"));

        long rolPermiso1 = rolPermisoControl.insertRolPermiso(new RolPermiso(1,(int)rolAdmin, (int)permisoAdmin));
        long rolPermiso2 = rolPermisoControl.insertRolPermiso(new RolPermiso(2,(int)rolEncargado, (int)permisoEncargado));
        long rolPermiso3 = rolPermisoControl.insertRolPermiso(new RolPermiso(3,(int)rolCliente, (int)permisoCliente));

        long usuarioAdmin = usuarioControl.insertUsuario(new Usuario(1, (int)rolAdmin, 0,"admin", "admin123", "admin@gmail.com", "Eduardo", "Orellana", "78787878", 0));
        long usuarioEncargado = usuarioControl.insertUsuario(new Usuario(2, (int)rolEncargado, 1,"encargado", "encargado123", "encargado@gmail.com", "Julia", "Lopez", "79235499", 0));
        long usuarioCliente = usuarioControl.insertUsuario(new Usuario(3, (int)rolCliente, 0,"cliente", "cliente123", "encargado@gmail.com", "Julia", "Lopez", "75757575", 0));
    }
}