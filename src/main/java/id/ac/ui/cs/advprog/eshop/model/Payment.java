package id.ac.ui.cs.advprog.eshop.model;

import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
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

    public Payment(String id, String subNameMethod, Map<String, String> paymentData, Order order) {
        this.id = id;
        this.subNameMethod = subNameMethod;
        this.paymentData = paymentData;
        this.order = order;

        if (paymentData.isEmpty()) {
            throw new IllegalArgumentException();
        }

        if (order == null) {
            throw new IllegalArgumentException();
        }

        if (subNameMethod.equals("VOUCHER")) {
            status = validateVoucherPayment();
            setStatus(status);
        } else if (subNameMethod.equals("BANK_TRANSFER")) {
            status = validateBankTransferPayment();
            setStatus(status);
        } else {
            throw new IllegalArgumentException();
        }
    }

    public String validateVoucherPayment() {
        String voucherCode = paymentData.get("voucherCode");
        long numericCount = 0;

        if (voucherCode == null || voucherCode.isEmpty()
                || voucherCode.length() != 16 || !voucherCode.startsWith("ESHOP")) {
            return PaymentStatus.REJECTED.getValue();
        }

        for (int i = 0; i < voucherCode.length(); i++) {
            if (Character.isDigit(voucherCode.charAt(i))) {
                numericCount++;
            }
        }

        if (numericCount != 8) {
            return PaymentStatus.REJECTED.getValue();
        }

        return PaymentStatus.SUCCESS.getValue();
    }

    public String validateBankTransferPayment() {
        String bankName = paymentData.get("bankName");
        String referenceCode = paymentData.get("referenceCode");

        if (bankName == null || bankName.isEmpty() || referenceCode == null || referenceCode.isEmpty()) {
            return PaymentStatus.REJECTED.getValue();
        }
        return PaymentStatus.SUCCESS.getValue();
    }

    public Payment(String id, String subNameMethod, String status, Map<String, String> paymentData, Order order) {
        this.id = id;
        this.subNameMethod = subNameMethod;
        this.paymentData = paymentData;
        this.order = order;
        setStatus(status);
    }

    public void setStatus(String status) {
        if (PaymentStatus.contains(status)) {
            this.status = status;
            if (status.equals(PaymentStatus.SUCCESS.getValue())) {
                order.setStatus(PaymentStatus.SUCCESS.getValue());
            } else {
                order.setStatus("FAILED");
            }
        } else {
            throw new IllegalArgumentException();
        }
    }
}
