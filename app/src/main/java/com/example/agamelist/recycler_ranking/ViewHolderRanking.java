package com.example.agamelist.recycler_ranking;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.agamelist.R;

public class ViewHolderRanking extends RecyclerView.ViewHolder {

    ImageView fotoView;
    TextView notaView, tituloView, puestoView;


    public ViewHolderRanking(@NonNull View itemView) {
        super(itemView);

        fotoView = itemView.findViewById(R.id.fotoJuegoRanking);
        notaView = itemView.findViewById(R.id.notaRanking);
        tituloView = itemView.findViewById(R.id.tituloJuegoRanking);
        puestoView = itemView.findViewById(R.id.numero_ranking);
    }
}
