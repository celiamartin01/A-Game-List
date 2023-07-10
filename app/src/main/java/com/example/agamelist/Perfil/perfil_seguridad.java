package com.example.agamelist.Perfil;

import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.agamelist.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Arrays;

public class perfil_seguridad extends AppCompatActivity {

    Spinner sp_visibilidad;
    ImageButton fotoPerfil;
    TextView tv_name_title, tv_user_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_seguridad);
        fotoPerfil=findViewById(R.id.ib_profile);
        sp_visibilidad = findViewById(R.id.sp_visibilidad);
        tv_name_title = findViewById(R.id.tv_name_title);
        tv_user_title = findViewById(R.id.tv_user_title);
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
                    int foto = documentSnapshot.getLong("profilePic").intValue();
                    String usuario = documentSnapshot.getString("user");
                    String nombre = documentSnapshot.getString("name");
                    // Obtén el valor guardado para el Spinner
                    String visibilidad = documentSnapshot.getString("visibilidad");

                    // Obtiene el array de recursos para el Spinner
                    String[] opciones = getResources().getStringArray(R.array.array_visibilidad);

                    // Encuentra la posición seleccionada
                    int posicionSeleccionada_visibilidad = Arrays.asList(opciones).indexOf(visibilidad);

                    // Establece la posición seleccionada en el Spinner
                    sp_visibilidad.setSelection(posicionSeleccionada_visibilidad);
                    fotoPerfil.setImageResource(foto);
                    tv_name_title.setText(nombre);
                    tv_user_title.setText(usuario);
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

        String respuestasSeleccionadas_visibilidad = sp_visibilidad.getSelectedItem().toString();

        // Actualizar los datos en Firestore
        docRef.update(
                "visibilidad", respuestasSeleccionadas_visibilidad
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