package br.com.compass.paymentservicepb.service;

import br.com.compass.paymentservicepb.dto.OrderDto;
import br.com.compass.paymentservicepb.dto.PaymentDto;
import br.com.compass.paymentservicepb.dto.TokenDto;
import br.com.compass.paymentservicepb.form.OrderForm;
import br.com.compass.paymentservicepb.form.PaymentForm;
import br.com.compass.paymentservicepb.http.AuthClient;
import br.com.compass.paymentservicepb.http.PbBankClient;
import br.com.compass.paymentservicepb.model.PaymentEntity;
import br.com.compass.paymentservicepb.repository.PaymentRepository;
import br.com.compass.paymentservicepb.util.MappersUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PaymentService {

    @Autowired
    private AuthClient auth;

    @Autowired
    private PaymentRepository repository;

    @Autowired
    private OrderService orderService;

    @Autowired
    private PbBankClient pbBankClient;

    public OrderDto createPayment(OrderForm form) {
        OrderDto order = new OrderDto();
        order = MappersUtil.convertOrderFormToDto(form);
        order.setTransactionAmount(orderService.getTotal(form.getItens(), form));
        return order;
    }

    public List<PaymentDto> getAll() {
        List<PaymentEntity> payments = repository.findAll();
        return MappersUtil.convertListOfEntityToDto(payments);
    }

    public ResponseEntity<PaymentDto> getById(Long id) {
         Optional<PaymentEntity> entity = repository.findById(id);

         if (entity.isPresent()) {
             return ResponseEntity.ok(new PaymentDto());
         } else {
             return ResponseEntity.notFound().build();
         }

    }

    public String validateToken(TokenDto validToken) {
        String accessToken = validToken.getAccessToken();
        String tokenType = validToken.getTokenType();
        String bearerToken = tokenType + " " + accessToken;

        return bearerToken;
    }
    
    public PaymentForm validateTokenAndCallGateway(TokenDto validToken, OrderDto orderDto) {
        String bearerToken = validateToken(validToken);

        PaymentForm paymentForm = pbBankClient.approvePayment(orderDto, bearerToken);
        return paymentForm;
    }

    public PaymentDto registerPayment(PaymentForm paymentForm) {
        PaymentDto paymentDto = MappersUtil.convertPaymentFormToDto(paymentForm);
        PaymentEntity entity = MappersUtil.convertPaymentDtoToEntity(paymentDto);
        repository.save(entity);
        paymentDto.setOrderId(entity.getOrderId());

        return paymentDto;
    }
}
