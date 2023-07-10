package com.example.agamelist.scrollLayout;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.agamelist.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Adapter extends RecyclerView.Adapter<ViewHolder>{

    Context context;
    List<ItemJuego> itemJuegos;
    List<ItemJuego> listaOriginal;
    private ItemClickListener itemClickListener;

    public Adapter(Context context, List<ItemJuego> itemJuegos) {
        this.context = context;
        this.itemJuegos = itemJuegos;
        listaOriginal = new ArrayList<>();
        listaOriginal.addAll(itemJuegos);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.juego_layout_horizontal, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
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
    public void setItemClickListener(ItemClickListener itemClickListener) {
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
}
