package com.pao.laboratory10.exercise3;

import com.pao.laboratory10.exercise1.TipTranzactie;
import java.util.*;
import java.util.stream.Collectors;

class TranzactieExtinsa {
    private int id;
    private double suma;
    private String data;
    private TipTranzactie tip;
    private String contSursa;

    public TranzactieExtinsa(int id, double suma, String data, TipTranzactie tip, String contSursa) {
        this.id = id;
        this.suma = suma;
        this.data = data;
        this.tip = tip;
        this.contSursa = contSursa;
    }

    public int getId() { return id; }
    public double getSuma() { return suma; }
    public String getData() { return data; }
    public TipTranzactie getTip() { return tip; }
    public String getContSursa() { return contSursa; }

    @Override
    public String toString() {
        return String.format(Locale.US, "[%d] %s %s: %.2f RON (Sursa: %s)", id, data, tip, suma, contSursa);
    }
}

public class Main {
    public static void main(String[] args) {
        List<TranzactieExtinsa> tranzactii = Arrays.asList(
            new TranzactieExtinsa(1, 150.0, "2024-01-10", TipTranzactie.CREDIT, "RO01BANK"),
            new TranzactieExtinsa(2, 200.0, "2024-01-15", TipTranzactie.DEBIT, "RO02BANK"),
            new TranzactieExtinsa(3, 50.0,  "2024-01-20", TipTranzactie.DEBIT, "RO01BANK"),
            new TranzactieExtinsa(4, 1000.0,"2024-02-05", TipTranzactie.CREDIT, "RO03BANK"),
            new TranzactieExtinsa(5, 300.0, "2024-02-14", TipTranzactie.DEBIT, "RO02BANK"),
            new TranzactieExtinsa(6, 450.0, "2024-02-28", TipTranzactie.CREDIT, "RO01BANK"),
            new TranzactieExtinsa(7, 120.0, "2024-03-02", TipTranzactie.DEBIT, "RO03BANK"),
            new TranzactieExtinsa(8, 800.0, "2024-03-10", TipTranzactie.CREDIT, "RO02BANK"),
            new TranzactieExtinsa(9, 25.0,  "2024-03-15", TipTranzactie.DEBIT, "RO01BANK"),
            new TranzactieExtinsa(10, 600.0, "2024-04-25", TipTranzactie.CREDIT, "RO03BANK")
        );

        // 1. filter(tip == CREDIT)
        System.out.println("\n1. Lista tranzactii CREDIT");
        tranzactii.stream().filter(t -> t.getTip() == TipTranzactie.CREDIT).forEach(System.out::println);

        // 2. mapToDouble(suma).sum()
        System.out.println("\n2. Total procesat");
        double total = tranzactii.stream().mapToDouble(TranzactieExtinsa::getSuma).sum();
        System.out.printf(Locale.US, "Total procesat: %.2f RON\n", total);

        // 3. Suma totala per luna 
        System.out.println("\n");
        Map<String, Double> sumePeLuna = tranzactii.stream().collect(Collectors.groupingBy(
                        t -> t.getData().substring(0, 7), 
                        TreeMap::new,                     
                        Collectors.summingDouble(TranzactieExtinsa::getSuma) 
                ));
        sumePeLuna.forEach((luna, suma) -> System.out.printf(Locale.US, "%s: %.2f RON\n", luna, suma));

        // 4. sorted(reversed).limit(3)
        System.out.println("\n4. Top 3 tranzactii");
        tranzactii.stream().sorted(Comparator.comparingDouble(TranzactieExtinsa::getSuma).reversed())
        .limit(3).forEach(System.out::println);

        // 5. map(contSursa).distinct().collect()
        System.out.println("\n5. Conturi sursa unice");
        List<String> conturiUnice = tranzactii.stream()
                .map(TranzactieExtinsa::getContSursa)
                .distinct()
                .collect(Collectors.toList());
        System.out.println("Conturi sursa unice: " + conturiUnice);

        // 6. mapToDouble(suma).average()
        System.out.println("\n6. Suma medie");
        tranzactii.stream()
                .mapToDouble(TranzactieExtinsa::getSuma)
                .average()
                .ifPresent(avg -> System.out.printf(Locale.US, "Suma medie: %.2f RON\n", avg));


        // 7. Extrase de cont lunare 
        System.out.println("\n7. Extrase de cont lunare (Detaliat)");
        Map<String, List<TranzactieExtinsa>> grupatePeLuna = tranzactii.stream()
                .collect(Collectors.groupingBy(
                        t -> t.getData().substring(0, 7),
                        TreeMap::new,
                        Collectors.toList() 
                ));

        grupatePeLuna.forEach((luna, lista) -> {
        double totalLuna = lista.stream().mapToDouble(TranzactieExtinsa::getSuma).sum();
        System.out.printf(Locale.US, "EXTRAS DE CONT - %s: %d tranzactii, total: %.2f RON\n", 
                        luna, lista.size(), totalLuna);
        });
    }
}