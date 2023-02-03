package hust.semantic_web.rdf_animal_project.service;

import hust.semantic_web.rdf_animal_project.config.ConnectSparql;
import hust.semantic_web.rdf_animal_project.model.RdfModel;
import org.eclipse.rdf4j.model.Value;
import org.eclipse.rdf4j.query.BindingSet;
import org.eclipse.rdf4j.query.TupleQuery;
import org.eclipse.rdf4j.query.TupleQueryResult;
import org.eclipse.rdf4j.repository.Repository;
import org.eclipse.rdf4j.repository.RepositoryConnection;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RdfService {
    public Object searchBird(String name) {
        List<Map> res = new ArrayList<>();
        Repository repository = ConnectSparql.getInstance();
        try (RepositoryConnection conn = repository.getConnection()){
            String query = "SELECT ?x ?p ?y WHERE {{?x ?p ?y .FILTER regex(?y, \""+name+"\") } UNION {?x ?p ?y. ?y ?p1 ?o .FILTER regex(?o, \""+name+"\") } }";
            TupleQuery tupleQuery = conn.prepareTupleQuery(query);
            try (TupleQueryResult result = tupleQuery.evaluate()){
                while (result.hasNext()){
                    BindingSet binding = result.next();
                    Value valueOfX = binding.getValue("x");
                    Value valueOfY = binding.getValue("y");
                    Value valueOfP = binding.getValue("p");
                    Map<String, RdfModel> map = new HashMap<>();
                    RdfModel modelX = RdfModel.fromValue(valueOfX);
                    RdfModel modelP = RdfModel.fromValue(valueOfP);
                    RdfModel modelY = RdfModel.fromValue(valueOfY);

                    map.put("X", modelX);
                    map.put("P", modelP);
                    map.put("Y", modelY);
                    res.add(map);
                }

            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }
    public Object getAll() {
        List<Map> res = new ArrayList<>();
        Repository repository = ConnectSparql.getInstance();
        try (RepositoryConnection conn = repository.getConnection()){
            String query = "SELECT ?x ?p ?y WHERE {{?x ?p ?y} ?x ?p ?y}";
            TupleQuery tupleQuery = conn.prepareTupleQuery(query);
            try (TupleQueryResult result = tupleQuery.evaluate()){
                while (result.hasNext()){
                    BindingSet binding = result.next();
                    Value valueOfX = binding.getValue("x");
                    Value valueOfY = binding.getValue("y");
                    Value valueOfP = binding.getValue("p");
                    Map<String, String> map = new HashMap<>();
                    map.put("X", valueOfX.stringValue());
                    map.put("P", valueOfP.stringValue());
                    map.put("Y", valueOfY.stringValue());
                    res.add(map);
                }

            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }
}
