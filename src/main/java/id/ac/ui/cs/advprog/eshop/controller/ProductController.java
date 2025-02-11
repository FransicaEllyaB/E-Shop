package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ProductController {

    @Autowired
    private ProductService service;

    @GetMapping
    public String index() {
        return "home";
    }

    /**
     * Fungsi ini memanggil halaman create product dengan metode get
     */
    @GetMapping("/product/create")
    public String createProductPage(Model model) {
        Product product = new Product();
        model.addAttribute("product", product);
        return "createProduct";
    }

    /**
     * Fungsi ini untuk membuat product dengan metode post
     */
    @PostMapping("/product/create")
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
    @GetMapping("/product/list")
    public String productListPage(Model model) {
        List<Product> allProducts = service.findAll();
        model.addAttribute("products", allProducts);
        return "productList";
    }

    /**
     * Fungsi ini memanggil halaman edit product dengan metode get
     */
    @GetMapping("/product/edit/{productId}")
    public String editProductPage(Model model, @PathVariable String productId) {
        Product product = service.findById(productId);
        model.addAttribute("product", product);
        return "editProduct";
    }

    /**
     * Fungsi ini untuk mengedit product dengan metode post
     */
    @PostMapping("/product/edit/{productId}")
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
    @GetMapping("/product/delete/{productId}")
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
    @PostMapping("/product/delete/{productId}")
    public String deleteProductPost(@PathVariable String productId) {
        service.deleteById(productId);
        return "redirect:/product/list";
    }
}
