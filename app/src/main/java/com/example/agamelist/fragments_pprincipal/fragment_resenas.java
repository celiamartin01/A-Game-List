
package com.example.agamelist.fragments_pprincipal;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.agamelist.GameInfoTask;
import com.example.agamelist.GameInfoTaskResenia;
import com.example.agamelist.R;
import com.example.agamelist.scrollResenasPrincipal.AdapterResenaPrincipal;
import com.example.agamelist.scrollResenasPrincipal.ItemResenaPrincipal;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
/*
public class fragment_resenas extends Fragment implements SearchView.OnQueryTextListener, GameInfoTask.GameInfoCallback, AdapterPechaJuegos.ItemClickListener  {

    SearchView searchView;
    AdapterResenaPrincipal adapter;
    List<ItemResenaPrincipal> itemsRes;

    String palabra = "";

    String name;
    String arrobaUser;
    String review;
    double rate;
    int fotoUsu;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_resenas, container, false);
        searchView = view.findViewById(R.id.searchbarResena);

        searchView.setOnQueryTextListener(this);





        RecyclerView recyclerView = view.findViewById(R.id.recyclerResenaPrincip);

        itemsRes = new ArrayList<ItemResenaPrincipal>();
        /*itemsRes.add(new ItemResenaPrincipal("Juanjogamer05", "juajitoelmejor", "El fifa es increíble jaja tengo la camiseta de " +
                "MBappé q es el mejor, se le ve en hd en el juego parece de vd está guapo. El fifa es increíble jaja tengo la camiseta de " +
                "MBappé q es el mejor, se le ve en hd en el juego parece de vd está guapo. El fifa es increíble jaja tengo la camiseta de " +
                "MBappé q es el mejor, se le ve en hd en el juego parece de vd está guapo", R.drawable.foto_perfil, R.drawable.fifa, 5, 38));
        itemsRes.add(new ItemResenaPrincipal("MarioVaquerizo", "marioyluigi", "Me vi la pelicula de Mario Brothers el otro día y el " +
                "juego no es como la peli, BOwser no canta y Peaches solo va de castillo en castillo. Una decepción.", R.drawable.beti, R.drawable.mario_odyssey, 2, 38));
        itemsRes.add(new ItemResenaPrincipal("No se me ocurre", "noloseMecanso", "Estoy hasta el coño de este juego se lo compré a Samu " +
                "por su cumpleaños una vez esque vaya entre esto y lo de la camiseta del Madrid de 100 pavos pfff puto amor lo que le hace a uno en la cabeza",
                R.drawable.yo, R.drawable.god_of_war, 1, 8));*/
/*
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new AdapterResenaPrincipal(getContext(), itemsRes);
        recyclerView.setAdapter(adapter);

        obtenerReseñas();

        return view;


    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        adapter.filtrado(newText);
        return false;
    }


    fragment_resenas contexto = this;
    public void obtenerReseñas() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        final AdapterResenaPrincipal internalAdapter = this.adapter; // Nueva variable final para el adaptador

        db.collection("Reviews")
                .orderBy("date", Query.Direction.DESCENDING)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Map<String, Object> reviewData = document.getData();

                                // Extraer los campos de cada review
                                rate = ((Long) reviewData.get("rate")).doubleValue();
                                review = (String) reviewData.get("review");
                                Timestamp date = (Timestamp) reviewData.get("date");
                                DocumentReference userRef = (DocumentReference) reviewData.get("user");
                                long game = (long) reviewData.get("game");
                                palabra = ""+game;

                                // Obtener los datos del usuario (asumiendo que tienes una colección de usuarios)
                                userRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        if (task.isSuccessful()) {
                                            DocumentSnapshot userDoc = task.getResult();
                                            if (userDoc.exists()) {
                                                name = userDoc.getString("name");
                                                arrobaUser = userDoc.getString("user");
                                                fotoUsu = userDoc.getLong("profilePic").intValue();

                                                GameInfoTask gameInfoTask = new GameInfoTask(contexto, 8, palabra);
                                                gameInfoTask.execute();


                                                // Pasar los datos a los campos (Creo que con hacerlos un array sirve pero no se)
                                                internalAdapter.notifyDataSetChanged();

                                            }
                                        }
                                    }
                                });
                            }
                        } else {
                            Log.d(TAG, "Error getting reviews: ", task.getException());
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
                    String nameGame = gameObj.getString("nameGame");
                    JSONObject coverObj = gameObj.getJSONObject("cover");
                    String coverUrl = "https:" + coverObj.getString("url");

                    //cambio la url de la portada para que sea de portada grande
                    String palabraBuscada = "t_thumb";
                    String nuevaPalabra = "t_screenshot_med";
                    String portadaGrande = coverUrl.replace(palabraBuscada, nuevaPalabra);

                    itemsRes.add(new ItemResenaPrincipal(id, name, nameGame, arrobaUser, review, fotoUsu, portadaGrande, rate));
                }

                //adapter.notifyDataSetChanged(); // Actualizar el adaptador


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
*//*
        // Obtén el ID del juego seleccionado en función de la posición en la lista
        String gameId = itemsRes.get(position).getIdJuego();
        String nombreUser = itemsRes.get(position).getNombreUser();
        String nombreGame = itemsRes.get(position).getNombreGame();
        String portadaUrl = itemsRes.get(position).getFotoJuego();
        String arroba = itemsRes.get(position).getArroba();
        String txtResena = itemsRes.get(position).getTxtResena();
        int fotoPerfil =itemsRes.get(position).getFotoPerfil();
        String fotoJuego = itemsRes.get(position).getFotoJuego();
        double puntuacion =itemsRes.get(position).getPuntuacion();



        // Crea un Intent para abrir la Activity deseada
        Intent intent = new Intent(getActivity(), main_resenia.class);

        // Pasa los datos del juego al Intent
        intent.putExtra("gameId", gameId);
        intent.putExtra("nombreUser", nombreUser);
        intent.putExtra("nombreGame", nombreGame);
        intent.putExtra("portadaUrl", portadaUrl);
        intent.putExtra("arroba", arroba);
        intent.putExtra("txtResena", txtResena);
        intent.putExtra("fotoPerfil", fotoPerfil);
        intent.putExtra("fotoJuego", fotoJuego);
        intent.putExtra("puntuacion", puntuacion);


        startActivity(intent);
    }
}
*/


