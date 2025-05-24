package gic1;

import java.util.HashSet;
import java.util.Set;

public class ListaProductii {
    private Set<Productie> lista = new HashSet<>();

    public void addProductie(Productie productie) {
        this.lista.add(productie);
    }

    public Set<Productie> getProductii() {
        return this.lista;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Productie productie : lista) {
            sb.append(productie.toString()).append("\n");
        }
        return sb.toString();
    }
}
