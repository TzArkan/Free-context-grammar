package gic1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class Lema3 {

    private ListaProductii listaProductii;

    public Lema3(ListaProductii listaProductii) {
        this.listaProductii = listaProductii;
    }

    public RezultatAnaliza analizeaza() {
        Set<String> nulabile = new HashSet<>();
        Set<String> terminale = new HashSet<>();
        Set<String> neterminale = new HashSet<>();
        Set<Productie> productii = new HashSet<>(listaProductii.getProductii());
        boolean update;

        
        do {
            update = false;
            for (Productie productie : listaProductii.getProductii()) {
                String neterminal = productie.getNeterminal();
                neterminale.add(neterminal);
                String dreapta = productie.getDreapta();

              
                // Determinăm terminalele (litere mici în partea dreaptă)
                for (char c : dreapta.toCharArray()) {
                    if (Character.isLowerCase(c)) {
                        terminale.add(String.valueOf(c));
                    }
                }
            }
        } while (update);

        return new RezultatAnaliza(nulabile, terminale, neterminale, productii);
    }
    
public RezultatAnaliza aplicaLema1(Set<String> terminale, Set<Productie> productii) {
    // Seturi pentru neterminalele utile și producțiile utile
    Set<String> neterminaleUtile = new HashSet<>();
    Set<Productie> productiiUtile = new HashSet<>();
    
    // Pasul 1: Identificăm neterminalele utile
    boolean updated;
    do {
        updated = false;
        
        // Parcurgem fiecare producție
        for (Productie productie : productii) {
            String neterminal = productie.getNeterminal();
            String dreapta = productie.getDreapta();

            // Dacă dreapta este goală, tratăm ca producție lambda
            if (dreapta.isEmpty()) {
                // Dacă producția este lambda, considerăm neterminalul util
                if (!neterminaleUtile.contains(neterminal)) {
                    neterminaleUtile.add(neterminal);
                    updated = true;
                }
            } else {
                // Verificăm dacă partea dreaptă conține doar terminale sau neterminale utile
                boolean poateFiUtila = true;
                for (char simbol : dreapta.toCharArray()) {
                    if (Character.isUpperCase(simbol) && !neterminaleUtile.contains(String.valueOf(simbol))) {
                        poateFiUtila = false;
                        break;
                    }
                }

                // Dacă producția poate genera terminale, adăugăm neterminalul la neterminalele utile
                if (poateFiUtila && !neterminaleUtile.contains(neterminal)) {
                    neterminaleUtile.add(neterminal);
                    updated = true;
                }
            }
        }
    } while (updated);

    // Pasul 2: Păstrăm doar producțiile utile
    for (Productie productie : productii) {
        String neterminal = productie.getNeterminal();
        String dreapta = productie.getDreapta();

        // Verificăm dacă producția are un neterminal util pe partea stângă
        if (neterminaleUtile.contains(neterminal)) {
            boolean esteUtila = true;

            // Verificăm dacă partea dreaptă a producției conține doar terminale sau neterminale utile
            if (!dreapta.isEmpty()) {  // Dacă dreapta nu este goală
                for (char simbol : dreapta.toCharArray()) {
                    if (Character.isUpperCase(simbol) && !neterminaleUtile.contains(String.valueOf(simbol))) {
                        esteUtila = false;
                        break;
                    } else if (Character.isLowerCase(simbol) && !terminale.contains(String.valueOf(simbol))) {
                        esteUtila = false;
                        break;
                    }
                }
            }

            // Dacă producția este utilă, o adăugăm la setul de producții utile
            if (esteUtila) {
                productiiUtile.add(productie);
            }
        }
    }

    // Returnăm rezultatele cu neterminalele utile și producțiile utile
    return new RezultatAnaliza(new HashSet<>(), terminale, neterminaleUtile, productiiUtile);
}



public RezultatAnaliza aplicaLema2(String neterminalStart, Set<String> neterminaleUtile, Set<Productie> productiiUtile) {
    // Seturi pentru neterminale și producții accesibile
    Set<String> neterminaleAccesibile = new HashSet<>();
    Set<Productie> productiiAccesibile = new HashSet<>();

    // Inițial, doar neterminalul de start este accesibil
    neterminaleAccesibile.add(neterminalStart);

    boolean updated;
    do {
        updated = false;

        // Parcurgem fiecare producție din gramatica filtrată (Lema 1)
        for (Productie productie : productiiUtile) {
            String neterminalStanga = productie.getNeterminal();
            String dreapta = productie.getDreapta();

            // Verificăm dacă neterminalul din stânga este deja accesibil
            if (neterminaleAccesibile.contains(neterminalStanga)) {
                // Dacă da, adăugăm producția la producțiile accesibile
                if (productiiAccesibile.add(productie)) {
                    updated = true; // Am adăugat o producție nouă
                }

                // Adăugăm în set neterminale noi din partea dreaptă a producției
                for (char simbol : dreapta.toCharArray()) {
                    if (Character.isUpperCase(simbol) && neterminaleUtile.contains(String.valueOf(simbol))) {
                        if (neterminaleAccesibile.add(String.valueOf(simbol))) {
                            updated = true; // Am adăugat un neterminal nou
                        }
                    }
                }
            }
        }
    } while (updated);

    // Returnăm rezultatele cu neterminale și producții accesibile
    return new RezultatAnaliza(new HashSet<>(), new HashSet<>(), neterminaleAccesibile, productiiAccesibile);
}

public boolean verificaSubsir(String neterminal, String subsir, Set<Productie> productii) {

    for (Productie productie : productii) {
        if (productie.getNeterminal().equals(neterminal)) {
            String dreapta = productie.getDreapta();

            if (dreapta.contains(subsir)) {
                System.out.println("Subsirul '" + subsir + "' a fost gasit in productia: " + productie);
                return true;
            }

            for (char simbol : dreapta.toCharArray()) {
                if (Character.isUpperCase(simbol)) { 
                    if (verificaSubsir(String.valueOf(simbol), subsir, productii)) {
                        return true;
                    }
                }
            }
        }
    }
    return false;
}


public RezultatAnaliza aplicaLema3(Set<Productie> productii, Set<String> terminale, Set<String>neterminale) {
    // Set pentru neterminale nulabile
    Set<String> nulabile = new HashSet<>();
    boolean updated;

    // Pasul 1: Determinarea neterminalelor nulabile
    do {
    updated = false;
    for (Productie productie : productii) {
        String neterminal = productie.getNeterminal();
        String dreapta = productie.getDreapta();

        // Dacă partea dreaptă este goală (lambda), adaugă neterminalul în nulabile
        if (dreapta.isEmpty() && !nulabile.contains(neterminal)) {
            nulabile.add(neterminal);
            updated = true;
        } else {
            // Dacă toate simbolurile din dreapta sunt nulabile
            boolean toateNulabile = true;
            for (char simbol : dreapta.toCharArray()) {
                if (Character.isUpperCase(simbol)) { // Simbol neterminal
                    if (!nulabile.contains(String.valueOf(simbol))) {
                        toateNulabile = false;
                        break;
                    }
                } else { // Simbol terminal
                    toateNulabile = false;
                    break;
                }
            }
            if (toateNulabile && !nulabile.contains(neterminal)) {
                nulabile.add(neterminal);
                updated = true;
            }
        }
    }
} while (updated);


    // Pasul 2: Generarea noilor producții
    Set<Productie> productiiNoi = new HashSet<>();
    for (Productie productie : productii) {
        String dreapta = productie.getDreapta();

        // Generăm toate combinațiile posibile eliminând simbolurile nulabile
        Set<String> combinatii = genereazaCombinatii(dreapta, nulabile);

        // Adaugăm producțiile modificate în lista de producții noi
        for (String combinatie : combinatii) {
            productiiNoi.add(new Productie(productie.getNeterminal(), combinatie));
        }
    }

    // Pasul 3: Eliminăm producțiile redundante (dacă este cazul)
    productiiNoi.removeIf(productie -> productie.getDreapta().isEmpty());

    // Returnăm rezultatul
    return new RezultatAnaliza(nulabile, terminale, neterminale, productiiNoi);
}

// Funcție auxiliară pentru generarea combinațiilor posibile prin eliminarea nulabilelor
private Set<String> genereazaCombinatii(String dreapta, Set<String> nulabile) {
    Set<String> combinatii = new HashSet<>();
    combinatii.add(""); // Începe cu producția goală (lambda)

    for (char simbol : dreapta.toCharArray()) {
        Set<String> noiCombinatii = new HashSet<>();

        // Dacă simbolul este nulabil, generează combinații cu și fără el
        if (Character.isUpperCase(simbol) && nulabile.contains(String.valueOf(simbol))) {
            for (String combinatie : combinatii) {
                noiCombinatii.add(combinatie); // Fără simbol
                noiCombinatii.add(combinatie + simbol); // Cu simbol
            }
        } else {
            // Dacă nu este nulabil, doar îl adăugăm
            for (String combinatie : combinatii) {
                noiCombinatii.add(combinatie + simbol);
            }
        }

        combinatii = noiCombinatii;
    }

    return combinatii;
}



    public static ListaProductii citesteDinFisier(String numeFisier) throws IOException {
        ListaProductii listaProductii = new ListaProductii();

        try (BufferedReader reader = new BufferedReader(new FileReader(numeFisier))) {
            String linie;
            while ((linie = reader.readLine()) != null) {
                linie = linie.trim();
                if (!linie.isEmpty()) {
                    String[] parti = linie.split(" ", 2);
                    String neterminal = parti[0];
                    String dreapta = (parti.length > 1) ? parti[1] : "";
                    listaProductii.addProductie(new Productie(neterminal, dreapta));
                }
            }
        }

        return listaProductii;
    }
}
