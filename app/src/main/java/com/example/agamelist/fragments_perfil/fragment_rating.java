package com.example.agamelist.fragments_perfil;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.agamelist.R;
import com.example.agamelist.recycler_ranking.AdapterRanking;
import com.example.agamelist.recycler_ranking.ItemRanking;
import com.example.agamelist.scrollResenasPrincipal.AdapterResenaPrincipal;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class fragment_rating extends Fragment {
    List<ItemRanking> itemsRanking;
    AdapterRanking adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rating, container, false);

        RecyclerView recyclerRanking = view.findViewById(R.id.recyclerRatingPerfil);

        itemsRanking = new ArrayList<ItemRanking>();
        recyclerRanking.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new AdapterRanking(getContext(), itemsRanking);
        recyclerRanking.setAdapter(adapter);
        obtenerReseñas();
        return view;
    }
    public void obtenerReseñas() {
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = FirebaseFirestore.getInstance().collection("Users").document(uid);

        db.collection("Reviews")
                .orderBy("rate", Query.Direction.DESCENDING) // Ordenar por rate en orden descendente
                .whereEqualTo("user", docRef) // Filtrar por el usuario logueado
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Map<String, Object> reviewData = document.getData();

                                // Extraer los campos de cada review
                                int rate = ((Long) reviewData.get("rate")).intValue();
                                String fotoJuego = (String) reviewData.get("foto");
                                String titulo = (String) reviewData.get("titulo");
                                int puesto=0;
                                puesto++;

                                itemsRanking.add(new ItemRanking(puesto, titulo, rate, fotoJuego));

                                adapter.notifyDataSetChanged(); // Actualizar el adaptador

                            }
                        } else {
                            Log.d(TAG, "Error getting reviews: ", task.getException());
                        }
                    }
                });
    }

}