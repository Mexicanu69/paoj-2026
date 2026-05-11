package com.pao.laboratory10.exercise1;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        LinkedList<Tranzactie> coada = new LinkedList<>();

        while (scanner.hasNext()) {
            String comanda = scanner.next();

            switch (comanda) {
                case "ENQUEUE":
                    coada.addLast(citesteTranzactie(scanner));
                    break;

                case "PUSH":
                    coada.addFirst(citesteTranzactie(scanner));
                    break;

                case "DEQUEUE":
                    if (coada.isEmpty()) {
                        System.out.println("Coada goala.");
                    } else {
                        System.out.println("Procesat: " + coada.removeFirst());
                    }
                    break;

                case "POP":
                    if (coada.isEmpty()) {
                        System.out.println("Coada goala.");
                    } else {
                        System.out.println("Extras: " + coada.removeFirst());
                    }
                    break;

                case "REMOVE_DEBIT":
                    int eliminatDebit = 0;
                    Iterator<Tranzactie> itDebit = coada.iterator();
                    while (itDebit.hasNext()) {
                        if (itDebit.next().getTip() == TipTranzactie.DEBIT) {
                            itDebit.remove();
                            eliminatDebit++;
                        }
                    }
                    System.out.println("Eliminat " + eliminatDebit + " tranzactii DEBIT.");
                    break;

                case "REMOVE_BELOW":
                    double threshold = Double.parseDouble(scanner.next());
                    int eliminatPrag = 0;
                    Iterator<Tranzactie> itPrag = coada.iterator();
                    while (itPrag.hasNext()) {
                        if (itPrag.next().getSuma() < threshold) {
                            itPrag.remove();
                            eliminatPrag++;
                        }
                    }
                    System.out.printf(Locale.US, "Eliminat %d tranzactii sub %.2f RON.\n", eliminatPrag, threshold);
                    break;

                case "PRINT":
                    for (Tranzactie t : coada) {
                        System.out.println(t);
                    }
                    break;

                case "SIZE":
                    System.out.println("Dimensiune coada: " + coada.size());
                    break;
            }
        }
        scanner.close();
    }

    private static Tranzactie citesteTranzactie(Scanner sc) {
        int id = sc.nextInt();
        double suma = Double.parseDouble(sc.next());
        String data = sc.next();
        TipTranzactie tip = TipTranzactie.valueOf(sc.next());
        return new Tranzactie(id, suma, data, tip);
    }
}