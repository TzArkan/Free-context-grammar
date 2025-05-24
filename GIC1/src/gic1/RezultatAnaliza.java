package gic1;

import java.util.Set;

public class RezultatAnaliza {
    private Set<String> nulabile;
    private Set<String> terminale;
    private Set<String> neterminale;
    private Set<Productie> productii;

    public RezultatAnaliza(Set<String> nulabile, Set<String> terminale, Set<String> neterminale, Set<Productie> productii) {
        this.nulabile = nulabile;
        this.terminale = terminale;
        this.neterminale = neterminale;
        this.productii = productii;
    }

    public Set<String> getNulabile() {
        return nulabile;
    }

    public Set<String> getTerminale() {
        return terminale;
    }

    public Set<String> getNeterminale() {
        return neterminale;
    }
    public Set<Productie> getProductii() {
        return productii;
    }
}
