package br.com.compass.paymentservicepb.form;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ClientAuthenticationForm {

    @JsonProperty("client_id")
    private String clientId;

    @JsonProperty("api_key")
    private String apiKey;
}
