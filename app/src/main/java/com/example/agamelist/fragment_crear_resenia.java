package com.example.agamelist;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.agamelist.recycler_pecha_juegos_descubrir.AdapterPechaJuegos;
import com.example.agamelist.scrollLayout.Adapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class fragment_crear_resenia extends Fragment implements AdapterPechaJuegos.ItemClickListener{

   // Adapter adapter;
    EditText et_nota, et_reseña;
    Button bt_publicar;

    private AdapterPechaJuegos adapter;
    SearchView buscarJuego;
    String palabra = "a";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View fragmentView = inflater.inflate(R.layout.fragment_crear_resenia, container, false);
        et_nota = fragmentView.findViewById(R.id.et_nota);
        et_reseña = fragmentView.findViewById(R.id.et_reseña);
        bt_publicar =fragmentView.findViewById(R.id.bt_publicar);
        bt_publicar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int nota = Integer.parseInt(et_nota.getText().toString());
                String reseña = et_reseña.getText().toString();
                //String juego = et_nota.getText().toString(); como hacer????????
                nuevaReseña(reseña,nota);

            }
        });
        return fragmentView;
    }

    public void nuevaReseña(String reseña, int rate) {  //METER ID PARÁMETRO
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference userRef = db.collection("Users").document(uid);

        userRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        List<DocumentReference> reseñasAr = (List<DocumentReference>) document.get("reseñas");
                        int tamanioReseñasAr = reseñasAr.size() + 1;
                        Log.d("TAG", "Tamaño del array reseñasAr: " + tamanioReseñasAr);

                        // Crear el nuevo documento en la colección "Reviews"
                        String nombreDocumento = "review" + uid + "_" + tamanioReseñasAr;
                        List<Integer> comments = Arrays.asList();
                        String review = reseña;
                        int nota = rate;
                        int likes = 0;
                        //int game = ; Descomentar cuando se haya extraido la id del juego

                        Map<String, Object> listaData = new HashMap<>();
                        listaData.put("rate", nota);
                        listaData.put("review", review);
                        listaData.put("likes", likes);
                        listaData.put("date", FieldValue.serverTimestamp());
                        listaData.put("comentarios", comments);
                        listaData.put("user", userRef);
                        //listaData.put("game",game); Descomentar cuando se haya extraido la id del juego

                        db.collection("Reviews").document(nombreDocumento)
                                .set(listaData)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d("TAG", "Documento creado correctamente.");

                                        // Obtener la referencia al documento recién creado
                                        DocumentReference nuevaReviewRef = db.collection("Reviews").document(nombreDocumento);

                                        // Agregar la referencia al array "listas" del documento de usuario
                                        reseñasAr.add(nuevaReviewRef);
                                        userRef.update("reseñas", reseñasAr)
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        Log.d("TAG", "Referencia agregada al array 'listas' correctamente.");
                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        Log.d("TAG", "Error al agregar la referencia al array 'listas': " + e.getMessage());
                                                    }
                                                });
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.d("TAG", "Error al crear el documento: " + e.getMessage());
                                    }
                                });
                    } else {
                        Log.d("TAG", "El documento no existe.");
                    }
                } else {
                    Log.d("TAG", "Error al obtener el documento: " + task.getException());
                }
            }
        });
    }

    @Override
    public void onItemClick(int position) {

    }
}