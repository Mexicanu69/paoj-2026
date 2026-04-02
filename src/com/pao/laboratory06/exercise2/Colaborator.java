package com.pao.laboratory06.exercise2;

import java.util.Scanner;

interface IOperatiiCitireScriere {
    void citeste(Scanner in);
    void afiseaza();
    String tipContract();
    default boolean areBonus() { return false; }
}

interface PersoanaFizica {}
interface PersoanaJuridica {}

public abstract class Colaborator implements IOperatiiCitireScriere {
    protected String nume;
    protected String prenume;
    protected double venitBrutLunar;
    
    public abstract double calculeazaVenitNetAnual();
    public abstract TipColaborator getTip();
    
    @Override
    public void afiseaza() {
        System.out.printf("%s: %s %s, venit net anual: %.2f lei\n", 
            getTip(), nume, prenume, calculeazaVenitNetAnual());
    }
    
    @Override
    public String tipContract() {
        return getTip().toString();
    }
}