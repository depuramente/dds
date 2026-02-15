package com.depuramente.dds.common.dto.setmore.token;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;
import java.util.List;

public record Token(
        @JsonProperty("access_token") String accessToken,
        @JsonProperty("type") String type,
        @JsonProperty("token_type") String tokenType,
        @JsonProperty("expires") long expires,
        @JsonProperty("issued_to") String issuedTo,
        @JsonProperty("scopes") List<String> scopes,
        @JsonProperty("access_type") String accessType,
        @JsonProperty("user_id") String userId,
        @JsonProperty("project_id") String projectId,
        @JsonProperty("ok") boolean ok,
        @JsonProperty("expires_in") long expiresIn
) {
    public boolean isExpired() {
        return expiresAt().isBefore(Instant.now());
    }

    public Instant expiresAt() {
        return Instant.ofEpochMilli(expires);
    }
}

