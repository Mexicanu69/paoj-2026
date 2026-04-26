package com.pao.proiect.bookster.exception;

// Prima exceptie custom
public class CarteNedisponibilaException extends Exception {
    public CarteNedisponibilaException(String mesaj) {
        super(mesaj);
    }
}