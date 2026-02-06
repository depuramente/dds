package com.depuramente.dds.common.dto.setmore.token;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Token(@JsonProperty("access_token") String accessToken,
                    @JsonProperty("token_type") String tokenType,
                    @JsonProperty("expires_in") long expiresIn,
                    @JsonProperty("user_id") String userId
) {
}
