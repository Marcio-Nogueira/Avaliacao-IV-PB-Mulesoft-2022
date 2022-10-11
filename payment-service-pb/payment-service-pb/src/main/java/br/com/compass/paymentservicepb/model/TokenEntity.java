package br.com.compass.paymentservicepb.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
@Table(name="access_tokens")
public class TokenEntity {

    @Id
    @GeneratedValue
    private Long id;

    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("token_type")
    private String tokenType;

    @JsonProperty("expires_in")
    private int expiresIn;

    private LocalDateTime tokenCreationDate = LocalDateTime.now();

    private boolean isExpired;
}
