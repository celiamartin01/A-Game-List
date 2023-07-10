package com.example.agamelist.recycler_pecha_juegos_descubrir;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.agamelist.R;

public class ViewHolderPechaJuegos extends RecyclerView.ViewHolder {

    TextView titulo;
    ImageView fotoJuego;

    public ViewHolderPechaJuegos(@NonNull View itemView) {
        super(itemView);

        titulo = itemView.findViewById(R.id.pechaJuegosTitulo);
        fotoJuego = itemView.findViewById(R.id.pechaJuegosFoto);
    }
}
