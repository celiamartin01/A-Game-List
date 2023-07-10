package com.example.agamelist.fragments_pprincipal;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.agamelist.GameInfoTask;
import com.example.agamelist.R;
import com.example.agamelist.pestana_juego;
import com.example.agamelist.recycler_pecha_juegos_descubrir.AdapterPechaJuegos;
import com.example.agamelist.scrollLayout.Adapter;
import com.example.agamelist.scrollLayout.ItemJuego;
import com.example.agamelist.scrollLayoutResenasRecientes.AdapterResena;
import com.example.agamelist.scrollLayoutResenasRecientes.ItemResena;
import com.google.android.play.core.integrity.e;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FragmentDescubrir extends Fragment implements GameInfoTask.GameInfoCallback, AdapterPechaJuegos.ItemClickListener {

    int numero;
    private List<ItemJuego> items;
    private AdapterPechaJuegos adapter;
    SearchView buscador;
    String palabra = "a";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_descubrir, container, false);

        items = new ArrayList<>();
        adapter = new AdapterPechaJuegos(getContext(), items);
        adapter.setItemClickListener(this);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerPechaJuegos);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        numero = 4; // El número que deseas pasar al archivo 4
        GameInfoTask gameInfoTask = new GameInfoTask(this, numero, palabra);
        gameInfoTask.execute();

        numero = 6;
        buscador = view.findViewById(R.id.searchBarPechaJuegos);
        buscador.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                filterResults(newText);

                return true;
            }
        });


        return view;


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

                                String palabraBuscada = "t_thumb";
                                String nuevaPalabra = "t_1080p";

                                String portadaGrande = coverUrl.replace(palabraBuscada, nuevaPalabra);
                                Log.d("pruebaDESCUBRIR", "id juego: " + id);
                                Log.d("pruebaDESCUBRIR", "nombre juego: " + name);
                                Log.d("pruebaDESCUBRIR", "url cover: " + coverUrl);

                                items.add(new ItemJuego(name, portadaGrande, id));

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

}