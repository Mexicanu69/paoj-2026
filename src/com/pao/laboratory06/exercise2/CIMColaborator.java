package com.pao.laboratory06.exercise2;

import java.util.Scanner;

public class CIMColaborator extends Colaborator implements PersoanaFizica {
    private boolean bonus;
    
    @Override
    public void citeste(Scanner in) {
        this.nume = in.next();
        this.prenume = in.next();
        this.venitBrutLunar = in.nextDouble();
        if (in.hasNext()) {
            this.bonus = in.next().equals("DA");
        }
    }
    
    @Override
    public double calculeazaVenitNetAnual() {
        double venit = venitBrutLunar * 12 * 0.55;
        return bonus ? venit * 1.10 : venit;
    }
    
    @Override
    public TipColaborator getTip() {
        return TipColaborator.CIM;
    }
}