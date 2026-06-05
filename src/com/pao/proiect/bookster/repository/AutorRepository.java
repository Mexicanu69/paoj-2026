package com.pao.proiect.bookster.repository;

import com.pao.proiect.bookster.model.Autor;
import com.pao.proiect.bookster.util.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AutorRepository implements Repository<Autor, String> {
    private final Connection conn = DatabaseConnection.getInstance().getConnection();

    @Override
    public void save(Autor autor) {
        String sql = "INSERT INTO Autori(email, nume, biografie) VALUES(?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, autor.getEmail());
            pstmt.setString(2, autor.getNume());
            pstmt.setString(3, autor.getBiografia());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<Autor> findById(String email) {
        String sql = "SELECT * FROM Autori WHERE LOWER(email) = LOWER(?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, email);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Autor autor = new Autor(rs.getString("nume"), rs.getString("email"), rs.getString("biografie"));
                    return Optional.of(autor);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<Autor> findAll() {
        List<Autor> autori = new ArrayList<>();
        String sql = "SELECT * FROM Autori";
        try (PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                autori.add(new Autor(rs.getString("nume"), rs.getString("email"), rs.getString("biografie")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return autori;
    }

    @Override
    public void update(Autor autor) {
        String sql = "UPDATE Autori SET nume = ?, biografie = ? WHERE LOWER(email) = LOWER(?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, autor.getNume());
            pstmt.setString(2, autor.getBiografia());
            pstmt.setString(3, autor.getEmail());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(String email) {
        String sql = "DELETE FROM Autori WHERE LOWER(email) = LOWER(?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, email);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}