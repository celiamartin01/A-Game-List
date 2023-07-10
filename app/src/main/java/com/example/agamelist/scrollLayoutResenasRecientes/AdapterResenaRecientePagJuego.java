package com.example.agamelist.scrollLayoutResenasRecientes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.agamelist.R;
import com.example.agamelist.scrollLayoutResenasRecientes.ItemResena;
import com.example.agamelist.scrollLayoutResenasRecientes.ViewHolderResena;

import java.util.List;

public class AdapterResenaRecientePagJuego extends RecyclerView.Adapter<ViewHolderResena> {

    Context context;
    List<ItemResena> itemsResenaPagJuego;

    public AdapterResenaRecientePagJuego(Context context, List<ItemResena> itemsResenaPagJuego) {
        this.context = context;
        this.itemsResenaPagJuego = itemsResenaPagJuego;
    }

    @NonNull
    @Override
    public ViewHolderResena onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolderResena(LayoutInflater.from(context).inflate(R.layout.recyclerview_resenas_recientes, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderResena holder, int position) {
        holder.fotoJuego.setImageResource(itemsResenaPagJuego.get(position).getFotoJuego());
        holder.puntuacion.setText(String.valueOf(itemsResenaPagJuego.get(position).getPuntuacion()));
        holder.nombreUser.setText(itemsResenaPagJuego.get(position).getNombreUser());
        holder.arroba.setText(itemsResenaPagJuego.get(position).getArroba());
        holder.minutos.setText(String.valueOf(itemsResenaPagJuego.get(position).getMinutos()));
        holder.txtResena.setText(itemsResenaPagJuego.get(position).getTxtResena());
    }

    @Override
    public int getItemCount() {
        return itemsResenaPagJuego.size();
    }
}
