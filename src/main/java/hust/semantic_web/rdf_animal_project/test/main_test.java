package hust.semantic_web.rdf_animal_project.test;

import hust.semantic_web.rdf_animal_project.common.Constant;
import hust.semantic_web.rdf_animal_project.utils.File;
import org.apache.jena.rdf.model.InfModel;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;

public class main_test {
    public static void main(String[] args) {
        Model defineAnimalModel = File.readFileRdf(Constant.PathFileTtl.animalDefinePath);
        Model birdModel = File.readFileRdf(Constant.PathFileTtl.animalBirdPath);
//        Model birdModel1 = File.readFileRdf("data/data.ttl");
//        birdModel1.write(System.out);
//        InfModel infmodel = ModelFactory.createRDFSModel(defineAnimalModel, birdModel);
//        infmodel.write(System.out);
    }
}
