package com.example.agamelist;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.agamelist.Perfil.main_perfil;
import com.example.agamelist.fragments_pprincipal.FragmentCrear;
import com.example.agamelist.fragments_pprincipal.FragmentDescubrir;
import com.example.agamelist.fragments_pprincipal.fragment_noticias;
import com.example.agamelist.recycler_buscador_nuevaResena.AdapterJuegoBuscador;
import com.example.agamelist.recycler_buscador_nuevaResena.ItemJuegoBuscador;
import com.example.agamelist.recycler_pecha_juegos_descubrir.AdapterPechaJuegos;
import com.example.agamelist.scrollLayout.Adapter;
import com.example.agamelist.scrollLayout.ItemJuego;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class main_pagina_principal extends AppCompatActivity implements GameInfoTask.GameInfoCallback, fragment_crearlista.OnListaCreadaListener, AdapterPechaJuegos.ItemClickListener{

    TabLayout tabLayout;
    ViewPager2 viewPager2;
    adaptador_main_pagina_principal myViewPagerAdapter;

    BottomNavigationView bottomNavigationView;

    com.example.agamelist.fragments_pprincipal.fragment_noticias fragment_noticias;
    FragmentCrear fragmentCrear;
    FragmentDescubrir fragmentDescubrir;

    //  Variables para dialog
    Dialog dialog;
    Dialog dialogBotones;
    ImageButton fotoPerfil;
    String nombrelista;
    List<ItemJuego> items;
    private AdapterPechaJuegos adapter;
    int numero;
    String palabra = "a";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_pagina_principal);

        fragment_noticias = new fragment_noticias();
        fragmentCrear = new FragmentCrear();
        fragmentDescubrir = new FragmentDescubrir();
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        fotoPerfil =findViewById(R.id.ib_principal_perfil);
      //  getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment_noticias).commit();

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.inicio:
                        viewPager2.setVisibility(View.VISIBLE);
                        tabLayout.setVisibility(View.VISIBLE);
                        findViewById(R.id.container).setVisibility(View.GONE);
                        return true;
                    case R.id.crear:

                        crear();

                        return true;
                    case R.id.descubrir:
                        viewPager2.setVisibility(View.GONE);
                        tabLayout.setVisibility(View.GONE);
                        findViewById(R.id.container).setVisibility(View.VISIBLE);
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragmentDescubrir).commit();

                        return true;
                }
                return false;
            }
        });


        tabLayout = findViewById(R.id.tab_layout);
        viewPager2 = findViewById(R.id.view_pager);
        myViewPagerAdapter = new adaptador_main_pagina_principal(this);
        viewPager2.setAdapter(myViewPagerAdapter);

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

    public void abrirPerfil(View view) {
        Intent intent = new Intent(this, main_perfil.class);
        startActivity(intent);
    }
    protected void onResume() {
        super.onResume();
        // Aquí puedes realizar las tareas de actualización de la interfaz de usuario
        obtenerDatosUsuario();
    }
    private void crear(){


        this.dialog = new Dialog(this, R.style.DialogBotonStyle);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        this.dialog.setContentView(R.layout.fragment_botonesflotantes);

        ImageView btn_crearres = dialog.findViewById(R.id.fab_botonesflotantes_resenia);
        ImageView btn_crearlis = dialog.findViewById(R.id.fab_botonesflotantes_lista);

        btn_crearres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mostrarPopup(1);
            }
        });

        btn_crearlis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mostrarPopup(2);
            }
        });

        this.dialog.show();


    }
    private void realizarAccionesListaCreada(String nombre) {
        Log.d("TAG", "Nombre de la lista creada: " + nombre);

    }
    private void mostrarPopup(int lyNum){


        this.dialogBotones = new Dialog(this, R.style.DialogStyle);

        //le pasamos en layout que queremos mostrar
        switch (lyNum){
            case 1:
                this.dialogBotones.setContentView(R.layout.fragment_crear_resenia);

                RecyclerView recyclerView = dialogBotones.findViewById(R.id.recyclerCrearResena);

                items = new ArrayList<ItemJuego>();
                adapter = new AdapterPechaJuegos(dialogBotones.getContext(), items);
                adapter.setItemClickListener(this);

                recyclerView.setLayoutManager(new LinearLayoutManager(dialogBotones.getContext()));
                recyclerView.setAdapter(new Adapter(dialogBotones.getContext(), items));

                SearchView searchView = dialogBotones.findViewById(R.id.buscadorCrearResena);
                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String s) {
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String s) {
                        filterResults(s);

                        if (TextUtils.isEmpty(s)) {
                            recyclerView.setVisibility(View.GONE); // Oculta el RecyclerView si el texto está vacío
                        } else {
                            recyclerView.setVisibility(View.VISIBLE); // Muestra el RecyclerView si hay texto
                        }
                        return true;
                    }
                });


                ImageView btnCerrarRES = dialogBotones.findViewById(R.id.btn_crearresenia_atras);

                dialogBotones.setCanceledOnTouchOutside(false);
                btnCerrarRES.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialogBotones.dismiss();
                    }
                });

                break;

            case 2:
                fragment_crearlista dialogFragment = new fragment_crearlista();
                dialogFragment.setOnListaCreadaListener(this);
                dialogFragment.show(getSupportFragmentManager(), "custom_dialog");

                Log.d("pre", ""+nombrelista);

                break;
        }


        //dialog.getWindow().setBackgroundDrawableResource(R.drawable);
        this.dialogBotones.show();


    }
    private void obtenerDatosUsuario() {
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DocumentReference docRef = FirebaseFirestore.getInstance().collection("Users").document(uid);

        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    // Documento encontrado, puedes obtener los datos del usuario
                    int foto = documentSnapshot.getLong("profilePic").intValue();
                    // Otros datos...
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

    private void filterResults(String query) {
        palabra=query;
        Log.d("palabra", ""+query);

        // Vaciar la lista de items
        items.clear();

        GameInfoTask gameInfoTask = new GameInfoTask(this, numero, palabra);
        gameInfoTask.execute();


    }

    @Override
    public void onListaCreada(String nombre) {
        nombrelista=nombre;
        Log.d("Lista : ","lista llamada"+nombrelista);
    }

    @Override
    public void onGameInfoReceived(String response) {
        // Aquí se maneja la respuesta recibida por la consulta

        if (response != null) {
            try {
                JSONArray jsonArray = new JSONArray(response);

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject gameObj = jsonArray.getJSONObject(i);


                    if (numero == 6) {
                        //Guardamos el objeto solo si tiene url
                        if (gameObj.has("cover")){
                            JSONObject coverObj = gameObj.getJSONObject("cover");
                            if (coverObj.has("url")){
                                String id = gameObj.getString("id");
                                String name = gameObj.getString("name");

                                String coverUrl = "https:" + coverObj.getString("url");

                                Log.d("pruebaDESCUBRIR", "id juego: " + id);
                                Log.d("pruebaDESCUBRIR", "nombre juego: " + name);
                                Log.d("pruebaDESCUBRIR", "url cover: " + coverUrl);

                                items.add(new ItemJuego(name, coverUrl, id));

                                List<ItemJuego> filteredList = new ArrayList<>();

                                for (ItemJuego juego : items) {

                                    filteredList.add(juego);

                                }

                                adapter.setItems(filteredList);
                                adapter.notifyDataSetChanged();
                            }

                        }
                    }

                }
                adapter.notifyDataSetChanged();

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onGameInfoError() {

    }


    @Override
    public void onItemClick(int position) {

    }
}
