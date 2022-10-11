package br.com.compass.paymentservicepb.form;

import br.com.compass.paymentservicepb.constant.PaymentStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class PaymentForm {

    @JsonProperty("payment_id")
    private String paymentId;

    @JsonProperty("seller_id")
    private String sellerId;

    @JsonProperty("transaction_amount")
    private BigDecimal total;

    private String currency;

    private PaymentStatus status;

    @JsonProperty("received_at")
    private String receivedAt;

    private AuthorizationForm authorization;
}
