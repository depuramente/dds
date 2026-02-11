package com.depuramente.dds.setmore.auth;

import com.depuramente.dds.common.dto.setmore.token.Token;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.test.StepVerifier;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Tag("Integration")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class SetmoreAuthServiceTest {
    @Autowired
    private SetmoreAuthService authService;

    @Value("${setmore.refresh-token}")
    private String refreshToken;

    @BeforeAll
    void checkEnv() {
        assertNotNull(refreshToken, "refresh token must be provided");
    }

    @Test
    @DisplayName("Successfully retrieves a new token")
    void happyCase() {
        var token = authService.getToken();
        StepVerifier.create(token).assertNext(t -> {
            assertFalse(t.isExpired());
            assertNotNull(t.accessToken());
            assertEquals("OFFLINE", t.accessType());
        });
    }

    @Test
    @DisplayName("Returns same token if not expired")
    void shouldReturnSameTokenIfNotExpired() throws InterruptedException {
        var token1 = authService.getToken().block();
        var token2 = authService.getToken().block();
        assertNotNull(token1);
        assertNotNull(token2);
        assertEquals(token1.accessToken(), token2.accessToken());
    }

    @Test
    @DisplayName("Validates token is expired")
    void shouldBeExpired() {
        long expireTime = Instant.now().toEpochMilli() - 10_000L;
        Token t = new Token("", "", "", expireTime, "", null, "", "", "", true, 0);
        assertTrue(t.isExpired(), "Token should be expired");
    }
}