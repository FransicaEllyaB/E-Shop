package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProductRepositoryTest {

    @InjectMocks
    ProductRepository productRepository;

    @BeforeEach
    void setUp() {
    }

    @Test
    void testCreateAndFind(){
        Product product = new Product();
        product.setId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setName("Sampo Cap Bambang");
        product.setQuantity(100);
        productRepository.create(product);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product savedProduct = productIterator.next();
        assertEquals(product.getId(), savedProduct.getId());
        assertEquals(product.getName(), savedProduct.getName());
        assertEquals(product.getQuantity(), savedProduct.getQuantity());
    }

    @Test
    void testFindAllIfEmpty() {
        Iterator<Product> productIterator = productRepository.findAll();
        assertFalse(productIterator.hasNext());
    }

    @Test
    void testFindAllIfMoreThanOneProduct() {
        Product product1 = new Product();
        product1.setId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product1.setName("Sampo Cap Bambang");
        product1.setQuantity(100);
        productRepository.create(product1);

        Product product2 = new Product();
        product2.setId("a0f9de46-90b1-437d-a0bf-d0821dde9096");
        product2.setName("Sampo Cap Usep");
        product2.setQuantity(50);
        productRepository.create(product2);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product savedProduct = productIterator.next();
        assertEquals(product1.getId(), savedProduct.getId());
        savedProduct = productIterator.next();
        assertEquals(product2.getId(), savedProduct.getId());
        assertFalse(productIterator.hasNext());
    }

    @Test
    void testDelete() {
        Product product1 = new Product();
        product1.setId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product1.setName("Laptop");
        product1.setQuantity(10);
        productRepository.create(product1);

        productRepository.delete(product1);

        Iterator<Product> productIterator = productRepository.findAll();
        assertFalse(productIterator.hasNext());
    }

    @Test
    void testFailedDelete() {
        Product product1 = new Product();
        product1.setId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product1.setName("Laptop");
        product1.setQuantity(10);
        productRepository.create(product1);

        Product wrongProduct = new Product();
        wrongProduct.setId("a0f9de46-90b1-437d-a0bf-d0821dde9096");
        productRepository.delete(wrongProduct);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product savedProduct = productIterator.next();

        assertEquals(product1.getId(), savedProduct.getId());
        assertEquals(product1.getName(), savedProduct.getName());
        assertEquals(product1.getQuantity(), savedProduct.getQuantity());
    }

    @Test
    void testEdit() {
        Product product = new Product();
        product.setId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setName("Laptop");
        product.setQuantity(10);
        productRepository.create(product);

        Product updatedProduct = new Product();
        updatedProduct.setId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        updatedProduct.setName("Laptop Pro");
        updatedProduct.setQuantity(20);
        productRepository.edit(updatedProduct);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product savedProduct = productIterator.next();

        assertEquals("eb558e9f-1c39-460e-8860-71af6af63bd6", savedProduct.getId());
        assertEquals("Laptop Pro", savedProduct.getName());
        assertEquals(20, savedProduct.getQuantity());
    }

    @Test
    void testFailedEdit() {
        Product product = new Product();
        product.setId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setName("Laptop");
        product.setQuantity(10);
        productRepository.create(product);

        Product updatedProduct = new Product();
        updatedProduct.setId("a0f9de46-90b1-437d-a0bf-d0821dde9096");
        updatedProduct.setName("Laptop Pro");
        updatedProduct.setQuantity(20);
        productRepository.edit(updatedProduct);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product savedProduct = productIterator.next();

        assertEquals("eb558e9f-1c39-460e-8860-71af6af63bd6", savedProduct.getId());
        assertEquals("Laptop", savedProduct.getName());
        assertEquals(10, savedProduct.getQuantity());
    }
}
