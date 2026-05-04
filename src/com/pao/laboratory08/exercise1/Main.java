package com.pao.laboratory08.exercise1;

import java.io.*;
import java.util.*;

public class Main {
    private static final String FILE_PATH = "src/com/pao/laboratory08/tests/studenti.txt";

    public static void main(String[] args) {
        List<Student> studenti = incarcăStudenți();
        Scanner scanner = new Scanner(System.in);

        if (!scanner.hasNextLine()) return;
        String line = scanner.nextLine();
        String[] parts = line.split(" ", 2);
        String comanda = parts[0];

        try {
            switch (comanda) {
                case "PRINT":
                    studenti.forEach(System.out::println);
                    break;

                case "SHALLOW":
                    if (parts.length > 1) {
                        Student original = gasesteStudent(studenti, parts[1]);
                        if (original != null) {
                            Student clona = original.shallowClone();
                            clona.getAdresa().setOras("MODIFICAT");
                            System.out.println("Original: " + original);
                            System.out.println("Clona: " + clona);
                        }
                    }
                    break;

                case "DEEP":
                    if (parts.length > 1) {
                        Student original = gasesteStudent(studenti, parts[1]);
                        if (original != null) {
                            Student clona = original.deepClone();
                            clona.getAdresa().setOras("MODIFICAT");
                            System.out.println("Original: " + original);
                            System.out.println("Clona: " + clona);
                        }
                    }
                    break;
            }
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
    }

    private static List<Student> incarcăStudenți() {
        List<Student> lista = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String linie;
            while ((linie = br.readLine()) != null) {
                if (linie.trim().isEmpty()) continue;
                String[] date = linie.split(",");
                // Format: Nume, Varsta, Oras, Strada
                String nume = date[0].trim();
                int varsta = Integer.parseInt(date[1].trim());
                Adresa adresa = new Adresa(date[2].trim(), date[3].trim());
                lista.add(new Student(nume, varsta, adresa));
            }
        } catch (IOException e) {
            System.err.println("Eroare la citirea fișierului: " + e.getMessage());
        }
        return lista;
    }

    private static Student gasesteStudent(List<Student> lista, String nume) {
        return lista.stream()
                .filter(s -> s.getNume().equalsIgnoreCase(nume))
                .findFirst()
                .orElse(null);
    }
}