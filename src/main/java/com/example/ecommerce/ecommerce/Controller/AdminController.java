    package com.example.ecommerce.ecommerce.Controller;

    import com.example.ecommerce.ecommerce.Entity.Email;
    import com.example.ecommerce.ecommerce.Entity.MenuPhoto;
    import com.example.ecommerce.ecommerce.Entity.Restaurant;
    import com.example.ecommerce.ecommerce.Entity.checkOut;
    import com.example.ecommerce.ecommerce.Service.EmailService;
    import com.example.ecommerce.ecommerce.Service.MenuPhotoService;
    import com.example.ecommerce.ecommerce.Service.RestaurantService;
    import com.example.ecommerce.ecommerce.Service.checkOutService;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.stereotype.Controller;
    import org.springframework.ui.Model;
    import org.springframework.web.bind.annotation.*;
    import org.springframework.web.multipart.MultipartFile;

    import java.io.IOException;
    import java.time.LocalDateTime;
    import java.util.List;

    @Controller
    public class AdminController {
    @Autowired
    private RestaurantService restaurantService;
        @Autowired
        public MenuPhotoService menuPhotoService;


    @Autowired
    private checkOutService checkOutService;
        @GetMapping("/admin/add-menu")
        public String showAddMenuForm(Model model,
        @ModelAttribute("MenuForm") Restaurant restauran,
        @ModelAttribute("MenuPhoto") MenuPhoto MenuPhoto)
        {
            List<checkOut> list= checkOutService.getAllCustomer();
            model.addAttribute("order", list);
            List<Restaurant> allEntities = restaurantService.getAllRestauanrDetails();
            model.addAttribute("orders", allEntities);
            model.addAttribute("currentDateTime", LocalDateTime.now());
            Double deliveryCharges = 50.00;
            model.addAttribute("showAlert", true);
            for (Restaurant restaurant : allEntities
            ) {
                Double price = restaurant.getPrice();
                Double totalPrice = price + deliveryCharges;
                restaurant.setTotal(totalPrice);
                model.addAttribute("total", totalPrice);
            }
            // Here, you don't need to include overview in the method signature
            // The overview object will be added to the model in the addoverview method
            return "adminPage";
        }


        @PostMapping("/admin/save-menu")
        public String addMenu(@RequestParam("photo-data") MultipartFile photoData,Restaurant restaurant,Model model) throws IOException {
           restaurantService.saveform(photoData,restaurant);
            model.addAttribute("showAlert", true);

            return "redirect:/admin/add-menu"; // Redirect back to the form for adding another menu item
        }
        @PostMapping("/admin/save-menuPhoto")
        public String addMenuPhoto(@RequestParam("MenuPhotoData") MultipartFile photoData, MenuPhoto MenuPhoto, Model model) throws IOException {
            menuPhotoService.savePhoto(photoData,MenuPhoto);
            return "redirect:/admin/add-menu"; // Redirect back to the form for adding another menu item
        }




    }
