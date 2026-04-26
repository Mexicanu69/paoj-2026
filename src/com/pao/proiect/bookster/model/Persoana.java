package com.pao.proiect.bookster.model;

// Clasa abstracta - nivel 1 de mostenire
public abstract class Persoana {
    protected String nume;
    protected String email;

    public Persoana(String nume, String email) {
        this.nume = nume;
        this.email = email;
    }

    public abstract String getRol(); // Metoda abstracta obligatorie

    public String getNume() { return nume; }
    public String getEmail() { return email; }
}