package br.com.compass.paymentservicepb.http;

import br.com.compass.paymentservicepb.dto.OrderDto;
import br.com.compass.paymentservicepb.form.PaymentForm;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import javax.validation.Valid;

@FeignClient(name = "paymentClient", url = "${url.heroku}/v1/payments")
public interface PbBankClient {

    String AUTH_TOKEN = "Authorization";

    @PostMapping("/credit-card")
    public PaymentForm approvePayment(@RequestBody @Valid OrderDto orderDto,
                                      @RequestHeader(AUTH_TOKEN) String bearerToken

    );


}
