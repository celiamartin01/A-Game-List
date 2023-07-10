package com.example.agamelist;

import android.os.AsyncTask;


import org.joda.time.DateTime;

import java.util.List;


public class GameInfoTask extends AsyncTask<Void, Void, String> {
    private final GameInfoCallback callback;
    private final int numero; // Variable de instancia para almacenar el número
    private String varString;
    private List<Integer> listId;

    // Obtener la fecha actual
    DateTime currentDate = new DateTime();

    // Añadir 1 día a la fecha actual para obtener el día siguiente
    DateTime tomorrowDate = currentDate.plusDays(1);

    long unixTimestamp = currentDate.getMillis() / 1000;
    String fechaHoy = ""+unixTimestamp;

    //DOS CONSTRUCTORES, UNO LLAMA A LOS RECICLE VIEW OTRO A LA PAGINA PERTANA JUEGO
    public GameInfoTask(GameInfoCallback callback, int numero, List<Integer> listId) {
        this.callback = callback;
        this.numero = numero; // Asigna el valor del parámetro al campo numero
        this.listId = listId;
    }

    public GameInfoTask(GameInfoCallback callback, int numero, String varString) {
        this.callback = callback;
        this.numero = numero; // Asigna el valor del parámetro al campo numero
        this.varString = varString;
    }


    public GameInfoTask(GameInfoCallback callback, int numero) {
        this.callback = callback;
        this.numero = numero; // Asigna el valor del parámetro al campo numero
    }




    @Override
    protected String doInBackground(Void... voids) {


        String endpoint;
        String query;

        // Utiliza la variable numero en el switch para determinar endpoint y query
        switch (numero) {
            case 1:
                endpoint = "games?fields=name&search=witcher"; //  CONSULTA
                query = "fields name;"; //
                break;
            case 2:
                endpoint = "games"; //  CONSULTA
                query = "fields id,name,cover.url; search \"witcher\";"; //
                break;
            case 3:
                endpoint = "release_dates"; //
                query = "fields date,game.name,game.cover.url; sort date desc;where game.cover.url != null & date != null & date < "+fechaHoy+";  limit 10;"; //

                break;
            case 4:
                endpoint = "games"; //
                query = "fields id,first_release_date,name,cover.url; sort first_release_date desc;where summary != null & genres != null & keywords != null & player_perspectives != null & rating != null & cover.url != null & first_release_date != null & first_release_date < "+fechaHoy+";  limit 10;"; //
                break;

            case 5:
                endpoint = "games"; //
                query = "fields id,name,cover.url,similar_games,rating,summary,genres.name, keywords.name, player_perspectives.name; where id = " + varString +";"; // saco el nombre, resumen...
                break;

            case 6:
                endpoint = "games";
                query = "fields id,name,cover.url; search \""+varString+"\"; limit 15;"; //busqueda por nombre (para buscador
                break;

            case 7:
                endpoint = "games"; //busqueda para las listas
                query = "fields id,name,cover.url;where summary != null & genres != null & keywords != null & player_perspectives != null & rating != null & cover.url != null & id = "+ varString + ";";
                break;

            case 8:
                endpoint = "games"; //busqueda para reviews
                query = "fields id,name,cover.url;where id = "+ varString + ";";
                break;
            case 9:
                endpoint = "games"; //
                query = "fields id,name,cover.url,rating; sort rating desc;where summary != null & genres != null & keywords != null & player_perspectives != null & rating != null & cover.url != null ; limit 5;"; // saco el nombre, resumen...
                break;
            case 10:
                endpoint = "games"; //
                query = "fields id,name,cover.url,platforms.name; where summary != null & genres != null & keywords != null & platforms != null & rating != null & cover.url != null & platforms.name = PlayStation 5; limit 6;"; // saco el nombre, resumen...
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
        } else {
            callback.onGameInfoError();
        }
    }

    public interface GameInfoCallback {
        void onGameInfoReceived(String response);
        void onGameInfoError();
    }
}