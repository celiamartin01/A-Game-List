package com.example.agamelist;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.agamelist.scrollLayout.Adapter;
import com.example.agamelist.scrollLayout.ItemJuego;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class fragment_crearlista extends DialogFragment {

    Button btn_crearLista;
    ImageButton btn_cerrar;
    EditText campoNombre;
    OnListaCreadaListener listener;
    FirebaseFirestore db;
    String uid;
    DocumentReference userRef;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_crearlista, container, false);
        db = FirebaseFirestore.getInstance();
        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        userRef = db.collection("users").document(uid);
        //arraylong();

        campoNombre = view.findViewById(R.id.edittxt_crearlista_nombrelista);
        btn_crearLista = view.findViewById(R.id.bt_crearlista_publicar);
        btn_crearLista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nombre = campoNombre.getText().toString();
                if (listener != null) {
                    listener.onListaCreada(nombre);
                    arraylong(nombre);
                }

                dismiss();

            }
        });


        btn_cerrar = view.findViewById(R.id.btn_crearlista_atras);
        btn_cerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        return view;
    }

    public interface OnListaCreadaListener {
        void onListaCreada(String nombre);
    }

    public void setOnListaCreadaListener(OnListaCreadaListener listener) {
        this.listener = listener;
    }

 public void arraylong(String nombre) {
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
                     int tamanioListasAr = listasAr.size()+1;
                     Log.d("TAG", "Tamaño del array listasAr: " + tamanioListasAr);

                     // Crear el nuevo documento en la colección "Listas"
                     String nombreDocumento = "Lista" + uid + "_" + tamanioListasAr;
                     List<Integer> games = Arrays.asList();
                     String name = nombre;

                     Map<String, Object> listaData = new HashMap<>();
                     listaData.put("games", games);
                     listaData.put("name", name);

                     db.collection("Lists").document(nombreDocumento)
                             .set(listaData)
                             .addOnSuccessListener(new OnSuccessListener<Void>() {
                                 @Override
                                 public void onSuccess(Void aVoid) {
                                     Log.d("TAG", "Documento creado correctamente.");

                                     // Obtener la referencia al documento recién creado
                                     DocumentReference nuevaListaRef = db.collection("Lists").document(nombreDocumento);

                                     // Agregar la referencia al array "listas" del documento de usuario
                                     listasAr.add(nuevaListaRef);
                                     userRef.update("listas", listasAr)
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
}