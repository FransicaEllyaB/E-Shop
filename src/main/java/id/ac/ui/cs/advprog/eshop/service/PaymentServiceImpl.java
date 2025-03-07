package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.enums.PaymentMethod;
import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    private PaymentRepository paymentRepository;

    public Payment addPayment(Order order, String method, Map<String, String> paymentData) {
        if (!PaymentMethod.contains(method)) {
            throw new IllegalArgumentException();
        }

        Payment payment = new Payment(UUID.randomUUID().toString(), method, paymentData, order);
        payment = paymentRepository.save(payment);
        return payment;
    }

    public Payment setStatus(Payment payment, String status) {
        Payment savedPayment = paymentRepository.findById(payment.getId());
        if (savedPayment != null) {
            savedPayment.setStatus(status);
            paymentRepository.save(savedPayment);
            return savedPayment;
        } else {
            throw new NoSuchElementException();
        }
    }

    public Payment getPayment(String paymentId) {
        return paymentRepository.findById(paymentId);
    }

    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }
}
