package com.example.agamelist.recycler_buscador_nuevaResena;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.agamelist.R;
import com.example.agamelist.scrollLayout.ViewHolder;

import java.util.List;

public class AdapterJuegoBuscador extends RecyclerView.Adapter<ViewHolderJuegoBuscador> {

    Context context;
    List<ItemJuegoBuscador> items;

    public AdapterJuegoBuscador(Context context, List<ItemJuegoBuscador> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolderJuegoBuscador onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolderJuegoBuscador(LayoutInflater.from(context).inflate(R.layout.recycler_crear_resena_juego, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderJuegoBuscador holder, int position) {
        holder.titulo.setText(items.get(position).getTitulo());
        holder.fotoJuego.setImageResource(items.get(position).getFotoJuego());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
