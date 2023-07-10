package com.example.agamelist.fragments_pprincipal;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.agamelist.GameInfoTask;
import com.example.agamelist.pestana_juego;
import com.example.agamelist.R;
import com.example.agamelist.scrollLayout.Adapter;
import com.example.agamelist.scrollLayout.ItemJuego;
import com.example.agamelist.scrollLayoutResenasRecientes.AdapterResena;
import com.example.agamelist.scrollLayoutResenasRecientes.ItemResena;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class fragment_noticias extends Fragment implements GameInfoTask.GameInfoCallback, Adapter.ItemClickListener {

    private List<ItemJuego> items;
    private Adapter adapter;

    int numero = 0; // numero para elegir la query
    ImageView anuncio;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_noticias, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewNoticia);
        RecyclerView recyclerViewResena = view.findViewById(R.id.recyclerViewResena);
        anuncio = view.findViewById(R.id.iv_anuncio);
        anuncio.setImageResource(R.drawable.anuncio);
        anuncio.setScaleType(ImageView.ScaleType.FIT_XY);
        items = new ArrayList<>();
        adapter = new Adapter(getContext(), items);
        adapter.setItemClickListener(this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);


        List<ItemResena> itemsResena = new ArrayList<>();
        itemsResena.add(new ItemResena("Marcos Martín", "@marquitos22", "Amo este juego, el nivel de personalización y builds para pelear son increíbles, además tantos caminos, quests y jefes me dan un entusiasmo que no había encontrado en un juego durante ya mucho tiempo. \n" +
                "No tengo mucho tiempo para jugar así que lo he estado jugando a pocos, llevo 100 horas de juego repartidas en 2 meses y recién voy a pelear con la silueta dorada de Godfrey con una build increíble que me armé, es simplemente épico y emocionante. \n" +
                "Recomiendo que si tienen tiempo jueguen esto sin una guía, yo tuve que consultar algunas más que todo para encontrar ubicaciones, ya que como no tengo mucho tiempo para jugar, no podía pasarme 2 horas explorando, lo que me arruinó muchas sorpresas, creo que lo mejor es jugar la primer run sin ninguna guía, es lo mejor. ",
                "https://images.igdb.com/igdb/image/upload/t_1080p/co4jni.jpg", 5, 9));
        itemsResena.add(new ItemResena("Lucía Pérez", "@lucy98", "Es un gran juego. Yo odio los RPGs por turnos. Básicamente conocí esta saga por el Persona 4 Arena (un juego de pelea), y quise darle una oportunidad a este tipo de juegos " +
                "con la quinta entrega. No me arrepiento de nada. Un buen sistema de combate, diversos personajes con diversas técnicas, un sistema interesante de fortalecimiento, y una gran banda sonora que hacen emocionantes todos los encuentros, incluso los de grindeo. Altamente recomendado.",
                "https://images.igdb.com/igdb/image/upload/t_1080p/co1nic.jpg", 5, 13));
        itemsResena.add(new ItemResena("Susana Romero", "@susiRomero", "El juego que pudo ser la sorpresa de toda la generación y lamentablemente quedó en la nada:\n" +
                "\n" +
                " La idea del juego es genial y la historia si bien no es de las mejores cumple su objetivo. Pero queda meramente ahí. \n" +
                "\n" +
                " La ciudad tiene un tamaño respetable y varios lugares para ir pero una vez que terminas la historia principal no tienes muchas opciones que realizar.",
                "https://images.igdb.com/igdb/image/upload/t_1080p/co4hk8.jpg", 3, 20));



        LinearLayoutManager resenaLayoutManager = new LinearLayoutManager(getContext());
        recyclerViewResena.setLayoutManager(resenaLayoutManager);
        recyclerViewResena.setAdapter(new AdapterResena(getContext(), itemsResena));

        numero = 4; // El número que deseas pasar al archivo 2
        GameInfoTask gameInfoTask = new GameInfoTask(this, numero);
        gameInfoTask.execute();


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

    @Override
    public void onGameInfoReceived(String response) {
        if (response != null) {
            try {
                JSONArray jsonArray = new JSONArray(response);

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject gameObj = jsonArray.getJSONObject(i);


                    if(numero == 4){
                        String id = gameObj.getString("id");
                        String name = gameObj.getString("name");
                        JSONObject coverObj = gameObj.getJSONObject("cover");
                        String coverUrl = "https:" + coverObj.getString("url");
                        String palabraBuscada = "t_thumb";
                        String nuevaPalabra = "t_1080p";

                        String portadaGrande = coverUrl.replace(palabraBuscada, nuevaPalabra);

                        items.add(new ItemJuego(name, portadaGrande, id));
                    }
                    if(numero == 5){
                        String id = gameObj.getString("id");
                        String name = gameObj.getString("name");
                        JSONObject coverObj = gameObj.getJSONObject("cover");
                        String coverUrl = "https:" + coverObj.getString("url");

                        String resumen = gameObj.getString("summary");

                        Log.d("prueba", "el resumen en este paso es asi: " + resumen);

                        // Obtener los datos adicionales del juego
                        JSONArray similarGamesArray = gameObj.getJSONArray("similar_games");
                        float rating = (float) gameObj.getDouble("rating");
                        JSONArray genresArray = gameObj.getJSONArray("genres");
                        JSONArray keywordsArray = gameObj.getJSONArray("keywords");
                        JSONArray perspectivesArray = gameObj.getJSONArray("player_perspectives");

                        // Crear listas para almacenar los id de juegosSimilares y los nombres de los géneros, palabras clave y perspectivas de jugador
                        List<String> juegosSimilares = new ArrayList<>();
                        List<String> genreList = new ArrayList<>();
                        List<String> keywordList = new ArrayList<>();
                        List<String> perspectiveList = new ArrayList<>();

                        // Recorrer el JSONArray y agregar los IDs al ArrayList
                        for (int x = 0; x < similarGamesArray.length(); x++) {
                            juegosSimilares.add(similarGamesArray.getString(i));
                        }

                        // Recorrer los arrays y obtener los nombres correspondientes
                        for (int y = 0; y < genresArray.length(); y++) {
                            JSONObject genreObj = genresArray.getJSONObject(i);
                            String genreName = genreObj.getString("name");
                            genreList.add(genreName);
                        }

                        for (int x = 0; x < keywordsArray.length(); x++) {
                            JSONObject keywordObj = keywordsArray.getJSONObject(i);
                            String keywordName = keywordObj.getString("name");
                            keywordList.add(keywordName);
                        }

                        for (int x = 0; x < perspectivesArray.length(); x++) {
                            JSONObject perspectiveObj = perspectivesArray.getJSONObject(i);
                            String perspectiveName = perspectiveObj.getString("name");
                            perspectiveList.add(perspectiveName);
                        }

                        String palabraBuscada = "t_thumb";
                        String nuevaPalabra = "t_1080p";

                        String portadaGrande = coverUrl.replace(palabraBuscada, nuevaPalabra);
                        //comprobar datos antes de mandarlos
                        Log.d("rating", "ESTE era EL rating: "+rating);
                        Log.d("genreList", "ESTE era EL genreList: "+genreList);

                        // Crear el objeto ItemJuego con todos los datos recopilados
                        items.add(new ItemJuego(name, portadaGrande, id, juegosSimilares, rating, resumen, genreList, keywordList, perspectiveList));
                    }

                }

                // Actualiza la vista con los nuevos elementos
                adapter.notifyDataSetChanged();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onGameInfoError() {
        // Maneja el caso en el que la solicitud a la API falla
    }
}

