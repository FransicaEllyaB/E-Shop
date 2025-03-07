package id.ac.ui.cs.advprog.eshop.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class ProductTest {

    Product product;
    @BeforeEach
    void setUp() {
        this.product = new Product();
        this.product.setId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        this.product.setName("Sampo Cap Bambang");
        this.product.setQuantity(100);
    }

    @Test
    void testGetId() {
        assertEquals("eb558e9f-1c39-460e-8860-71af6af63bd6", this.product.getId());
    }

    @Test
    void testGetName() {
        assertEquals("Sampo Cap Bambang", this.product.getName());
    }

    @Test
    void testGetQuantity() {
        assertEquals(100, this.product.getQuantity());
    }

    // Negative Test Cases
    @Test
    void testSetInvalidId() {
        product.setId(null);
        assertNull(product.getId());

        product.setId("");
        assertEquals("", product.getId());
    }

    @Test
    void testSetInvalidName() {
        product.setName(null);
        assertNull(product.getName());

        product.setName("");
        assertEquals("", product.getName());
    }

    @Test
    void testSetNegativeQuantity() {
        product.setQuantity(-10);
        assertTrue(product.getQuantity() >= 0);
    }
}