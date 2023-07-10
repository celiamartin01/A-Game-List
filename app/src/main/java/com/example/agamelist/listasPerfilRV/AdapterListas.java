package com.example.agamelist.listasPerfilRV;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.agamelist.R;

import java.util.List;

public class AdapterListas extends RecyclerView.Adapter<ViewHolderListasJuegos> {

    // ADAPTADOR RECYCLERVIEW ANIDADOS??

    Context context;
    List<ItemLista> lista;

    public AdapterListas(Context context, List<ItemLista> lista) {
        this.context = context;
        this.lista = lista;
    }

    @NonNull
    @Override
    public ViewHolderListasJuegos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolderListasJuegos(LayoutInflater.from(context).inflate(R.layout.juego_layout_horizontal, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderListasJuegos holder, int position) {
        holder.titulo.setText(lista.get(position).getTitulo());
        holder.fotoJuego.setImageResource(lista.get(position).getFoto());

    }

    @Override
    public int getItemCount() {
        return lista.size();
    }
}
