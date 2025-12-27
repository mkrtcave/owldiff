package cz.cvut.fel.diff;

import org.semanticweb.owlapi.model.OWLClass;

import java.util.Set;

public class CexSide {
    private final Set<OWLClass> diffR;
    private final Set<OWLClass> diffL;

    public CexSide(Set<OWLClass> diffR, Set<OWLClass> diffL) {
        this.diffR = diffR;
        this.diffL = diffL;
    }

    public Set<OWLClass> getDiffR() { return diffR; }
    public Set<OWLClass> getDiffL() { return diffL; }

    public static CexSide empty() {
        return new CexSide(Set.of(), Set.of());
    }
}
