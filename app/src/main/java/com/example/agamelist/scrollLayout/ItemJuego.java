package com.example.agamelist.scrollLayout;

import java.util.ArrayList;
import java.util.List;

public class ItemJuego {

    private String titulo;
    private String fotoJuegoUrl;
    private String id;
    private List<String> similar_games;
    private float rating;
    private String resumen;
    private List<String> generos;
    private List<String> palabrasclave;
    private List<String> perspectiva;

    public ItemJuego(String titulo, String fotoJuegoUrl, String id) {
        this.titulo = titulo;
        this.fotoJuegoUrl = fotoJuegoUrl;
        this.id = id;
    }

    public ItemJuego(String titulo, String fotoJuegoUrl, String id, List<String> similar_games, float rating, String resumen, List<String> g, List<String> pc, List<String> pers) {
        this.titulo = titulo;
        this.fotoJuegoUrl = fotoJuegoUrl;
        this.similar_games = similar_games;
        this.rating = rating;
        this.resumen = resumen;
        this.generos = g;
        this.palabrasclave = pc;
        this.perspectiva = pers;


    }

    public void setId(String id) {this.id = id;}

    public String getId() { return id; }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getFotoJuegoUrl() { return fotoJuegoUrl; }

    public void setFotoJuegoUrl(String fotoJuegoUrl) {
        this.fotoJuegoUrl = fotoJuegoUrl;
    }

    public List<String> getSimilar_games() {
        return similar_games;
    }

    public void setSimilar_games(List<String> similar_games) {
        this.similar_games = similar_games;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getResumen() {
        return resumen;
    }

    public void setResumen(String resumen) {
        this.resumen = resumen;
    }

    public List<String> getGeneros() {
        return generos;
    }

    public void setGeneros(List<String> generos) {
        this.generos = generos;
    }

    public List<String> getPalabrasclave() {
        return palabrasclave;
    }

    public void setPalabrasclave(List<String> palabrasclave) {
        this.palabrasclave = palabrasclave;
    }

    public List<String> getPerspectiva() {
        return perspectiva;
    }

    public void setPerspectiva(List<String> perspectiva) {
        this.perspectiva = perspectiva;
    }
}

