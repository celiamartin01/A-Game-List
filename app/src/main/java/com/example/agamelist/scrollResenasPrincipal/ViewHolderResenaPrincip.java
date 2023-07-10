package com.example.agamelist.scrollResenasPrincipal;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.agamelist.R;

public class ViewHolderResenaPrincip extends RecyclerView.ViewHolder{

    ImageView fotoPerfil, fotoJuego;
    TextView nombreUser, arroba, txtResena, puntuacion, minutos;

    public ViewHolderResenaPrincip(@NonNull View itemView) {
        super(itemView);

        fotoPerfil = itemView.findViewById(R.id.iv_fotoPerfil_reseñaInicio);
        fotoJuego = itemView.findViewById(R.id.iv_fotoJuego_reseñaInicio);
        nombreUser = itemView.findViewById(R.id.tv_nombre_reseñaInicio);
        arroba = itemView.findViewById(R.id.tv_usuario_reseñaInicio);
        txtResena = itemView.findViewById(R.id.tv_reseña_reseñaInicio);
        puntuacion = itemView.findViewById(R.id.tv_nota_reseñaInicio);
        //minutos = itemView.findViewById(R.id.tv_tiempoPubli_reseñaInicio);

    }
}
