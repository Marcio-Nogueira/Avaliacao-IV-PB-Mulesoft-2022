package br.com.compass.paymentservicepb.service;

import br.com.compass.paymentservicepb.constant.*;
import br.com.compass.paymentservicepb.dto.*;
import br.com.compass.paymentservicepb.form.*;
import br.com.compass.paymentservicepb.http.AuthClient;
import br.com.compass.paymentservicepb.model.PaymentEntity;
import br.com.compass.paymentservicepb.repository.PaymentRepository;
import br.com.compass.paymentservicepb.util.MappersUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

@SpringBootTest
class PaymentServiceTest {

    @Mock
    private PaymentService service;

    private OrderForm orderForm;

    private ItemForm item;

    private OrderForm order;

    @Captor
    private ArgumentCaptor<TokenDto> captor;

    @Mock
    private PaymentRepository repositoryMock;

    @Mock
    AuthClient authClientMock;

    @Mock
    br.com.compass.paymentservicepb.http.PbBankClient pbBankClientMock;

    @BeforeEach
    public void beforeEach() {
        MockitoAnnotations.initMocks(this);
        this.service = new PaymentService(this.authClientMock, this.pbBankClientMock ,this.repositoryMock);
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
        List<PaymentEntity> paymentEntityList = createPaymentList();
        Mockito.when(repositoryMock.findAll()).thenReturn(paymentEntityList);

        List<PaymentDto> paymentDtoList = service.getAll();

        Mockito.verify(repositoryMock).findAll();
        assertNotEquals(null, paymentDtoList);
    }

    @Test
    void ShouldReturnOneExistentPaymentDto() {
        Optional<PaymentEntity> entity = Optional.of(createPaymentList().get(0));
        Mockito.when(repositoryMock.findById(Mockito.any())).thenReturn(entity);

        ResponseEntity<PaymentDto> paymentDtoResponse = service.getById(Mockito.any());

        Mockito.verify(repositoryMock).findById(Mockito.any());
        assertEquals(new ResponseEntity<>(HttpStatus.OK).getStatusCode(), paymentDtoResponse.getStatusCode());
    }

    @Test
    void ShouldNotReturnOnePaymentDtoInsteadShouldReturnNotFound() {
        ResponseEntity<PaymentDto> paymentDtoResponse = service.getById(1l);

        assertEquals(new ResponseEntity<>(HttpStatus.NOT_FOUND).getStatusCode(), paymentDtoResponse.getStatusCode());
    }

    @Test
    void ShouldReturnOneStringWithTheTypeBearerPlusToken() {
        TokenDto dto = createTokenDto();

        String bearerToken = service.getBearerToken(dto);
        assertEquals("Bearer sadsads-adas.a4q55qd5", bearerToken);
    }

    @Test
    void ShouldValidateTokenAndReturnOrderpaymentForm() {
        OrderDto orderDto = createOrderDto();
        String bearerToken = "Bearer sadsads-adas.a4q55qd5";
        TokenDto tokenDto = createTokenDto();

        Mockito.when(pbBankClientMock.approvePayment(orderDto, bearerToken)).thenReturn(new PaymentForm());
        PaymentForm paymentForm = service.callGateway(orderDto, tokenDto);

        assertNotEquals(null, paymentForm);
    }

    @Test
    void shouldAprovePayment() {
        PaymentForm paymentForm = createPaymentForm();
        service.registerPayment(paymentForm);

        assertNotEquals(null, paymentForm);
    }



    //Mock Util methods

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

    public List<PaymentEntity> createPaymentList() {
        List<PaymentEntity> paymentEntityList = new ArrayList<>();

        PaymentEntity payment1 = new PaymentEntity();
        PaymentEntity payment2 = new PaymentEntity();

        paymentEntityList.add(payment1);
        paymentEntityList.add(payment2);

        return paymentEntityList;
    }

    public TokenDto createTokenDto() {
        TokenDto tokenDto = new TokenDto("sadsads-adas.a4q55qd5", "Bearer", 180);
        return tokenDto;
    }

    public ClientAuthenticationForm createClientForm() {
        ClientAuthenticationForm authenticationForm = new ClientAuthenticationForm(
                "4516asd58","sdadsa56d1a8a.dsf1s3df1");
        return authenticationForm;
    }

    public OrderDto createOrderDto() {
        OrderDto orderDto = new OrderDto("aada",new CustomerDto(DocumentType.CPF, "12354984631"), PaymentType.CREDIT_CARD, CurrencyType.BRL, BigDecimal.valueOf(300), new CardDto( "13165464", "sdasdas", "aadsasda", Brand.MASTERCARD, "sdadsadsa", "saasda"));
        return orderDto;
    }

    public PaymentForm createPaymentForm() {
        PaymentForm paymentForm = new PaymentForm("1231","213123332", BigDecimal.valueOf(100), "sadada",PaymentStatus.APPROVED,"2022", new AuthorizationForm("asdasdas"));
        return paymentForm;
    }

}