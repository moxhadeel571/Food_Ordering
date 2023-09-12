package com.example.ecommerce.ecommerce.Repository;

import com.example.ecommerce.ecommerce.Entity.Restaurant;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RestaurentRepository extends MongoRepository<Restaurant, String> {

    @Query("{ 'itemName': { $regex: ?0, $options: 'i' } }")
 List<Restaurant> findByTitleContaining(String location);

    @Query("{ '_id' : ?0 }")
    Optional<Restaurant> findRById(String id);
}