public class fragment_resenas extends Fragment implements SearchView.OnQueryTextListener,  GameInfoTaskResenia.GameInfoCallback  {

    SearchView searchView;
    AdapterResenaPrincipal adapter;
    List<ItemResenaPrincipal> itemsRes;
    fragment_resenas contexto = this;

    String nombreVideojuego ="";
    String portadaJuego="";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_resenas, container, false);
        searchView = view.findViewById(R.id.searchbarResena);

        searchView.setOnQueryTextListener(this);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerResenaPrincip);

        itemsRes = new ArrayList<ItemResenaPrincipal>();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new AdapterResenaPrincipal(getContext(), itemsRes);
        recyclerView.setAdapter(adapter);

        obtenerReseñas();

        return view;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        adapter.filtrado(newText);
        return false;
    }

    public void obtenerReseñas() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        final AdapterResenaPrincipal adapterInterno = this.adapter; // Nueva variable final para el adaptador

        db.collection("Reviews")
                .orderBy("date", Query.Direction.DESCENDING)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            int count = 0;
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Map<String, Object> reviewData = document.getData();
                                count++;
                                // Extraer los campos de cada review
                                int rate = ((Long) reviewData.get("rate")).intValue();
                                String resenia = (String) reviewData.get("review");
                                Timestamp date = (Timestamp) reviewData.get("date");
                                DocumentReference userRef = (DocumentReference) reviewData.get("user");
                                long game = (long) reviewData.get("game");
                                String fotoJuego = (String) reviewData.get("foto");

                                String idJuego = ""+ game;
                                Log.d("idJuego", "esta: "+ idJuego);




                                // Obtener los datos del usuario (asumiendo que tienes una colección de usuarios)
                                userRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        if (task.isSuccessful()) {
                                            DocumentSnapshot userDoc = task.getResult();
                                            if (userDoc.exists()) {
                                                String name = userDoc.getString("name");
                                                String user = userDoc.getString("user");
                                                int foto = userDoc.getLong("profilePic").intValue();

                                                //INTENTO FALLIDO, TENEMOS QUE METER LA FOTO A MANO
                                                //GameInfoTaskResenia gameInfoTask = new GameInfoTaskResenia(contexto, 8, idJuego, name, user, resenia, foto, rate, itemsRes, adapterInterno );
                                                //GameInfoTaskResenia gameInfoTask = new GameInfoTaskResenia(contexto, 8, idJuego );
                                                //gameInfoTask.execute();


                                                    itemsRes.add(new ItemResenaPrincipal(name, user, resenia, foto, fotoJuego, rate, idJuego, nombreVideojuego));
                                                    adapterInterno.notifyDataSetChanged();
                                            }
                                        }
                                    }
                                });
                            }
                        } else {
                            Log.d(TAG, "Error getting reviews: ", task.getException());
                        }
                    }
                });
    }

    @Override
    public void onGameInfoReceived(String response) {

    }

    @Override
    public void onGameInfoError() {

    }
}
