package hust.semantic_web.rdf_animal_project.test;

import hust.semantic_web.rdf_animal_project.common.Constant;
import hust.semantic_web.rdf_animal_project.utils.File;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.query.*;
import org.apache.jena.rdf.model.InfModel;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.reasoner.Reasoner;
import org.apache.jena.reasoner.ReasonerRegistry;

public class main_test {
    public static void main(String[] args) {
        Model defineAnimalModel = File.readFileRdf(Constant.PathFileTtl.animalDefinePath);
        Model birdModel = File.readFileRdf(Constant.PathFileTtl.animalBirdPath);
        InfModel infmodel = ModelFactory.createRDFSModel(defineAnimalModel, birdModel);
        infmodel.write(System.out);

        // Tạo OntModel từ RDF data
        OntModel ontModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
        ontModel.read("data.rdf");

// Tạo Reasoner và InfModel từ OntModel và các luật suy diễn
        Reasoner reasoner = ReasonerRegistry.getOWLReasoner();
        reasoner = reasoner.bindSchema(ontModel);
        InfModel infModel = ModelFactory.createInfModel(reasoner, ontModel);

// Tạo truy vấn SPARQL trên InfModel
        String queryString = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "
                + "PREFIX ex: <http://example.com/> "
                + "SELECT ?person "
                + "WHERE { "
                + "  ?person rdf:type ex:Person "
                + "}";
        Query query = QueryFactory.create(queryString);
        QueryExecution qexec = QueryExecutionFactory.create(query, infModel);
        try {
            ResultSet results = qexec.execSelect();
            while (results.hasNext()) {
                QuerySolution soln = results.nextSolution();
                Resource person = soln.getResource("person");
                System.out.println(person.toString());
            }
        } finally {
            qexec.close();
        }
    }
}
