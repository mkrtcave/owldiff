package cz.cvut.fel.ontodiff.properties;

import java.util.Set;

public class SynonymPropety {

    public static final Set<String> SYNONYM_PROPERTY_IRIS = Set.of(
            "http://www.geneontology.org/formats/oboInOwl#hasExactSynonym",
            "http://www.geneontology.org/formats/oboInOwl#hasRelatedSynonym",
            "http://www.geneontology.org/formats/oboInOwl#hasBroadSynonym",
            "http://www.geneontology.org/formats/oboInOwl#hasNarrowSynonym"
    );
}
