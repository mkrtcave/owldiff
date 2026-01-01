package cz.cvut.fel.ontodiff;

import cz.cvut.fel.ontodiff.export.GraphDbUploader;
import cz.cvut.fel.ontodiff.export.RdfDiffModelBuilder;
import cz.cvut.fel.ontodiff.export.RdfDiffModelBuilderImpl;
import cz.cvut.fel.ontodiff.service.HighLevelDiffService;
import cz.cvut.fel.ontodiff.service.HighLevelDiffServiceImpl;
import cz.cvut.fel.ontodiff.service.OWLDiffOntologyDiffService;
import cz.cvut.fel.ontodiff.service.OntologyDiffService;
import org.apache.jena.query.Dataset;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.riot.Lang;
import org.apache.jena.riot.RDFDataMgr;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.io.IRIDocumentSource;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLException;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class Diff {

    public Diff() {
    }
    public void diff(){

        String u1 = "file:/D:/apollo_sv(1).owl";
        String u2 = "file:/D:/apollo_sv.owl";

        try {
            final OWLOntologyManager originalM = OWLManager.createOWLOntologyManager();
            final OWLOntologyManager updateM = OWLManager.createOWLOntologyManager();

            OWLOntology originalO = originalM.loadOntologyFromOntologyDocument(new IRIDocumentSource(IRI.create(u1)));
            OWLOntology updateO = updateM.loadOntologyFromOntologyDocument(new IRIDocumentSource(IRI.create(u2)));

            OntologyDiffService service = new OWLDiffOntologyDiffService();

            DiffResult diffResult = service.diff(originalO, updateO, Engine.SYNTACTIC);

            HighLevelDiffServiceImpl diffService = new HighLevelDiffServiceImpl();

            HighLevelDiffService.HighLevelDiff highDiff = diffService.from(diffResult);

            RdfDiffModelBuilder builder = new RdfDiffModelBuilderImpl();

            RdfDiffModelBuilder.DiffRunMetadata metadata =
                    new RdfDiffModelBuilder.DiffRunMetadata(
                            "http://w3id.org/ontodiff/instance#DiffRun_2025_01_15",
                            "http://w3id.org/ontodiff/instance#appolo_sv_v1",
                            "http://w3id.org/ontodiff/instance#appolo_sv_v2",
                            "http://w3id.org/ontodiff/instance#DiffRun_2025_01_15",
                            "ontodiff-cli",
                            "1.0.0",
                            java.time.Instant.now()
                    );

            Dataset dataset = builder.build(highDiff, metadata, "http://w3id.org/ontodiff/instance#DiffRun_2025_01_15");
//            RDFDataMgr.write(System.out, dataset, Lang.TURTLE);
            GraphDbUploader uploader = new GraphDbUploader(
                    "http://osw.felk.cvut.cz:7200/repositories/9999/statements",
                    "http://w3id.org/ontodiff/instance#DiffRun_2025_01_15",
                    null,
                    null
            );

//            ByteArrayOutputStream baos = new ByteArrayOutputStream();
//            RDFDataMgr.write(baos, dataset, Lang.TRIG);
//            byte[] trigData = baos.toByteArray();

            uploader.upload(dataset);
        } catch (OWLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
