package com.example.ecommerce.ecommerce.Implementation;

import com.example.ecommerce.ecommerce.Entity.Restaurant;
import com.example.ecommerce.ecommerce.Repository.RestaurentRepository;
import com.example.ecommerce.ecommerce.Service.RestaurantService;
import com.mongodb.client.result.UpdateResult;
import org.bson.BsonValue;
import org.bson.Document;
import org.bson.types.Binary;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Component
public class RestaurentImpl implements RestaurantService {

    private final MongoTemplate mongoTemplate;

    @Autowired
    public RestaurentImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }
@Autowired
private RestaurantService restaurantService;
    @Autowired
    private RestaurentRepository restaurentRepository;
    @Override
    public ObjectId saveform(MultipartFile photoData, Restaurant restaurant) throws IOException {
        if (photoData == null) {
            throw new IllegalArgumentException("File cannot be null.");
        }
        byte[] fileData = photoData.getBytes();
        String fileName = photoData.getOriginalFilename();
        String contentType = photoData.getContentType();
        Document menu = new Document();
        menu.append("itemName", restaurant.getItemName());
        menu.append("contentType", contentType);
        menu.append("fileName", fileName);
        menu.append("fileData", new Binary(fileData)); // Wrap fileData in Binary
        menu.append("price", restaurant.getPrice());
        menu.append("description", restaurant.getDescription());
        menu.append("id", restaurant.getId());
        menu.append("ingredients", restaurant.getIngredients());
        menu.append("ordertime", restaurant.getOrderTime());
        menu.append("orderDate",restaurant.getOrderDate());
        menu.append("isOrder",restaurant.isOrder());
        menu.append("name", restaurant.getName());
        menu.append("address",restaurant.getAddress());
        menu.append("description",restaurant.getDescription());
        menu.append("availability",restaurant.getAvailability());
        menu.append("contactNumber",restaurant.getContactNumber());
        menu.append("openingHours",restaurant.getOpeningHours());
        menu.append("closingHours",restaurant.getClosingHours());
        mongoTemplate.getCollection("restaurants").insertOne(menu);
        return menu.getObjectId("_id");

    }
    @Override
    public BsonValue saveQuantity(Restaurant restaurant) throws IOException {
        Query query = new Query(Criteria.where("_id").is(restaurant.getId()));

        Update update = new Update();
        update.set("quantity", restaurant.getQuantity());

        UpdateResult result = mongoTemplate.updateFirst(query, update, Restaurant.class);
        return result.getUpsertedId(); // Return the ID of the updated document
    }





    @Override
    public List<Restaurant> getAllRestauanrDetails() {
        Query query = new Query();
        List<Restaurant> Menu = mongoTemplate.find(query, Restaurant.class);
        return Menu;
    }


    @Override
    public byte[] getFileData(String id) {
        Query query = Query.query(Criteria.where("_id").is(id));
        Document candidateDocument = mongoTemplate.findOne(query, Document.class, "restaurants");

        if (candidateDocument != null) {
            Binary fileData = candidateDocument.get("fileData", Binary.class);
            if (fileData != null) {
                return fileData.getData();
            }
        }

        return null;
    }

    @Override
    public String getContentType(String id) {
        Query query = Query.query(Criteria.where("_id").is(id));
        Document candidateDocument = mongoTemplate.findOne(query, Document.class, "restaurants");

        if (candidateDocument != null) {
            return candidateDocument.getString("contentType");
        }

        return null;
    }

    @Override
    public String getFileName(String id) {
        Query query = Query.query(Criteria.where("_id").is(id));
        Document candidateDocument = mongoTemplate.findOne(query, Document.class, "restaurants");

        if (candidateDocument != null) {
            return candidateDocument.getString("fileName");
        }

        return null;
    }


    @Override
    public void deleteItem(ObjectId productId) {
        Query query = Query.query(Criteria.where("_id").is(productId));
        mongoTemplate.remove(query, "restaurants");
    }


    @Override
    public List<Restaurant> getRestaurantById(String id) {
        Query query = new Query(Criteria.where("_id").is(id)); // Assuming your ID field is named "id"
        return mongoTemplate.find(query, Restaurant.class);
    }



}
