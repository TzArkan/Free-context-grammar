/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gic1;
import java.util.*;

public class VerificareAB {
    // Verifică dacă un neterminal poate genera un șir ce conține "ab"
    public static boolean poateGeneraAB(String neterminal, Set<Productie> productii) {
        // Set pentru a evita buclele infinite (pentru neterminale deja verificate)
        Set<String> vizitat = new HashSet<>();
        return verificaRecursiv(neterminal, productii, vizitat);
    }

    private static boolean verificaRecursiv(String neterminal, Set<Productie> productii, Set<String> vizitat) {
        // Dacă am verificat deja acest neterminal, oprim explorarea
        if (vizitat.contains(neterminal)) return false;

        // Marcam neterminalul ca vizitat
        vizitat.add(neterminal);

        // Iterăm prin toate producțiile
        for (Productie productie : productii) {
            if (productie.getNeterminal().equals(neterminal)) {
                String dreapta = productie.getDreapta();

                // Verificăm dacă dreapta conține "ab" direct
                if (dreapta.contains("ab")) {
                    return true;
                }

                // Verificăm dacă dreapta conține doar neterminale
                boolean doarNeterminale = true;
                for (char simbol : dreapta.toCharArray()) {
                    if (Character.isLowerCase(simbol)) { // Terminal
                        doarNeterminale = false;
                        break;
                    }
                }

                // Dacă sunt doar neterminale, verificăm recursiv derivatele lor
                if (doarNeterminale) {
                    for (char simbol : dreapta.toCharArray()) {
                        if (Character.isUpperCase(simbol)) { // Neterminal
                            if (verificaRecursiv(String.valueOf(simbol), productii, vizitat)) {
                                return true;
                            }
                        }
                    }
                }
            }
        }

        // Dacă nu s-a găsit "ab", returnăm false
        return false;
    }
}
