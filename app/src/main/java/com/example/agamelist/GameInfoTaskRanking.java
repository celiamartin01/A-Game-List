package com.example.agamelist;

import android.os.AsyncTask;

import com.example.agamelist.scrollLayout.Adapter;
import com.example.agamelist.scrollLayout.ItemJuego;

import org.joda.time.DateTime;

import java.util.List;


public class GameInfoTaskRanking extends AsyncTask<Void, Void, List<ItemJuego>> {
    private List<ItemJuego> itemList;
    private int numero;
    private Adapter adapter;

    public GameInfoTaskRanking(List<ItemJuego> itemList, int numero, Adapter adapter) {
        this.itemList = itemList;
        this.numero = numero;
        this.adapter = adapter;
    }



    @Override
    protected List<ItemJuego> doInBackground(Void... voids) {


        String endpoint;
        String query;

        switch (numero) {

            case 1:
                endpoint = "games"; //
                query = "fields id,name,cover.url,rating; sort rating desc;where summary != null & genres != null & keywords != null & player_perspectives != null & rating != null & cover.url != null ; limit 5;"; // saco el nombre, resumen...
                break;

            default:
                endpoint = ""; //
                query = "";
                break;
        }

        return itemList;
    }

    @Override
    protected void onPostExecute(List<ItemJuego> itemList) {
        adapter.notifyDataSetChanged();
    }

    public interface GameInfoCallback {
        void onGameInfoReceived(String response);
        void onGameInfoError();
    }
}