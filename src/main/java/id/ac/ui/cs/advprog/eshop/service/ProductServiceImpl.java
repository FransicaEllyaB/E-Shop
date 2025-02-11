package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public void validateProduct(Product product) {
        List<String> errors = new ArrayList<>();

        if (product.getProductName() == null || product.getProductName().trim().isEmpty()) {
            errors.add("Product name cannot be empty.");
        }
        if (product.getProductName().length() < 3 || product.getProductName().length() > 50) {
            errors.add("Product name must be between 3 and 50 characters.");
        }
        if (product.getProductQuantity() < 1) {
            errors.add("Quantity must be at least 1.");
        }

        if (!errors.isEmpty()) {
            throw new IllegalArgumentException(String.join(", ", errors));
        }
    }
}