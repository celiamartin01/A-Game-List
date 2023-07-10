package com.example.agamelist.Perfil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.agamelist.MainActivity;
import com.example.agamelist.R;
import com.example.agamelist.fragment_fotoPerfil;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class activity_perfil extends AppCompatActivity {

    TextView tv_name, tv_user;
    ImageButton fotoPerfil;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        tv_name = findViewById(R.id.tv_name);
        tv_user = findViewById(R.id.tv_user);
        fotoPerfil = findViewById(R.id.ib_profile);


    }
    protected void onResume() {
        super.onResume();
        // Aquí puedes realizar las tareas de actualización de la interfaz de usuario
        obtenerDatosUsuario();
    }
    public void cerrarPerfil(View view) {
        finish();
    }

    public void abrirPerfilPublico(View view) {
        Intent intent = new Intent(this, perfil_publico.class);
        startActivity(intent);
    }

    public void abrirPerfilPrivado(View view) {
        Intent intent = new Intent(this, perfil_privado.class);
        startActivity(intent);
    }

    public void abrirPerfilComentario(View view) {
        Intent intent = new Intent(this, perfil_comentarios.class);
        startActivity(intent);
    }

    public void abrirPerfilRedes(View view) {
        Intent intent = new Intent(this, perfil_redes.class);
        startActivity(intent);
    }

    public void abrirPerfilSeguridad(View view) {
        Intent intent = new Intent(this, perfil_seguridad.class);
        startActivity(intent);
    }
    public void abrirCambiarFoto(View view){

        Intent intent = new Intent(this, fragment_fotoPerfil.class);
        startActivity(intent);

    }

    public void singOut(View view) {
        FirebaseAuth.getInstance().signOut();
        GoogleSignIn.getClient(this, GoogleSignInOptions.DEFAULT_SIGN_IN).signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // Acciones adicionales después de cerrar sesión
                        // Redirigir al usuario a la pantalla de inicio de sesión
                        Intent intent = new Intent(activity_perfil.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    }
                });
    }

    private void obtenerDatosUsuario() {
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DocumentReference docRef = FirebaseFirestore.getInstance().collection("Users").document(uid);

        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    // Documento encontrado, puedes obtener los datos del usuario
                    String nombre = documentSnapshot.getString("name");
                    String usuario = documentSnapshot.getString("user");
                    int foto = documentSnapshot.getLong("profilePic").intValue();
                    // Otros datos...
                    tv_name.setText(nombre);
                    tv_user.setText(usuario);
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
}