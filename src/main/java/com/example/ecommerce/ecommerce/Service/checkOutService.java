package com.example.ecommerce.ecommerce.Service;

import com.example.ecommerce.ecommerce.Entity.checkOut;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface checkOutService {
    ObjectId saveCheckout(checkOut checkOut);

    List<checkOut> getAllCustomer();
}
