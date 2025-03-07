package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.service.ProductService;

import org.springframework.ui.Model;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class ProductControllerTest {
    @Mock
    private ProductService productService;

    @Mock
    private Model model;

    @InjectMocks
    private ProductController productController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateProductPage() {
        String result = productController.createProductPage(model);
        assertEquals("createProduct", result);
    }

    @Test
    void testCreateProductPost() {
        Product product = new Product();
        String result = productController.createProductPost(product, model);
        assertEquals("redirect:list", result);
        verify(productService, times(1)).create(product);
    }

    @Test
    void testCreateProductPostWhenValidationFails() {
        Product invalidProduct = new Product();
        invalidProduct.setName("");
        invalidProduct.setQuantity(0);

        doThrow(new IllegalArgumentException("Invalid product details."))
                .when(productService).create(any(Product.class));

        String result = productController.createProductPost(invalidProduct, model);
        assertEquals("createProduct", result);
        verify(model, times(1)).addAttribute(eq("error"), anyString());
    }

    @Test
    void testProductListPage() {
        List<Product> products = new ArrayList<>();
        when(productService.findAll()).thenReturn(products);
        String result = productController.productListPage(model);
        assertEquals("productList", result);
        verify(model, times(1)).addAttribute("products", products);
    }

    @Test
    void testDeleteProductPage() {
        String id = "eb558e9f-1c39-460e-8860-71af6af63bd6";
        Product product = new Product();
        product.setId(id);
        when(productService.findById(id)).thenReturn(product);
        String result = productController.deleteProductPage(model, id);
        assertEquals("deleteProduct", result);
        verify(model, times(1)).addAttribute("id", id);
        verify(model, times(1)).addAttribute("name", product.getName());
        verify(model, times(1)).addAttribute("quantity", product.getQuantity());
    }

    @Test
    void testDeleteProductPost() {
        String id = "eb558e9f-1c39-460e-8860-71af6af63bd6";

        RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();
        String result = productController.deleteProductPost(id, redirectAttributes);

        assertEquals("redirect:/product/list", result);
        verify(productService, times(1)).deleteById(id);
    }

    @Test
    void testDeleteProductPostWhenProductNotFound() {
        String id = "invalid-id";
        doThrow(new IllegalArgumentException("Product not found."))
                .when(productService).deleteById(id);

        RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();
        String result = productController.deleteProductPost(id, redirectAttributes);

        assertEquals("redirect:/product/list", result);
        assertTrue(redirectAttributes.getFlashAttributes().containsKey("errorMessage"));
        assertEquals("Product not found.", redirectAttributes.getFlashAttributes().get("errorMessage"));
    }

    @Test
    void testEditProductPage() {
        String id = "eb558e9f-1c39-460e-8860-71af6af63bd6";
        Product product = new Product();
        product.setId(id);
        when(productService.findById(id)).thenReturn(product);
        String result = productController.editProductPage(model, id);
        assertEquals("editProduct", result);
        verify(model, times(1)).addAttribute("product", product);
    }

    @Test
    void testEditProductPageWhenProductNotFound() {
        String id = "invalid-id";
        when(productService.findById(id)).thenReturn(null);

        String result = productController.editProductPage(model, id);

        assertEquals("redirect:/product/list", result);
    }



    @Test
    void testEditProductPost() {
        Product product = new Product();
        product.setId("eb558e9f-1c39-460e-8860-71af6af63bd6");

        Model model = mock(Model.class);

        String result = productController.editProductPost(product, model);

        assertEquals("redirect:/product/list", result);
        verify(productService, times(1)).edit(product);
    }

    @Test
    void testEditProductPostWhenValidationFails() {
        Product product = new Product();
        product.setId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        Model model = mock(Model.class);
        doThrow(new IllegalArgumentException("Product name cannot be empty."))
                .when(productService).edit(any(Product.class));
        String result = productController.editProductPost(product, model);
        assertEquals("editProduct", result);
        verify(model, times(1)).addAttribute(eq("error"), anyString());
    }
}