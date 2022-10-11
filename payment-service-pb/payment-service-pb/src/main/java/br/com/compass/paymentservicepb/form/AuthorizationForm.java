package br.com.compass.paymentservicepb.form;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AuthorizationForm {

    @JsonProperty("reason_message")
    private String message;
}
