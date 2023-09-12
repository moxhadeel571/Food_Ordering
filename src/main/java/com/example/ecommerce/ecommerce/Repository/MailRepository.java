package com.example.ecommerce.ecommerce.Repository;


import com.example.ecommerce.ecommerce.Entity.Email;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MailRepository extends MongoRepository<Email,Long> {
}
