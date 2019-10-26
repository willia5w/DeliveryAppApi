package io.swagger.api.PizzaDetails;

import io.swagger.model.PizzaDetails.Pizza;
import java.util.List;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PizzaRepository extends MongoRepository<Pizza, ObjectId> {

  Pizza findByName(String name);

  List<Pizza> findAll();
}
