package br.com.compass.paymentservicepb.util;

import br.com.compass.paymentservicepb.constant.DocumentType;
import br.com.compass.paymentservicepb.dto.*;
import br.com.compass.paymentservicepb.constant.CurrencyType;
import br.com.compass.paymentservicepb.form.OrderForm;
import br.com.compass.paymentservicepb.form.PaymentForm;
import br.com.compass.paymentservicepb.constant.PaymentType;
import br.com.compass.paymentservicepb.model.PaymentEntity;

import java.util.List;
import java.util.stream.Collectors;

public class MappersUtil {

    public static PaymentEntity convertPaymentDtoToEntity(PaymentDto dto) {
        PaymentEntity entity = new PaymentEntity();
        entity.setTotal(dto.getTotal());
        entity.setPaymentId(dto.getPaymentId());
        entity.setStatus(dto.getStatus());
        entity.setMessage(dto.getMessage());

        return entity;
    }

    public static PaymentDto convertPaymentEntityToDto(PaymentEntity entity) {
        PaymentDto dto = new PaymentDto();
        dto.setOrderId(entity.getOrderId());
        dto.setTotal(entity.getTotal());
        dto.setPaymentId(entity.getPaymentId());
        dto.setStatus(entity.getStatus());
        dto.setMessage(entity.getMessage());

        return dto;
    }


    public static List<PaymentDto> convertListOfEntityToDto(List<PaymentEntity> entityList) {
        return entityList.stream().map(MappersUtil::convertPaymentEntityToDto).collect(Collectors.toList());
    }

    public static OrderDto convertOrderFormToDto(OrderForm form) {
        OrderDto orderDto = new OrderDto();
        orderDto.setSellerId("7be8890e-f4da-40c2-975e-0b9a87c5ad69");
        CustomerDto customer = new CustomerDto();
        orderDto.setCustomer(customer);
        customer.setDocumentType(DocumentType.CPF);
        customer.setDocumentNumber(form.getCpf());
        orderDto.setPaymentType(PaymentType.CREDIT_CARD);
        orderDto.setCurrency(CurrencyType.BRL);
        CardDto cardDto = new CardDto();
        orderDto.setCardDto(cardDto);
        cardDto.setNumberToken(form.getCreditCardForm().getCardNumber());//falta criptografar usando jwt
        cardDto.setName(form.getCreditCardForm().getCardHolderName());
        cardDto.setSecurityCode(form.getCreditCardForm().getSecurityCode());
        cardDto.setBrand(form.getCreditCardForm().getBrand());
        cardDto.setExpirationMonth(String.valueOf(form.getCreditCardForm().getExpirationMonth()));
        cardDto.setExpirationYear(String.valueOf(form.getCreditCardForm().getExpirationYear()));

        return orderDto;
    }

    public static PaymentDto convertPaymentFormToDto (PaymentForm paymentForm) {
        PaymentDto dto = new PaymentDto();
        dto.setTotal(paymentForm.getTotal());
        dto.setPaymentId(paymentForm.getPaymentId());
        dto.setStatus(paymentForm.getStatus());
        dto.setMessage(paymentForm.getAuthorization().getMessage());

        return dto;
    }
}
