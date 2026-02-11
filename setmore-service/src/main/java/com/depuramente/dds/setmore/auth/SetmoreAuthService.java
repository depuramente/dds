package com.depuramente.dds.setmore.auth;


import com.depuramente.dds.common.dto.setmore.token.Token;
import com.depuramente.dds.common.dto.setmore.token.TokenResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.concurrent.atomic.AtomicReference;

import static com.depuramente.dds.common.constants.setmore.SetmoreConstants.REFRESH_TOKEN;
import static com.depuramente.dds.common.constants.setmore.SetmoreConstants.SETMORE_TOKEN_PATH;

@Service
public class SetmoreAuthService {

    private static final Logger log = LoggerFactory.getLogger(SetmoreAuthService.class);
    private final WebClient client;
    private final String refreshToken;
    private final AtomicReference<Token> cachedToken = new AtomicReference<>();
    private final Object refreshLock = new Object();


    public SetmoreAuthService(
            @Qualifier("setmoreWebClient") WebClient client,
            @Value("${setmore.refresh-token}") String refreshToken) {
        this.client = client;
        this.refreshToken = refreshToken;
    }

    public Mono<Token> requestNewToken() {
        return client.get()
                .uri(uriBuilder -> uriBuilder
                        .path(SETMORE_TOKEN_PATH)
                        .queryParam(REFRESH_TOKEN, refreshToken)
                        .build())
                .retrieve()
                .bodyToMono(TokenResponse.class)
                .map(res -> res.data().token());
    }


    public Mono<String> getAccessToken() {
        return getToken()
                .map(Token::accessToken);
    }

    public Mono<Token> getToken() {
        synchronized (refreshLock) {
            if (cachedToken.get() == null || cachedToken.get().isExpired()) {
                if (cachedToken.get() == null) {
                    log.warn("No token cached, fetching new one");
                }
                if (cachedToken.get() != null && cachedToken.get().isExpired()) {
                    log.warn("Cached token expired, refreshing");
                }
                cachedToken.set(requestNewToken().block());
                log.warn("Caching Token");
            }
            log.info("Returning cached token");
            return Mono.just(cachedToken.get());
        }
    }




    /* --------------------------------------- */

//    public String getAToken() {
//        if (cachedToken.get() != null || cachedToken.get().isExpired()) {
//            var newToken = requestNewToken().block();
//            cachedToken.set(requestNewToken().block());
//            return newToken;
//        } else {
//            return cachedToken.get().accessToken();
//        }
//    }
//
//    public Mono<String> getAccessToken() {
//        return Mono.defer(() -> {
//            Token token = cachedToken.get();
//
//            if (token != null && !token.isExpired()) {
//                return Mono.just(token.accessToken());
//            }
//
//            // Token missing or expired → refresh
//            return refreshTokenIfNeeded()
//                    .map(Token::accessToken);
//        });
//    }
//
//    private Mono<Token> refreshTokenIfNeeded() {
//        synchronized (refreshLock) {
//            Token current = cachedToken.get();
//
//            // Double-check inside the lock
//            if (current != null && !current.isExpired()) {
//                return Mono.just(current);
//            }
//
//            // Perform refresh
//            return requestNewToken()
//                    .doOnNext(cachedToken::set);
//        }
//    }





/*
    private SetmoreAuthService() {
    }

    // Cache the token in memory
    private final AtomicReference<Token> cachedToken = new AtomicReference<>();

    public SetmoreAuthService(
            @Qualifier("setmoreWebClient") WebClient client,
            @Value("${setmore.refresh-token}") String refreshToken) {
        this.client = client;
        this.refreshToken = refreshToken;

    }

    public boolean isExpired() {
        return expirationTime > System.currentTimeMillis();
    }

    public synchronized String getValidAccessToken() {
        if (isExpired()) {
            // Logic to refresh or fetch a new token
            System.out.println("Token expired. Refreshing...");
            getValidToken(); // A synchronized method to get a new token
        }
        return accessToken;
    }

    public synchronized SetmoreAuthService getInstance() {
        if (instance == null) {
            instance = new SetmoreAuthService(client, refreshToken);
        }
        return instance;
    }

    public Mono<String> getAccessToken() {
        return getValidToken()
                .map(Token::accessToken);
    }

    private Mono<Token> getValidToken() {
        Token current = cachedToken.get();

        if (current == null || current.isExpired()) {
            return refreshToken()
                    .doOnNext(cachedToken::set);
        }

        return Mono.just(current);
    }

    private Mono<Token> refreshToken() {
        return client.get()
                .uri(uriBuilder -> uriBuilder
                        .path(SETMORE_TOKEN_PATH)
                        .queryParam(REFRESH_TOKEN, refreshToken)
                        .build())
                .retrieve()
                .bodyToMono(TokenResponse.class)
                .map(res -> res.data().token());
    }

 */
}
