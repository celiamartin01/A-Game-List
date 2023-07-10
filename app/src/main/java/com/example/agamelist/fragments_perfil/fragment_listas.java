package com.example.agamelist.fragments_perfil;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.agamelist.GameInfoTask;
import com.example.agamelist.R;
import com.example.agamelist.fragment_crearlista;
import com.example.agamelist.pestana_juego;
import com.example.agamelist.scrollLayout.Adapter;
import com.example.agamelist.scrollLayout.ItemJuego;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class fragment_listas extends Fragment implements fragment_crearlista.OnListaCreadaListener, GameInfoTask.GameInfoCallback, Adapter.ItemClickListener {

    Button btnNuevaLista;
    View layoutListaPersonalizada1;
    View layoutListaPersonalizada2;
    View layoutListaPersonalizada3;
    View layoutListaPersonalizada4;
    View layoutListaPersonalizada5;
    View layoutListaPersonalizada6;
    View layoutListaPersonalizada7;
    View layoutListaPersonalizada8;
    View layoutListaPersonalizada9;
    View layoutListaPersonalizada10;
    View view;
    FirebaseFirestore db;
    String uid;
    DocumentReference userRef;
    TextView tv_titulolista1, tv_titulolista2, tv_titulolista3, tv_titulolista4, tv_titulolista5, tv_titulolista6, tituloListaPersonalizada1, tituloListaPersonalizada2,tituloListaPersonalizada3;
    private Context mContext;

    List<Integer> juegosInt = new ArrayList<>();
    private List<ItemJuego> items;
    private Adapter adapter;
    private List<ItemJuego> items2;
    private Adapter adapter2;
    int numero;
    LinearLayoutManager layoutManager1, layoutManager2;

    ArrayList<Boolean> listasPersonalizadasLibres = new ArrayList<>(Arrays.asList(true, true, true, true, true, true, true, true, true, true));
    //true : libre, false: ocupada

    List<RecyclerView> recyclerListas = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_listas, container, false);
        db = FirebaseFirestore.getInstance();
        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        userRef = db.collection("users").document(uid);
        mContext = getActivity();

        tv_titulolista1 = view.findViewById(R.id.tv_tituloLista1);
        tv_titulolista2 = view.findViewById(R.id.tv_tituloLista2);
        tv_titulolista3 = view.findViewById(R.id.tv_tituloLista3);
        tv_titulolista4 = view.findViewById(R.id.tv_tituloLista4);
        tv_titulolista5 = view.findViewById(R.id.tv_tituloLista5);
        tv_titulolista6 = view.findViewById(R.id.tv_tituloLista6);
        tituloListaPersonalizada1 = view.findViewById(R.id.tituloListaPersonalizada1);
        tituloListaPersonalizada2 = view.findViewById(R.id.tituloListaPersonalizada2);
        tituloListaPersonalizada3 = view.findViewById(R.id.tituloListaPersonalizada3);

        // Agregar las referencias de los RecyclerView a la lista
        recyclerListas.add(view.findViewById(R.id.recyclerLista1));
        recyclerListas.add(view.findViewById(R.id.recyclerLista2));
        recyclerListas.add(view.findViewById(R.id.recyclerLista3));
        recyclerListas.add(view.findViewById(R.id.recyclerLista4));
        recyclerListas.add(view.findViewById(R.id.recyclerLista5));
        recyclerListas.add(view.findViewById(R.id.recyclerLista6));

        layoutManager1 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        layoutManager2 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);


        items = new ArrayList<>();
        adapter = new Adapter(getContext(), items);
        adapter.setItemClickListener(this);
        items2 = new ArrayList<>();
        adapter2 = new Adapter(getContext(), items2);
        adapter2.setItemClickListener(this);


        numero = 7; // El número que deseas pasar al archivo 7

        nombreListas();
        arraylong();

        layoutListaPersonalizada1 = view.findViewById(R.id.layout_ListaPersonalizada1);
        layoutListaPersonalizada2 = view.findViewById(R.id.layout_ListaPersonalizada2);
        layoutListaPersonalizada3 = view.findViewById(R.id.layout_ListaPersonalizada3);
        layoutListaPersonalizada4 = view.findViewById(R.id.layout_ListaPersonalizada4);
        layoutListaPersonalizada5 = view.findViewById(R.id.layout_ListaPersonalizada5);
        layoutListaPersonalizada6 = view.findViewById(R.id.layout_ListaPersonalizada6);
        layoutListaPersonalizada7 = view.findViewById(R.id.layout_ListaPersonalizada7);
        layoutListaPersonalizada8 = view.findViewById(R.id.layout_ListaPersonalizada8);
        layoutListaPersonalizada9 = view.findViewById(R.id.layout_ListaPersonalizada9);
        layoutListaPersonalizada10 = view.findViewById(R.id.layout_ListaPersonalizada10);

        // ------------- AL PULSAR BOTÓN "NUEVA LISTA" SE ABRE EL DIALOG -----------------------

        btnNuevaLista = view.findViewById(R.id.btn_nuevaLista);

        btnNuevaLista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarDialogo();
            }
        });


        return view;

    }

    @Override
    public void onItemClick(int position) {
        // Obtén el ID del juego seleccionado en función de la posición en la lista
        String gameId = items.get(position).getId();
        String nombre = items.get(position).getTitulo();
        String portadaUrl = items.get(position).getFotoJuegoUrl();

        // Crea un Intent para abrir la Activity deseada
        Intent intent = new Intent(getActivity(), pestana_juego.class);

        // Pasa los datos del juego al Intent
        intent.putExtra("gameId", gameId);
        intent.putExtra("titulo", nombre);
        intent.putExtra("portada", portadaUrl);


        startActivity(intent);


    }

    public void mostrarDialogo() {
        fragment_crearlista dialogFragment = new fragment_crearlista();
        dialogFragment.setOnListaCreadaListener(this);
        //   dialogFragment.setOnListaCreadaListener(this);
        dialogFragment.show(getChildFragmentManager(), "custom_dialog");
    }

    @Override
    public void onListaCreada(String nombre) {

        if (listasPersonalizadasLibres.get(0)) {
            TextView tituloLista = getView().findViewById(R.id.tituloListaPersonalizada1);
            tituloLista.setText(nombre.toUpperCase(Locale.ROOT));
            layoutListaPersonalizada1.setVisibility(View.VISIBLE);
            listasPersonalizadasLibres.set(0, false);

        } else if (listasPersonalizadasLibres.get(1)) {
            TextView tituloLista = getView().findViewById(R.id.tituloListaPersonalizada2);
            tituloLista.setText(nombre.toUpperCase(Locale.ROOT));
            layoutListaPersonalizada2.setVisibility(View.VISIBLE);
            listasPersonalizadasLibres.set(1, false);
        } else if (listasPersonalizadasLibres.get(2)) {
            TextView tituloLista = getView().findViewById(R.id.tituloListaPersonalizada3);
            tituloLista.setText(nombre.toUpperCase(Locale.ROOT));
            layoutListaPersonalizada3.setVisibility(View.VISIBLE);
            listasPersonalizadasLibres.set(2, false);
        } else if (listasPersonalizadasLibres.get(3)) {
            TextView tituloLista = getView().findViewById(R.id.tituloListaPersonalizada4);
            tituloLista.setText(nombre.toUpperCase(Locale.ROOT));
            layoutListaPersonalizada4.setVisibility(View.VISIBLE);
            listasPersonalizadasLibres.set(3, false);
        } else if (listasPersonalizadasLibres.get(4)) {
            TextView tituloLista = getView().findViewById(R.id.tituloListaPersonalizada5);
            tituloLista.setText(nombre.toUpperCase(Locale.ROOT));
            layoutListaPersonalizada5.setVisibility(View.VISIBLE);
            listasPersonalizadasLibres.set(4, false);
        } else if (listasPersonalizadasLibres.get(5)) {
            TextView tituloLista = getView().findViewById(R.id.tituloListaPersonalizada6);
            tituloLista.setText(nombre.toUpperCase(Locale.ROOT));
            layoutListaPersonalizada6.setVisibility(View.VISIBLE);
            listasPersonalizadasLibres.set(5, false);
        } else if (listasPersonalizadasLibres.get(6)) {
            TextView tituloLista = getView().findViewById(R.id.tituloListaPersonalizada7);
            tituloLista.setText(nombre.toUpperCase(Locale.ROOT));
            layoutListaPersonalizada7.setVisibility(View.VISIBLE);
            listasPersonalizadasLibres.set(6, false);
        } else if (listasPersonalizadasLibres.get(7)) {
            TextView tituloLista = getView().findViewById(R.id.tituloListaPersonalizada8);
            tituloLista.setText(nombre.toUpperCase(Locale.ROOT));
            layoutListaPersonalizada8.setVisibility(View.VISIBLE);
            listasPersonalizadasLibres.set(7, false);
        } else if (listasPersonalizadasLibres.get(8)) {
            TextView tituloLista = getView().findViewById(R.id.tituloListaPersonalizada9);
            tituloLista.setText(nombre.toUpperCase(Locale.ROOT));
            layoutListaPersonalizada9.setVisibility(View.VISIBLE);
            listasPersonalizadasLibres.set(8, false);
        } else if (listasPersonalizadasLibres.get(9)) {
            TextView tituloLista = getView().findViewById(R.id.tituloListaPersonalizada10);
            tituloLista.setText(nombre.toUpperCase(Locale.ROOT));
            layoutListaPersonalizada10.setVisibility(View.VISIBLE);
            listasPersonalizadasLibres.set(9, false);
        }

    }

    fragment_listas contexto = this;

    public void arraylong() {
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference userRef = db.collection("Users").document(uid);

        userRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        List<DocumentReference> listasAr = (List<DocumentReference>) document.get("listas");
                        if (listasAr != null && !listasAr.isEmpty()) {
                            DocumentReference listaRef = listasAr.get(0); // Obtén el primer elemento de la lista
                            listaRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.isSuccessful()) {
                                        DocumentSnapshot listaDocument = task.getResult();
                                        if (listaDocument.exists()) {
                                            String nombreLista = listaDocument.getString("name");
                                            List<Number> games = (List<Number>) listaDocument.get("games");

                                            for (Number game : games) {
                                                int juegoID = game.intValue();
                                                juegosInt.add(juegoID);
                                            }

                                            TextView textView = tv_titulolista1; // Obtén el TextView correspondiente a la primera lista
                                            textView.setText(nombreLista);

                                            RecyclerView recyclerLista = view.findViewById(R.id.recyclerLista1); // Obtén el RecyclerView correspondiente a la primera lista
                                            recyclerLista.setLayoutManager(layoutManager1);
                                            recyclerLista.setAdapter(adapter);

                                            Log.d("arrayjuegos", "" + juegosInt);
                                            for (int idUnJuego : juegosInt) {
                                                String esaId = "" + idUnJuego;
                                                GameInfoTask gameInfoTask = new GameInfoTask(contexto, numero, esaId);
                                                gameInfoTask.execute();
                                            }
                                        } else {
                                            // El documento de la lista no existe
                                        }
                                    } else {
                                        // Error al obtener el documento de la lista
                                    }
                                }
                            });
                        } else {
                            // La lista está vacía
                        }
                    } else {
                        // El documento del usuario no existe
                    }
                } else {
                    // Error al obtener el documento del usuario
                }
            }
        });
    }



    @Override
    public void onGameInfoReceived(String response) {
        if (response != null) {
            try {
                JSONArray jsonArray = new JSONArray(response);

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject gameObj = jsonArray.getJSONObject(i);
                    String id = gameObj.getString("id");
                    String name = gameObj.getString("name");
                    JSONObject coverObj = gameObj.getJSONObject("cover");
                    String coverUrl = "https:" + coverObj.getString("url");
                    String palabraBuscada = "t_thumb";
                    String nuevaPalabra = "t_1080p";

                    String portadaGrande = coverUrl.replace(palabraBuscada, nuevaPalabra);

                    items.add(new ItemJuego(name, portadaGrande, id));

                }


                adapter.notifyDataSetChanged();

                System.out.println("PASA POR AQUI");


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onGameInfoError() {
    }

    public void nombreListas() {
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference userRef = db.collection("Users").document(uid);

        userRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        List<DocumentReference> listasAr = (List<DocumentReference>) document.get("listas");
                        int numListas = Math.min(9, listasAr.size()); // Obtener el mínimo entre 7 y el tamaño de la lista
                        List<TextView> textViews = new ArrayList<>(); // ArrayList para almacenar los TextViews

                        // Agregar los TextViews correspondientes al ArrayList
                        textViews.add(tv_titulolista1);
                        textViews.add(tv_titulolista2);
                        textViews.add(tv_titulolista3);
                        textViews.add(tv_titulolista4);
                        textViews.add(tv_titulolista5);
                        textViews.add(tv_titulolista6);
                        textViews.add(tituloListaPersonalizada1);
                        textViews.add(tituloListaPersonalizada2);
                        textViews.add(tituloListaPersonalizada3);

                        for (int i = 0; i < numListas; i++) {
                            DocumentReference listaRef = listasAr.get(i);
                            int finalI = i;
                            listaRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.isSuccessful()) {
                                        DocumentSnapshot listaDocument = task.getResult();
                                        if (listaDocument.exists()) {
                                            String nombreLista = listaDocument.getString("name");

                                            // Verificar si la lista está vacía antes de asignar el nombre al TextView
                                            if (!nombreLista.isEmpty()) {
                                                // Obtener el TextView correspondiente según el índice
                                                TextView textView = textViews.get(finalI);
                                                textView.setText(nombreLista);
                                            }

                                            // Verificar si es la lista número 7 y no está vacía
                                            if (finalI == 6 && !nombreLista.isEmpty()) {
                                                layoutListaPersonalizada1.setVisibility(View.VISIBLE);
                                            }
                                            if (finalI == 7 && !nombreLista.isEmpty()) {
                                                layoutListaPersonalizada2.setVisibility(View.VISIBLE);
                                            }
                                            if (finalI == 8 && !nombreLista.isEmpty()) {
                                                layoutListaPersonalizada3.setVisibility(View.VISIBLE);
                                            }
                                        } else {
                                            // El documento de la lista no existe
                                        }
                                    } else {
                                        // Error al obtener el documento de la lista
                                    }
                                }
                            });
                        }
                    } else {
                        // El documento del usuario no existe
                    }
                } else {
                    // Error al obtener el documento del usuario
                }
            }
        });
    }



}