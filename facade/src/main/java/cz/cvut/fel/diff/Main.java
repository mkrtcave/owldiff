package cz.cvut.fel.diff;

import cz.cvut.fel.diff.service.OWLDiffOntologyDiffService;
import cz.cvut.fel.diff.service.OntologyDiffService;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.io.OWLOntologyDocumentSource;
import org.semanticweb.owlapi.io.StreamDocumentSource;
import org.semanticweb.owlapi.model.OWLOntologyManager;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        Diff diff = new Diff();
        diff.diff();
    }
}