package com.example.ecommerce.ecommerce.Repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface Overview extends MongoRepository<Overview, ObjectId> {
}
