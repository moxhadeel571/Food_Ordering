package com.example.ecommerce.ecommerce.Repository;

import com.example.ecommerce.ecommerce.Entity.checkOut;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface checkOutRepository extends MongoRepository<checkOut, ObjectId> {
}
