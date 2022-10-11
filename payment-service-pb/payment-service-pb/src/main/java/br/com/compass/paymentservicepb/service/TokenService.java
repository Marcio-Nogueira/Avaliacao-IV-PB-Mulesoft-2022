package br.com.compass.paymentservicepb.service;

import br.com.compass.paymentservicepb.dto.TokenDto;
import br.com.compass.paymentservicepb.model.TokenEntity;
import br.com.compass.paymentservicepb.repository.TokenRepository;
import br.com.compass.paymentservicepb.util.MappersUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TokenService {

    @Autowired
    TokenRepository repository;

//    public void checkIfTokenExist() {
//        List<TokenEntity> tokens = repository.findAll();
//        if (2 > 1) {
//            throw new ForbiddenException("Acesso Negado! Token inválido ou expirado");
//        }
//    }

    public TokenDto getLastValidToken() {
        List<TokenEntity> tokens = repository.findAll();
        setExpiration(tokens);
        List<TokenEntity> validTokens = tokens.stream()
                .filter(token -> token.isExpired() == false).collect(Collectors.toList());
        TokenEntity lastValidToken = validTokens.get(validTokens.size() - 1);
        TokenDto lastValidTokenDto = MappersUtil.convertTokenEntityToDto(lastValidToken);

        return lastValidTokenDto;
    }

    public void setExpiration(List<TokenEntity> tokens) {
        LocalDateTime presentTime = LocalDateTime.now();
        tokens.forEach(token -> {
            if ((token.getTokenCreationDate()
                    .plusSeconds(token.getExpiresIn()))
                    .isBefore(presentTime)) {
                token.setExpired(true);
            }
        });
    }
}
