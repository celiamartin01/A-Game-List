package com.example.agamelist.recycler_pecha_juegos_descubrir;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.agamelist.R;
import com.example.agamelist.scrollLayout.ItemJuego;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.stream.Collectors;

public class AdapterPechaJuegos extends RecyclerView.Adapter<ViewHolderPechaJuegos> {

    Context context;
    List<ItemJuego> itemJuegos;
    List<ItemJuego> listaOriginal;

    private ItemClickListener itemClickListener;

    public AdapterPechaJuegos(Context context, List<ItemJuego> itemJuegos) {
        this.context = context;
        this.itemJuegos = itemJuegos;
    }

    @NonNull
    @Override
    public ViewHolderPechaJuegos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolderPechaJuegos(LayoutInflater.from(context).inflate(R.layout.view_juego_pecha_juegos, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderPechaJuegos holder, int position) {
        ItemJuego itemJuego = itemJuegos.get(position);

        holder.titulo.setText(itemJuegos.get(position).getTitulo()); //carga el titulo
        Picasso.get().load(itemJuego.getFotoJuegoUrl()).into(holder.fotoJuego); //carga la foto

        //controla cuando clickas el item
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int clickedPosition = holder.getAdapterPosition();
                if (clickedPosition != RecyclerView.NO_POSITION && itemClickListener != null) {
                    itemClickListener.onItemClick(clickedPosition);
                }
            }
        });
    }

    //metodos para manejar los clicks
    public void setItemClickListener(AdapterPechaJuegos.ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(int position);
    }


    public void filtrado(String txtBuscar){
        if(txtBuscar.length() == 0){
            itemJuegos.clear();
            itemJuegos.addAll(listaOriginal);

        }
        else{
            List<ItemJuego> collection = itemJuegos.stream().filter(i -> i.getTitulo().toLowerCase().contains(txtBuscar.toLowerCase()))
                    .collect(Collectors.toList());
            itemJuegos.clear();
            itemJuegos.addAll(collection);
        }

        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return itemJuegos.size();
    }



    public void setItems(List<ItemJuego> itemList) {
        itemJuegos = itemList;
    }
}

