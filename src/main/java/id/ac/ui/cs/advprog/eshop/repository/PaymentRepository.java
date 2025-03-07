package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.enums.PaymentMethod;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class PaymentRepository {
    private Map<String, Payment> idPaymentData = new HashMap<>();

    public Payment save(Payment payment) {
        idPaymentData.put(payment.getId(), payment);
        return payment;
    }

    public Payment findById(String id) {
        return idPaymentData.get(id);
    }

    public List<Payment> findAll() {
        return new ArrayList<>(idPaymentData.values());
    }

    public List<Payment> findAllByPaymentMethod(String subNameMethod) {
        List<Payment> result = new ArrayList<>();
        if (PaymentMethod.contains(subNameMethod)) {
            if (subNameMethod.equals(PaymentMethod.VOUCHER_CODE.getValue())) {
                result = idPaymentData.values().stream()
                        .filter(payment -> PaymentMethod.VOUCHER_CODE.getValue().equals(payment.getSubNameMethod()))
                        .collect(Collectors.toList());
            } else {
                result = idPaymentData.values().stream()
                        .filter(payment -> PaymentMethod.BANK_TRANSFER.getValue().equals(payment.getSubNameMethod()))
                        .collect(Collectors.toList());
            }
        }
        return result;
    }

    public void delete(String id) {
        idPaymentData.remove(id);
    }


}