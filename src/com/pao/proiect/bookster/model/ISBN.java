package com.pao.proiect.bookster.model;

// Clasa imutabila conform cerintei
public final class ISBN {
    private final String prefix;
    private final String codGrup;
    private final String codEditura;
    private final String cifraControl;

    public ISBN(String prefix, String codGrup, String codEditura, String cifraControl) {
        this.prefix = prefix;
        this.codGrup = codGrup;
        this.codEditura = codEditura;
        this.cifraControl = cifraControl;
    }

    public String getIsbnComplet() {
        return prefix + "-" + codGrup + "-" + codEditura + "-" + cifraControl;
    }

    @Override
    public String toString() {
        return getIsbnComplet();
    }
}