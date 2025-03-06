package id.ac.ui.cs.advprog.eshop.model;

import id.ac.ui.cs.advprog.eshop.enums.PaymentMethod;
import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
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
            Payment payment = new Payment("6c93d3e2-b009-46ba-9d15-f03d85adc2de", PaymentMethod.BANK_TRANSFER.getValue(), paymentData,
                    this.orders.getFirst());
        });
    }

    @Test
    void testCreatePaymentEmptyOrder() {
        Map<String, String> paymentData = new HashMap<String, String>();
        paymentData.put("voucherCode", "ESHOP1234ABC5678");
        assertThrows(IllegalArgumentException.class, () -> {
            Payment payment = new Payment("6c93d3e2-b009-46ba-9d15-f03d85adc2de", PaymentMethod.BANK_TRANSFER.getValue(), paymentData,null);
        });
    }

    @Test
    void testCreatePaymentInvalidSubname() {
        Map<String, String> paymentData = new HashMap<String, String>();
        paymentData.put("voucherCode", "ESHOP1234ABC5678");
        assertThrows(IllegalArgumentException.class, () -> {
            Payment payment = new Payment("6c93d3e2-b009-46ba-9d15-f03d85adc2de", "VoucherPalsu", paymentData,
                    this.orders.getFirst());
        });
    }


    // TEST VOUCHER PAYMENT
    @Test
    void testCreatePaymentVoucherCodeSuccess() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP12345678ABC"); // Tepat 8 digit, memenuhi persyaratan

        Payment payment = new Payment("6c93d3e2-b009-46ba-9d15-f03d85adc2de", PaymentMethod.VOUCHER_CODE.getValue(),
                paymentData, this.orders.getFirst());
        assertEquals(PaymentStatus.SUCCESS.getValue(), payment.getStatus());
    }

    @Test
    void testCreatePaymentVoucherCodeNull() {
        Map<String, String> paymentData = new HashMap<String, String>();
        paymentData.put("voucherCode", null);
        Payment payment = new Payment("6c93d3e2-b009-46ba-9d15-f03d85adc2de", PaymentMethod.VOUCHER_CODE.getValue(),
                paymentData, this.orders.getFirst());
        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
    }

    @Test
    void testCreatePaymentVoucherCodeEmpty() {
        Map<String, String> paymentData = new HashMap<String, String>();
        paymentData.put("voucherCode", "");
        Payment payment = new Payment("6c93d3e2-b009-46ba-9d15-f03d85adc2de", PaymentMethod.VOUCHER_CODE.getValue(),
                paymentData, this.orders.getFirst());
        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
    }

    @Test
    void testCreatePaymentVoucherCodeRejectedNot16Characters() {
        Map<String, String> paymentData = new HashMap<String, String>();
        paymentData.put("voucherCode", "ESHOP1234ABC56789");
        Payment payment = new Payment("6c93d3e2-b009-46ba-9d15-f03d85adc2de", PaymentMethod.VOUCHER_CODE.getValue(),
                paymentData, this.orders.getFirst());
        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
    }

    @Test
    void testCreatePaymentVoucherCodeRejectedNotStartedWithESHOP() {
        Map<String, String> paymentData = new HashMap<String, String>();
        paymentData.put("voucherCode", "ESHOOP1234AB5678");
        Payment payment = new Payment("6c93d3e2-b009-46ba-9d15-f03d85adc2de", PaymentMethod.VOUCHER_CODE.getValue(),
                paymentData, this.orders.getFirst());
        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
    }

    @Test
    void testCreatePaymentVoucherCodeRejectedWith7Digits() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP123ABCDE4567"); // 7 digit (123,4567)

        Payment payment = new Payment("6c93d3e2-b009-46ba-9d15-f03d85adc2de", PaymentMethod.VOUCHER_CODE.getValue(),
                paymentData, this.orders.getFirst());
        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
    }

    // TEST BANK TRANSFER PAYMENT
    @Test
    void testCreatePaymentBankTransferSuccess() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("bankName", "Bank Mandiri");
        paymentData.put("referenceCode", "INV12345678");

        Payment payment = new Payment("6h92d3h2-b009-46ba-9e15-f03d86adc2de", PaymentMethod.BANK_TRANSFER.getValue(), PaymentStatus.SUCCESS.getValue(), paymentData, this.orders.getFirst());
        assertEquals(PaymentStatus.SUCCESS.getValue(), payment.getStatus());
    }

    @Test
    void testCreatePaymentBankTransferRejectedEmptyBankName() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("bankName", "");
        paymentData.put("referenceCode", "INV12345678");

        Payment payment = new Payment("6h92d3h2-b009-46ba-9e15-f03d86adc2de", PaymentMethod.BANK_TRANSFER.getValue(), paymentData, this.orders.getFirst());
        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
    }

    @Test
    void testCreatePaymentBankTransferRejectedNullBankName() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("bankName", null);
        paymentData.put("referenceCode", "INV12345678");

        Payment payment = new Payment("6h92d3h2-b009-46ba-9e15-f03d86adc2de", PaymentMethod.BANK_TRANSFER.getValue(), paymentData, this.orders.getFirst());
        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
    }

    @Test
    void testCreatePaymentBankTransferRejectedEmptyReferenceCode() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("bankName", "Bank Mandiri");
        paymentData.put("referenceCode", "");

        Payment payment = new Payment("6h92d3h2-b009-46ba-9e15-f03d86adc2de", PaymentMethod.BANK_TRANSFER.getValue(), paymentData, this.orders.getFirst());
        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
    }

    @Test
    void testCreatePaymentBankTransferRejectedNullReferenceCode() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("bankName", "Bank Mandiri");
        paymentData.put("referenceCode", null);

        Payment payment = new Payment("6h92d3h2-b009-46ba-9e15-f03d86adc2de", PaymentMethod.BANK_TRANSFER.getValue(), paymentData, this.orders.getFirst());
        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
    }

    // SET STATUS PAYMENT
    @Test
    void testSetStatusPaymentBankTransferSuccess() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("bankName", "Bank Mandiri");
        paymentData.put("referenceCode", "INV12345678");

        Payment payment = new Payment("6h92d3h2-b009-46ba-9e15-f03d86adc2de", PaymentMethod.BANK_TRANSFER.getValue(), paymentData, this.orders.getFirst());
        payment.setStatus(PaymentStatus.SUCCESS.getValue());
        assertEquals(PaymentStatus.SUCCESS.getValue(), payment.getStatus());
    }

    @Test
    void testSetStatusPaymentInvalid() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("bankName", "Bank Mandiri");
        paymentData.put("referenceCode", "INV12345678");

        assertThrows(IllegalArgumentException.class, () -> {
            Payment payment = new Payment("6h92d3h2-b009-46ba-9e15-f03d86adc2de", PaymentMethod.BANK_TRANSFER.getValue(), paymentData, this.orders.getFirst());
            payment.setStatus("DECLINED");
        });
    }

    @Test
    void testSubNamePaymentVoucherCodeSuccess() {
        Map<String, String> paymentData = new HashMap<String, String>();
        paymentData.put("voucherCode", "ESHOP1234ABC567D");
        Payment payment = new Payment("6c93d3e2-b009-46ba-9d15-f03d85adc2de", PaymentMethod.VOUCHER_CODE.getValue(),
                paymentData, this.orders.getFirst());
        payment.setStatus(PaymentStatus.SUCCESS.getValue());
        assertEquals(PaymentStatus.SUCCESS.getValue(), payment.getOrder().getStatus());
    }

    @Test
    void testSetStatusPaymentReferenceCodeRejected() {
        Map<String, String> paymentData = new HashMap<String, String>();
        paymentData.put("voucherCode", "ESHOP1234ABC567D");
        Payment payment = new Payment("6c93d3e2-b009-46ba-9d15-f03d85adc2de", PaymentMethod.VOUCHER_CODE.getValue(),
                paymentData, this.orders.getFirst());
        payment.setStatus(PaymentStatus.REJECTED.getValue());
        assertEquals("FAILED", payment.getOrder().getStatus());
    }

    @Test
    void testSetStatusPaymentRejected() {
        Map<String, String> paymentData = new HashMap<String, String>();
        paymentData.put("voucherCode", "ESHOP1234ABC567D");
        Payment payment = new Payment("6c93d3e2-b009-46ba-9d15-f03d85adc2de", PaymentMethod.VOUCHER_CODE.getValue(),
                paymentData, this.orders.getFirst());
        payment.setStatus(PaymentStatus.REJECTED.getValue());
        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
    }
}
