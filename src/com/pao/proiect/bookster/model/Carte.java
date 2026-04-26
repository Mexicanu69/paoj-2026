package com.pao.proiect.bookster.model;

import java.util.Objects;

// Clasa care implementeaza Comparable pentru sortare
public class Carte implements Comparable<Carte> {
    private String titlu;
    private Autor autor;
    private ISBN isbn;
    private String categorie;
    private int exemplareDisponibile;

    public Carte(String titlu, Autor autor, ISBN isbn, String categorie, int exemplare) {
        this.titlu = titlu;
        this.autor = autor;
        this.isbn = isbn;
        this.categorie = categorie;
        this.exemplareDisponibile = exemplare;
    }

    // Supracriere equals si hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Carte carte = (Carte) o;
        return Objects.equals(isbn.getIsbnComplet(), carte.isbn.getIsbnComplet());
    }

    @Override
    public int hashCode() {
        return Objects.hash(isbn.getIsbnComplet());
    }

    @Override
    public int compareTo(Carte o) {
        return this.titlu.compareTo(o.titlu);
    }

    // Getteri si Setteri
    public String getTitlu() { return titlu; }
    public Autor getAutor() { return autor; }
    public String getCategorie() { return categorie; }
    public int getExemplareDisponibile() { return exemplareDisponibile; }
    public void setExemplareDisponibile(int nr) { this.exemplareDisponibile = nr; }

    @Override
    public String toString() {
        return titlu + " de " + autor.getNume() + " [" + isbn + "]";
    }
}