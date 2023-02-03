package hust.semantic_web.rdf_animal_project.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.eclipse.rdf4j.model.Value;

import java.util.Arrays;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RdfModel {
    private String type;
    private Object value;
    private Object originValue;

    public static RdfModel fromValue(Value rdfValue) {
        RdfModel model = new RdfModel();
        model.value = rdfValue.stringValue();
        model.originValue = rdfValue.stringValue();
        if (rdfValue.isIRI()) {
            if (rdfValue.stringValue().contains("www.w3.org")) {
                model.type = "URI-W3";
            } else {
                model.type = "URI";
            }
            model.value = Arrays.stream(rdfValue.stringValue().split("#")).toArray()[1];
        } else if (rdfValue.isResource()) {
            model.type = "RESOURCE";
        } else if (rdfValue.isLiteral()) {
            model.type = "LITERAL";
        } else if (rdfValue.isBNode()) {
            model.type = "NODE";
        } else if (rdfValue.isTriple()) {
            model.type = "TRIPLE";
        }
        return model;
    }
}
