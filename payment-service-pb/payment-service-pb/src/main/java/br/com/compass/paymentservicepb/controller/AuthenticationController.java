package br.com.compass.paymentservicepb.controller;

import br.com.compass.paymentservicepb.dto.TokenDto;
import br.com.compass.paymentservicepb.form.ClientAuthenticationForm;
import br.com.compass.paymentservicepb.http.AuthClient;
import br.com.compass.paymentservicepb.model.TokenEntity;
import br.com.compass.paymentservicepb.repository.TokenRepository;
import br.com.compass.paymentservicepb.service.TokenService;
import br.com.compass.paymentservicepb.util.MappersUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("api/v1/auth")
public class AuthenticationController {

    @Autowired
    private AuthClient auth;

    @Autowired
    private TokenRepository repository;

    @Autowired
    private TokenService service;

    @PostMapping
    public ResponseEntity<TokenDto> getToken(@RequestBody @Valid ClientAuthenticationForm form, UriComponentsBuilder uriBuilder) {
        TokenDto tokenDto = auth.getToken(form);

        TokenEntity token = MappersUtil.convertTokenDtoToEntity(tokenDto);
        repository.save(token);

        URI uri = uriBuilder.path("/auth/{id}").buildAndExpand(token.getId()).toUri();
        return ResponseEntity.created(uri).body(tokenDto);
    }

}
