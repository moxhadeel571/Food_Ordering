package com.example.ecommerce.ecommerce.Entity;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.MongoId;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Time;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Document(collection = "restaurants")
public class Restaurant {

    @MongoId
    private ObjectId id;
    private boolean isOrder;
    private String itemName;
    private List<String> category;
    private Integer quantity;
    private Double total;
    private Double price;
    private String description;
    private MultipartFile pdf;
    @Field("file_reference")
    private String fileReference;
    @Field("file_name")
    private String fileName;
    @Field("file_content_type")
    private String fileContentType;
    @Field("file_data")
    private byte[] fileData;
    private String ingredients;
    private String currency;
    private String method;
    private String intent;
    private Date orderDate;
    private Time orderTime;
    private String name;
    private String address;
    private String availability;
    private double discountPercentage;
    private List<String> specialTags;
    private String contactNumber;
    private String openingHours;
    private String closingHours;
    public double getDiscountPercentage() {
        return 50.0; // Set the discount percentage to 50%
    }
}

