package com.example.agamelist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import com.example.agamelist.scrollLayoutResenasRecientes.ItemResena;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressLint("ResourceAsColor")
public class pestana_juego extends AppCompatActivity implements GameInfoTaskParaLayout.GameInfoCallback {
    //LLAMADA A LA API PARA SACAR LAS ETIQUETAS NECESARIAS
    String[] etiquetas = {"-", "-", "-", "-"};
    String[] etiquetasAbajo = {"-", "-"};

    ArrayList<String> opcionesSpinner = new ArrayList<>(Arrays.asList("Opción 1", "Opción 2", "Opción 3", "Opción 4"));
    Spinner spinner;

    String listaSeleccionada;
    String id;

    Context context = this;

    int numero = 0;

    // Crea un Typeface a partir de la fuente que voy a usar en las etiquetas
    Typeface typeface;

    //  Variables para dialog
    ImageButton btn_atras;
    Dialog dialog;

    TextView textoMismaFranquicia;


    //CREACION DE VARIABLES PARA LA ANIMACION DEL BOTON FLOTANTE ...................................

    boolean clicked;

    FloatingActionButton fab_mas;
    FloatingActionButton fab_res;
    FloatingActionButton fab_list;


    private Animation rotateOpen;

    private Animation getRotateOpen() {
        if (rotateOpen == null) {
            rotateOpen = AnimationUtils.loadAnimation(this, R.anim.rotate_open_anim);
        }
        return rotateOpen;
    }


    private Animation rotateClose;

    private Animation getRotateClose() {
        if (rotateClose == null) {
            rotateClose = AnimationUtils.loadAnimation(this, R.anim.rotate_close_anim);
        }
        return rotateClose;
    }


    private Animation fromBottom;

    private Animation getFromBottom() {
        if (fromBottom == null) {
            fromBottom = AnimationUtils.loadAnimation(this, R.anim.from_bottom_anim);
        }
        return fromBottom;
    }


    private Animation toBottom;

    private Animation getToBottom() {
        if (toBottom == null) {
            toBottom = AnimationUtils.loadAnimation(this, R.anim.to_bottom_anim);
        }
        return toBottom;
    }
    //..............................................................................................

