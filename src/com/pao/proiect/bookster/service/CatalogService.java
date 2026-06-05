package com.pao.proiect.bookster.service;

import com.pao.proiect.bookster.model.Carte;
import com.pao.proiect.bookster.repository.CarteRepository;
import java.util.List;
import java.util.stream.Collectors;

public class CatalogService {
    private static CatalogService instance;
    private final CarteRepository carteRepository = new CarteRepository();
    private final AuditService audit = AuditService.getInstance();

    private CatalogService() {}

    public static CatalogService getInstance() {
        if (instance == null) instance = new CatalogService();
        return instance;
    }

    public void adaugaCarte(Carte c) {
        audit.logActiune("adauga_carte");
        carteRepository.save(c);
    }

    public void stergeCarte(String titlu) {
        audit.logActiune("sterge_carte");
        carteRepository.delete(titlu);
    }

    public List<Carte> listeazaToate() {
        audit.logActiune("listeaza_toate_cartile");
        return carteRepository.findAll();
    }

    public Carte cautaDupaTitlu(String titlu) {
        audit.logActiune("cauta_carte_dupa_titlu");
        return carteRepository.findById(titlu).orElse(null);
    }

    public List<Carte> filterDupaCategorie(String cat) {
        audit.logActiune("listeaza_carti_dupa_categorie");
        return carteRepository.findAll().stream()
                .filter(c -> c.getCategorie().equalsIgnoreCase(cat))
                .collect(Collectors.toList());
    }
}