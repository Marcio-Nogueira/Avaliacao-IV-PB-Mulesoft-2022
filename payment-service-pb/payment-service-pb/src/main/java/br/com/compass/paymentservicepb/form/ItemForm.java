package br.com.compass.paymentservicepb.form;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter  @Setter
public class ItemForm {

    @NotBlank
    private String item;

    @Positive
    private BigDecimal value;

    @Positive
    private int qty;
}
