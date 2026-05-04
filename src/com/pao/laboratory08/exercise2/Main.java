package com.pao.laboratory08.exercise2;

import com.pao.laboratory08.exercise1.Adresa;
import com.pao.laboratory08.exercise1.Student;
import java.io.*;
import java.util.*;

public class Main {
    private static final String FILE_PATH = "src/com/pao/laboratory08/tests/studenti.txt";

    public static void main(String[] args) {
        // 1. Citește studenții din FILE_PATH cu BufferedReader
        List<Student> totiStudentii = citesteDinFisier();

        // 2. Citește pragul de vârstă din stdin cu Scanner
        Scanner scanner = new Scanner(System.in);
        if (!scanner.hasNextInt()) return;
        int prag = scanner.nextInt();

        // 3. Filtrează studenții cu varsta >= prag
        List<Student> filtrati = new ArrayList<>();
        for (Student s : totiStudentii) {
            if (s.getVarsta() >= prag) {
                filtrati.add(s);
            }
        }

        // 4. Scrie filtrații în "rezultate.txt" cu BufferedWriter
        scrieInFisier(filtrati);

        // 5. Afișează sumarul la consolă conform formatului cerut
        System.out.println("Filtru: varsta >= " + prag);
        System.out.println("Rezultate: " + filtrati.size() + " studenti");
        System.out.println(); // Linie goală

        for (Student s : filtrati) {
            System.out.println(s);
        }

        System.out.println(); // Linie goală
        System.out.println("Scris in: rezultate.txt");
    }

    private static List<Student> citesteDinFisier() {
        List<Student> lista = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String linie;
            while ((linie = br.readLine()) != null) {
                if (linie.trim().isEmpty()) continue;
                String[] date = linie.split(",");
                if (date.length == 4) {
                    String nume = date[0].trim();
                    int varsta = Integer.parseInt(date[1].trim());
                    Adresa adresa = new Adresa(date[2].trim(), date[3].trim());
                    lista.add(new Student(nume, varsta, adresa));
                }
            }
        } catch (IOException e) {
            System.err.println("Eroare la citire: " + e.getMessage());
        }
        return lista;
    }

    private static void scrieInFisier(List<Student> filtrati) {
        try (BufferedWriter fout = new BufferedWriter(new FileWriter("rezultate.txt"))) {
            for (Student s : filtrati) {
                fout.write(s.toString());
                fout.newLine(); // Adaugă separatorul de linie specific sistemului
            }
        } catch (IOException e) {
            System.err.println("Eroare la scriere: " + e.getMessage());
        }
    }
}