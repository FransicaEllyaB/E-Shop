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
    void testIndex() {
        String result = productController.index();
        assertEquals("home", result);
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
        invalidProduct.setProductName("");
        invalidProduct.setProductQuantity(0);

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
        String productId = "eb558e9f-1c39-460e-8860-71af6af63bd6";
        Product product = new Product();
        product.setProductId(productId);
        when(productService.findById(productId)).thenReturn(product);
        String result = productController.deleteProductPage(model, productId);
        assertEquals("deleteProduct", result);
        verify(model, times(1)).addAttribute("productId", productId);
        verify(model, times(1)).addAttribute("productName", product.getProductName());
        verify(model, times(1)).addAttribute("productQuantity", product.getProductQuantity());
    }

    @Test
    void testDeleteProductPost() {
        String productId = "eb558e9f-1c39-460e-8860-71af6af63bd6";
        String result = productController.deleteProductPost(productId);
        assertEquals("redirect:/product/list", result);
        verify(productService, times(1)).deleteById(productId);
    }

    @Test
    void testDeleteProductPostWhenProductNotFound() {
        String productId = "invalid-id";
        doThrow(new IllegalArgumentException("Product not found."))
                .when(productService).deleteById(productId);

        String result = productController.deleteProductPost(productId);
        assertEquals("redirect:/product/list", result);
    }

    @Test
    void testEditProductPage() {
        String productId = "eb558e9f-1c39-460e-8860-71af6af63bd6";
        Product product = new Product();
        product.setProductId(productId);
        when(productService.findById(productId)).thenReturn(product);
        String result = productController.editProductPage(model, productId);
        assertEquals("editProduct", result);
        verify(model, times(1)).addAttribute("product", product);
    }

    @Test
    void testEditProductPageWhenProductNotFound() {
        String productId = "invalid-id";
        when(productService.findById(productId)).thenReturn(null);
        String result = productController.editProductPage(model, productId);
        assertEquals("redirect:/product/list", result);
    }

    @Test
    void testEditProductPost() {
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");

        Model model = mock(Model.class);

        String result = productController.editProductPost(product, model);

        assertEquals("redirect:/product/list", result);
        verify(productService, times(1)).edit(product);
    }

    @Test
    void testEditProductPostWhenValidationFails() {
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        Model model = mock(Model.class);
        doThrow(new IllegalArgumentException("Product name cannot be empty."))
                .when(productService).edit(any(Product.class));
        String result = productController.editProductPost(product, model);
        assertEquals("editProduct", result);
        verify(model, times(1)).addAttribute(eq("error"), anyString());
    }

}