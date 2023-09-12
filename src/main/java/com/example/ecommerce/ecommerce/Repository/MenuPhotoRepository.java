package com.example.ecommerce.ecommerce.Repository;

import com.example.ecommerce.ecommerce.Entity.MenuPhoto;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MenuPhotoRepository extends MongoRepository<MenuPhoto, ObjectId> {
}
