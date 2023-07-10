package com.example.agamelist.recycler_ranking;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.agamelist.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterRanking extends RecyclerView.Adapter<ViewHolderRanking> {

    Context context;
    List<ItemRanking> itemsRanking;

    public AdapterRanking(Context context, List<ItemRanking> itemsRanking) {
        this.context = context;
        this.itemsRanking = itemsRanking;
    }

    @NonNull
    @Override
    public ViewHolderRanking onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolderRanking(LayoutInflater.from(context).inflate(R.layout.recycler_ranking_ciudad, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderRanking holder, int position) {
        //holder.puestoView.setText(String.valueOf(itemsRanking.get(position).getPuesto()));
        holder.tituloView.setText(itemsRanking.get(position).getTitulo());
        holder.notaView.setText(String.valueOf(itemsRanking.get(position).getNota()));
        Picasso.get().load(itemsRanking.get(position).getFoto()).into(holder.fotoView); //carga la foto
    }

    @Override
    public int getItemCount() {
        return itemsRanking.size();
    }
}
