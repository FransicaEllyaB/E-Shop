package id.ac.ui.cs.advprog.eshop.model;

import lombok.Setter;
import lombok.Getter;
import java.util.UUID;

@Getter @Setter
public class Product {
    private static int counter;
    private String productId;
    private String productName;
    private int productQuantity;

    public Product() {
        this.productId = UUID.randomUUID().toString();
    }

    public String getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public int getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(int productQuantity) {
        this.productQuantity = Math.max(productQuantity, 0);
    }
}