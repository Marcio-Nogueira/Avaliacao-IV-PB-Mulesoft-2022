package br.com.compass.paymentservicepb.dto;

import br.com.compass.paymentservicepb.constant.Brand;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CardDto {

    @JsonProperty("number_token")
    private String numberToken;

    @JsonProperty("cardholder_name")
    private String name;

    @JsonProperty("security_code")
    private String securityCode;

    private Brand brand;

    @JsonProperty("expiration_month")
    private String expirationMonth;

    @JsonProperty("expiration_year")
    private String expirationYear;

}
