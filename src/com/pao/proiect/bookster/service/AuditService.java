package com.pao.proiect.bookster.service;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

public class AuditService {
    private static AuditService instance;
    private static final String FILE_PATH = "audit.csv";

    private AuditService() {}

    public static synchronized AuditService getInstance() {
        if (instance == null) {
            instance = new AuditService();
        }
        return instance;
    }

    // Metoda thread-safe
    public synchronized void logActiune(String numeActiune) {
        try (FileWriter fw = new FileWriter(FILE_PATH, true);
             PrintWriter pw = new PrintWriter(fw)) {
            pw.println(numeActiune + "," + LocalDateTime.now());
        } catch (IOException e) {
            System.err.println("Eroare la scrierea in audit: " + e.getMessage());
        }
    }
}