package com.example.agamelist.Perfil;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.agamelist.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class perfil_redes extends AppCompatActivity {

    TextView tv_name_title, tv_user_title;
    EditText et_twitter, et_instagram, et_tiktok, et_facebook, et_youtube;
    ImageButton fotoPerfil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_redes);

        tv_name_title = findViewById(R.id.tv_name_title);
        tv_user_title = findViewById(R.id.tv_user_title);
        et_twitter = findViewById(R.id.et_twitter);
        et_instagram = findViewById(R.id.et_instagram);
        et_tiktok = findViewById(R.id.et_tiktok);
        et_facebook = findViewById(R.id.et_facebook);
        et_youtube = findViewById(R.id.et_youtube);
        fotoPerfil = findViewById(R.id.ib_profile);
        obtenerDatosUsuario();
    }

    public void cerrarPerfil(View view) {
        finish();
    }

    private void obtenerDatosUsuario() {
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DocumentReference docRef = FirebaseFirestore.getInstance().collection("Users").document(uid);

        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    // Documento encontrado, puedes obtener los datos del usuario
                    String usuario = documentSnapshot.getString("user");
                    String nombre = documentSnapshot.getString("name");
                    int foto = documentSnapshot.getLong("profilePic").intValue();
                    List<String> redes = (List<String>) documentSnapshot.get("Redes");
                    if (redes != null) {
                        for (int i = 0; i < redes.size(); i++) {
                            String red = redes.get(i);
                            switch (i) {
                                case 0:
                                    et_twitter.setText(red);
                                    break;
                                case 1:
                                    et_instagram.setText(red);
                                    break;
                                case 2:
                                    et_tiktok.setText(red);
                                    break;
                                case 3:
                                    et_facebook.setText(red);
                                    break;
                                case 4:
                                    et_youtube.setText(red);
                                    break;
                            }
                        }
                    }

                    // Otros datos...
                    tv_name_title.setText(nombre);
                    tv_user_title.setText(usuario);
                    fotoPerfil.setImageResource(foto);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Error al cargar los datos de la base de datos", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void actualizarDatos(View view) {
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DocumentReference docRef = FirebaseFirestore.getInstance().collection("Users").document(uid);

        docRef.update(
                "Redes", Arrays.asList(
                        et_twitter.getText().toString(),
                        et_instagram.getText().toString(),
                        et_tiktok.getText().toString(),
                        et_facebook.getText().toString(),
                        et_youtube.getText().toString()
                )
        ).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(getApplicationContext(), "Perfil actualizado correctamente", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Error al actualizar el perfil", Toast.LENGTH_SHORT).show();
            }
        });
    }
}