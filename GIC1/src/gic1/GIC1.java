package gic1;

import java.io.IOException;


public class GIC1 {
    public static void main(String[] args) {
    try {
        // Citim producțiile din fișierul GiC.txt
        ListaProductii listaProductii = Lema3.citesteDinFisier("GiC.txt");

        // Afișăm producțiile citite pentru verificare
        System.out.println("Productii initiale citite:\n" + listaProductii);

        // Calculăm mulțimile inițiale
        Lema3 lema3 = new Lema3(listaProductii);
        RezultatAnaliza rezultatInitial = lema3.analizeaza();

        // Afișăm mulțimile inițiale
        System.out.println("Multimea initiala a neterminalelor: " + rezultatInitial.getNeterminale());
        System.out.println("Multimea initiala a terminalelor: " + rezultatInitial.getTerminale());

        // Aplicăm Lema 1 pentru a obține doar elementele utile
        RezultatAnaliza rezultatLema1 = lema3.aplicaLema1(rezultatInitial.getTerminale(), rezultatInitial.getProductii());

        // Afișăm rezultatele după aplicarea Lemei 1
        System.out.println("\nRezultate dupa aplicarea Lemei 1:");
        System.out.println("Neterminale utile: " + rezultatLema1.getNeterminale());
        System.out.println("Terminale utile: " + rezultatLema1.getTerminale());
        System.out.println("Productii utile:");
        for (Productie productie : rezultatLema1.getProductii()) {
            System.out.println(productie);
        }
                // Aplicăm Lema 2 pe rezultatele din Lema 1
        RezultatAnaliza rezultatLema2 = lema3.aplicaLema2("S",rezultatLema1.getNeterminale(), rezultatLema1.getProductii());

        // Afișăm rezultatele după aplicarea Lemei 2
        System.out.println("\nRezultate dupa aplicarea Lemei 2:");
        System.out.println("Neterminale accesibile: " + rezultatLema2.getNeterminale());
        System.out.println("Productii accesibile:");
        for (Productie productie : rezultatLema2.getProductii()) {
            System.out.println(productie);
        }
        
        // Aplicăm Lema 3 pe rezultatele din Lema 1
        RezultatAnaliza rezultatLema3 = lema3.aplicaLema3(rezultatLema2.getProductii(), rezultatLema2.getTerminale(), rezultatLema2.getNeterminale());

        // Afișăm rezultatele după aplicarea Lemei 2
        System.out.println("\nRezultate dupa aplicarea Lemei 3:");
        System.out.println("Neterminale nulabile: " + rezultatLema3.getNulabile());
        System.out.println("Productii:");
        for (Productie productie : rezultatLema3.getProductii()) {
            System.out.println(productie);
        }
        boolean poateGenereazaAB = lema3.verificaSubsir("B", "ab", rezultatLema3.getProductii());

        if (poateGenereazaAB) {
            System.out.println("B poate genera un sir care contine subsirul 'ab'.");
        } else {
            System.out.println("B nu poate genera un sir care contine subsirul 'ab'.");
        }


    } catch (IOException e) {
        System.err.println("Eroare la citirea fisierului: " + e.getMessage());
    }
}
    
}
