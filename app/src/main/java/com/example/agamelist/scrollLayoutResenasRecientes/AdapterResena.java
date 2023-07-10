package com.example.agamelist.scrollLayoutResenasRecientes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.agamelist.R;
import com.example.agamelist.scrollLayout.ItemJuego;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterResena extends RecyclerView.Adapter<ViewHolderResena> {

    Context context;
    List<ItemResena> items;

    public AdapterResena(Context context, List<ItemResena> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolderResena onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolderResena(LayoutInflater.from(context).inflate(R.layout.recyclerview_resenas_recientes, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderResena holder, int position) {
        ItemResena itemResena = items.get(position);

        Picasso.get().load(itemResena.getFotoJuegoString()).into(holder.fotoJuego); //carga la foto

     //   holder.fotoJuego.setImageResource(items.get(position).getFotoJuego());
        holder.puntuacion.setText(String.valueOf(items.get(position).getPuntuacion()));
        holder.nombreUser.setText(items.get(position).getNombreUser());
        holder.arroba.setText(items.get(position).getArroba());
        holder.minutos.setText(String.valueOf(items.get(position).getMinutos()));
        holder.txtResena.setText(items.get(position).getTxtResena());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
