package com.example.agamelist.scrollResenasPrincipal;

public class ItemResenaPrincipal {

    private String nombreGame;
    private String idJuego;
    String nombreUser, arroba, txtResena;
    String fotoJuego, minutos;
    int fotoPerfil;
    int puntuacion;

    public ItemResenaPrincipal(String nombreUser, String arroba, String txtResena, int fotoPerfil, String fotoJuego, int puntuacion) {
        this.nombreUser = nombreUser;
        this.arroba = arroba;
        this.txtResena = txtResena;
        this.fotoPerfil = fotoPerfil;
        this.fotoJuego = fotoJuego;
        this.puntuacion = puntuacion;
        //this.minutos = minutos;
    }

    public ItemResenaPrincipal(String nombreUser, String arroba, String txtResena, int fotoPerfil, String fotoJuego, int puntuacion, String idJuego, String nombreGame) {
        this.idJuego = idJuego;
        this.nombreUser = nombreUser;
        this.arroba = arroba;
        this.txtResena = txtResena;
        this.fotoPerfil = fotoPerfil;
        this.fotoJuego = fotoJuego;
        this.puntuacion = puntuacion;
        this.nombreGame = nombreGame;

        //this.minutos = minutos;
    }

    public String getNombreUser() {
        return nombreUser;
    }

    public void setNombreUser(String nombreUser) {
        this.nombreUser = nombreUser;
    }

    public String getArroba() {
        return arroba;
    }

    public void setArroba(String arroba) {
        this.arroba = arroba;
    }

    public String getTxtResena() {
        return txtResena;
    }

    public void setTxtResena(String txtResena) {
        this.txtResena = txtResena;
    }

    public int getFotoPerfil() {
        return fotoPerfil;
    }

    public void setFotoPerfil(int fotoPerfil) {
        this.fotoPerfil = fotoPerfil;
    }

    public String getFotoJuego() {
        return fotoJuego;
    }

    public void setFotoJuego(String fotoJuego) {
        this.fotoJuego = fotoJuego;
    }

    public int getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(int puntuacion) {
        this.puntuacion = puntuacion;
    }

}