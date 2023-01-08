package hust.semantic_web.rdf_animal_project;

import hust.semantic_web.rdf_animal_project.common.Constant;
import hust.semantic_web.rdf_animal_project.utils.File;
import org.apache.jena.rdf.model.InfModel;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RdfAnimalProjectApplication {
    public static Model defineAnimalModel;
    public static Model birdModel;
    public static InfModel infmodel;

    public static void main(String[] args) {
        SpringApplication.run(RdfAnimalProjectApplication.class, args);
        defineAnimalModel = File.readFileRdf(Constant.PathFileTtl.animalDefinePath);
        birdModel = File.readFileRdf(Constant.PathFileTtl.animalBirdPath);
        infmodel = ModelFactory.createRDFSModel(defineAnimalModel, birdModel);
//        infmodel.write(System.out);
    }

}
