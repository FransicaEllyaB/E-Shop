package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.ProductRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @InjectMocks
    ProductServiceImpl productService;

    @Mock
    ProductRepository productRepository;

    @BeforeEach
    void setUp() {
    }

    @Test
    void testCreate(){
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
        when(productRepository.create(any(Product.class))).thenReturn(product);

        Product savedProduct = productService.create(product);

        assertEquals(product.getProductId(), savedProduct.getProductId());
        assertEquals(product.getProductName(), savedProduct.getProductName());
        assertEquals(product.getProductQuantity(), savedProduct.getProductQuantity());
    }

    @Test
    void testFindAll() {
        Product product1 = new Product();
        product1.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        Product product2 = new Product();
        product2.setProductId("a0f9de46-90b1-437d-a0bf-d0821dde9096");
        when(productRepository.findAll()).thenReturn(Arrays.asList(product1, product2).iterator());

        List<Product> allProducts = productService.findAll();

        assertEquals(2, allProducts.size());
    }

    @Test
    void testFindByIdWhenProductExists() {
        String productId = "eb558e9f-1c39-460e-8860-71af6af63bd6";
        Product product = new Product();
        product.setProductId(productId);
        when(productRepository.findAll()).thenReturn(Arrays.asList(product).iterator());

        Product foundProduct = productService.findById(productId);

        assertNotNull(foundProduct);
        assertEquals(product.getProductId(), foundProduct.getProductId());
    }

    @Test
    void testFindByIdWhenProductDoesNotExist() {
        String productId = "eb558e9f-1c39-460e-8860-71af6af63bd6";
        String nonExistingProductId = "-eb558e9f-1c39-460e-8860-71af6af63bd6";

        Product product = new Product();
        product.setProductId(productId);
        when(productRepository.findAll()).thenReturn(Arrays.asList(product).iterator());

        Product foundProduct = productService.findById(nonExistingProductId);

        assertNull(foundProduct);
    }

    @Test
    void testDeleteByIdWhenProductExists() {
        String productId = "eb558e9f-1c39-460e-8860-71af6af63bd6";
        Product product = new Product();
        product.setProductId(productId);
        when(productRepository.findAll()).thenReturn(Arrays.asList(product).iterator());

        productService.deleteById(productId);

        verify(productRepository, times(1)).delete(product);
    }

    @Test
    void testDeleteById_WhenProductDoesNotExist() {
        String productId = "eb558e9f-1c39-460e-8860-71af6af63bd6";
        String nonExistingProductId = "-eb558e9f-1c39-460e-8860-71af6af63bd6";
        Product product = new Product();
        product.setProductId(productId);
        when(productRepository.findAll()).thenReturn(Arrays.asList(product).iterator());

        assertThrows(ResponseStatusException.class, () -> productService.deleteById(nonExistingProductId));
    }

    @Test
    void testEditWhenProductExists() {
        String productId = "eb558e9f-1c39-460e-8860-71af6af63bd6";
        Product product = new Product();
        product.setProductId(productId);
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
        when(productRepository.findAll()).thenReturn(Arrays.asList(product).iterator());

        Product editedProduct = new Product();
        editedProduct.setProductId(productId);
        editedProduct.setProductName("Sampo Cap Bango");
        editedProduct.setProductQuantity(50);
        productService.edit(editedProduct);

        verify(productRepository, times(1)).edit(editedProduct);
    }

    @Test
    void testEditWhenProductDoesNotExist() {
        String productId = "eb558e9f-1c39-460e-8860-71af6af63bd6";
        String nonExistingProductId = "-eb558e9f-1c39-460e-8860-71af6af63bd6";

        Product product = new Product();
        product.setProductId(productId);
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);

        Product editedProduct = new Product();
        editedProduct.setProductId(nonExistingProductId);
        editedProduct.setProductName("Sampo Cap Bango");
        editedProduct.setProductQuantity(50);

        when(productRepository.findAll()).thenReturn(Arrays.asList(product).iterator());

        assertThrows(ResponseStatusException.class, () -> productService.edit(editedProduct));
    }

    @Test
    void testCreateWhenInvalidProduct() {
        Product invalidProduct = new Product();
        invalidProduct.setProductName(""); // Invalid product name
        invalidProduct.setProductQuantity(0); // Invalid quantity

        assertThrows(IllegalArgumentException.class, () -> productService.create(invalidProduct));
    }

    @Test
    void testValidateProductValidProduct() {
        Product product = new Product();
        product.setProductName("Valid Product");
        product.setProductQuantity(10);

        assertDoesNotThrow(() -> productService.validateProduct(product));
    }

    @Test
    void testValidateProductEmptyName() {
        Product product = new Product();
        product.setProductName("");
        product.setProductQuantity(10);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> productService.validateProduct(product));
        assertTrue(exception.getMessage().contains("Product name cannot be empty"));
    }

    @Test
    void testValidateProductNullName() {
        Product product = new Product();
        product.setProductName(null);
        product.setProductQuantity(10);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> productService.validateProduct(product));
        assertTrue(exception.getMessage().contains("Product name cannot be empty"));
    }

    @Test
    void testValidateProductProductNameTooShort() {
        Product product = new Product();
        product.setProductName("AB");
        product.setProductQuantity(10);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> productService.validateProduct(product));
        assertTrue(exception.getMessage().contains("Product name must be between 3 and 50 characters"));
    }

    @Test
    void testValidateProductProductNameTooLong() {
        Product product = new Product();
        product.setProductName("A".repeat(51));
        product.setProductQuantity(10);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> productService.validateProduct(product));
        assertTrue(exception.getMessage().contains("Product name must be between 3 and 50 characters"));
    }

    @Test
    void testValidateProductNegativeQuantity() {
        Product product = new Product();
        product.setProductName("Valid Name");
        product.setProductQuantity(0);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> productService.validateProduct(product));
        assertTrue(exception.getMessage().contains("Quantity must be at least 1"));
    }
}