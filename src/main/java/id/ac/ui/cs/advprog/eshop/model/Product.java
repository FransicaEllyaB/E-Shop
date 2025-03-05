package id.ac.ui.cs.advprog.eshop.model;

import lombok.Setter;
import lombok.Getter;
import java.util.UUID;

@Getter @Setter
public class Product {
    private String id;
    private String name;
    private int quantity;

    public Product() {
        this.id = UUID.randomUUID().toString();
    }

    public void setQuantity(int quantity) {
        this.quantity = Math.max(quantity, 0);
    }
}