package com.example.agamelist.recycler_ranking;

public class ItemRanking {

    String titulo;
    String foto;
    int nota;
    int puesto;


    public ItemRanking(int puesto, String titulo, int nota, String foto) {
        this.puesto = puesto;
        this.titulo = titulo;
        this.nota = nota;
        this.foto = foto;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getNota() {
        return nota;
    }

    public void setNota(int nota) {
        this.nota = nota;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public int getPuesto() {
        return puesto;
    }

    public void setPuesto(int puesto) {
        this.puesto = puesto;
    }
}
