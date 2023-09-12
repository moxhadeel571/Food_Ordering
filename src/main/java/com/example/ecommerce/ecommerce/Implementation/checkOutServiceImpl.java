package com.example.ecommerce.ecommerce.Implementation;

import com.example.ecommerce.ecommerce.Entity.Restaurant;
import com.example.ecommerce.ecommerce.Entity.checkOut;
import com.example.ecommerce.ecommerce.Service.checkOutService;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class checkOutServiceImpl implements checkOutService {
    private final MongoTemplate mongoTemplate;

    @Autowired
    public checkOutServiceImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }


    @Override
    public ObjectId saveCheckout(checkOut checkOut) {
        Document document = new Document();
        document.append("firstName", checkOut.getFirstName());
        document.append("lastName", checkOut.getLastName());
        document.append("email", checkOut.getEmail());
        document.append("phone", checkOut.getPhone());
        document.append("addressLine1", checkOut.getAddressLine1());
        document.append("addressLine2", checkOut.getAddressLine2());
        document.append("country", checkOut.getCountry());
        document.append("city", checkOut.getCity());
        document.append("state", checkOut.getState());
        document.append("zip", checkOut.getZipcode());

        Query query = new Query();
        List<Restaurant> menu = mongoTemplate.find(query, Restaurant.class);

        // Associate the list of Restaurant objects with the checkOut object
        checkOut.setRestaurant(menu);

        // Convert the checkOut object to a MongoDB document
        Object checkOutDocument = mongoTemplate.getConverter().convertToMongoType(checkOut);

        // Append the checkOut document to the main document
        document.append("checkoutData", checkOutDocument);

        // Insert the combined document into the "checkOut" collection
        mongoTemplate.getCollection("checkOut").insertOne(document);

        return document.getObjectId("_id");
    }


    @Override
    public List<checkOut> getAllCustomer() {
        Query query = new Query();
        List<checkOut> checkOut = mongoTemplate.find(query, checkOut.class);
        return checkOut;
    }

}
