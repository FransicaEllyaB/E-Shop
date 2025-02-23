package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Car;
import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.service.CarServiceImpl;
import id.ac.ui.cs.advprog.eshop.service.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService service;

    /**
     * Fungsi ini memanggil halaman create product dengan metode get
     */
    @GetMapping("/create")
    public String createProductPage(Model model) {
        Product product = new Product();
        model.addAttribute("product", product);
        return "createProduct";
    }

    /**
     * Fungsi ini untuk membuat product dengan metode post
     */
    @PostMapping("/create")
    public String createProductPost(@ModelAttribute Product product, Model model) {
        try{
            service.create(product);
            return "redirect:list";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "createProduct";
        }
    }

    /**
     * Fungsi ini memanggil halaman list produk dengan metode get
     */
    @GetMapping("/list")
    public String productListPage(Model model) {
        List<Product> allProducts = service.findAll();
        model.addAttribute("products", allProducts);
        return "productList";
    }

    /**
     * Fungsi ini memanggil halaman edit product dengan metode get
     */
    @GetMapping("/edit/{productId}")
    public String editProductPage(Model model, @PathVariable String productId) {
        Product product = service.findById(productId);
        if (product == null) {
            return "redirect:/product/list";
        }
        model.addAttribute("product", product);
        return "editProduct";
    }

    /**
     * Fungsi ini untuk mengedit product dengan metode post
     */
    @PostMapping("/edit/{productId}")
    public String editProductPost(@ModelAttribute Product product, Model model) {
        try {
            service.edit(product);
            return "redirect:/product/list";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "editProduct";
        }
    }

    /**
     * Fungsi ini memanggil halaman delete product dengan metode get
     */
    @GetMapping("/delete/{productId}")
    public String deleteProductPage(Model model, @PathVariable String productId) {
        Product product = service.findById(productId);
        model.addAttribute("productId", product.getProductId());
        model.addAttribute("productName", product.getProductName());
        model.addAttribute("productQuantity", product.getProductQuantity());
        return "deleteProduct";
    }

    /**
     * Fungsi ini untuk menghapus product dengan metode post
     */
    @PostMapping("/delete/{productId}")
    public String deleteProductPost(@PathVariable String productId, RedirectAttributes redirectAttributes) {
        try {
            service.deleteById(productId);
            redirectAttributes.addFlashAttribute("successMessage", "Product deleted successfully.");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Product not found.");
        }
        return "redirect:/product/list";
    }

    @Controller
    @RequestMapping("/car")
    class CarController extends ProductController {

        @Autowired
        private CarServiceImpl carservice;

        @GetMapping("/createCar")
        public String createCarPage(Model model) {
            Car car = new Car();
            model.addAttribute("car", car);
            return "createCar";
        }

        @PostMapping("/createCar")
        public String createCarPost(@ModelAttribute Car car, Model model) {
            carservice.create(car);
            return "redirect:listCar";
        }

        @GetMapping("/listCar")
        public String carListPage(Model model) {
            List<Car> allCars = carservice.findAll();
            model.addAttribute("cars", allCars);
            return "carList";
        }

        @GetMapping("/editCar/{carId}")
        public String editCarPage(@PathVariable String carId, Model model) {
            Car car = carservice.findById(carId);
            model.addAttribute("car", car);
            return "editCar";
        }

        @PostMapping("/editCar")
        public String editCarPost(@ModelAttribute Car car, Model model) {
            System.out.println(car.getCarId());
            carservice.update(car.getCarId(), car);
            return "redirect:listCar";
        }

        @PostMapping("/deleteCar")
        public String deleteCar(@RequestParam("carId") String carId) {
            carservice.deleteCarById(carId);
            return "redirect:listCar";
        }
    }

}
