package br.com.compass.paymentservicepb.dto;

import br.com.compass.paymentservicepb.constant.CurrencyType;
import br.com.compass.paymentservicepb.constant.PaymentType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter @Setter
public class OrderDto {

    @JsonProperty("seller_id")
    private String SellerId;

    private CustomerDto customer;

    @JsonProperty("payment_type")
    private PaymentType paymentType;

    private CurrencyType currency;

    @JsonProperty("transaction_amount")
    private BigDecimal transactionAmount;

    @JsonProperty("card")
    private CardDto cardDto;

}
