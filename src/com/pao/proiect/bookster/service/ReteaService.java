package com.pao.proiect.bookster.service;

import com.pao.proiect.bookster.exception.*;
import com.pao.proiect.bookster.model.*;
import java.util.*;

public class ReteaService {
    private static ReteaService instance;
    private Set<String> companiiPartnere = new HashSet<>(); // Colectie tip Set
    private List<Client> clienti = new ArrayList<>();
    private Map<String, List<Imprumut>> imprumuturiPeCompanie = new HashMap<>(); // Colectie tip Map

    private ReteaService() {}

    public static ReteaService getInstance() {
        if (instance == null) instance = new ReteaService();
        return instance;
    }

    public void adaugaCompanie(String nume) {
        companiiPartnere.add(nume);
    }

    public void inregistreazaClient(Client c) {
        if (companiiPartnere.contains(c.getNumeCompanie())) {
            clienti.add(c);
            System.out.println("Procesare inregistrare finalizata.");
        }
        else{
            System.out.print("Compania nu exista");
        }
    }

    public void realizeazaImprumut(String emailClient, String titluCarte) throws ClientNegasitException, CarteNedisponibilaException {
        Client gasit = null;
        for (Client c : clienti) {
            if (c.getEmail().equals(emailClient)) {
                gasit = c;
                break;
            }
        }

        if (gasit == null) throw new ClientNegasitException("Clientul cu email-ul " + emailClient + " nu exista.");

        Carte carte = CatalogService.getInstance().cautaDupaTitlu(titluCarte);
        if (carte == null || carte.getExemplareDisponibile() <= 0) {
            throw new CarteNedisponibilaException("Cartea nu este in stoc.");
        }

        carte.setExemplareDisponibile(carte.getExemplareDisponibile() - 1);
        Imprumut i = new Imprumut(gasit, carte);
        
        imprumuturiPeCompanie.putIfAbsent(gasit.getNumeCompanie(), new ArrayList<>());
        imprumuturiPeCompanie.get(gasit.getNumeCompanie()).add(i);
    }

    public List<Client> getClientiDinCompanie(String numeCompanie) {
        List<Client> rezultate = new ArrayList<>();
        for (Client c : clienti) {
            if (c.getNumeCompanie().equals(numeCompanie)) rezultate.add(c);
        }
        return rezultate;
    }

    public void returneazaCarte(String email, String titlu) {
        // Logica simplificata pentru returnare
        for (List<Imprumut> lista : imprumuturiPeCompanie.values()) {
            for (Imprumut i : lista) {
                if (i.getClient().getEmail().equals(email) && i.getCarte().getTitlu().equals(titlu) && !i.esteReturnat()) {
                    i.returneaza();
                    i.getCarte().setExemplareDisponibile(i.getCarte().getExemplareDisponibile() + 1);
                    return;
                }
            }
        }
    }
}