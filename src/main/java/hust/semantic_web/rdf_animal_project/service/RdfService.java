package hust.semantic_web.rdf_animal_project.service;

import hust.semantic_web.rdf_animal_project.RdfAnimalProjectApplication;
import org.apache.jena.rdf.model.Model;
import org.springframework.stereotype.Service;

@Service
public class RdfService {
    public Object searchBird(String name) {
        Model rawModel = RdfAnimalProjectApplication.infmodel.getRawModel();
        rawModel.write(System.out);
        return "abc";
    }
}
