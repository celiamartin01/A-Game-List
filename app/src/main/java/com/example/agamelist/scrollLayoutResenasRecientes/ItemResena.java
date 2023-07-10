package com.example.agamelist.scrollLayoutResenasRecientes;

public class ItemResena {

    String nombreUser, arroba, txtResena, fotoJuegoString;
    int fotoJuego, puntuacion, minutos;

    public ItemResena(String nombreUser, String arroba, String txtResena, int fotoJuego, int puntuacion, int minutos) {
        this.nombreUser = nombreUser;
        this.arroba = arroba;
        this.txtResena = txtResena;
        this.fotoJuego = fotoJuego;
        this.puntuacion = puntuacion;
        this.minutos = minutos;
    }

    public ItemResena(String nombreUser, String arroba, String txtResena, String fotoJuegoString, int puntuacion, int minutos) {
        this.nombreUser = nombreUser;
        this.arroba = arroba;
        this.txtResena = txtResena;
        this.fotoJuegoString = fotoJuegoString;
        this.puntuacion = puntuacion;
        this.minutos = minutos;
    }

    public String getFotoJuegoString() {
        return fotoJuegoString;
    }

    public void setFotoJuegoString(String fotoJuegoString) {
        this.fotoJuegoString = fotoJuegoString;
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

    public int getFotoJuego() {
        return fotoJuego;
    }

    public void setFotoJuego(int fotoJuego) {
        this.fotoJuego = fotoJuego;
    }

    public int getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(int puntuacion) {
        this.puntuacion = puntuacion;
    }

    public int getMinutos() {
        return minutos;
    }

    public void setMinutos(int minutos) {
        this.minutos = minutos;
    }
}
