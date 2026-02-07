package com.depuramente.dds.setmore.services;

import com.depuramente.dds.setmore.auth.SetmoreAuthService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;
import reactor.core.publisher.Mono;

import java.util.function.Consumer;

import static com.depuramente.dds.common.constants.setmore.SetmoreConstants.BEARER;

@Component
public class SetmoreClient {
    private final WebClient client;
    private final SetmoreAuthService authService;

    public SetmoreClient(@Qualifier("setmoreWebClient") WebClient client,
                         SetmoreAuthService authService) {
        this.client = client;
        this.authService = authService;
    }

    public <T> Mono<T> get(String path, Class<T> bodyType, Consumer<UriBuilder> uriCustomizer) {
        return authService.getAccessToken()
                .flatMap(token -> client.get()
                        .uri(uriBuilder -> {
                            uriBuilder.path(path);
                            if (uriCustomizer != null) uriCustomizer.accept(uriBuilder);
                            return uriBuilder.build();
                        })
                        .header(HttpHeaders.AUTHORIZATION, BEARER + token)
                        .retrieve()
                        .bodyToMono(bodyType));
    }

    public <T> Mono<T> post(String path, Object body, Class<T> bodyType) {
        return authService.getAccessToken()
                .flatMap(token -> client.post()
                        .uri(path)
                        .header(HttpHeaders.AUTHORIZATION, BEARER + token)
                        .bodyValue(body)
                        .retrieve()
                        .bodyToMono(bodyType));
    }

    public <T> Mono<T> put(String path, Class<T> bodyType) {
        return authService.getAccessToken()
                .flatMap(token -> client.put()
                        .uri(path)
                        .header(HttpHeaders.AUTHORIZATION, BEARER + token)
                        .retrieve()
                        .bodyToMono(bodyType));
    }
}
