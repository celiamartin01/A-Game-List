package com.example.agamelist.fragments_pprincipal;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.agamelist.GameInfoTask;
import com.example.agamelist.GameInfoTaskRanking;
import com.example.agamelist.R;
import com.example.agamelist.pestana_juego;
import com.example.agamelist.scrollLayout.Adapter;
import com.example.agamelist.scrollLayout.ItemJuego;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class fragment_ranking extends Fragment implements GameInfoTask.GameInfoCallback, Adapter.ItemClickListener {
    private List<ItemJuego> items,itemsPs5;
    private Adapter adapter, adapterPs5;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_ranking, container, false);
        // Inflate the layout for this fragment


        // ----------------------- RECYCLER "MEJORES JUEGOS SEGÚN LA COMUNIDAD" -----------------------
        RecyclerView recyclerRanking = view.findViewById(R.id.recyclerRankingPrincip);
        recyclerRanking.setBackground(null);

        items = new ArrayList<>();
        adapter = new Adapter(getContext(), items);
        adapter.setItemClickListener(this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerRanking.setLayoutManager(layoutManager);
        recyclerRanking.setAdapter(adapter);

        GameInfoTask gameInfoTask = new GameInfoTask(this, 9);
        gameInfoTask.execute();

        Log.d("items ranking", "estos son: "+items);

        RecyclerView recyclerPs5 = view.findViewById(R.id.recyclerRankingPs5);

        // ----------------------- RECYCLER "MEJORES JUEGOS SEGÚN LA COMUNIDAD" -----------------------

        List<ItemJuego> itemsPs5 = new ArrayList<ItemJuego>();
        itemsPs5.add(new ItemJuego("Goat Simulator 3", "https://images.igdb.com/igdb/image/upload/t_1080p/co4uks.jpg","204360"));
        itemsPs5.add(new ItemJuego("The Last of Us Part I", "https://images.igdb.com/igdb/image/upload/t_1080p/co5xex.jpg","204350"));
        itemsPs5.add(new ItemJuego("The Elder Scrolls V: Skyrim - Anniversary Edition", "https://images.igdb.com/igdb/image/upload/t_1080p/co3lyu.jpg","165192"));
        itemsPs5.add(new ItemJuego("The Witcher 3: Wild Hunt - Complete Edition", "https://images.igdb.com/igdb/image/upload/t_thumb/co5uct.jpg ", "119402"));
        itemsPs5.add(new ItemJuego("CrossCode", "https://images.igdb.com/igdb/image/upload/t_1080p/co28wy.jpg", "35282"));
        itemsPs5.add(new ItemJuego("The Binding of Isaac: Repentance", "https://images.igdb.com/igdb/image/upload/t_1080p/co5dfa.jpg", "109241"));

        LinearLayoutManager linearPs5 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerPs5.setLayoutManager(linearPs5);
        recyclerPs5.setAdapter(new Adapter(getContext(), itemsPs5));


        // ----------------------- RECYCLER "MEJORES JUEGOS ESPAÑOLES" -----------------------

        RecyclerView recyclerAventura = view.findViewById(R.id.recyclerMejoresAventuras);

        List<ItemJuego> itemsAdventure = new ArrayList<ItemJuego>();
        itemsAdventure.add(new ItemJuego("Goblin Sword", "https://images.igdb.com/igdb/image/upload/t_1080p/co1zdd.jpg", "92497"));
        itemsAdventure.add(new ItemJuego("The Legend of Zelda: Tears of the Kingdom", "https://images.igdb.com/igdb/image/upload/t_1080p/co5vmg.jpg", "119388"));
        itemsAdventure.add(new ItemJuego("Metal Gear Solid: Snake Eater 3D", "https://images.igdb.com/igdb/image/upload/t_1080p/co62i0.jpg", "21073"));
        itemsAdventure.add(new ItemJuego("Metal Gear Solid: The Legacy Collection", "https://images.igdb.com/igdb/image/upload/t_1080p/co45jf.jpg", "20196"));
        itemsAdventure.add(new ItemJuego("Goat Simulator 3", "https://images.igdb.com/igdb/image/upload/t_1080p/co4uks.jpg", "204360"));
        itemsPs5.add(new ItemJuego("The Last of Us Part I", "https://images.igdb.com/igdb/image/upload/t_1080p/co5xex.jpg", "204350"));

        LinearLayoutManager linearEspana = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerAventura.setLayoutManager(linearEspana);
        recyclerAventura.setAdapter(new Adapter(getContext(), itemsAdventure));

        return view;
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
                    // Actualiza la vista con los nuevos elementos
                    adapter.notifyDataSetChanged();
                }
            }catch (JSONException e) {
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
