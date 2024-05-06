package fr.dawan.springdatamongodb.typed;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface InventoryRepository extends MongoRepository<Inventory, ObjectId> {

    List<Inventory> findByStatus(String status);
}
