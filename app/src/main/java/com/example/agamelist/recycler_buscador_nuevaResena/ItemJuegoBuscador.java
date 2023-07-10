package com.example.agamelist.recycler_buscador_nuevaResena;

public class ItemJuegoBuscador {

    String titulo;
    int fotoJuego;

    public ItemJuegoBuscador(String titulo, int fotoJuego) {
        this.titulo = titulo;
        this.fotoJuego = fotoJuego;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getFotoJuego() {
        return fotoJuego;
    }

    public void setFotoJuego(int fotoJuego) {
        this.fotoJuego = fotoJuego;
    }
}
