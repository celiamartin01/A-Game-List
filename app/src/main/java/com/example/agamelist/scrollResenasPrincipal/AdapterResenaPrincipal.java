package com.example.agamelist.scrollResenasPrincipal;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.agamelist.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class AdapterResenaPrincipal extends RecyclerView.Adapter<ViewHolderResenaPrincip> {

    Context context;
    List<ItemResenaPrincipal> itemsResPrincip;

    List<ItemResenaPrincipal> itemsResPrincip_Original;

    public AdapterResenaPrincipal(Context context, List<ItemResenaPrincipal> itemsResPrincip) {
        this.context = context;
        this.itemsResPrincip = itemsResPrincip;

        // Esta es la lista que usarÃ¡ el SearchView
        itemsResPrincip_Original = new ArrayList<>();
        itemsResPrincip_Original.addAll(itemsResPrincip);
    }

    @NonNull
    @Override
    public ViewHolderResenaPrincip onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolderResenaPrincip(LayoutInflater.from(context).inflate(R.layout.recycler_resenas, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderResenaPrincip holder, int position) {
        holder.fotoPerfil.setImageResource(itemsResPrincip.get(position).getFotoPerfil());
        Picasso.get().load(itemsResPrincip.get(position).getFotoJuego()).into(holder.fotoJuego); //carga la foto
        holder.nombreUser.setText(itemsResPrincip.get(position).getNombreUser());
        holder.arroba.setText(itemsResPrincip.get(position).getArroba());
        //holder.minutos.setText(String.valueOf(itemsResPrincip.get(position).getMinutos()));
        holder.puntuacion.setText(String.valueOf(itemsResPrincip.get(position).getPuntuacion()));
        holder.txtResena.setText(itemsResPrincip.get(position).getTxtResena());

    }

    public void filtrado(String txtBuscar){
        int longitud = txtBuscar.length();
        if(longitud == 0){
            itemsResPrincip.clear();
            itemsResPrincip.addAll(itemsResPrincip_Original);
        }
        else{
            if (Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N){
                List<ItemResenaPrincipal> collection = itemsResPrincip.stream().filter(i -> i.getNombreUser().toLowerCase().contains(txtBuscar.toLowerCase())).collect(Collectors.toList());
                itemsResPrincip.clear();
                itemsResPrincip.addAll(collection);
            }
            else{
                for (ItemResenaPrincipal c: itemsResPrincip_Original){
                    if (c.getNombreUser().toLowerCase().contains(txtBuscar.toLowerCase())){
                        itemsResPrincip.add(c);
                    }
                }
            }
        }

        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return itemsResPrincip.size();
    }
}