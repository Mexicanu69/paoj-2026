package com.pao.laboratory08.exercise1;

public class Student implements Cloneable {
    private String nume;
    private int varsta;
    private Adresa adresa;

    public Student(String nume, int varsta, Adresa adresa) {
        this.nume = nume;
        this.varsta = varsta;
        this.adresa = adresa;
    }

    // Geteri și Seteri
    public String getNume() { return nume; }
    public Adresa getAdresa() { return adresa; }
    public int getVarsta() { return varsta; }
    public void setAdresa(Adresa adresa) { this.adresa = adresa; }

    @Override
    public String toString() {
        return "Student{nume='" + nume + "', varsta=" + varsta + ", adresa=" + adresa + "}";
    }

    // Metoda pentru Shallow Clone (Partea B)
    public Student shallowClone() throws CloneNotSupportedException {
        return (Student) super.clone();
    }

    // Metoda pentru Deep Clone (Partea C)
    public Student deepClone() throws CloneNotSupportedException {
        Student clona = (Student) super.clone();
        // Clonăm și obiectul compus (Adresa) pentru a rupe legătura cu originalul
        clona.setAdresa((Adresa) this.adresa.clone());
        return clona;
    }
}