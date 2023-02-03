package hust.semantic_web.rdf_animal_project.controller;

import hust.semantic_web.rdf_animal_project.service.RdfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class SearchController {

    @Autowired
    private RdfService rdfService;

    @GetMapping("/bird")
    public ResponseEntity<?> searchBird(@RequestParam(required = false) String search) {
        try {
            Object res = rdfService.searchBird(search);
            return ResponseEntity.ok(res);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/data")
    public ResponseEntity<?> getAllData() {
        try {
            Object res = rdfService.getAll();
            return ResponseEntity.ok(res);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
