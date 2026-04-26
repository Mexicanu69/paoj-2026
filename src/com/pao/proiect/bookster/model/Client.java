package com.pao.proiect.bookster.model;

// Nivel 2 de mostenire
public class Client extends Persoana {
    private String numeCompanie;

    public Client(String nume, String email, String numeCompanie) {
        super(nume, email);
        this.numeCompanie = numeCompanie;
    }

    @Override
    public String getRol() {
        return "CLIENT";
    }

    public String getNumeCompanie() { return numeCompanie; }

    @Override
    public String toString() {
        return "Client: " + nume + " (Companie: " + numeCompanie + ")";
    }
}