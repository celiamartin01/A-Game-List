package com.example.agamelist.Perfil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.agamelist.R;
import com.example.agamelist.fragment_fotoPerfil;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class main_perfil extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager2 viewPager2;
    ViewPagerAdapterPerfil viewPagerAdapterPerfil;
    TextView tv_userTitulo, tv_name, tv_user, tv_siguiendo, tv_seguidores, tv_reseñas;
    ImageButton fotoPerfil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_perfil);

        //Esto es para sacar los datos de la BBDD                                                   PD:Hola al que este leyendo esto
        tv_userTitulo = findViewById(R.id.tv_user_title);
        tv_name = findViewById(R.id.tv_name);
        tv_user = findViewById(R.id.tv_user);
        fotoPerfil = findViewById(R.id.ib_perfil);
        tv_siguiendo = findViewById(R.id.tv_siguiendo);
        tv_seguidores= findViewById(R.id.tv_seguidores);
        tv_reseñas= findViewById(R.id.tv_reseñas);
        obtenerDatosUsuario();

        tabLayout = findViewById(R.id.tab_layout_perfil);
        viewPager2 = findViewById(R.id.view_pager_perfil);
        viewPagerAdapterPerfil = new ViewPagerAdapterPerfil(this);

        viewPager2.setAdapter(viewPagerAdapterPerfil);
        fotoPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirCambiarFoto();
            }
        });
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                tabLayout.getTabAt(position).select();
            }
        });


    }
    protected void onResume() {
        super.onResume();
        // Aquí puedes realizar las tareas de actualización de la interfaz de usuario
        obtenerDatosUsuario();
    }
    public void abrirActivityPerfil(View view) {
        Intent intent = new Intent(this, activity_perfil.class);
        startActivity(intent);
    }
    public void abrirCambiarFoto(){

        Intent intent = new Intent(this, fragment_fotoPerfil.class);
        startActivity(intent);

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
                    String nombre = documentSnapshot.getString("name");
                    String usuario = documentSnapshot.getString("user");
                    int siguiendo = documentSnapshot.getLong("siguiendo").intValue();
                    int seguidores = documentSnapshot.getLong("seguidores").intValue();
                    int reseñas = documentSnapshot.getLong("reseñasCount").intValue();
                    int foto = documentSnapshot.getLong("profilePic").intValue();

                    // Otros datos...
                    tv_userTitulo.setText(usuario);
                    tv_name.setText(nombre);
                    tv_user.setText(usuario);
                    fotoPerfil.setImageResource(foto);
                    tv_siguiendo.setText(String.valueOf(siguiendo));
                    tv_seguidores.setText(String.valueOf(seguidores));
                    tv_reseñas.setText(String.valueOf(reseñas));

                }}
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Error al cargar los datos de la base de datos", Toast.LENGTH_SHORT).show();
            }
        });
    }

}