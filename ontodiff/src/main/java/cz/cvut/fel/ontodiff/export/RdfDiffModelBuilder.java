package cz.cvut.fel.ontodiff.export;

import cz.cvut.fel.ontodiff.service.HighLevelDiffService;
import org.apache.jena.query.Dataset;
import org.apache.jena.rdf.model.Model;

import java.time.Instant;

public interface RdfDiffModelBuilder {

    record DiffRunMetadata(
                    String diffRunIri,
                    String fromOntologyVersionIri,
                    String toOntologyVersionIri,
                    String projectIri,
                    String toolName,
                    String toolVersion,
                    Instant generatedAt
            ) {}

    Dataset build(HighLevelDiffService.HighLevelDiff highLevelDiff,
                  DiffRunMetadata metadata,
                  String diffGraphIri);
}
