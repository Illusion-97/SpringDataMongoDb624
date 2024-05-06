package fr.dawan.springdatamongodb.untyped;

import lombok.RequiredArgsConstructor;
import org.bson.Document;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/{collection}")
@RequiredArgsConstructor
public class MongoController {
    private final MongoTemplate template; // représente la connection à une DATABASE

    @GetMapping
    public List<Document> find(@PathVariable String collection) {
        Criteria filter = new Criteria(); // Permet de créer des filtres
        Query query = new Query(filter); // Remplace le BSON utilisé par le driver et se crée soit vide, soit à partir des "critères" de recherche
        return template.find(query, Document.class, collection);
    }

    @GetMapping("byStatus/{status}")
    public List<Document> findByStatus(@PathVariable String collection, @PathVariable String status) {
        Criteria filter = new Criteria("status").is(status);
        Query query = new Query(filter);
        return template.find(query, Document.class, collection);
    }

    @GetMapping("byStatusAndQtyLt/{status}/{qty}")
    public List<Document> findByStatusAndQtyLessThan(@PathVariable String collection, @PathVariable String status, @PathVariable int qty) {
        Criteria filterStatus = new Criteria("status").is(status);
        Criteria filterQty = new Criteria("qty").lt(50);
        Criteria filter = new Criteria().andOperator(filterStatus,filterQty);
        Query query = new Query(filter);
        return template.find(query, Document.class, collection);
    }
}
