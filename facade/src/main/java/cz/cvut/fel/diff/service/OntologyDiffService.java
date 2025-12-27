package cz.cvut.fel.diff.service;

import cz.cvut.fel.diff.DiffResult;
import cz.cvut.fel.diff.Engine;
import org.semanticweb.owlapi.model.OWLOntology;

public interface OntologyDiffService {

    DiffResult diff(OWLOntology original,
                    OWLOntology update,
                    Engine engine);
}
