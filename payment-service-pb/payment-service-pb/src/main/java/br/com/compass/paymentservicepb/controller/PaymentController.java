package br.com.compass.paymentservicepb.controller;

import br.com.compass.paymentservicepb.dto.OrderDto;
import br.com.compass.paymentservicepb.dto.PaymentDto;
import br.com.compass.paymentservicepb.dto.TokenDto;
import br.com.compass.paymentservicepb.form.OrderForm;
import br.com.compass.paymentservicepb.form.PaymentForm;
import br.com.compass.paymentservicepb.service.PaymentService;
import br.com.compass.paymentservicepb.service.TokenService;
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
    private PaymentService service;

    @Autowired
    private TokenService tokenService;

    @PostMapping()
    public ResponseEntity<PaymentDto> registerPayment(@RequestBody @Valid OrderForm form, @NotNull UriComponentsBuilder uriBuilder) {
        OrderDto orderDto = service.createPayment(form);
        TokenDto validToken = tokenService.getLastValidToken();
        PaymentForm paymentForm = service.validateTokenAndCallGateway(validToken, orderDto);
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
