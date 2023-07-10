package com.example.agamelist.dialog_misma_franquicia;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.agamelist.R;
import com.example.agamelist.recycler_ranking.ItemRanking;
import com.example.agamelist.recycler_ranking.ViewHolderRanking;

import java.util.List;

public class AdapterMismaFranquicia extends RecyclerView.Adapter<ViewHolderMismaFranquicia>{

    Context context;
    List<ItemMismaFranquicia> itemsFranquicia;

    public AdapterMismaFranquicia(Context context, List<ItemMismaFranquicia> itemsFranquicia) {
        this.context = context;
        this.itemsFranquicia = itemsFranquicia;
    }

    @NonNull
    @Override
    public ViewHolderMismaFranquicia onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolderMismaFranquicia(LayoutInflater.from(context).inflate(R.layout.fragment_mismafranquicia, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderMismaFranquicia holder, int position) {
        holder.tituloJuego.setText(itemsFranquicia.get(position).getTitulo());
        holder.descripcionJuego.setText(itemsFranquicia.get(position).getDescripcion());
        holder.fotoJuego.setImageResource(itemsFranquicia.get(position).getImagen());

    }

    @Override
    public int getItemCount() {
        return itemsFranquicia.size();
    }
}
