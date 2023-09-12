package com.example.ecommerce.ecommerce.Service;

import com.example.ecommerce.ecommerce.Entity.Restaurant;
import org.bson.BsonValue;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public interface RestaurantService {
    ObjectId saveform(MultipartFile photoData, Restaurant restaurant) throws IOException;
    BsonValue saveQuantity(Restaurant restaurant) throws IOException;
    List<Restaurant> getAllRestauanrDetails();
    byte[] getFileData(String id);
    String getContentType(String id);

    String getFileName(String id);

    void deleteItem(ObjectId productId);

    List<Restaurant> getRestaurantById(String id);

}
