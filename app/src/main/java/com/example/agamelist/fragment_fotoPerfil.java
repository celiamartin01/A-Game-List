package com.example.agamelist;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class fragment_fotoPerfil extends AppCompatActivity {

    ImageButton mg1, mg2, mg3, mg4, mg5;
    int fotoSeleccionada;
    String uid;
    FirebaseFirestore db;
    Button btn_fotoPerfil_guardar;
    ImageButton bt_atras;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_foto_perfil);

        btn_fotoPerfil_guardar = findViewById(R.id.btn_fotoPerfil_guardar);
        mg1 = findViewById(R.id.foto_perfil1);
        mg2 = findViewById(R.id.foto_perfil2);
        mg3 = findViewById(R.id.foto_perfil3);
        mg4 = findViewById(R.id.foto_perfil4);
        mg5 = findViewById(R.id.foto_perfil5);
        bt_atras = findViewById(R.id.bt_cambiarfoto_atras);
        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        db = FirebaseFirestore.getInstance();
        bt_atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mg1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fotoSeleccionada = getResources().getIdentifier("fotoperfil1", "drawable", getPackageName());
            }
        });
        mg2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fotoSeleccionada =  getResources().getIdentifier("fotoperfil2", "drawable", getPackageName());
            }
        });
        mg3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fotoSeleccionada = getResources().getIdentifier("fotoperfil3", "drawable", getPackageName());
            }
        });
        mg4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fotoSeleccionada =  getResources().getIdentifier("fotoperfil4", "drawable", getPackageName());
            }
        });
        mg5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fotoSeleccionada = getResources().getIdentifier("fotoperfil5", "drawable", getPackageName());
            }
        });

        btn_fotoPerfil_guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                guardarFotoBBDD();
                finish();
            }
        });
    }

    public void guardarFotoBBDD() {
        if(fotoSeleccionada!=0){
        Map<String, Object> newUser = new HashMap<>();
        newUser.put("profilePic", fotoSeleccionada);

        db.collection("Users").document(uid)
                .update(newUser)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(fragment_fotoPerfil.this, "Foto actualizada correctamente.", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(fragment_fotoPerfil.this, "Error al actualizar la foto.", Toast.LENGTH_SHORT).show();
                    }
                });
    }}
}
