package br.com.compass.paymentservicepb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class PaymentServicePbApplication {

	public static void main(String[] args) {
		SpringApplication.run(PaymentServicePbApplication.class, args);
	}

}
