package cz.cvut.fel.diff;

import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLOntology;

import java.time.Instant;
import java.util.List;
import java.util.Set;

public class DiffResult {
    private final OWLOntology original;
    private final OWLOntology update;
    private final Engine engine;
    private final Instant computedAt;

    private final List<OWLAxiom> onlyInOriginal;
    private final List<OWLAxiom> onlyInUpdate;

    private final Set<OWLAxiom> inferredInUpdate;
    private final Set<OWLAxiom> possiblyNotEntailedInUpdate;

    private final CexSide originalCex;
    private final CexSide updateCex;

    public DiffResult(Builder b) {
        this.original = b.getOriginal();
        this.update = b.getUpdate();
        this.engine = b.getEngine();
        this.computedAt = b.getComputedAt();

        this.onlyInOriginal = List.copyOf(b.getOnlyInOriginal());
        this.onlyInUpdate = List.copyOf(b.getOnlyInUpdate());
        this.inferredInUpdate = b.getInferredInUpdate();
        this.possiblyNotEntailedInUpdate = b.getPossiblyNotEntailedInUpdate();
        this.originalCex = b.getOriginalCex();
        this.updateCex = b.getUpdateCex();
    }

    public OWLOntology getOriginal() {
        return original;
    }

    public OWLOntology getUpdate() {
        return update;
    }

    public Engine getEngine() {
        return engine;
    }

    public Instant getComputedAt() {
        return computedAt;
    }

    public List<OWLAxiom> getOnlyInOriginal() {
        return onlyInOriginal;
    }

    public List<OWLAxiom> getOnlyInUpdate() {
        return onlyInUpdate;
    }

    public Set<OWLAxiom> getInferredInUpdate() {
        return inferredInUpdate;
    }

    public Set<OWLAxiom> getPossiblyNotEntailedInUpdate() {
        return possiblyNotEntailedInUpdate;
    }

    public CexSide getOriginalCex() {
        return originalCex;
    }

    public CexSide getUpdateCex() {
        return updateCex;
    }

    public static Builder builder() {
        return new Builder();
    }
}