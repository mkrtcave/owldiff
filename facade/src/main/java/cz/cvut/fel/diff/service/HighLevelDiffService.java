package cz.cvut.fel.diff.service;

import cz.cvut.fel.diff.DiffResult;

import java.util.Set;

public interface HighLevelDiffService {

    record NodeCreation(String iri){}
    record NodeDeletion(String iri) {}
    record NodeObsoletion(String iri, String reason) {}
    record SynonymReplacement( String entityIri, String propertyIri, String oldSynonym, String newSynonym) {}
    record EdgeCreation(String srcIri, String propIri, String tgtIri) {}
    record EdgeDeletion(String srcIri, String propIri, String tgtIri) {}
    record ClassCreation(String iri) {}
    record NodeMove(String chldIri, String oldPrtIri, String newPrtIri) {}
    record PredicateChange() {}

    record HighLevelDiff(
            Set<NodeCreation> nodeCreations,
            Set<NodeDeletion> nodeDeletions,
            Set<NodeObsoletion> nodeObsoletions,
            Set<SynonymReplacement> synonymReplacement,
            Set<EdgeCreation> edgeCreations,
            Set<EdgeDeletion> edgeDeletions,
            Set<ClassCreation> classCreations,
            Set<NodeMove> nodeMoves
    ) {}

    HighLevelDiff from(DiffResult diffResult);
}
