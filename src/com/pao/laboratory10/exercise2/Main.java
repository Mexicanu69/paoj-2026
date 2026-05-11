package com.pao.laboratory10.exercise2;

import com.pao.laboratory10.exercise1.TipTranzactie;
import com.pao.laboratory10.exercise1.Tranzactie;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in).useLocale(Locale.US);
        ArrayList<Tranzactie> listaTranzactii = new ArrayList<>();

        if (!scanner.hasNextInt()) return;
        int n = scanner.nextInt();

        // 1. Citirea celor N tranzacții
        for (int i = 0; i < n; i++) {
            int id = scanner.nextInt();
            double suma = scanner.nextDouble();
            String data = scanner.next();
            TipTranzactie tip = TipTranzactie.valueOf(scanner.next());
            listaTranzactii.add(new Tranzactie(id, suma, data, tip));
        }

        // 2. Procesarea comenzilor până la EOF
        while (scanner.hasNext()) {
            String comanda = scanner.next();

            switch (comanda) {
                case "UNIQUE_IDS":
                    LinkedHashSet<Integer> ids = new LinkedHashSet<>();
                    for (Tranzactie t : listaTranzactii) {
                        ids.add(t.getId());
                    }
                    System.out.println("IDs unice (" + ids.size() + "): " + ids);
                    break;

                case "MONTHLY_REPORT":
                    // TreeMap sortează cheile (yyyy-MM) alfabetic/cronologic
                    TreeMap<String, double[]> raport = new TreeMap<>();
                    for (Tranzactie t : listaTranzactii) {
                        String luna = t.getData().substring(0, 7);
                        raport.putIfAbsent(luna, new double[2]); // [0] = CREDIT, [1] = DEBIT
                        
                        if (t.getTip() == TipTranzactie.CREDIT) {
                            raport.get(luna)[0] += t.getSuma();
                        } else {
                            raport.get(luna)[1] += t.getSuma();
                        }
                    }
                    raport.forEach((luna, sume) -> 
                        System.out.printf(Locale.US, "%s: CREDIT %.2f RON, DEBIT %.2f RON\n", 
                                          luna, sume[0], sume[1]));
                    break;

                case "TOP":
                    int topN = scanner.nextInt();
                    ArrayList<Tranzactie> copieTop = new ArrayList<>(listaTranzactii);
                    copieTop.sort((t1, t2) -> Double.compare(t2.getSuma(), t1.getSuma())); // Descrescător
                    System.out.println("Top " + topN + ":");
                    for (int i = 0; i < Math.min(topN, copieTop.size()); i++) {
                        System.out.println(copieTop.get(i));
                    }
                    break;

                case "SORT_ASC":
                    listaTranzactii.sort(Comparator.comparingDouble(Tranzactie::getSuma));
                    afiseazaLista(listaTranzactii);
                    break;

                case "SORT_DESC":
                    listaTranzactii.sort((t1, t2) -> Double.compare(t2.getSuma(), t1.getSuma()));
                    afiseazaLista(listaTranzactii);
                    break;

                case "REVERSE":
                    Collections.reverse(listaTranzactii);
                    afiseazaLista(listaTranzactii);
                    break;

                case "MIN_MAX":
                    if (!listaTranzactii.isEmpty()) {
                        Tranzactie min = Collections.min(listaTranzactii, Comparator.comparingDouble(Tranzactie::getSuma));
                        Tranzactie max = Collections.max(listaTranzactii, Comparator.comparingDouble(Tranzactie::getSuma));
                        System.out.println("MIN: " + min);
                        System.out.println("MAX: " + max);
                    }
                    break;

                case "CME_DEMO":
                    try {
                        for (Tranzactie t : listaTranzactii) {
                            listaTranzactii.remove(t); // Va arunca excepția la prima iterare
                        }
                    } catch (ConcurrentModificationException e) {
                        System.out.println("ConcurrentModificationException prins: modificare in iteratie detectata.");
                    }
                    break;
            }
        }
        scanner.close();
    }

    private static void afiseazaLista(List<Tranzactie> lista) {
        for (Tranzactie t : lista) {
            System.out.println(t);
        }
    }
}