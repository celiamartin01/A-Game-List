package com.example.agamelist.recyclerMismaFranquiciaAchatao;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.agamelist.R;
import com.example.agamelist.scrollLayout.ItemJuego;

import java.util.List;

public class AdapterAchatao extends RecyclerView.Adapter<ViewHolderJuegoAchatao> {

    Context context;
    List<ItemJuego> juegosChatos;

    public AdapterAchatao(Context context, List<ItemJuego> juegosChatos) {
        this.context = context;
        this.juegosChatos = juegosChatos;
    }

    @NonNull
    @Override
    public ViewHolderJuegoAchatao onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolderJuegoAchatao(LayoutInflater.from(context).inflate(R.layout.fragment_pestaniajuego_mismafranquicia, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderJuegoAchatao holder, int position) {

        holder.tituloTextoChato.setText(juegosChatos.get(position).getTitulo());
        //holder.imageAchatada.setImageResource(juegosChatos.get(position).getFotoJuego());
    }

    @Override
    public int getItemCount() {
        return juegosChatos.size();
    }
}
