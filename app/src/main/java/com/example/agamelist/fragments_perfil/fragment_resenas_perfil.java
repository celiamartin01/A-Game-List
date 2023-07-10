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
import com.example.agamelist.scrollResenasPrincipal.AdapterResenaPrincipal;
import com.example.agamelist.scrollResenasPrincipal.ItemResenaPrincipal;
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

public class fragment_resenas_perfil extends Fragment {

    List<ItemResenaPrincipal> itemsRes;
    AdapterResenaPrincipal adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_resenas_perfil, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerResenasPerfil);

        itemsRes = new ArrayList<ItemResenaPrincipal>();

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new AdapterResenaPrincipal(getContext(), itemsRes);
        recyclerView.setAdapter(adapter);

        obtenerReseñas();
        return view;
    }

    public void obtenerReseñas() {

        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = FirebaseFirestore.getInstance().collection("Users").document(uid);

        final AdapterResenaPrincipal adapter = this.adapter; // Nueva variable final para el adaptador

        db.collection("Reviews")
                .orderBy("date", Query.Direction.DESCENDING)
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
                                String review = (String) reviewData.get("review");
                                Timestamp date = (Timestamp) reviewData.get("date");
                                DocumentReference userRef = (DocumentReference) reviewData.get("user");
                                long game = (long) reviewData.get("game");
                                String fotoJuego = (String) reviewData.get("foto");

                                // Obtener los datos del usuario (asumiendo que tienes una colección de usuarios)
                                userRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        if (task.isSuccessful()) {
                                            DocumentSnapshot userDoc = task.getResult();
                                            if (userDoc.exists()) {
                                                String name = userDoc.getString("name");
                                                String user = userDoc.getString("user");
                                                int foto = userDoc.getLong("profilePic").intValue();


                                                // Pasar los datos a los campos
                                                itemsRes.add(new ItemResenaPrincipal(name, user, review, foto, fotoJuego, rate));

                                                adapter.notifyDataSetChanged(); // Actualizar el adaptador
                                            }
                                        }
                                    }
                                });
                            }
                        } else {
                            Log.d(TAG, "Error getting reviews: ", task.getException());
                        }
                    }
                });
    }

}