package com.pao.proiect.bookster.repository;

import com.pao.proiect.bookster.model.Client;
import com.pao.proiect.bookster.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ClientRepository implements Repository<Client, String> {
    private final Connection conn = DatabaseConnection.getInstance().getConnection();

    @Override
    public void save(Client client) {
        String sql = "INSERT INTO Clienti(email, nume, nume_companie) VALUES(?,?,?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, client.getEmail());
            pstmt.setString(2, client.getNume());
            pstmt.setString(3, client.getNumeCompanie());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<Client> findById(String email) {
        String sql = "SELECT * FROM Clienti WHERE email = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, email);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(new Client(rs.getString("nume"), rs.getString("email"), rs.getString("nume_companie")));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<Client> findAll() {
        List<Client> list = new ArrayList<>();
        String sql = "SELECT * FROM Clienti";
        try (PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                list.add(new Client(rs.getString("nume"), rs.getString("email"), rs.getString("nume_companie")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public void update(Client entity) {}

    @Override
    public void delete(String email) {}
}