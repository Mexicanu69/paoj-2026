package com.pao.laboratory09.exercise2;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.Scanner;

public class Main {
    private static final String OUTPUT_FILE = "output/lab09_ex2.bin";
    private static final int RECORD_SIZE = 32;

    public static void main(String[] args) throws Exception {
        Locale.setDefault(Locale.US);
        Scanner scanner = new Scanner(System.in);

        File out = new File(OUTPUT_FILE);
        if (out.getParentFile() != null) out.getParentFile().mkdirs();

        if (!scanner.hasNextInt()) return;
        int n = scanner.nextInt();

        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(OUTPUT_FILE))) {
            for (int i = 0; i < n; i++) {
                int id = scanner.nextInt();
                double suma = scanner.nextDouble();
                String data = scanner.next();
                String tipStr = scanner.next(); 
                
                dos.write(ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN).putInt(id).array());
                
                dos.write(ByteBuffer.allocate(8).order(ByteOrder.LITTLE_ENDIAN).putDouble(suma).array());

                byte[] dataBytes = new byte[10];
                byte[] rawData = data.getBytes(StandardCharsets.US_ASCII);
                System.arraycopy(rawData, 0, dataBytes, 0, Math.min(rawData.length, 10));
                for (int j = rawData.length; j < 10; j++) dataBytes[j] = (byte) ' ';
                dos.write(dataBytes);

                dos.writeByte(tipStr.equalsIgnoreCase("CREDIT") ? 0 : 1);
                dos.writeByte(0); 

                dos.write(new byte[8]);
            }
        }

        try (RandomAccessFile raf = new RandomAccessFile(OUTPUT_FILE, "rw")) {
            while (scanner.hasNext()) {
                String comanda = scanner.next();
                if (comanda.equals("READ")) {
                    printRecord(raf, scanner.nextInt());
                } else if (comanda.equals("UPDATE")) {
                    int idx = scanner.nextInt();
                    String newStatus = scanner.next();
                    int statusCode = newStatus.equals("PROCESSED") ? 1 : newStatus.equals("REJECTED") ? 2 : 0;
                    
                    raf.seek((long) idx * RECORD_SIZE + 23);
                    raf.write(statusCode);
                    System.out.println("Updated [" + idx + "]: " + newStatus);
                } else if (comanda.equals("PRINT_ALL")) {
                    for (int i = 0; i < n; i++) {
                        printRecord(raf, i);
                    }
                }
            }
        }
    }

    private static void printRecord(RandomAccessFile raf, int idx) throws IOException {
        byte[] buffer = new byte[RECORD_SIZE];
        raf.seek((long) idx * RECORD_SIZE);
        raf.readFully(buffer);

        ByteBuffer bb = ByteBuffer.wrap(buffer).order(ByteOrder.LITTLE_ENDIAN);
        
        int id = bb.getInt(0);
        double suma = bb.getDouble(4);
        String data = new String(buffer, 12, 10, StandardCharsets.US_ASCII).trim();
        
        int tipCode = buffer[22] & 0xFF;
        int statusCode = buffer[23] & 0xFF;

        String tip = (tipCode == 0) ? "CREDIT" : "DEBIT";
        String status = (statusCode == 1) ? "PROCESSED" : (statusCode == 2) ? "REJECTED" : "PENDING";

        System.out.printf("[%d] id=%d data=%s tip=%s suma=%.2f RON status=%s\n",
                idx, id, data, tip, suma, status);
    }
}