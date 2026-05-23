package com.pao.laboratory11.exercise3;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class Main {

    public record Transaction(int id, BigDecimal amount, LocalDate date, String country, String channel) {}

    public static final class Snapshot {
        private final Map<String, Long> countByCountry;
        private final Map<String, Long> countByChannel;
        private final BigDecimal totalAmount;
        private final List<Transaction> topTransactions;

        public Snapshot(Map<String, Long> byCountry, Map<String, Long> byChannel, BigDecimal total, List<Transaction> top) {
            this.countByCountry = Collections.unmodifiableMap(new HashMap<>(byCountry));
            this.countByChannel = Collections.unmodifiableMap(new HashMap<>(byChannel));
            this.totalAmount = total;
            this.topTransactions = List.copyOf(top); 
        }

        public Map<String, Long> getCountByCountry() { return countByCountry; }
        public Map<String, Long> getCountByChannel() { return countByChannel; }
        public BigDecimal getTotalAmount() { return totalAmount; }
        public List<Transaction> getTopTransactions() { return topTransactions; }
    }

    public static class CustomCollectors {
        
        public static Collector<Transaction, ?, Snapshot> toSnapshot(int topN) {
            class Agg {
                final Map<String, Long> byCountry = new HashMap<>();
                final Map<String, Long> byChannel = new HashMap<>();
                BigDecimal total = BigDecimal.ZERO;
                final List<Transaction> allTransactions = new ArrayList<>();

                void accumulate(Transaction tx) {
                    byCountry.put(tx.country(), byCountry.getOrDefault(tx.country(), 0L) + 1);
                    byChannel.put(tx.channel(), byChannel.getOrDefault(tx.channel(), 0L) + 1);
                    total = total.add(tx.amount());
                    allTransactions.add(tx);
                }

                Agg combine(Agg other) {
                    other.byCountry.forEach((k, v) -> this.byCountry.put(k, this.byCountry.getOrDefault(k, 0L) + v));
                    other.byChannel.forEach((k, v) -> this.byChannel.put(k, this.byChannel.getOrDefault(k, 0L) + v));
                    this.total = this.total.add(other.total);
                    this.allTransactions.addAll(other.allTransactions);
                    return this;
                }

                Snapshot finisher() {
                    List<Transaction> top = allTransactions.stream()
                            .sorted(Comparator.comparing(Transaction::amount).reversed()
                                    .thenComparing(Transaction::id))
                            .limit(topN)
                            .collect(Collectors.toList());

                    return new Snapshot(byCountry, byChannel, total, top);
                }
            }

            return Collector.of(
                    Agg::new,
                    Agg::accumulate,
                    Agg::combine,
                    Agg::finisher
            );
        }
    }

    public static void main(String[] args) {

        List<Transaction> data = List.of(
                new Transaction(1, new BigDecimal("1200.50"), LocalDate.now(), "RO", "WEB"),
                new Transaction(2, new BigDecimal("500.00"), LocalDate.now(), "US", "APP"),
                new Transaction(3, new BigDecimal("2500.00"), LocalDate.now(), "RO", "CRYPTO"),
                new Transaction(4, new BigDecimal("1500.00"), LocalDate.now(), "FR", "WEB"),
                new Transaction(5, new BigDecimal("1500.00"), LocalDate.now(), "DE", "APP"),
                new Transaction(6, new BigDecimal("300.00"), LocalDate.now(), "RO", "POS"),
                new Transaction(7, new BigDecimal("4500.00"), LocalDate.now(), "US", "CRYPTO")
        );

        Snapshot snap = data.stream().collect(CustomCollectors.toSnapshot(3));

        // Test 1
        System.out.println("Interogare 1: Top 3 Tranzactii ca valoare (cu tie-breaker pe ID):");
        snap.getTopTransactions().forEach(tx -> 
                System.out.printf("   ID: %d | Suma: %s | Tara: %s | Canal: %s%n", 
                        tx.id(), tx.amount(), tx.country(), tx.channel())
        );
        System.out.println();

        // Test 2 
        System.out.println("Interogare 2: Volumul de tranzactii pe tari (Ordonat Descrescator):");
        snap.getCountByCountry().entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .forEach(e -> System.out.printf("   Tara: %s -> %d tranzactii%n", e.getKey(), e.getValue()));
        System.out.println();

        // Test 3
        System.out.println("Interogare 3: Canalele utilizate ordonate dupa frecventa:");
        snap.getCountByChannel().entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .forEach(e -> System.out.printf("   Canal: %s -> utilizat de %d ori%n", e.getKey(), e.getValue()));
        System.out.println();

    }
}