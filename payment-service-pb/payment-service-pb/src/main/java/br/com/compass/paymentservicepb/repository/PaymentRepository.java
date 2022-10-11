package br.com.compass.paymentservicepb.repository;

import br.com.compass.paymentservicepb.model.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<PaymentEntity, Long> {

}
