package com.example.agamelist;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.agamelist.fragments_pprincipal.fragment_resenas;
import com.example.agamelist.scrollResenasPrincipal.AdapterResenaPrincipal;
import com.example.agamelist.scrollResenasPrincipal.ItemResenaPrincipal;

import org.joda.time.DateTime;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;


public class GameInfoTaskResenia extends AsyncTask<Void, Void, String> {
    private final GameInfoCallback callback;
    private final int numero;
    private String varString;
    private List<Integer> listId;
    private Context context;

    String idJuego;
    String name;
    String user;
    String resenia;
    int foto;
    double rate;
    List<ItemResenaPrincipal> itemRes;
    AdapterResenaPrincipal adapterInterno;

    // Obtener la fecha actual
    DateTime currentDate = new DateTime();

    // Añadir 1 día a la fecha actual para obtener el día siguiente
    DateTime tomorrowDate = currentDate.plusDays(1);

    long unixTimestamp = currentDate.getMillis() / 1000;
    String fechaHoy = ""+unixTimestamp;


    //DOS CONSTRUCTORES, UNO LLAMA A LOS RECICLE VIEW OTRO A LA PAGINA PERTANA JUEGO
    public GameInfoTaskResenia(GameInfoCallback callback, int numero, List<Integer> listId) {
        this.callback = callback;
        this.numero = numero; // Asigna el valor del parámetro al campo numero
        this.listId = listId;
    }

    public GameInfoTaskResenia(GameInfoCallback callback, int numero, String varString) {
        this.callback = callback;
        this.numero = numero; // Asigna el valor del parámetro al campo numero
        this.varString = varString;
    }


    public GameInfoTaskResenia(GameInfoCallback callback, int numero) {
        this.callback = callback;
        this.numero = numero; // Asigna el valor del parámetro al campo numero
    }

    public GameInfoTaskResenia(GameInfoCallback callback, int numero, String idJuego, String name, String user, String resenia, int foto, double rate, List<ItemResenaPrincipal> itemRes,  AdapterResenaPrincipal adapterInterno) {
        this.callback = callback;
        this.numero = numero;
        this.idJuego = idJuego;
        this.name = name;
        this.user = user;
        this.resenia = resenia;
        this.foto = foto;
        this.rate = rate;
        this.itemRes = itemRes;
        this.adapterInterno = adapterInterno;

    }


    @Override
    protected String doInBackground(Void... voids) {


        String endpoint;
        String query;

        // Utiliza la variable numero en el switch para determinar endpoint y query
        switch (numero) {
            case 1:
                endpoint = "games"; //busqueda para reviews
                query = "fields id,name,cover.url;where id = "+ idJuego + ";";
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