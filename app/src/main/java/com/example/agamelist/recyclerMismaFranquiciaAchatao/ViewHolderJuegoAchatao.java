package com.example.agamelist.recyclerMismaFranquiciaAchatao;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.agamelist.R;

public class ViewHolderJuegoAchatao extends RecyclerView.ViewHolder {

    ImageView imageAchatada;
    TextView tituloTextoChato;

    public ViewHolderJuegoAchatao(@NonNull View itemView) {
        super(itemView);

        imageAchatada = itemView.findViewById(R.id.imageview_juegoachatado);
        tituloTextoChato = itemView.findViewById(R.id.tx_juegoachatado_nombre);
    }
}
