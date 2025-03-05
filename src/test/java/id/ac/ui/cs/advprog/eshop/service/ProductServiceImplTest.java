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
        product.setId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setName("Sampo Cap Bambang");
        product.setQuantity(100);
        when(productRepository.create(any(Product.class))).thenReturn(product);

        Product savedProduct = productService.create(product);

        assertEquals(product.getId(), savedProduct.getId());
        assertEquals(product.getName(), savedProduct.getName());
        assertEquals(product.getQuantity(), savedProduct.getQuantity());
    }

    @Test
    void testFindAll() {
        Product product1 = new Product();
        product1.setId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        Product product2 = new Product();
        product2.setId("a0f9de46-90b1-437d-a0bf-d0821dde9096");
        when(productRepository.findAll()).thenReturn(Arrays.asList(product1, product2).iterator());

        List<Product> allProducts = productService.findAll();

        assertEquals(2, allProducts.size());
    }

    @Test
    void testFindByIdWhenProductExists() {
        String id = "eb558e9f-1c39-460e-8860-71af6af63bd6";
        Product product = new Product();
        product.setId(id);
        when(productRepository.findAll()).thenReturn(Arrays.asList(product).iterator());

        Product foundProduct = productService.findById(id);

        assertNotNull(foundProduct);
        assertEquals(product.getId(), foundProduct.getId());
    }

    @Test
    void testFindByIdWhenProductDoesNotExist() {
        String id = "eb558e9f-1c39-460e-8860-71af6af63bd6";
        String nonExistingId = "-eb558e9f-1c39-460e-8860-71af6af63bd6";

        Product product = new Product();
        product.setId(id);
        when(productRepository.findAll()).thenReturn(Arrays.asList(product).iterator());

        Product foundProduct = productService.findById(nonExistingId);

        assertNull(foundProduct);
    }

    @Test
    void testDeleteByIdWhenProductExists() {
        String id = "eb558e9f-1c39-460e-8860-71af6af63bd6";
        Product product = new Product();
        product.setId(id);
        when(productRepository.findAll()).thenReturn(Arrays.asList(product).iterator());

        productService.deleteById(id);

        verify(productRepository, times(1)).delete(product);
    }

    @Test
    void testDeleteById_WhenProductDoesNotExist() {
        String id = "eb558e9f-1c39-460e-8860-71af6af63bd6";
        String nonExistingId = "-eb558e9f-1c39-460e-8860-71af6af63bd6";
        Product product = new Product();
        product.setId(id);
        when(productRepository.findAll()).thenReturn(Arrays.asList(product).iterator());

        assertThrows(ResponseStatusException.class, () -> productService.deleteById(nonExistingId));
    }

    @Test
    void testEditWhenProductExists() {
        String id = "eb558e9f-1c39-460e-8860-71af6af63bd6";
        Product product = new Product();
        product.setId(id);
        product.setName("Sampo Cap Bambang");
        product.setQuantity(100);
        when(productRepository.findAll()).thenReturn(Arrays.asList(product).iterator());

        Product editedProduct = new Product();
        editedProduct.setId(id);
        editedProduct.setName("Sampo Cap Bango");
        editedProduct.setQuantity(50);
        productService.edit(editedProduct);

        verify(productRepository, times(1)).edit(editedProduct);
    }

    @Test
    void testEditWhenProductDoesNotExist() {
        String id = "eb558e9f-1c39-460e-8860-71af6af63bd6";
        String nonExistingId = "-eb558e9f-1c39-460e-8860-71af6af63bd6";

        Product product = new Product();
        product.setId(id);
        product.setName("Sampo Cap Bambang");
        product.setQuantity(100);

        Product editedProduct = new Product();
        editedProduct.setId(nonExistingId);
        editedProduct.setName("Sampo Cap Bango");
        editedProduct.setQuantity(50);

        when(productRepository.findAll()).thenReturn(Arrays.asList(product).iterator());

        assertThrows(ResponseStatusException.class, () -> productService.edit(editedProduct));
    }

    @Test
    void testCreateWhenInvalidProduct() {
        Product invalidProduct = new Product();
        invalidProduct.setName(""); // Invalid product name
        invalidProduct.setQuantity(0); // Invalid quantity

        assertThrows(IllegalArgumentException.class, () -> productService.create(invalidProduct));
    }

    @Test
    void testValidateProductValidProduct() {
        Product product = new Product();
        product.setName("Valid Product");
        product.setQuantity(10);

        assertDoesNotThrow(() -> productService.validateProduct(product));
    }

    @Test
    void testValidateProductEmptyName() {
        Product product = new Product();
        product.setName("");
        product.setQuantity(10);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> productService.validateProduct(product));
        assertTrue(exception.getMessage().contains("Product name cannot be empty"));
    }

    @Test
    void testValidateProductNullName() {
        Product product = new Product();
        product.setName(null);
        product.setQuantity(10);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> productService.validateProduct(product));
        assertTrue(exception.getMessage().contains("Product name cannot be empty"));
    }

    @Test
    void testValidateProductProductNameTooShort() {
        Product product = new Product();
        product.setName("AB");
        product.setQuantity(10);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> productService.validateProduct(product));
        assertTrue(exception.getMessage().contains("Product name must be between 3 and 50 characters"));
    }

    @Test
    void testValidateProductProductNameTooLong() {
        Product product = new Product();
        product.setName("A".repeat(51));
        product.setQuantity(10);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> productService.validateProduct(product));
        assertTrue(exception.getMessage().contains("Product name must be between 3 and 50 characters"));
    }

    @Test
    void testValidateProductNegativeQuantity() {
        Product product = new Product();
        product.setName("Valid Name");
        product.setQuantity(0);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> productService.validateProduct(product));
        assertTrue(exception.getMessage().contains("Quantity must be at least 1"));
    }
}