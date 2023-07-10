package com.example.agamelist.dialog_misma_franquicia;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.agamelist.R;

public class ViewHolderMismaFranquicia extends RecyclerView.ViewHolder {

    ImageView fotoJuego;
    TextView tituloJuego, descripcionJuego;


    public ViewHolderMismaFranquicia(@NonNull View itemView) {
        super(itemView);

        fotoJuego = itemView.findViewById(R.id.imageview_mismafranquicia_juego);
        descripcionJuego = itemView.findViewById(R.id.tx_mismafranquicia_descrpcion);
        tituloJuego = itemView.findViewById(R.id.tx_mismafranquicia_nombre_juego);

    }
}
