package com.pao.proiect.bookster;

import com.pao.proiect.bookster.exception.*;
import com.pao.proiect.bookster.model.*;
import com.pao.proiect.bookster.service.*;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        CatalogService catalog = CatalogService.getInstance();
        ReteaService retea = ReteaService.getInstance();
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        System.out.println("Platforma bookster (placeholder name lol)");

        while (running) {
            System.out.println("\nSelectati o actiune:");
            System.out.println("1. Adauga companie partenera");
            System.out.println("2. Inregistreaza client nou");
            System.out.println("3. Adauga carte in catalog");
            System.out.println("4. Imprumuta o carte");
            System.out.println("5. Returneaza o carte");
            System.out.println("6. Cauta carte dupa titlu");
            System.out.println("7. Listeaza carti dupa categorie");
            System.out.println("8. Verifica stoc (exemplare disponibile)");
            System.out.println("9. Listeaza angajatii unei companii");
            System.out.println("10. Sterge o carte din sistem");
            System.out.println("0. Iesire");
            System.out.print("Optiune: ");

            int optiune = Integer.parseInt(scanner.nextLine());

            switch (optiune) {
                case 1:
                    System.out.print("Nume companie: ");
                    String numeComp = scanner.nextLine();
                    retea.adaugaCompanie(numeComp);
                    System.out.println("Companie adaugata cu succes.");
                    break;

                case 2:
                    System.out.print("Nume client: ");
                    String numeCl = scanner.nextLine();
                    System.out.print("Email client: ");
                    String emailCl = scanner.nextLine();
                    System.out.print("Nume companie angajatoare: ");
                    String compAngajator = scanner.nextLine();
                    retea.inregistreazaClient(new Client(numeCl, emailCl, compAngajator));
                    break;

                case 3:
                    System.out.print("Titlu carte: ");
                    String titlu = scanner.nextLine();
                    System.out.print("Nume autor: ");
                    String numeAut = scanner.nextLine();
                    System.out.print("Categorie: ");
                    String cat = scanner.nextLine();
                    System.out.print("Prefix ISBN (ex: 978): ");
                    String pref = scanner.nextLine();
                    System.out.print("Nr. exemplare: ");
                    int stoc = Integer.parseInt(scanner.nextLine());
                    
                    Autor aut = new Autor(numeAut, numeAut.toLowerCase() + "@autor.com", "Biografie standard.");
                    ISBN isbn = new ISBN(pref, "973", "100", "1");
                    catalog.adaugaCarte(new Carte(titlu, aut, isbn, cat, stoc));
                    System.out.println("Carte adaugata in catalog.");
                    break;

                case 4:
                    System.out.print("Email client: ");
                    String eImprumut = scanner.nextLine();
                    System.out.print("Titlu carte: ");
                    String tImprumut = scanner.nextLine();
                    try {
                        retea.realizeazaImprumut(eImprumut, tImprumut);
                        System.out.println("Imprumut inregistrat.");
                    } catch (ClientNegasitException | CarteNedisponibilaException e) {
                        System.out.println("Eroare: " + e.getMessage());
                    }
                    break;

                case 5:
                    System.out.print("Email client: ");
                    String eRet = scanner.nextLine();
                    System.out.print("Titlu carte: ");
                    String tRet = scanner.nextLine();
                    retea.returneazaCarte(eRet, tRet);
                    System.out.println("Operatiune de returnare finalizata.");
                    break;

                case 6:
                    System.out.print("Introduceti titlul: ");
                    String tCautat = scanner.nextLine();
                    Carte c = catalog.cautaDupaTitlu(tCautat);
                    System.out.println(c != null ? "Gasit: " + c : "Cartea nu a fost gasita.");
                    break;

                case 7:
                    System.out.print("Categorie: ");
                    String catFiltru = scanner.nextLine();
                    List<Carte> filtrate = catalog.filterDupaCategorie(catFiltru);
                    System.out.println("Rezultate: " + filtrate);
                    break;

                case 8:
                    System.out.print("Titlu carte: ");
                    String tStoc = scanner.nextLine();
                    Carte cStoc = catalog.cautaDupaTitlu(tStoc);
                    if (cStoc != null) {
                        System.out.println("Exemplare disponibile: " + cStoc.getExemplareDisponibile());
                    } else {
                        System.out.println("Carte inexistenta.");
                    }
                    break;

                case 9:
                    System.out.print("Nume companie: ");
                    String nCompList = scanner.nextLine();
                    List<Client> angajati = retea.getClientiDinCompanie(nCompList);
                    System.out.println("Angajati inregistrati: " + angajati);
                    break;

                case 10:
                    System.out.print("Titlu carte de sters: ");
                    String tSters = scanner.nextLine();
                    catalog.stergeCarte(tSters);
                    System.out.println("Daca a existat, cartea a fost eliminata.");
                    break;

                case 0:
                    running = false;
                    System.out.println("Inchidere aplicatie...");
                    break;

                default:
                    System.out.println("Optiune invalida.");
            }
        }
        scanner.close();
    }
}