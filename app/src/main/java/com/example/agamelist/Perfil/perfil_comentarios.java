package com.example.agamelist.Perfil;

import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ImageButton;
import android.widget.ImageView;

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

public class perfil_comentarios extends AppCompatActivity {

    Spinner sp_respuestas, sp_notis, sp_email;
    ImageButton fotoPerfil;
    TextView tv_name_title,tv_user_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_comentarios);

        sp_respuestas = findViewById(R.id.sp_respuestas);
        sp_notis = findViewById(R.id.sp_notis);
        sp_email = findViewById(R.id.sp_email);
        fotoPerfil = findViewById(R.id.ib_profile);
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
                    // Obtén el valor guardado para el Spinner
                    String respuestas = documentSnapshot.getString("coments_respuestas");
                    String email = documentSnapshot.getString("noti_email");
                    String notis = documentSnapshot.getString("notis");
                    int foto = documentSnapshot.getLong("profilePic").intValue();
                    String usuario = documentSnapshot.getString("user");
                    String nombre = documentSnapshot.getString("name");

                    // Obtiene el array de recursos para el Spinner
                    String[] opciones = getResources().getStringArray(R.array.array_visibilidad);

                    // Encuentra la posición seleccionada
                    int posicionSeleccionada_respuestas = Arrays.asList(opciones).indexOf(respuestas);
                    int posicionSeleccionada_email = Arrays.asList(opciones).indexOf(email);
                    int posicionSeleccionada_notis = Arrays.asList(opciones).indexOf(notis);

                    // Establece la posición seleccionada en el Spinner
                    sp_respuestas.setSelection(posicionSeleccionada_respuestas);
                    sp_email.setSelection(posicionSeleccionada_email);
                    sp_notis.setSelection(posicionSeleccionada_notis);
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

        String respuestasSeleccionadas = sp_respuestas.getSelectedItem().toString();
        String emailSeleccionado = sp_email.getSelectedItem().toString();
        String notisSeleccionadas = sp_notis.getSelectedItem().toString();

        // Actualizar los datos en Firestore
        docRef.update(
                "coments_respuestas", respuestasSeleccionadas,
                "noti_email", emailSeleccionado,
                "notis", notisSeleccionadas
        ).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(getApplicationContext(), "Perfil actualizado correctamente", Toast.LENGTH_SHORT).show();
                //actualizarCamposModificados(respuestasSeleccionadas, emailSeleccionado, notisSeleccionadas);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Error al actualizar el perfil", Toast.LENGTH_SHORT).show();
            }
        });
    }
}