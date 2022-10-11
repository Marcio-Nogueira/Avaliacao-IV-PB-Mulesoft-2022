package br.com.compass.paymentservicepb.model;

import br.com.compass.paymentservicepb.constant.PaymentStatus;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name="payments")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long orderId;

    private BigDecimal total;

    private String paymentId;

    private PaymentStatus status;

    private String message;

}
