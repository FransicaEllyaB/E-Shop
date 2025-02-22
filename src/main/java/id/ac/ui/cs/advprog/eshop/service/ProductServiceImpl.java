package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Product create(Product product) {
        validateProduct(product);
        productRepository.create(product);
        return product;
    }

    @Override
    public List<Product> findAll() {
        Iterator<Product> productIterator = productRepository.findAll();
        List<Product> allProduct = new ArrayList<>();
        productIterator.forEachRemaining(allProduct::add);
        return allProduct;
    }

    @Override
    public Product findById(String productId) {
        Iterator<Product> productIterator = productRepository.findAll();
        while (productIterator.hasNext()) {
            Product product = productIterator.next();
            if (product.getProductId().equals(productId)) {
                return product;
            }
        }
        return null;
    }

    @Override
    public void deleteById(String productId) {
        Product product = findById(productId);
        if (product == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");
        }
        productRepository.delete(product);
    }

    @Override
    public Product edit(Product product) {
        Product editedProduct = findById(product.getProductId());
        if (editedProduct == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");
        }
        validateProduct(product);
        productRepository.edit(product);
        return product;
    }

    public void validateProduct(Product product) {
        if (product.getProductName() == null || product.getProductName().trim().isEmpty()) {
            throw new IllegalArgumentException("Product name cannot be empty.");
        }
        else if (product.getProductName().length() < 3 || product.getProductName().length() > 50) {
            throw new IllegalArgumentException("Product name must be between 3 and 50 characters.");
        }
        else if (product.getProductQuantity() < 1) {
            throw new IllegalArgumentException("Quantity must be at least 1.");
        }
    }
}