package com.example.agamelist;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.util.Log;
import android.view.ViewManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.res.ResourcesCompat;

import org.joda.time.DateTime;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class GameInfoTaskParaLayout extends AsyncTask<Void, Void, String> {
    private final GameInfoCallback callback;
    private final int numero; // Variable de instancia para almacenar el número
    private String gameId;
    private Context context; // Agrega una variable de instancia para el contexto

    public GameInfoTaskParaLayout(GameInfoCallback callback, int numero, String gameId, Context context) {
        this.callback = callback;
        this.numero = numero;
        this.gameId = gameId;
        this.context = context;
    }



    @Override
    protected String doInBackground(Void... voids) {


        String endpoint;
        String query;

        // Utiliza la variable numero en el switch para determinar endpoint y query
        switch (numero) {
            case 1:
                endpoint = "games"; //
                query = "fields id,name,cover.url,similar_games,rating,summary,genres.name, keywords.name, platforms.name, player_perspectives.name; where id = " + gameId +";"; // saco el nombre, resumen...
                break;

            default:
                endpoint = ""; //
                query = "";
                break;
        }

        return IGDBApiClient.makeRequest(endpoint, query);
    }

    @Override
    protected void onPostExecute(String response) {
        if (response != null) {
            callback.onGameInfoReceived(response);

            try {
                // Actualizar resumen
                JSONArray jsonArray = new JSONArray(response);
                JSONObject jsonResponse = jsonArray.getJSONObject(0);
                String resumen = jsonResponse.getString("summary");
                callback.updateSummary(resumen);

                // Actualizar rating
                String rating = jsonResponse.getString("rating");
                callback.updateRating(rating);

                JSONArray genresArray = jsonResponse.getJSONArray("genres");
                JSONArray keywordsArray = jsonResponse.getJSONArray("keywords");
                JSONArray platformsArray = jsonResponse.getJSONArray("platforms");
                JSONArray perspectivesArray = jsonResponse.getJSONArray("player_perspectives");

                List<String> tagList1 = new ArrayList<>();
                List<String> tagList2 = new ArrayList<>();

                int tope;
                int limiteTope=2;
                if (genresArray.length()>2){
                    tope=limiteTope;
                }else{
                    tope= genresArray.length();
                }
                for (int y = 0; y < tope; y++) {
                    JSONObject genreObj = genresArray.getJSONObject(y);
                    String genreName = genreObj.getString("name");
                    tagList1.add(genreName);
                }

                if (keywordsArray.length()>2){
                    tope=limiteTope;
                }else{
                    tope= keywordsArray.length();
                }
                for (int y = 0; y < tope; y++) {
                    JSONObject keywordsObj = keywordsArray.getJSONObject(y);
                    String keyword = keywordsObj.getString("name");
                    tagList1.add(keyword);
                }

                if (platformsArray.length()>2){
                    tope=limiteTope;
                }else{
                    tope= platformsArray.length();
                }
                 for (int y = 0; y < tope; y++) {
                    JSONObject platformsObj = platformsArray.getJSONObject(y);
                    String platform = platformsObj.getString("name");
                    tagList2.add(platform);
                }

                if (perspectivesArray.length()>2){
                    tope=limiteTope;
                }else{
                    tope= perspectivesArray.length();
                }
                for (int y = 0; y < tope; y++) {
                    JSONObject perspectiveObj = perspectivesArray.getJSONObject(y);
                    String perspective = perspectiveObj.getString("name");
                    tagList2.add(perspective);
                }



                callback.onLinearLayoutUpdated(tagList1,tagList2);


            } catch (JSONException e) {
                e.printStackTrace();
                callback.onGameInfoError();
            }

        } else {
            callback.onGameInfoError();
        }
    }

    public interface GameInfoCallback {
        void onGameInfoReceived(String response);
        void onGameInfoError();

        void updateSummary(String resumen);
        void updateRating(String rating);
        void onLinearLayoutUpdated(List<String> tagList1,List<String> tagList2);
    }
}
