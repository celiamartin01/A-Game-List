package com.example.agamelist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.agamelist.Perfil.main_perfil;

public class ActivityStart extends AppCompatActivity {
    private Button BtRegistrarse, BtLogin, Btwuser;
    private TextView txContrasena;

    private Button btnPrincipalPrueba, btnJUEGO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        //        FindviewBy de los elementos de la pantalla splash
        FragmentManager fragmentManager = getSupportFragmentManager();

        BtRegistrarse = findViewById(R.id.bt_start_Registrarse);
        BtLogin = findViewById(R.id.bt_start_Login);
        txContrasena = findViewById(R.id.tx_start_forgotPass);
        Btwuser = findViewById(R.id.bt_start_AccederSinSesion);

        // PRUEBA PARA VISUALIZAR LA PAG PRINCIPAL Y RESEÑAS (CELIA)

        btnPrincipalPrueba = findViewById(R.id.btnPrincipalPrueba);

        btnPrincipalPrueba.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ActivityStart.this, main_pagina_principal.class);
                startActivity(intent);
            }
        });

        btnJUEGO = findViewById(R.id.btnJuego);

        btnJUEGO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (ActivityStart.this, pestana_juego.class);
                startActivity(intent);
            }
        });


    }
}