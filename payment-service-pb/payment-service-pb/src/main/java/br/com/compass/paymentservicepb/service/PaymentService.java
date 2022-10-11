package br.com.compass.paymentservicepb.service;

import br.com.compass.paymentservicepb.dto.OrderDto;
import br.com.compass.paymentservicepb.dto.PaymentDto;
import br.com.compass.paymentservicepb.dto.TokenDto;
import br.com.compass.paymentservicepb.form.ClientAuthenticationForm;
import br.com.compass.paymentservicepb.form.ItemForm;
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

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class PaymentService {

    @Autowired
    private AuthClient authClient;

    @Autowired
    private PaymentRepository repository;

    @Autowired
    private PbBankClient pbBankClient;

    public BigDecimal getTotal(List<ItemForm> itens, OrderForm order) {
        BigDecimal total = BigDecimal.ZERO;

        if(itens.isEmpty()) {
            return total;
        }

        for (int i = 0; i < itens.size(); i++) {
            total = total.add(itens.get(i).getValue().multiply(BigDecimal.valueOf(itens.get(i).getQty())));
        }
        total = total.subtract(order.getDiscount()).add(order.getShipping());

        return total;
    }

    public OrderDto createPaymentOrder(OrderForm form) {
        OrderDto order = new OrderDto();
        order = MappersUtil.convertOrderFormToDto(form);
        order.setTransactionAmount(getTotal(form.getItens(), form));
        return order;
    }

    public List<PaymentDto> getAll() {
        List<PaymentEntity> payments = repository.findAll();
        return MappersUtil.convertListOfEntityToDto(payments);
    }

    public ResponseEntity<PaymentDto> getById(Long id) {
         Optional<PaymentEntity> entity = repository.findById(id);

         if (entity.isPresent()) {
             return ResponseEntity.ok().body(MappersUtil.convertPaymentEntityToDto(entity.get()));
         } else {
             return ResponseEntity.notFound().build();
         }

    }

    public String getToken() {
        ClientAuthenticationForm clientForm = new ClientAuthenticationForm("client_id_mulesoft", "91452c37-e343-4738-a94a-be113875cb2b");
        TokenDto tokenDto = authClient.getToken(clientForm);
        String accessToken = tokenDto.getAccessToken();
        String tokenType = tokenDto.getTokenType();
        String bearerToken = tokenType + " " + accessToken;

        return bearerToken;
    }

    public PaymentForm CallGateway(OrderDto orderDto) {
        String bearerToken = getToken();

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
