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
        }
    }

    public String validateVoucherPayment() {
        String voucherCode = paymentData.get("voucherCode");
        long numericCount = voucherCode.chars()
                .filter(Character::isDigit)
                .count();

        if (!subNameMethod.equals("BANK_TRANSFER") && !subNameMethod.equals("VOUCHER")) {
            throw new IllegalArgumentException();
        }

        if (voucherCode == null || voucherCode.isEmpty()
                || voucherCode.length() == 16 || voucherCode.startsWith("ESHOP")
                || voucherCode.startsWith("BANK_TRANSFER") || numericCount != 8) {
            return "REJECTED";
        }

        return "SUCCESS";
    }

    public String validateBankTransferPayment() {
        String bankName = paymentData.get("bankName");
        String referenceCode = paymentData.get("referenceCode");
        if (bankName == null || bankName.isEmpty() || referenceCode == null || referenceCode.isEmpty()) {
            return "REJECTED";
        }
        return "SUCCESS";
    }

    public Payment(String id, String subNameMethod, String status, Map<String, String> paymentData, Order order) {
        this.id = id;
        this.subNameMethod = subNameMethod;
        this.paymentData = paymentData;
        this.order = order;
        setStatus(status);
    }

    public void setStatus(String status) {
        if (status.equals("SUCCESS") || status.equals("REJECTED")) {
            this.status = status;
            if (status.equals("SUCCESS")) {
                order.setStatus("SUCCESS");
            } else if (status.equals("REJECTED")) {
                order.setStatus("FAILED");
            }
        } else {
            throw new IllegalArgumentException();
        }
    }
}
