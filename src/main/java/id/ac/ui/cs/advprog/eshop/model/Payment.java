package id.ac.ui.cs.advprog.eshop.model;

import lombok.Builder;
import lombok.Getter;
import java.util.Map;

@Getter @Builder
public class Payment {
    String id;
    String subNameMethod;
    String status;
    Map<String, String> paymentData;
    Order order;

    public Payment(String id, String subNameMethod, Map<String, String> paymentData, Order order) {}

    public Payment(String id, String subNameMethod, String status, Map<String, String> paymentData, Order order) {}

    public void setStatus(String status) {}
}
