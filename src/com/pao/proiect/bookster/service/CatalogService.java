package com.pao.proiect.bookster.service;

import java.util.*;

import com.pao.proiect.bookster.model.Carte;

public class CatalogService {
    private static CatalogService instance;
    private List<Carte> carti = new ArrayList<>(); // Colectie tip List

    private CatalogService() {}

    public static CatalogService getInstance() {
        if (instance == null) instance = new CatalogService();
        return instance;
    }

    public void adaugaCarte(Carte c) {
        carti.add(c);
        Collections.sort(carti); // Sortare automata
    }

    public void stergeCarte(String titlu) {
        carti.removeIf(c -> c.getTitlu().equalsIgnoreCase(titlu));
    }

    public List<Carte> listeazaToate() {
        return carti;
    }

    public Carte cautaDupaTitlu(String titlu) {
        for (Carte c : carti) {
            if (c.getTitlu().equalsIgnoreCase(titlu)) return c;
        }
        return null;
    }

    public List<Carte> filterDupaCategorie(String cat) {
        List<Carte> rezultate = new ArrayList<>();
        for (Carte c : carti) {
            if (c.getCategorie().equalsIgnoreCase(cat)) rezultate.add(c);
        }
        return rezultate;
    }
}