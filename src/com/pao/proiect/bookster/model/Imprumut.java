package com.pao.proiect.bookster.model;

import java.time.LocalDateTime;

public class Imprumut {
    private Client client;
    private Carte carte;
    private LocalDateTime dataImprumut;
    private boolean esteReturnat;

    public Imprumut(Client client, Carte carte) {
        this.client = client;
        this.carte = carte;
        this.dataImprumut = LocalDateTime.now();
        this.esteReturnat = false;
    }

    public Client getClient() { return client; }
    public Carte getCarte() { return carte; }
    public boolean esteReturnat() { return esteReturnat; }
    public void returneaza() { this.esteReturnat = true; }
}