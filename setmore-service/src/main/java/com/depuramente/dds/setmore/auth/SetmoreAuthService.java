package com.depuramente.dds.setmore.auth;


import com.depuramente.dds.common.dto.setmore.token.TokenResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static com.depuramente.dds.common.constants.setmore.SetmoreConstants.REFRESH_TOKEN;
import static com.depuramente.dds.common.constants.setmore.SetmoreConstants.SETMORE_TOKEN_PATH;

@Service
public class SetmoreAuthService {
    private final WebClient client;
    private final String refreshToken;


    public SetmoreAuthService(
            @Qualifier("setmoreWebClient") WebClient client,
            @Value("${setmore.refresh-token}") String refreshToken) {
        this.client = client;
        this.refreshToken = refreshToken;
    }

    public Mono<String> getAccessToken() {
        return client.get()
                .uri(uriBuilder -> uriBuilder
                        .path(SETMORE_TOKEN_PATH)
                        .queryParam(REFRESH_TOKEN, refreshToken)
                        .build())
                .retrieve()
                .bodyToMono(TokenResponse.class)
                .map(res -> res.data().token().accessToken());
    }

}
