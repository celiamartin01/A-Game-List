package com.example.agamelist;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

public class main_resenia extends AppCompatActivity {

    ImageButton btnAtrasResnia;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_resenia);

        btnAtrasResnia = findViewById(R.id.btnAtrasResenia);
        btnAtrasResnia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (main_resenia.this, main_pagina_principal.class);
                startActivity(intent);

            }
        });

    }
}
