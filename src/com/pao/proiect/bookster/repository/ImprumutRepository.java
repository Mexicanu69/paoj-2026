package com.pao.proiect.bookster.repository;

import com.pao.proiect.bookster.util.DatabaseConnection;
import java.sql.*;
import java.time.LocalDateTime;

public class ImprumutRepository {
    private final Connection conn = DatabaseConnection.getInstance().getConnection();

    public void inregistreazaImprumutTranzactie(String emailClient, String titluCarte) throws SQLException {
        String sqlImprumut = "INSERT INTO Imprumuturi(email_client, titlu_carte, data_imprumut, este_returnat) VALUES(?,?,?,0)";
        String sqlStoc = "UPDATE Carti SET exemplare_disponibile = exemplare_disponibile - 1 WHERE LOWER(titlu) = LOWER(?) AND exemplare_disponibile > 0";

        try {
            conn.setAutoCommit(false);

            try (PreparedStatement pstmt1 = conn.prepareStatement(sqlImprumut);
                 PreparedStatement pstmt2 = conn.prepareStatement(sqlStoc)) {
                
                pstmt1.setString(1, emailClient);
                pstmt1.setString(2, titluCarte);
                pstmt1.setString(3, LocalDateTime.now().toString());
                pstmt1.executeUpdate();

                pstmt2.setString(1, titluCarte);
                int rânduriAfectate = pstmt2.executeUpdate();
                
                if (rânduriAfectate == 0) {
                    throw new SQLException("Stoc insuficient pentru cartea: " + titluCarte);
                }

                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        } finally {
            conn.setAutoCommit(true);
        }
    }

    public void returneazaCarteTranzactie(String emailClient, String titluCarte) throws SQLException {
        String sqlUpdateImprumut = "UPDATE Imprumuturi SET este_returnat = 1 WHERE email_client = ? AND LOWER(titlu_carte) = LOWER(?) AND este_returnat = 0";
        String sqlUpdateStoc = "UPDATE Carti SET exemplare_disponibile = exemplare_disponibile + 1 WHERE LOWER(titlu) = LOWER(?)";

        try {
            conn.setAutoCommit(false);
            try (PreparedStatement pstmt1 = conn.prepareStatement(sqlUpdateImprumut);
                 PreparedStatement pstmt2 = conn.prepareStatement(sqlUpdateStoc)) {
                
                pstmt1.setString(1, emailClient);
                pstmt1.setString(2, titluCarte);
                int modificate = pstmt1.executeUpdate();

                if (modificate > 0) {
                    pstmt2.setString(1, titluCarte);
                    pstmt2.executeUpdate();
                }
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        } finally {
            conn.setAutoCommit(true);
        }
    }

    // JOIN1
    public void afiseazaTopCartiImprumutate() {
        String sql = "SELECT c.titlu, c.nume_autor, COUNT(i.id) as total FROM Carti c " +
                     "JOIN Imprumuturi i ON LOWER(c.titlu) = LOWER(i.titlu_carte) " +
                     "GROUP BY c.titlu ORDER BY total DESC";
        try (PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            System.out.println("\n--- TOP CARTI IMPRUMUTATE (JOIN) ---");
            while (rs.next()) {
                System.out.println(rs.getString("titlu") + " de " + rs.getString("nume_autor") + " | Imprumuturi: " + rs.getInt("total"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // JOIN2
    public void afiseazaImprumuturiActive() {
        String sql = "SELECT cl.nume, cl.email, cl.nume_companie, i.titlu_carte FROM Imprumuturi i " +
                     "JOIN Clienti cl ON i.email_client = cl.email " +
                     "WHERE i.este_returnat = 0";
        try (PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            System.out.println("\n--- IMPRUMUTURI ACTIVE (JOIN) ---");
            while (rs.next()) {
                System.out.println("Client: " + rs.getString("nume") + " (" + rs.getString("nume_companie") + ") are in prezent: " + rs.getString("titlu_carte"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // JOIN3
    public void afiseazaStatisticiCompanii() {
        String sql = "SELECT comp.nume, COUNT(cl.email) as nr_angajati FROM Companii comp " +
                     "LEFT JOIN Clienti cl ON comp.nume = cl.nume_companie " +
                     "GROUP BY comp.nume";
        try (PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            System.out.println("\n--- STATISTICI COMPANII (JOIN) ---");
            while (rs.next()) {
                System.out.println("Compania: " + rs.getString("nume") + " | Angajati inscrisi: " + rs.getInt("nr_angajati"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}