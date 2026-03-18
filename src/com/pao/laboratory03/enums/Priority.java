package com.pao.laboratory03.enums;

/**
 * Exemplu demonstrativ — Enum-uri în Java.
 * Rulează acest main pentru a vedea cum funcționează enum-urile cu câmpuri și metode.
 * Apoi rezolvă exercițiul din Main.java (creezi Priority.java de la zero).
 */
public class Priority {

    // Un enum simplu — doar constante
    public enum Prioritate {
        LOW(1, "green"){
            @Override public String getEmoji() { return "🟢"; }
        },
        MEDIUM(2, "yellow"){
            @Override public String getEmoji() { return "🟡"; }
        }, 
        HIGH(3, "orange"){
            @Override public String getEmoji() { return "🟠"; }
        }, 
        CRITICAL(4, "red"){
            @Override public String getEmoji() { return "🔴"; }
        };

        private final int level;    
        private final String color;  

        Prioritate(int level, String color){
            this.level = level;
            this.color = color;
        }

        public int getLevel(){
            return level;
        }        
        public String getColor(){
            return color;
        }
        
        public abstract String getEmoji();
    }

}
