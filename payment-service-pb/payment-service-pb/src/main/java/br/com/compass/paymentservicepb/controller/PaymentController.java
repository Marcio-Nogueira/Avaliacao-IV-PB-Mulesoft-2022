package br.com.compass.paymentservicepb.controller;

import br.com.compass.paymentservicepb.dto.OrderDto;
import br.com.compass.paymentservicepb.dto.PaymentDto;
import br.com.compass.paymentservicepb.dto.TokenDto;
import br.com.compass.paymentservicepb.form.ClientAuthenticationForm;
import br.com.compass.paymentservicepb.form.OrderForm;
import br.com.compass.paymentservicepb.form.PaymentForm;
import br.com.compass.paymentservicepb.http.AuthClient;
import br.com.compass.paymentservicepb.service.PaymentService;
import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("api/v1/order/payment")
public class PaymentController {

    @Autowired
    private AuthClient authClient;

    @Autowired
    private PaymentService service;

    @PostMapping()
    public ResponseEntity<PaymentDto> registerPayment(@RequestBody @Valid OrderForm form, @NotNull UriComponentsBuilder uriBuilder) {
        ClientAuthenticationForm clientForm = new ClientAuthenticationForm("client_id_mulesoft", "91452c37-e343-4738-a94a-be113875cb2b");
        TokenDto tokenDto = authClient.getToken(clientForm);
        service.getBearerToken(tokenDto);

        OrderDto orderDto = service.createPaymentOrder(form);
        PaymentForm paymentForm = service.callGateway(orderDto, tokenDto);
        PaymentDto paymentDto = service.registerPayment(paymentForm);

        URI uri = uriBuilder.path("/payment/{orderId}").buildAndExpand(paymentDto.getOrderId()).toUri();
        return ResponseEntity.created(uri).body(paymentDto);
    }

    @GetMapping
    public List<PaymentDto> getList() {
        return service.getAll();
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<PaymentDto> getPaymentById(@PathVariable @NotNull Long orderId)  {
        ResponseEntity<PaymentDto> dto = service.getById(orderId);

        return dto;
    }

}
