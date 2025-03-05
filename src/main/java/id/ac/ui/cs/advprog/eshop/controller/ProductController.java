package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Product;
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
    @GetMapping("/edit/{id}")
    public String editProductPage(Model model, @PathVariable String id) {
        Product product = service.findById(id);
        if (product == null) {
            return "redirect:/product/list";
        }
        model.addAttribute("product", product);
        return "editProduct";
    }

    /**
     * Fungsi ini untuk mengedit product dengan metode post
     */
    @PostMapping("/edit/{id}")
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
    @GetMapping("/delete/{id}")
    public String deleteProductPage(Model model, @PathVariable String id) {
        Product product = service.findById(id);
        model.addAttribute("id", product.getId());
        model.addAttribute("name", product.getName());
        model.addAttribute("quantity", product.getQuantity());
        return "deleteProduct";
    }

    /**
     * Fungsi ini untuk menghapus product dengan metode post
     */
    @PostMapping("/delete/{id}")
    public String deleteProductPost(@PathVariable String id, RedirectAttributes redirectAttributes) {
        try {
            service.deleteById(id);
            redirectAttributes.addFlashAttribute("successMessage", "Product deleted successfully.");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Product not found.");
        }
        return "redirect:/product/list";
    }
}
