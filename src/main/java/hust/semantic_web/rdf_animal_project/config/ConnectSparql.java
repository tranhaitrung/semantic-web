package hust.semantic_web.rdf_animal_project.config;

import org.eclipse.rdf4j.repository.sparql.SPARQLRepository;

public class ConnectSparql extends SPARQLRepository {
    private static ConnectSparql connectSparql;
    private ConnectSparql() {
        super("http://localhost:3030/semantic_web/sparql");
    }

    public static ConnectSparql getInstance() {
        if(connectSparql == null){
            connectSparql = new ConnectSparql();
        }
        return connectSparql;
    }
}
