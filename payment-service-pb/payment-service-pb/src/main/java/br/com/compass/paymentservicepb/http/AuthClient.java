package br.com.compass.paymentservicepb.http;

import br.com.compass.paymentservicepb.dto.TokenDto;
import br.com.compass.paymentservicepb.form.ClientAuthenticationForm;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@FeignClient(name = "auth", url = "${url.heroku}/v1")
public interface AuthClient {

    @PostMapping("/auth")
    public TokenDto getToken(@RequestBody @Valid ClientAuthenticationForm client);
}
