package com.example.agamelist.listasPerfilRV;

import android.media.Image;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.agamelist.R;
import com.example.agamelist.scrollLayout.ItemJuego;

public class ViewHolderListasJuegos extends RecyclerView.ViewHolder {

    ImageView fotoJuego;
    TextView titulo;


    public ViewHolderListasJuegos(@NonNull View itemView) {
        super(itemView);

        titulo = itemView.findViewById(R.id.tituloJuego);
        fotoJuego = itemView.findViewById(R.id.fotoJuego);



    }
}
