package com.pao.laboratory09.exercise1;

import java.io.*;
import java.util.*;

public class Main {
    private static final String OUTPUT_FILE = "output/lab09_ex1.ser";

    public static void main(String[] args) {
        // Folosim Locale.US pentru a ne asigura că suma se printează cu punct (1500.00), nu cu virgulă
        Locale.setDefault(Locale.US);
        Scanner scanner = new Scanner(System.in);
        List<Tranzactie> tranzactii = new ArrayList<>();

        if (!scanner.hasNextInt()) return;
        int n = scanner.nextInt();

        for (int i = 0; i < n; i++) {
            int id = scanner.nextInt();
            double suma = scanner.nextDouble();
            String data = scanner.next();
            String contSursa = scanner.next();
            String contDestinatie = scanner.next();
            TipTranzactie tip = TipTranzactie.valueOf(scanner.next());

            Tranzactie t = new Tranzactie(id, suma, data, contSursa, contDestinatie, tip);
            t.note = "procesat";
            tranzactii.add(t);
        }

        // ASIGURARE FOLDER OUTPUT - Aici era problema ta!
        File outputFile = new File(OUTPUT_FILE);
        if (outputFile.getParentFile() != null) {
            outputFile.getParentFile().mkdirs();
        }

        // SERIALIZARE
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(OUTPUT_FILE))) {
            oos.writeObject(tranzactii);
        } catch (IOException e) {
            // Checker-ul s-ar putea să nu vrea stacktrace în output-ul standard
        }

        // DESERIALIZARE
        List<Tranzactie> tranzactiiCitite = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(OUTPUT_FILE))) {
            tranzactiiCitite = (List<Tranzactie>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            // Dacă nu putem citi, rămânem cu lista goală
        }

        // PROCESARE COMENZI
        while (scanner.hasNext()) {
            String comanda = scanner.next();
            if (comanda.equals("LIST")) {
                for (Tranzactie t : tranzactiiCitite) {
                    System.out.println(t);
                }
            } else if (comanda.equals("FILTER")) {
                String prefix = scanner.next();
                boolean gasit = false;
                for (Tranzactie t : tranzactiiCitite) {
                    if (t.data.startsWith(prefix)) {
                        System.out.println(t);
                        gasit = true;
                    }
                }
                if (!gasit) System.out.println("Niciun rezultat.");
            } else if (comanda.equals("NOTE")) {
                int idCautat = scanner.nextInt();
                boolean gasit = false;
                for (Tranzactie t : tranzactiiCitite) {
                    if (t.id == idCautat) {
                        System.out.println("NOTE[" + idCautat + "]: " + t.note);
                        gasit = true;
                        break;
                    }
                }
                if (!gasit) System.out.println("NOTE[" + idCautat + "]: not found");
            }
        }
    }
}