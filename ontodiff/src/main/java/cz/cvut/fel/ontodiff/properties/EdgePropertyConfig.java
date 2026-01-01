package cz.cvut.fel.ontodiff.properties;

import org.semanticweb.owlapi.model.*;

import java.util.Set;
import java.util.stream.Collectors;

public final class EdgePropertyConfig {

    private final Set<String> annotationEdgeProperties;

    public EdgePropertyConfig(Set<String> annotationEdgeProperties) {
        this.annotationEdgeProperties = Set.copyOf(annotationEdgeProperties);
    }

    public boolean isEdgeAnnotationProperty(OWLAnnotationProperty prop) {
        return annotationEdgeProperties.contains(prop.getIRI().toString());
    }

    public Set<String> getAnnotationEdgeProperties() {
        return annotationEdgeProperties;
    }

    public static EdgePropertyConfig fromOntology(OWLOntology ont) {
        Set<String> props = ont.getAnnotationPropertiesInSignature().stream()
                .filter(p -> isEdgeLike(p, ont))
                .map(p -> p.getIRI().toString())
                .collect(Collectors.toSet());
        return new EdgePropertyConfig(props);
    }

    private static boolean isEdgeLike(OWLAnnotationProperty prop, OWLOntology ont) {
        IRI iri = prop.getIRI();

        String iriStr = iri.toString();
        if (iriStr.startsWith("http://purl.obolibrary.org/obo/RO_")) return true;
        if (iriStr.startsWith("http://www.geneontology.org/formats/oboInOwl#inSubset")) return true;

        for (OWLAnnotationAssertionAxiom ax : ont.getAnnotationAssertionAxioms(iri)) {
            if (ax.getProperty().getIRI().getShortForm().equals("is_edge_predicate")
                    && ax.getValue() instanceof OWLLiteral lit
                    && "true".equalsIgnoreCase(lit.getLiteral())) {
                return true;
            }
        }

        return false;
    }
}

