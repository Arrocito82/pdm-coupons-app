package com.example.couponsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class InicioActivity extends AppCompatActivity {

    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    TextView nombre, email;
    ImageView foto;
    Button cerrar_sesion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
        nombre = findViewById(R.id.nombre);
        email = findViewById(R.id.email);
        foto = findViewById(R.id.foto_user);
        cerrar_sesion = findViewById(R.id.cerrar_sesion);
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(this,gso);
        GoogleSignInAccount cuenta = GoogleSignIn.getLastSignedInAccount(this);
        if(cuenta!= null){
            String nombreUser = cuenta.getDisplayName();
            String emailUser = cuenta.getEmail();
            Uri userPhoto = cuenta.getPhotoUrl();
            nombre.setText(nombreUser);
            email.setText(emailUser);
            Glide.with(this).load(String.valueOf(userPhoto)).into(foto);
        }

        cerrar_sesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cerrarSesion();
            }
        });
    }

    void cerrarSesion(){
        gsc.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(Task<Void> task) {
                finish();
                Intent intent = new Intent(InicioActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}