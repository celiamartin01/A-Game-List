package com.example.agamelist.listasPerfilRV;

import com.example.agamelist.scrollLayout.ItemJuego;

public class ItemLista {

    String titulo;
    int foto;

    public ItemLista(String titulo, int foto) {
        this.titulo = titulo;
        this.foto = foto;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getFoto() {
        return foto;
    }

    public void setFoto(int foto) {
        this.foto = foto;
    }
}
