package com.pao.proiect.bookster.repository;

import com.pao.proiect.bookster.model.Autor;
import com.pao.proiect.bookster.model.Carte;
import com.pao.proiect.bookster.model.ISBN;
import com.pao.proiect.bookster.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CarteRepository implements Repository<Carte, String> {
    private final Connection conn = DatabaseConnection.getInstance().getConnection();

    @Override
    public void save(Carte carte) {
        String sql = "INSERT INTO Carti(titlu, nume_autor, email_autor, biografie_autor, isbn_complet, categorie, exemplare_disponibile) VALUES(?,?,?,?,?,?,?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, carte.getTitlu());
            pstmt.setString(2, carte.getAutor().getNume());
            pstmt.setString(3, carte.getAutor().getEmail());
            pstmt.setString(4, carte.getAutor().getBiografie());
            pstmt.setString(5, carte.toString().substring(carte.toString().indexOf("[") + 1, carte.toString().indexOf("]"))); // sau adauga getter in Carte pentru isbn obiect
            pstmt.setString(6, carte.getCategorie());
            pstmt.setInt(7, carte.getExemplareDisponibile());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<Carte> findById(String titlu) {
        String sql = "SELECT * FROM Carti WHERE LOWER(titlu) = LOWER(?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, titlu);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Autor autor = new Autor(rs.getString("nume_autor"), rs.getString("email_autor"), rs.getString("biografie_autor"));
                    String[] parts = rs.getString("isbn_complet").split("-");
                    ISBN isbn = new ISBN(parts[0], parts.length > 1 ? parts[1] : "", parts.length > 2 ? parts[2] : "", parts.length > 3 ? parts[3] : "");
                    Carte carte = new Carte(rs.getString("titlu"), autor, isbn, rs.getString("categorie"), rs.getInt("exemplare_disponibile"));
                    return Optional.of(carte);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<Carte> findAll() {
        List<Carte> list = new ArrayList<>();
        String sql = "SELECT * FROM Carti ORDER BY titlu ASC";
        try (PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                Autor autor = new Autor(rs.getString("nume_autor"), rs.getString("email_autor"), rs.getString("biografie_autor"));
                String[] parts = rs.getString("isbn_complet").split("-");
                ISBN isbn = new ISBN(parts[0], parts.length > 1 ? parts[1] : "", parts.length > 2 ? parts[2] : "", parts.length > 3 ? parts[3] : "");
                list.add(new Carte(rs.getString("titlu"), autor, isbn, rs.getString("categorie"), rs.getInt("exemplare_disponibile")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public void update(Carte carte) {
        String sql = "UPDATE Carti SET exemplare_disponibile = ? WHERE LOWER(titlu) = LOWER(?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, carte.getExemplareDisponibile());
            pstmt.setString(2, carte.getTitlu());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(String titlu) {
        String sql = "DELETE FROM Carti WHERE LOWER(titlu) = LOWER(?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, titlu);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}