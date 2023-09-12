package com.example.ecommerce.ecommerce.Entity;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
@Data
@Document(collection="checkOut")
public class checkOut {
    @MongoId
    private ObjectId id;
    private List<Restaurant> restaurant;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String addressLine1;
    private String addressLine2;
    private String country;
    private String city;
    private String state;
    private String zipcode;

}
