package com.example.couponsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

public class MainActivity extends AppCompatActivity {

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
        Intent inte= new Intent(this, InicioActivity.class);
        startActivity(inte);
    }
}