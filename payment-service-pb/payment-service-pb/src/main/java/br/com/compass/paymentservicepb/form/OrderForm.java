package br.com.compass.paymentservicepb.form;

import br.com.compass.paymentservicepb.constant.CurrencyType;
import br.com.compass.paymentservicepb.constant.PaymentType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class OrderForm {

    @NotBlank
    @Size(min = 11)
    private String cpf;

    @JsonProperty("items")
    private List<ItemForm> itens = new ArrayList<>();

    @NotNull
    @Positive
    private BigDecimal shipping;

    @NotNull @Positive
    private BigDecimal discount;

    @NotNull
    @JsonProperty("payment_type")
    private PaymentType paymentType;

    @NotNull
    @JsonProperty("currency_type")
    private CurrencyType currency_type;

    @JsonProperty("payment")
    private CreditCardForm creditCardForm;
}
