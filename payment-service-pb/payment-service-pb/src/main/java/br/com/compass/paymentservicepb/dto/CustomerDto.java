package br.com.compass.paymentservicepb.dto;

import br.com.compass.paymentservicepb.constant.DocumentType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CustomerDto {

    @JsonProperty("document_type")
    private DocumentType documentType;

    @JsonProperty("document_number")
    private String documentNumber;
}
