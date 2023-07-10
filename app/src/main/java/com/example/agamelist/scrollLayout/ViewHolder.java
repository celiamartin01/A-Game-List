package com.example.agamelist.scrollLayout;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.agamelist.R;

public class ViewHolder extends RecyclerView.ViewHolder {

    TextView titulo;
    ImageView fotoJuego;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);

        titulo = itemView.findViewById(R.id.tituloJuego);
        fotoJuego = itemView.findViewById(R.id.fotoJuego);

    }
}
