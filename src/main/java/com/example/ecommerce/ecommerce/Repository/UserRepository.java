package com.example.ecommerce.ecommerce.Repository;

import com.example.ecommerce.ecommerce.Entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
   @Query("{ 'email' : ?0 }")
   User findByEmail(String email);
}
