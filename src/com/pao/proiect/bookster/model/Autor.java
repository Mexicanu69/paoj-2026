package com.pao.proiect.bookster.model;

// Al doilea tip de persoana
public class Autor extends Persoana {
    private String biografie;

    public Autor(String nume, String email, String biografie) {
        super(nume, email);
        this.biografie = biografie;
    }

    @Override
    public String getRol() {
        return "AUTOR";
    }

    public String getBiografie() { return biografie; }
}