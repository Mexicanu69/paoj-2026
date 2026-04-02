package com.pao.laboratory06.exercise2;

import java.util.Scanner;

public class PFAColaborator extends Colaborator implements PersoanaFizica {
    private double cheltuieliLunare;
    private static final double SALARIU_MINIM_ANUAL = 4050 * 12; // 48600
    
    @Override
    public void citeste(Scanner in) {
        this.nume = in.next();
        this.prenume = in.next();
        this.venitBrutLunar = in.nextDouble();
        this.cheltuieliLunare = in.nextDouble();
    }
    
    @Override
    public double calculeazaVenitNetAnual() {
        double venitNet = (venitBrutLunar - cheltuieliLunare) * 12;
        if (venitNet <= 0) return 0;
        
        double impozit = 0.10 * venitNet;
        
        // CASS
        double cass;
        if (venitNet < 6 * SALARIU_MINIM_ANUAL) {
            cass = 0.10 * 6 * SALARIU_MINIM_ANUAL;
        } else if (venitNet <= 72 * SALARIU_MINIM_ANUAL) {
            cass = 0.10 * venitNet;
        } else {
            cass = 0.10 * 72 * SALARIU_MINIM_ANUAL;
        }
        
        // CAS
        double cas = 0;
        if (venitNet >= 12 * SALARIU_MINIM_ANUAL) {
            if (venitNet <= 24 * SALARIU_MINIM_ANUAL) {
                cas = 0.25 * 12 * SALARIU_MINIM_ANUAL;
            } else {
                cas = 0.25 * 24 * SALARIU_MINIM_ANUAL;
            }
        }
        
        return venitNet - impozit - cass - cas;
    }
    
    @Override
    public TipColaborator getTip() {
        return TipColaborator.PFA;
    }
}