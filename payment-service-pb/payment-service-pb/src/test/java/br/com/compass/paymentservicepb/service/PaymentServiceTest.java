package br.com.compass.paymentservicepb.service;

import br.com.compass.paymentservicepb.constant.*;
import br.com.compass.paymentservicepb.dto.CustomerDto;
import br.com.compass.paymentservicepb.dto.OrderDto;
import br.com.compass.paymentservicepb.dto.PaymentDto;
import br.com.compass.paymentservicepb.dto.TokenDto;
import br.com.compass.paymentservicepb.form.*;
import br.com.compass.paymentservicepb.http.AuthClient;
import br.com.compass.paymentservicepb.model.PaymentEntity;
import br.com.compass.paymentservicepb.repository.PaymentRepository;
import br.com.compass.paymentservicepb.util.MappersUtil;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
class PaymentServiceTest {

    @Mock
    private PaymentService service;

    private OrderForm orderForm;

    @Mock
    private ItemForm item;

    @Mock
    private OrderForm order;

    @MockBean
    private PaymentRepository repository;

    @BeforeEach
    public void beforeEach() {
        MockitoAnnotations.initMocks(this);
        this.service = new PaymentService();
    }

    @Test
    void shouldCalculateTotal() {
        List<ItemForm> itemList = itens();
        OrderForm orderForm = new OrderForm();
        orderForm.setDiscount(BigDecimal.valueOf(10));
        orderForm.setShipping(BigDecimal.valueOf(20));

        assertEquals(BigDecimal.valueOf(140), service.getTotal(itemList, orderForm));
    }

    @Test
    void shouldReturnZeroIfListIsEmpty() {
        List<ItemForm> itemList = emptyItemList();
        OrderForm orderForm = new OrderForm();
        orderForm.setDiscount(BigDecimal.valueOf(10));
        orderForm.setShipping(BigDecimal.valueOf(20));

        assertEquals(BigDecimal.ZERO, service.getTotal(itemList, orderForm));
    }

    @Test
    void shouldReturnAPaymentOrderDto() {
        List<ItemForm> itemList = new ArrayList<>();

        CreditCardForm creditCardForm = new CreditCardForm("132111321113", "Joao", "123", 12, 25, Brand.MASTERCARD);
        orderForm = new OrderForm("12341414331", itemList, BigDecimal.valueOf(10), BigDecimal.valueOf(20), PaymentType.CREDIT_CARD, CurrencyType.BRL, creditCardForm );
        OrderDto orderDto = service.createPaymentOrder(orderForm);

        assertNotEquals(null, orderDto);
    }

    @Test
    void shouldRetunAListWithAllSuccesfullPaymentsTransactions() {
        List<PaymentEntity> payments = repository.findAll();

        assertNotEquals(null, payments);
    }

    @Test
    void ShouldReturnAExistentPaymentDto() {
        long orderId = 1;
        Optional<PaymentEntity> entity = repository.findById(orderId);

        assertNotEquals(null, entity);
    }

    @Test
    void ShouldReturnAStringWithTheTypeBearerPlusToken() {
        String accessToken = "token.sadadasda-sadsada";
        String tokenType = "Bearer";
        String bearerToken = tokenType + " " + accessToken;
        assertNotEquals(null, bearerToken);
        assertEquals("Bearer token.sadadasda-sadsada", bearerToken);
    }




    //itemlist
    private List<ItemForm> itens() {
        List<ItemForm> itemList = new ArrayList<>();

        ItemForm item1 = new ItemForm("item1", new BigDecimal(50), 2);
        ItemForm item2 = new ItemForm("item2", new BigDecimal(10), 3);

        itemList.add(item1);
        itemList.add(item2);

        return itemList;
    }

    private List<ItemForm> emptyItemList() {
        List<ItemForm> itemList = new ArrayList<>();

        ItemForm item1 = new ItemForm("item1", new BigDecimal(50), 2);
        ItemForm item2 = new ItemForm("item2", new BigDecimal(10), 3);



        return itemList;
    }


}