package cz.cvut.fel.ontodiff.export;

import org.apache.jena.query.Dataset;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.riot.Lang;
import org.apache.jena.riot.RDFDataMgr;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class GraphDbUploader {

    private final String statementsEndpoint;
    private final String graphIri;
    private final String username;
    private final String password;

    public GraphDbUploader(String statementsEndpoint,
                           String graphIri,
                           String username,
                           String password) {
        this.statementsEndpoint = statementsEndpoint;
        this.graphIri = graphIri;
        this.username = username;
        this.password = password;
    }

    public void upload(Dataset dataset) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        RDFDataMgr.write(baos, dataset, Lang.TRIG);
        byte[] data = baos.toByteArray();

        HttpURLConnection conn = (HttpURLConnection) new URL(statementsEndpoint).openConnection();
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);
        conn.setRequestProperty("Content-Type", "application/x-trig");

        if (username != null && !username.isBlank()) {
            String auth = username + ":" + (password == null ? "" : password);
            String encoded = Base64.getEncoder()
                    .encodeToString(auth.getBytes(StandardCharsets.UTF_8));
            conn.setRequestProperty("Authorization", "Basic " + encoded);
        }

        try (OutputStream os = conn.getOutputStream()) {
            os.write(data);
        }
        int status = conn.getResponseCode();

        if (status < 200 || status >= 300) {
            StringBuilder sb = new StringBuilder();
            try (InputStream es = conn.getErrorStream()) {
                if (es != null) {
                    try (BufferedReader br = new BufferedReader(
                            new InputStreamReader(es, StandardCharsets.UTF_8))) {
                        String line;
                        while ((line = br.readLine()) != null) {
                            sb.append(line).append('\n');
                        }
                    }
                }
            }
            throw new IOException("GraphDB upload failed, HTTP " + status +
                    (sb.length() > 0 ? (", response:\n" + sb) : ""));
        }
    }
}