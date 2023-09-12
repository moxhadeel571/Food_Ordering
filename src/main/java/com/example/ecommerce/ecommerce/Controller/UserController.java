    package com.example.ecommerce.ecommerce.Controller;

    import com.example.ecommerce.ecommerce.Entity.Email;
    import com.example.ecommerce.ecommerce.Entity.MenuPhoto;
    import com.example.ecommerce.ecommerce.Entity.Restaurant;
    import com.example.ecommerce.ecommerce.Entity.checkOut;
    import com.example.ecommerce.ecommerce.Repository.MenuPhotoRepository;
    import com.example.ecommerce.ecommerce.Repository.RestaurentRepository;
    import com.example.ecommerce.ecommerce.Service.*;

    import com.paypal.api.payments.Links;
    import com.paypal.api.payments.Payment;
    import com.paypal.base.rest.PayPalRESTException;

    import org.bson.types.ObjectId;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.http.HttpHeaders;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.MediaType;
    import org.springframework.http.ResponseEntity;
    import org.springframework.stereotype.Controller;
    import org.springframework.ui.Model;
    import org.springframework.web.bind.annotation.*;
    import org.springframework.web.multipart.MultipartFile;

    import javax.mail.MessagingException;
    import java.io.IOException;
    import java.time.LocalDateTime;
    import java.util.HashMap;
    import java.util.List;
    import java.util.Map;
    import java.util.Optional;

    @Controller
    public class UserController {
    @Autowired
    private PaypalService paypalService;

    @Autowired
    private MenuPhotoRepository menuPhotoRepository;
    @Autowired
    private RestaurentRepository restaurentRepository;
        @Autowired
        public RestaurantService restaurantService;
        @Autowired
        public MenuPhotoService menuPhotoService;

        @Autowired
        private checkOutService checkOutService;
        @Autowired
        private EmailService emailService;
        public static final String SUCCESS_URL = "/users/success";
        public static final String CANCEL_URL = "/users/failed";
        @GetMapping("/home")
        public String getMenuCategories(Model model,String id) {
            model.addAttribute("id",id);
            return "homepage";
        }

        @GetMapping("/users/MenuListing")
        public String getMenuListing(Model model,String id) {
            List<Restaurant> foodList = restaurantService.getAllRestauanrDetails();
            model.addAttribute("MenuList", foodList);
            model.addAttribute("id",id);
            return "shop";
        }

 @GetMapping("/users/discountpage")
        public String getDiscountLisitng(Model model,String id) {
            List<Restaurant> foodList = restaurantService.getAllRestauanrDetails();
            model.addAttribute("MenuList", foodList);
            List<Restaurant> allEntities = restaurantService.getAllRestauanrDetails();
            model.addAttribute("orders", allEntities);
            Boolean isDiscount=true;
            model.addAttribute("discount", isDiscount);
            for (Restaurant restaurant : foodList) {
            double originalPrice = restaurant.getPrice();
            double discountPercentage = restaurant.getDiscountPercentage();
            double discountAmount = originalPrice * (discountPercentage / 100.0);
            double discountedPrice = originalPrice - discountAmount;
            restaurant.setTotal(discountedPrice); // Update the total with the discounted price
            model.addAttribute("discountedPrice", discountedPrice);

     }
            return "discountpage";
        }

        @PostMapping("/users/saveform")
        public String saveForm(@ModelAttribute Restaurant restaurant, Model model, MultipartFile photoData) throws IOException {
            // Save the form data using the restaurantService
            if (photoData != null) {
                restaurant.setFileData(photoData.getBytes());
                restaurant.setFileContentType(photoData.getContentType());
                restaurant.setFileName(photoData.getOriginalFilename());
            }
            restaurantService.saveform(photoData, restaurant);
            return "redirect:/users/MenuListing"; // Redirect to the menu listing page
        }
        @PostMapping("/users/updateQuantity")
        @ResponseBody
        public ResponseEntity<Map<String, Object>> updateQuantity(
                @RequestParam String productId,
                @RequestParam int change) {
            Map<String, Object> response = new HashMap<>();

            try {
                ObjectId objectId = new ObjectId(productId);
                Object productObj = restaurantService.getRestaurantById(productId);

                if (productObj instanceof Restaurant) {
                    Restaurant product = (Restaurant) productObj;

                    int newQuantity = product.getQuantity() + change;
                    if (newQuantity >= 1) {
                        product.setQuantity(newQuantity);
                        restaurantService.saveQuantity(product);

                        response.put("quantity", newQuantity);
                        response.put("price", product.getPrice());

                        List<Restaurant> updatedProductsList = restaurantService.getAllRestauanrDetails();
                        double totalPrice = calculateTotalPrice(updatedProductsList);
                        response.put("totalPrice", totalPrice);

                        return ResponseEntity.ok(response);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
        private double calculateTotalPrice(List<Restaurant> products) {
            double total = 0.0;
            for (Restaurant product : products) {
                double itemTotal = product.getPrice() * product.getQuantity();
                total += itemTotal;
            }
            return total;
        }
        @GetMapping("/users/MenuListing/{id}")
        public ResponseEntity<byte[]> displayImage(@PathVariable String id,Model model) {
            byte[] fileData = restaurantService.getFileData(id);
            String contentType = restaurantService.getContentType(id);


            if (fileData != null) {
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.parseMediaType(contentType));
                return new ResponseEntity<>(fileData, headers, HttpStatus.OK);
            } else {
                return ResponseEntity.notFound().build();
            }
        }

        @GetMapping("/users/RestaurantListing/{id}")
        public String getRestaurantListing(Model model, @PathVariable("id") String id) throws IOException {
            Restaurant food = restaurentRepository.findById(id).orElse(null);
            model.addAttribute("orders", food);
            model.addAttribute("id", id); // Add the id to the model
            MenuPhoto menuPhoto = menuPhotoService.getAllPhoto();
            model.addAttribute("menuPhoto", menuPhoto);
            Optional<Restaurant> restaurantOptional = restaurentRepository.findRById(id);

            if (restaurantOptional.isPresent()) {
                Restaurant restaurant = restaurantOptional.get();

                // Process and set model attributes here
                model.addAttribute("list", restaurant);

                boolean isDiscount = true;
                model.addAttribute("discount", isDiscount);

                Double originalPrice = restaurant.getPrice();
                Integer quantity = restaurant.getQuantity();
                int FinalSubtotal = (int) (originalPrice * quantity);
                model.addAttribute("finalsubtotal", FinalSubtotal);
                double discountPercentage = restaurant.getDiscountPercentage();
                Double deliveryCharges = 50.00;
                double discountAmount = FinalSubtotal * (discountPercentage / 100.0);
                double discountedPrice = FinalSubtotal - discountAmount;
                restaurant.setTotal(discountedPrice); // Update the total with the discounted price
                model.addAttribute("discountedPrice", discountedPrice);

                double totalPrice = discountedPrice + deliveryCharges;
                restaurant.setTotal(totalPrice);
                model.addAttribute("deliveryCharges", deliveryCharges);
}

            return "detail"; // Return the "detail" template
        }



        @GetMapping("/users/MenuPhoto/{id}")
        public ResponseEntity<byte[]> displayMenuImage(@PathVariable String id) {
            byte[] fileData = menuPhotoService.getFileData(id);
            String contentType = menuPhotoService.getContentType(id);

            if (fileData != null) {
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.parseMediaType(contentType));
                return new ResponseEntity<>(fileData, headers, HttpStatus.OK);
            } else {
                return ResponseEntity.notFound().build();
            }
        }

        @GetMapping("/users/checkout/{id}")
        public String getCheckout(Model model, @PathVariable("id") String id, @ModelAttribute("checkoutform") checkOut checkOut) {
            Restaurant food = restaurentRepository.findById(id).orElse(null);

            if (food != null) {
                model.addAttribute("checkout", food);

                boolean isDiscount = true; // You can modify this logic based on your requirements
                model.addAttribute("discount", isDiscount);

                double originalPrice = food.getPrice();
                double discountPercentage = food.getDiscountPercentage();
                double discountAmount = originalPrice * (discountPercentage / 100.0);
                double discountedPrice = originalPrice - discountAmount;

                Double deliveryCharges = 50.00;
                double totalPrice = discountedPrice + deliveryCharges;

                model.addAttribute("discountedPrice", discountedPrice);
                model.addAttribute("totalPrice", totalPrice);
                model.addAttribute("deliveryCharges", deliveryCharges);

                model.addAttribute("totalPrice", totalPrice); // Fix: Set the total price in the model attribute

                model.addAttribute("deliveryCharges", deliveryCharges);
            } else {
                // Handle the case where the food item is not found
                // You might want to add some error handling or redirect logic here
            }

            return "checkout";
        }
        @GetMapping("/users/cart/{id}")
        public String getcart(Model model, @PathVariable("id") String id) {
            Optional<Restaurant> restaurantOptional = restaurentRepository.findRById(id);

            if (restaurantOptional.isPresent()) {
                Restaurant restaurant = restaurantOptional.get();

                // Process and set model attributes here
                model.addAttribute("list", restaurant);

                boolean isDiscount = true;
                model.addAttribute("discount", isDiscount);

                Double originalPrice = restaurant.getPrice();
                Integer quantity = restaurant.getQuantity();
                int FinalSubtotal = (int) (originalPrice * quantity);
                model.addAttribute("finalsubtotal", FinalSubtotal);
                double discountPercentage = restaurant.getDiscountPercentage();
                Double deliveryCharges = 50.00;
                double discountAmount = FinalSubtotal * (discountPercentage / 100.0);
                double discountedPrice = FinalSubtotal - discountAmount;
                restaurant.setTotal(discountedPrice); // Update the total with the discounted price
                model.addAttribute("discountedPrice", discountedPrice);

                double totalPrice = discountedPrice + deliveryCharges;
                restaurant.setTotal(totalPrice);
                model.addAttribute("deliveryCharges", deliveryCharges);

                return "cart"; // Assuming "cart" is the name of your Thymeleaf or JSP template
            } else {
                // Handle the case when the restaurant is not found
                // You can return an error view or display a message to the user
                return "restaurantNotFound"; // Replace with an appropriate error view name
            }
        }


        @GetMapping("/users/Cod/{id}")
        public String getCOD(@ModelAttribute("checkoutform")checkOut checkOut,Model model) {
        checkOutService.saveCheckout(checkOut);
            List<Restaurant> list = restaurantService.getAllRestauanrDetails();
            model.addAttribute("list",list);
            Double deliveryCharges = 50.00;
            for (Restaurant restaurant : list
            ) {
                Double price = restaurant.getPrice();
                Double totalPrice = price + deliveryCharges;
                restaurant.setTotal(totalPrice);
            }
            model.addAttribute("deliveryCharges", deliveryCharges);
            model.addAttribute("deliveryCharges", deliveryCharges);

            return "redirect:/users/MenuListing";
        }


        @PostMapping("/users/savecheckout/{id}")
        public String saveCheckout(Model model,checkOut checkOut,
                                   @ModelAttribute("checkoutform") checkOut checkoutForm,
                                   @ModelAttribute("UpdateEmail") Email email,
                                   @PathVariable("id") ObjectId id) throws MessagingException {
            checkOutService.saveCheckout(checkOut);
            List<Restaurant> list = restaurantService.getAllRestauanrDetails();
            model.addAttribute("list",list);
            Double deliveryCharges = 50.00;
            // Send email

            emailService.order_email(email,id);

            for (Restaurant restaurant : list
            ) {
                Double price = restaurant.getPrice();
                Double totalPrice = price + deliveryCharges;
                restaurant.setTotal(totalPrice);
            }
            model.addAttribute("deliveryCharges", deliveryCharges);
            model.addAttribute("deliveryCharges", deliveryCharges);


            checkOutService.saveCheckout(checkOut);
            return "redirect:/users/MenuListing";
        }
        @GetMapping("/header")
        public String renderHeader(Model model) {
            Restaurant food = (Restaurant) restaurentRepository.findAll();
            model.addAttribute("food", food);

            return "header";
        }

        @GetMapping("/users/deleteItem/{productId}")
        public String deleteRestaurantItem(@PathVariable("productId") ObjectId productId) {
            restaurantService.deleteItem(productId);
            return "redirect:/users/cart";
        }




        @GetMapping("/users/products/{id}")
        public ResponseEntity<Restaurant> searchLocation(@PathVariable("id") String id,Model model) {
            try {
                Optional<Restaurant> restaurantOptional = restaurentRepository.findById(id);
                model.addAttribute("food", restaurantOptional);
                if (restaurantOptional.isPresent()) {
                    Restaurant restaurant = restaurantOptional.get();
                    // Fetch the image data for the restaurant and set it in the response
                    byte[] fileData = restaurantService.getFileData(restaurant.getId().toString());
                    restaurant.setFileData(fileData);
                    return ResponseEntity.ok(restaurant);
                } else {
                    // Handle the case when the restaurant with the given ID is not found
                    return ResponseEntity.notFound().build();
                }
            } catch (Exception e) {
                // Handle exceptions and return appropriate error response
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }

        @GetMapping("/users/search")
        public ResponseEntity<List<Restaurant>> searchRestaurants(@RequestParam String query) {
            List<Restaurant> restaurants = restaurentRepository.findByTitleContaining(query);
            return ResponseEntity.ok(restaurants);
        }


        @GetMapping("/users/orders")
        public String getOrder(Model model, String id) {
            List<Restaurant> allEntities = restaurantService.getAllRestauanrDetails();
            model.addAttribute("orders", allEntities);
            Restaurant restaurants=new Restaurant();
            Double orderTotal=restaurants.getTotal();
            model.addAttribute("id",id);
            model.addAttribute("currentDateTime", LocalDateTime.now());
            Double deliveryCharges = 50.00;
            for (Restaurant restaurant : allEntities
            ) {
                Double price = restaurant.getPrice();
                Double totalPrice = price + deliveryCharges;
                restaurant.setTotal(totalPrice);
            }

            return "order";
        }


        @PostMapping("/users/send_email")
        public String sendEmailtoRandom(@ModelAttribute("UpdateEmail") Email email) throws MessagingException {
            emailService.sendEmail(email); // Use the email object passed from the form
            System.setProperty("mail.debug", "true");

            return "redirect:/users/MenuListing";
        }



    }

