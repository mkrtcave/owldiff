package cz.cvut.fel.ontodiff;

import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLOntology;

import java.time.Instant;
import java.util.List;
import java.util.Set;

public class Builder {
    private OWLOntology original;
    private OWLOntology update;
    private Engine engine;
    private Instant computedAt = Instant.now();

    private List<OWLAxiom> onlyInOriginal = List.of();
    private List<OWLAxiom> onlyInUpdate = List.of();

    private Set<OWLAxiom> inferredInUpdate;
    private Set<OWLAxiom> possiblyNotEntailedInUpdate;

    private CexSide originalCex = CexSide.empty();
    private CexSide updateCex = CexSide.empty();

    public Builder original(OWLOntology original) {
        this.original = original;
        return this;
    }

    public Builder update(OWLOntology update) {
        this.update = update;
        return this;
    }

    public Builder engine(Engine engine) {
        this.engine = engine;
        return this;
    }

    public Builder computedAt(Instant computedAt) {
        this.computedAt = computedAt;
        return this;
    }

    public Builder onlyInOriginal(List<OWLAxiom> onlyInOriginal) {
        this.onlyInOriginal = onlyInOriginal;
        return this;
    }

    public Builder onlyInUpdate(List<OWLAxiom> onlyInUpdate) {
        this.onlyInUpdate = onlyInUpdate;
        return this;
    }

    public Builder inferredInUpdate(Set<OWLAxiom> inferredInUpdate) {
        this.inferredInUpdate = inferredInUpdate;
        return this;
    }

    public Builder possiblyNotEntailedInUpdate(Set<OWLAxiom> possiblyNotEntailedInUpdate) {
        this.possiblyNotEntailedInUpdate = possiblyNotEntailedInUpdate;
        return this;
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

    public Builder originalCex(CexSide originalCex) {
        this.originalCex = originalCex;
        return this;
    }

    public Builder updateCex(CexSide updateCex) {
        this.updateCex = updateCex;
        return this;
    }

    public DiffResult build() {
        return new DiffResult(this);
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
}
