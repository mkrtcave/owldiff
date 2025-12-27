package cz.cvut.fel.diff;

import cz.cvut.fel.diff.service.HighLevelDiffService;
import cz.cvut.fel.diff.service.HighLevelDiffServiceImpl;
import cz.cvut.fel.diff.service.OWLDiffOntologyDiffService;
import cz.cvut.fel.diff.service.OntologyDiffService;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.io.IRIDocumentSource;
import org.semanticweb.owlapi.io.OWLOntologyDocumentSource;
import org.semanticweb.owlapi.io.StreamDocumentSource;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLException;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;

public class Diff {

    public Diff() {
    }
    public void diff(){

        String u1 = "file:/D:/apollo_sv(1).owl";
        String u2 = "file:/D:/apollo_sv.owl";

        try {
            final OWLOntologyManager originalM = OWLManager
                    .createOWLOntologyManager();

            final OWLOntology originalO = originalM.loadOntologyFromOntologyDocument(new IRIDocumentSource(IRI.create(u1)));

            final OWLOntologyManager updateM = OWLManager.createOWLOntologyManager();
            OWLOntology updateO = updateM.loadOntologyFromOntologyDocument(new IRIDocumentSource(IRI.create(u2)));
            OntologyDiffService service = new OWLDiffOntologyDiffService();
            DiffResult result = service.diff(originalO, updateO, Engine.SYNTACTIC);
            HighLevelDiffServiceImpl diffService = new HighLevelDiffServiceImpl();
            System.out.println(diffService.from(result).nodeMoves());
        } catch (OWLException e) {
            e.printStackTrace();
        }
    }
}
