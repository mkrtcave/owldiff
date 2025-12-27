package cz.cvut.fel.diff.service;

import cz.cvut.fel.diff.CexSide;
import cz.cvut.fel.diff.DiffResult;
import cz.cvut.fel.diff.Engine;
import cz.cvut.kbss.owldiff.OWLDiffException;
import cz.cvut.kbss.owldiff.diff.cex.CEXDiff;
import cz.cvut.kbss.owldiff.diff.cex.CEXDiffOutput;
import cz.cvut.kbss.owldiff.diff.entailments.EntailmentsExplanationsDiff;
import cz.cvut.kbss.owldiff.diff.entailments.EntailmentsExplanationsDiffOutput;
import cz.cvut.kbss.owldiff.diff.syntactic.SyntacticDiff;
import cz.cvut.kbss.owldiff.diff.syntactic.SyntacticDiffOutput;
import cz.cvut.kbss.owldiff.ontology.OntologyHandler;
import cz.cvut.kbss.owldiff.view.ProgressListener;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLOntology;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


public class OWLDiffOntologyDiffService implements OntologyDiffService {

    private final ProgressListener progressListener = null;

    public OWLDiffOntologyDiffService(){}

//    public OWLDiffOntologyDiffService(ProgressListener progressListener) {
//        this.progressListener = progressListener;
//    }

    @Override
    public DiffResult diff(OWLOntology original, OWLOntology update, Engine engine) {
        OntologyHandler handler = createHandler(original, update);
        try {
            switch (engine) {
                case SYNTACTIC:
                    return runSyntacticDiff(handler);
                case ENTAILMENT:
                    return runEntailmentDiff(handler);
                case CEX:
                    return runCexDiff(handler);
                default:
                    throw new IllegalArgumentException("Unsupported engine: " + engine);
            }
        } catch (OWLDiffException e) {
            throw new RuntimeException("Diff failed", e);
        }
    }

    private DiffResult runSyntacticDiff(OntologyHandler handler) throws OWLDiffException {
        SyntacticDiff syntactic = (progressListener == null)
                ? new SyntacticDiff(handler)
                : new SyntacticDiff(handler, progressListener);

        SyntacticDiffOutput out = syntactic.diff();

        List<OWLAxiom> onlyInOriginal = new ArrayList<>(out.getInOriginal());
        List<OWLAxiom> onlyInUpdate = new ArrayList<>(out.getInUpdate());

        return DiffResult.builder()
                .original(handler.getOriginalOntology())
                .update(handler.getUpdateOntology())
                .engine(Engine.SYNTACTIC)
                .onlyInOriginal(onlyInOriginal)
                .onlyInUpdate(onlyInUpdate)
                .build();
    }

    private DiffResult runEntailmentDiff(OntologyHandler handler) throws OWLDiffException {
        SyntacticDiff syntactic = (progressListener == null)
                ? new SyntacticDiff(handler)
                : new SyntacticDiff(handler, progressListener);

        SyntacticDiffOutput syntacticOut = syntactic.diff();

        EntailmentsExplanationsDiff entDiff = (progressListener == null)
                ? new EntailmentsExplanationsDiff(handler, null, syntacticOut)
                : new EntailmentsExplanationsDiff(handler, progressListener, syntacticOut);

        EntailmentsExplanationsDiffOutput entOut = entDiff.diff();

        Set<OWLAxiom> inferred = entOut.getInferred();
        Set<OWLAxiom> possiblyRemove = entOut.getPossiblyRemove();

        List<OWLAxiom> onlyInOriginal = new ArrayList<>(syntacticOut.getInOriginal());
        List<OWLAxiom> onlyInUpdate = new ArrayList<>(syntacticOut.getInUpdate());

        return DiffResult.builder()
                .original(handler.getOriginalOntology())
                .update(handler.getUpdateOntology())
                .engine(Engine.ENTAILMENT)
                .onlyInOriginal(onlyInOriginal)
                .onlyInUpdate(onlyInUpdate)
                .inferredInUpdate(inferred)
                .possiblyNotEntailedInUpdate(possiblyRemove)
                .build();
    }

    private DiffResult runCexDiff(OntologyHandler handler) throws OWLDiffException {
        CEXDiff cexDiff = (progressListener == null) ?
                new CEXDiff(handler)
                : new CEXDiff(handler, progressListener);

        CEXDiffOutput out = cexDiff.diff();

        CexSide originalSide = new CexSide(
                out.getOriginalDiffR(),
                out.getOriginalDiffL()
        );

        CexSide updateSide = new CexSide(
                out.getUpdateDiffR(),
                out.getUpdateDiffL()
        );

        return DiffResult.builder()
                .original(handler.getOriginalOntology())
                .update(handler.getUpdateOntology())
                .engine(Engine.CEX)
                .originalCex(originalSide)
                .updateCex(updateSide)
                .build();
    }

    private OntologyHandler createHandler(OWLOntology original,
                                          OWLOntology update) {
        return new OntologyHandler() {

            public OWLOntology getOriginalOntology() {
                return original;
            }

            public OWLOntology getUpdateOntology() {
                return update;
            }
        };
    }

}
