package id.ac.ui.cs.advprog.eshop.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PaymentTest {
    private List<Order> orders;
    private List<Product> products;

    @BeforeEach
    void setUp() {
        this.products = new ArrayList<>();
        this.orders = new ArrayList<>();

        Product product1 = new Product();
        product1.setId("b558e9f-1c39-460e-8860-71af6af63bd6");
        product1.setName("Sampo Cap Bambang");
        product1.setQuantity(2);

        Product product2 = new Product();
        product2.setId("a2c26328-4a37-4664-83c7-f32db8620155");
        product2.setName("Sabun Cap Usep");
        product2.setQuantity(1);

        this.products.add(product2);
        this.products.add(product1);

        Order order1 = new Order("13652556-012a-4c07-b546-54eb1396d79b", this.products, 1708560000L, "Safira Sudrajat");
        Order order2 = new Order("65525631-a210-074c-45b6-54eb1396d79b", this.products, 1708570000L, "Bambang Bambang");

        this.orders.add(order1);
        this.orders.add(order2);
    }

    @Test
    void testCreatePaymentInvalidMethod() {
        Map<String, String> paymentData = new HashMap<String, String>();
        assertThrows(IllegalArgumentException.class, () -> {
            Payment payment = new Payment("a2c26328-4a37-4664-83c7-f32db8620155", "DELIVERY", paymentData,
                    this.orders.getFirst());
        });
    }

    @Test
    void testCreatePaymentEmptyPaymentData() {
        Map<String, String> paymentData = new HashMap<String, String>();
        assertThrows(IllegalArgumentException.class, () -> {
            Payment payment = new Payment("6c93d3e2-b009-46ba-9d15-f03d85adc2de", "BANK_TRANSFER", paymentData,
                    this.orders.getFirst());
        });
    }

    @Test
    void testCreatePaymentEmptyOrder() {
        Map<String, String> paymentData = new HashMap<String, String>();
        paymentData.put("address", "Jalan Anggur");
        paymentData.put("deliveryFee", "12000");
        assertThrows(IllegalArgumentException.class, () -> {
            Payment payment = new Payment("6c93d3e2-b009-46ba-9d15-f03d85adc2de", "BANK_TRANSFER", paymentData,
                    null);
        });
    }

    // TEST VOUCHER PAYMENT
    @Test
    void testCreatePaymentVoucherCodeSuccess() {
        Map<String, String> paymentData = new HashMap<String, String>();
        paymentData.put("voucherCode", "ESHOP1234ABC5678");
        Payment payment = new Payment("6c93d3e2-b009-46ba-9d15-f03d85adc2de", "VOUCHER",
                paymentData, this.orders.getFirst());
        assertEquals("SUCCESS", payment.getStatus());
        assertEquals("SUCCESS", payment.getOrder().getStatus());
    }

    @Test
    void testCreatePaymentVoucherCodeRejectedNot16Characters() {
        Map<String, String> paymentData = new HashMap<String, String>();
        paymentData.put("voucherCode", "ESHOP1234ABC56789");
        Payment payment = new Payment("6c93d3e2-b009-46ba-9d15-f03d85adc2de", "VOUCHER",
                paymentData, this.orders.getFirst());
        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testCreatePaymentVoucherCodeRejectedNotStartedWithESHOP() {
        Map<String, String> paymentData = new HashMap<String, String>();
        paymentData.put("voucherCode", "ESHOOP1234AB5678");
        Payment payment = new Payment("6c93d3e2-b009-46ba-9d15-f03d85adc2de", "VOUCHER",
                paymentData, this.orders.getFirst());
        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testCreatePaymentVoucherCodeRejectedNotContain8NumericalCharacters() {
        Map<String, String> paymentData = new HashMap<String, String>();
        paymentData.put("voucherCode", "ESHOP1234ABC567D");
        Payment payment = new Payment("6c93d3e2-b009-46ba-9d15-f03d85adc2de", "VOUCHER",
                paymentData, this.orders.getFirst());
        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testSetStatusPaymentVoucherCodeSucess() {
        Map<String, String> paymentData = new HashMap<String, String>();
        paymentData.put("voucherCode", "ESHOP1234ABC567D");
        Payment payment = new Payment("6c93d3e2-b009-46ba-9d15-f03d85adc2de", "VOUCHER",
                paymentData, this.orders.getFirst());
        payment.setStatus(PaymentStatus.SUCCESS.getValue());
        assertEquals("REJECTED", payment.getOrder().getStatus());
    }

    // TEST BANK TRANSFER PAYMENT
    @Test
    void testSetStatusPaymentVoucherCodeRejected() {
        Map<String, String> paymentData = new HashMap<String, String>();
        paymentData.put("voucherCode", "ESHOP1234ABC567D");
        Payment payment = new Payment("6c93d3e2-b009-46ba-9d15-f03d85adc2de", "VOUCHER",
                paymentData, this.orders.getFirst());
        payment.setStatus("REJECTED");
        assertEquals("REJECTED", payment.getOrder().getStatus());
    }

    @Test
    void testCreatePaymentBankTransferSuccess() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("bankName", "Bank Mandiri");
        paymentData.put("referenceCode", "INV12345678");

        Payment payment = new Payment("6h92d3h2-b009-46ba-9e15-f03d86adc2de", "BANK_TRANSFER", paymentData, this.orders.getFirst());
        assertEquals("SUCCESS", payment.getStatus());
    }

    @Test
    void testCreatePaymentBankTransferRejectedEmptyBankName() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("bankName", "");
        paymentData.put("referenceCode", "INV12345678");

        Payment payment = new Payment("6h92d3h2-b009-46ba-9e15-f03d86adc2de", "BANK_TRANSFER", paymentData, this.orders.getFirst());
        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testCreatePaymentBankTransferRejectedEmptyReferenceCode() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("bankName", "Bank Mandiri");
        paymentData.put("referenceCode", "");

        Payment payment = new Payment("6h92d3h2-b009-46ba-9e15-f03d86adc2de", "BANK_TRANSFER", this.orders.getFirst(), paymentData);
        assertEquals("REJECTED", payment.getStatus());
    }
}
