package br.com.compass.paymentservicepb.repository;

import br.com.compass.paymentservicepb.model.TokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<TokenEntity, Long> {

}
