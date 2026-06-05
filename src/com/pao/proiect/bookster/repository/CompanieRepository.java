package com.pao.proiect.bookster.repository;

import com.pao.proiect.bookster.util.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CompanieRepository implements Repository<String, String> {
    private final Connection conn = DatabaseConnection.getInstance().getConnection();

    @Override
    public void save(String nume) {
        String sql = "INSERT INTO Companii(nume) VALUES(?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nume);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<String> findById(String nume) {
        String sql = "SELECT nume FROM Companii WHERE LOWER(nume) = LOWER(?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nume);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) return Optional.of(rs.getString("nume"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<String> findAll() {
        List<String> companii = new ArrayList<>();
        String sql = "SELECT nume FROM Companii";
        try (PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) companii.add(rs.getString("nume"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return companii;
    }

    @Override
    public void update(String entity) {}

    @Override
    public void delete(String id) {}
}