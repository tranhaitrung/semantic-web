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
            String query =
                    "PREFIX am:  <http://www.semantic-web.hust.edu.vn/animal#>" +
                    "SELECT distinct ?s ?p ?o \n" +
                            "WHERE {  \n" +
                            "  {?s ?p \""+name+"\" } \n" +
                            "  UNION\n" +
                            "  {?s am:animalName ?o.\n" +
                            "  ?s ?p1 ?o1.\n" +
                            "  ?o1 ?p2 \""+name+"\"}\n" +
                            "  UNION\n" +
                            "  {?s am:animalName ?o.\n" +
                            "  ?s ?p1 ?o1.\n" +
                            "  ?o1 ?p2 ?o2.\n" +
                            "  ?o2 ?p3 \""+name+"\"}\n" +
                            "  ?s ?p ?o}";
            TupleQuery tupleQuery = conn.prepareTupleQuery(query);
            try (TupleQueryResult result = tupleQuery.evaluate()){
                while (result.hasNext()){
                    BindingSet binding = result.next();
                    Value valueOfS = binding.getValue("s");
                    Value valueOfO = binding.getValue("o");
                    Value valueOfP = binding.getValue("p");
                    Map<String, RdfModel> map = new HashMap<>();
                    RdfModel modelS = RdfModel.fromValue(valueOfS);
                    RdfModel modelP = RdfModel.fromValue(valueOfP);
                    RdfModel modelO = RdfModel.fromValue(valueOfO);

                    map.put("S", modelS);
                    map.put("P", modelP);
                    map.put("O", modelO);
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
            String query = "SELECT ?s ?p ?o WHERE {{?s ?p ?o} ?s ?p ?o}";
            TupleQuery tupleQuery = conn.prepareTupleQuery(query);
            try (TupleQueryResult result = tupleQuery.evaluate()){
                while (result.hasNext()){
                    BindingSet binding = result.next();
                    Value valueOfS = binding.getValue("s");
                    Value valueOfO = binding.getValue("o");
                    Value valueOfP = binding.getValue("p");
                    Map<String, String> map = new HashMap<>();
                    map.put("S", valueOfS.stringValue());
                    map.put("P", valueOfP.stringValue());
                    map.put("O", valueOfO.stringValue());
                    res.add(map);
                }

            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    public Object getDetail(String name) {
        List<Map> res = new ArrayList<>();
        Repository repository = ConnectSparql.getInstance();
        try (RepositoryConnection conn = repository.getConnection()){
            String query = "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>" +
                    "SELECT ?s ?p ?o WHERE {  " +
                    "{?s ?pred ?o.\n" +
                    " ?s ?p1 \""+name+"\". ?pred rdfs:label ?p\n" +
                    "}\n" +
                    "}";
            TupleQuery tupleQuery = conn.prepareTupleQuery(query);
            try (TupleQueryResult result = tupleQuery.evaluate()){
                while (result.hasNext()){
                    BindingSet binding = result.next();
                    Value valueOfS = binding.getValue("s");
                    Value valueOfO = binding.getValue("o");
                    Value valueOfP = binding.getValue("p");
                    RdfModel modelS = RdfModel.fromValue(valueOfS);
                    RdfModel modelP = RdfModel.fromValue(valueOfP);
                    RdfModel modelO = RdfModel.fromValue(valueOfO);
                    Map<String, Object> map = new HashMap<>();
                    map.put("subject", modelS);
                    map.put("property", modelP);
                    map.put("value",modelO);
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
