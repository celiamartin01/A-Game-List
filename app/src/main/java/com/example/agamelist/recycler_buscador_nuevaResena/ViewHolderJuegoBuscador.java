package com.example.agamelist.recycler_buscador_nuevaResena;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.agamelist.R;

public class ViewHolderJuegoBuscador extends RecyclerView.ViewHolder {

    TextView titulo;
    ImageView fotoJuego;


    public ViewHolderJuegoBuscador(@NonNull View itemView) {
        super(itemView);

        titulo = itemView.findViewById(R.id.tituloJuegoCrearResena);
        fotoJuego = itemView.findViewById(R.id.fotoJuegoCrearResena);
    }
}
