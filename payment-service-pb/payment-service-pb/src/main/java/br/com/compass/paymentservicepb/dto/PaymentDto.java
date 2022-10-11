package br.com.compass.paymentservicepb.dto;

import br.com.compass.paymentservicepb.constant.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDto {

    private long orderId;

    private BigDecimal total;

    private String paymentId;

    private PaymentStatus status;

    private String message;

}