    LinearLayout linearLayoutEtiquetas1;
    LinearLayout linearLayoutEtiquetas2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pestana_juego);

        //Aqui recibo el Id desde fragment noticias
        String gameId = getIntent().getStringExtra("gameId");
        String nombre = getIntent().getStringExtra("titulo");
        String portada = getIntent().getStringExtra("portada");

        //cambio la url de la portada para que sea de portada grande
        String palabraBuscada = "t_thumb";
        String nuevaPalabra = "t_1080p";

        String portadaGrande = portada.replace(palabraBuscada, nuevaPalabra);

        //LLAMADA A LA API PARA LOS DATOS QUE FALTAN
        numero = 1; // El número que deseas pasar al archivo 2
        GameInfoTaskParaLayout gameInfoTask = new GameInfoTaskParaLayout(this, numero, gameId, this);
        gameInfoTask.execute();


        //COLOCAR DATOS DE LA API EN LOS ELEMENTOS:

        // PORTADA
        ImageView imageView = findViewById(R.id.img_pestanajuego_portadaGrande);
        Picasso.get().load(portadaGrande).into(imageView);

        // TITULO
        TextView myTextView = findViewById(R.id.txt_pestanajuego_titulo);
        myTextView.setText(nombre);

        TextView myTextView2 = findViewById(R.id.txt_pestanajuego_titulobarra);
        myTextView2.setText(nombre);




        // -------- RECYCLERVIEW RESEÑAS RECIENTES

        //RecyclerView recyclerViewResenas = findViewById(R.id.recyclerResenasRecientesJuego);       ------------------ descomentar luego

        List<ItemResena> itemsResena = new ArrayList<ItemResena>();
        itemsResena.add(new ItemResena("Pepe", "pepitoGrillo", "Mu guapo la verdad", R.drawable.mario_odyssey, 5, 2));
        itemsResena.add(new ItemResena("Miriam", "miriamLokita", "Me encanta, esque soy la princesa peach jaja soy yo literal, rubia noseq, " +
                "el juego está guay", R.drawable.mario_odyssey, 4, 14));
        itemsResena.add(new ItemResena("Laura", "LauritaGominolas", "Me canso de meter descripciones no se me ocurren ya más cosas, voy a copiar lo" +
                "del fifa: El fifa es increíble jaja tengo la camiseta de MBappé q es el mejor, se le ve en hd en el juego parece de vd está guapo.",
                R.drawable.mario_odyssey, 5, 18));
        itemsResena.add(new ItemResena("Nombre no sé", "nose", "Super Mario Odyssey (スーパーマリオ オデッセイ Sūpā Mario Odessei?) es un" +
                " videojuego de plataformas en tres dimensiones para Nintendo Switch desarrollado y publicado por Nintendo que se lanzó el 27 de octubre de 2017." +
                " La entrega nos introduce a una nueva aventura con Mario y su nuevo aliado Cappy, un ser en forma de sombrero que le permite a Mario poseer " +
                "diversos personajes y objetos, que en su vehículo aéreo la Odyssey se embarcan en una travesía visitando distintos reinos a lo largo del mundo " +
                "para salvar a la Princesa Peach del matrimonio forzado que Bowser planea. Es el decimonoveno título de la serie Super Mario y el séptimo juego " +
                "de plataformas en 3D de Mario Bros.2", R.drawable.mario_odyssey, 5, 35));

        //recyclerViewResenas.setLayoutManager(new LinearLayoutManager(this)); -------------------------------------------------descomentar luego
        //recyclerViewResenas.setAdapter(new AdapterResenaRecientePagJuego(getApplicationContext(), itemsResena)); --------------descomentar luego

        typeface = ResourcesCompat.getFont(this, R.font.advent_pro);
        linearLayoutEtiquetas1 = findViewById(R.id.layout_pestanajuego_etiquetas);
        this.clicked = false;


        btn_atras = findViewById(R.id.btn_pestaniajuego_atras);
        btn_atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(pestana_juego.this, main_pagina_principal.class);
                startActivity(intent);

            }
        });

        //ACCIONES DE LOS BOTONES FLOTANTES------------------------------------------------
        fab_mas = findViewById(R.id.fab_pestanajuego_mas);
        fab_res = findViewById(R.id.fab_pestanajuego_resenia);
        fab_list = findViewById(R.id.fab_pestanajuego_lista);

        fab_mas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Acciones a realizar cuando se hace clic en el botón "+"
                onAddButtonClicked();
            }
        });

        fab_res.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //createPopupWindow(1);
                mostrarPopup(1);


            }
        });

        fab_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //createPopupWindow(2);
                mostrarPopup(2);
            }
        });


        //CREACION DE TEXTO PARA QUE LAS ETIQUETAS SEAN VISIBLES
        List<TextView> textViews = new ArrayList<>();
        for (String palabra : etiquetas) {
            TextView textView = new TextView(this);
            textView.setText(palabra);
            textView.setTextColor(Color.WHITE);
            textView.setTypeface(typeface); // Establece el Typeface en el TextView
            textView.setBackgroundResource(R.color.colorPrimario);
            textView.setPadding(16, 8, 16, 8);
            textViews.add(textView);
            linearLayoutEtiquetas1.addView(textView);

            // TextView con espacio en blanco
            TextView spaceTextView = new TextView(this);
            spaceTextView.setText(" ");
            spaceTextView.setBackgroundResource(R.color.transparent);
            linearLayoutEtiquetas1.addView(spaceTextView);
        }

        //ETIQUETAS DE ABAJO
        linearLayoutEtiquetas2 = findViewById(R.id.layout_pestanajuego_etiquetasAbajo);
        List<TextView> textViewsetiquetasAbajo = new ArrayList<>();
        for (String p : etiquetasAbajo) {
            TextView textViewDos = new TextView(this);
            textViewDos.setText(p);
            textViewDos.setTextColor(Color.BLACK);
            textViewDos.setTypeface(typeface); // Establece el Typeface en el TextView
            textViewDos.setBackgroundResource(R.color.colorPrimario);
            textViewDos.setPadding(16, 8, 16, 8);

            int colorBorde = ContextCompat.getColor(this, R.color.colorPrimario);

            // Crear el drawable con el fondo y el borde
            GradientDrawable drawable = new GradientDrawable();
            drawable.setColor(getResources().getColor(R.color.colorSecundario)); // Color de fondo
            drawable.setStroke(2, colorBorde); // Ancho y color del borde
            textViewDos.setBackground(drawable);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(0, 0, 8, 0); // Margen derecho entre los TextViews
            textViewDos.setLayoutParams(params);
            textViewDos.setGravity(Gravity.CENTER);

            textViewsetiquetasAbajo.add(textViewDos);
            linearLayoutEtiquetas2.addView(textViewDos);

            // TextView con espacio en blanco
            TextView spaceTextView = new TextView(this);
            spaceTextView.setText(" ");
            spaceTextView.setBackgroundResource(R.color.transparent);
            linearLayoutEtiquetas2.addView(spaceTextView);
        }


        //----------------------------------------------------------------------------------------

    }

    //METODOS PARA EL BOTON FLOTANTE
    private void onAddButtonClicked() {
        setVisibility(this.clicked);
        setAnimation(this.clicked);
        setClickeable(this.clicked);

        this.clicked = !this.clicked; //invertir el valor de clicked

    }

    private void setAnimation(boolean clicked) {
        if (!this.clicked) {
            fab_list.startAnimation(getFromBottom());
            fab_res.startAnimation(getFromBottom());
            fab_mas.startAnimation(getRotateOpen());
        } else {
            fab_list.startAnimation(getToBottom());
            fab_res.startAnimation(getToBottom());
            fab_mas.startAnimation(getRotateClose());
        }
    }

    private void setVisibility(boolean clicked) {

        if (!this.clicked) {
            fab_list.setVisibility(View.VISIBLE);
            fab_res.setVisibility(View.VISIBLE);
        } else {
            fab_list.setVisibility(View.INVISIBLE);
            fab_res.setVisibility(View.INVISIBLE);
        }
    }

    private void setClickeable(boolean clicked) {
        if (!this.clicked) {
            fab_list.setClickable(true);
            fab_res.setClickable(true);
        } else {
            fab_list.setClickable(false);
            fab_res.setClickable(false);
        }
    }

    //-------------- POPUPS -------------------

    private void mostrarPopup(int lyNum) {


        this.dialog = new Dialog(this, R.style.DialogStyle);

        //le pasamos en layout que queremos mostrar
        switch (lyNum) {
            case 1:
                this.dialog.setContentView(R.layout.fragment_crear_resenia);

                ImageView btnCerrarRES = dialog.findViewById(R.id.btn_crearresenia_atras);

                dialog.setCanceledOnTouchOutside(false);
                btnCerrarRES.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                break;

            case 2:
                this.dialog.setContentView(R.layout.fragment_aniadirlista);

                ImageView btnCerrarLIST = dialog.findViewById(R.id.btn_aniadirlista_atras);
                spinner = dialog.findViewById(R.id.sp_listas);

                obtenerNombresListas();

                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        listaSeleccionada = adapterView.getItemAtPosition(i).toString();
                        Log.d("RESULTADO LISTA ELEGIDA", listaSeleccionada);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

                dialog.setCanceledOnTouchOutside(false);
                btnCerrarLIST.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                Button btnLista = dialog.findViewById(R.id.btn_Seleccionarlista);
                btnLista.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       actualizarDocumento( listaSeleccionada, id);
                       Log.d("PRUEBA ESTO ES EL NOMBREEEE", listaSeleccionada);
                       Log.d("PRUEBA ESTO ES EL ID DEL JUEGO", id);
                    }
                });
                break;
        }


        //dialog.getWindow().setBackgroundDrawableResource(R.drawable);
        this.dialog.show();


    }

    @Override
    public void onGameInfoReceived(String response) {
        if (response != null) {
            try {
                JSONArray jsonArray = new JSONArray(response);

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject gameObj = jsonArray.getJSONObject(i);
                    if (numero == 1) {
                        id = gameObj.getString("id");
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

                        //comprobar datos antes de mandarlos
                        Log.d("rating", "ESTE era EL rating: " + rating);
                        Log.d("genreList", "ESTE era EL genreList: " + genreList);


                        TextView myTextView = findViewById(R.id.tx_pestanajuego_descripcion);
                        myTextView.setText(resumen);

                        // Pasar los datos
                    }

                }

                // Actualiza la vista con los nuevos elementos
                //adapter.notifyDataSetChanged();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void onGameInfoError() {

    }

    @Override
    public void updateSummary(String resumen) {
        // Actualiza el TextView con el resumen del juego
        TextView myTextView = findViewById(R.id.tx_pestanajuego_descripcion);

        myTextView.setText(resumen);
    }

    @Override
    public void updateRating(String rating) {
        TextView myTextView = findViewById(R.id.tx_pestanajuego_rating);
        myTextView.setText(rating.substring(0, 2));
    }

    @Override
    public void onLinearLayoutUpdated(List<String> tagList1, List<String> tagList2) {

        // Elimina los elementos existentes del LinearLayout
        linearLayoutEtiquetas1.removeAllViews();
        linearLayoutEtiquetas2.removeAllViews();

        Typeface typeface;
        typeface = ResourcesCompat.getFont(this, R.font.advent_pro);

        //ETIQUETAS ARRIBA
        linearLayoutEtiquetas1 = findViewById(R.id.layout_pestanajuego_etiquetas);

        List<TextView> textViews = new ArrayList<>();
        for (String tag : tagList1) {
            TextView textView = new TextView(this); // Pasa el contexto al constructor de TextView
            textView.setText(tag);
            textView.setTextColor(Color.WHITE);
            textView.setTypeface(typeface);
            textView.setBackgroundResource(R.color.colorPrimario);
            textView.setPadding(16, 8, 16, 8);
            textViews.add(textView);
            linearLayoutEtiquetas1.addView(textView);

            // TextView con espacio en blanco
            TextView spaceTextView = new TextView(this); // Pasa el contexto al constructor de TextView
            spaceTextView.setText(" ");
            spaceTextView.setBackgroundResource(R.color.transparent);
            linearLayoutEtiquetas1.addView(spaceTextView);
        }

        //ETIQUETAS DE ABAJO
        linearLayoutEtiquetas2 = findViewById(R.id.layout_pestanajuego_etiquetasAbajo);

        List<TextView> textViewsetiquetasAbajo = new ArrayList<>();
        for (String tag : tagList2) {
            TextView textViewDos = new TextView(this);
            textViewDos.setText(tag);
            textViewDos.setTextColor(Color.BLACK);
            textViewDos.setTypeface(typeface); // Establece el Typeface en el TextView
            textViewDos.setBackgroundResource(R.color.colorPrimario);
            textViewDos.setPadding(16, 8, 16, 8);

            int colorBorde = ContextCompat.getColor(this, R.color.colorPrimario);

            // Crear el drawable con el fondo y el borde
            GradientDrawable drawable = new GradientDrawable();
            drawable.setColor(getResources().getColor(R.color.colorSecundario)); // Color de fondo
            drawable.setStroke(2, colorBorde); // Ancho y color del borde
            textViewDos.setBackground(drawable);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(0, 0, 8, 0); // Margen derecho entre los TextViews
            textViewDos.setLayoutParams(params);
            textViewDos.setGravity(Gravity.CENTER);

            textViewsetiquetasAbajo.add(textViewDos);
            linearLayoutEtiquetas2.addView(textViewDos);

            // TextView con espacio en blanco
            TextView spaceTextView = new TextView(this);
            spaceTextView.setText(" ");
            spaceTextView.setBackgroundResource(R.color.transparent);
            linearLayoutEtiquetas2.addView(spaceTextView);
        }


    }

    public void obtenerNombresListas() {
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
                        ArrayList<String> nombresListas = new ArrayList<>(); // ArrayList para almacenar los nombres de las listas

                        for (DocumentReference listaRef : listasAr) {
                            listaRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.isSuccessful()) {
                                        DocumentSnapshot listaDocument = task.getResult();
                                        if (listaDocument.exists()) {
                                            String nombreLista = listaDocument.getString("name");
                                            nombresListas.add(nombreLista);

                                            // Actualizar el Spinner con los nombres de las listas
                                            ArrayAdapter<String> adapter = new ArrayAdapter<>(context,
                                                    android.R.layout.simple_spinner_item, nombresListas);
                                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                            adapter.setDropDownViewResource(R.layout.custom_spinner_dropdown_item);
                                            spinner.setAdapter(adapter);

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

    public void actualizarDocumento(String nombreLista, String idJuego) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        // Obtiene la referencia al documento específico que deseas actualizar
        DocumentReference listaRef = db.collection("Lists").document(nombreLista);

        // Crea un mapa con el nuevo valor del campo "games"
        listaRef.update("games", FieldValue.arrayUnion(idJuego))
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            // La actualización se completó exitosamente
                            // Puedes realizar cualquier acción adicional aquí
                            // después de que el documento se haya actualizado correctamente
                            Log.d("FirestoreHelper", "Documento actualizado correctamente.");
                        } else {
                            // Se produjo un error al actualizar el documento
                            Log.e("FirestoreHelper", "Error al actualizar el documento.", task.getException());
                        }
                    }
                });
    }


}