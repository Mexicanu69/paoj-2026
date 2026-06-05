package com.pao.proiect.bookster.service;

import com.pao.proiect.bookster.exception.*;
import com.pao.proiect.bookster.model.*;
import com.pao.proiect.bookster.repository.*;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class ReteaService {
    private static ReteaService instance;
    private final CompanieRepository companieRepo = new CompanieRepository();
    private final ClientRepository clientRepo = new ClientRepository();
    private final ImprumutRepository imprumutRepo = new ImprumutRepository();
    private final AuditService audit = AuditService.getInstance();

    private ReteaService() {}

    public static ReteaService getInstance() {
        if (instance == null) instance = new ReteaService();
        return instance;
    }

    public void adaugaCompanie(String nume) {
        audit.logActiune("adauga_companie");
        companieRepo.save(nume);
    }

    public void inregistreazaClient(Client c) {
        audit.logActiune("inregistreaza_client");
        if (companieRepo.findById(c.getNumeCompanie()).isPresent()) {
            clientRepo.save(c);
            System.out.println("Procesare inregistrare finalizata.");
        } else {
            System.out.println("Compania nu exista.");
        }
    }

    public void realizeazaImprumut(String emailClient, String titluCarte) throws ClientNegasitException, CarteNedisponibilaException {
        audit.logActiune("imprumuta_carte");
        if (clientRepo.findById(emailClient).isEmpty()) {
            throw new ClientNegasitException("Clientul cu email-ul " + emailClient + " nu exista.");
        }
        
        Carte carte = CatalogService.getInstance().cautaDupaTitlu(titluCarte);
        if (carte == null || carte.getExemplareDisponibile() <= 0) {
            throw new CarteNedisponibilaException("Cartea nu este in stoc.");
        }

        try {
            imprumutRepo.inregistreazaImprumutTranzactie(emailClient, titluCarte);
        } catch (SQLException e) {
            throw new CarteNedisponibilaException("Tranzactia a esuat: " + e.getMessage());
        }
    }

    public List<Client> getClientiDinCompanie(String numeCompanie) {
        audit.logActiune("listeaza_angajati_companie");
        return clientRepo.findAll().stream()
                .filter(c -> c.getNumeCompanie().equalsIgnoreCase(numeCompanie))
                .collect(Collectors.toList());
    }

    public void returneazaCarte(String email, String titlu) {
        audit.logActiune("returneaza_carte");
        try {
            imprumutRepo.returneazaCarteTranzactie(email, titlu);
        } catch (SQLException e) {
            System.out.println("Eroare la returnare DB: " + e.getMessage());
        }
    }

    public void afiseazaTopCarti() { imprumutRepo.afiseazaTopCartiImprumutate(); }
    public void afiseazaActive() { imprumutRepo.afiseazaImprumuturiActive(); }
    public void afiseazaStatistici() { imprumutRepo.afiseazaStatisticiCompanii(); }
}