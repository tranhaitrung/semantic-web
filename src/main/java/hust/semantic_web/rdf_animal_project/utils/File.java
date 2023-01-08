package hust.semantic_web.rdf_animal_project.utils;

import lombok.extern.slf4j.Slf4j;
import lombok.extern.slf4j.XSlf4j;
import org.apache.jena.rdf.model.*;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.util.PrintUtil;

import java.io.InputStream;

@Slf4j
public class File {
    public static Model readFileRdf(String path) {
        log.info("[TTL] Start read file: "+ path);
        // create an empty model
        Model model = ModelFactory.createDefaultModel();

        // use the RDFDataMgr to find the input file
        InputStream in = RDFDataMgr.open(path);
        if (in == null) {
            throw new IllegalArgumentException(
                    "File: " + path + " not found");
        }

        // read the RDF/XML file
        model.read(in, null, "TTL");
        return model;
    }
}
