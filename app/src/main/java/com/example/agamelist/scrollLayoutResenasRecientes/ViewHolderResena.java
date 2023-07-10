package com.example.agamelist.scrollLayoutResenasRecientes;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.agamelist.R;

public class ViewHolderResena extends RecyclerView.ViewHolder {

    ImageView fotoJuego;
    TextView nombreUser, arroba, puntuacion, txtResena, minutos;

    public ViewHolderResena(@NonNull View itemView) {
        super(itemView);

        fotoJuego = itemView.findViewById(R.id.fotoJuegoResena);
        nombreUser = itemView.findViewById(R.id.nombreUsuario);
        arroba = itemView.findViewById(R.id.arroba);
        puntuacion = itemView.findViewById(R.id.puntuacionResena);
        txtResena = itemView.findViewById(R.id.txtResena);
        minutos = itemView.findViewById(R.id.txtResena);

    }
}
