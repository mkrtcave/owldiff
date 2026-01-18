package cz.cvut.fel.ontodiff.service;

import cz.cvut.fel.ontodiff.DiffResult;
import cz.cvut.fel.ontodiff.Engine;
import org.semanticweb.owlapi.model.OWLOntology;

public interface OntologyDiffService {

    DiffResult diff(OWLOntology original,
                    OWLOntology update,
                    Engine engine);
}
