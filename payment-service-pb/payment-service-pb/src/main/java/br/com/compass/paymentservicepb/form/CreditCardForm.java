package br.com.compass.paymentservicepb.form;

import br.com.compass.paymentservicepb.constant.Brand;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.*;

@Getter @Setter
@AllArgsConstructor
public class CreditCardForm {

    @NotBlank  @Size(min = 13, max = 16, message = "This field should have thirteen to sixteen digits")
    @JsonProperty("card_number")
    private String cardNumber;

//    @NotBlank
    @JsonProperty("cardholder_name")
    private String cardHolderName;

    @NotBlank @Size(min = 3, max = 4, message = "This field should contain three to four digits")
    @JsonProperty("security_code")
    private String securityCode;

    @Range(min = 1, max = 12, message = "This field should contain a valid expiration month")
    @JsonProperty("expiration_month")
    private int expirationMonth;

    @Range(min = 2022, max = 9999, message = "This field should contain a valid expiration year")
    @JsonProperty("expiration_year")
    private int expirationYear;

    private Brand brand;
}
