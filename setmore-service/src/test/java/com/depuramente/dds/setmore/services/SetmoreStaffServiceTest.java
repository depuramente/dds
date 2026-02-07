package com.depuramente.dds.setmore.services;

import com.depuramente.dds.common.dto.setmore.staff.StaffDto;
import com.depuramente.dds.common.dto.setmore.staff.StaffResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SetmoreStaffServiceTest {

    @Autowired
    private SetmoreStaffService service;

    @Test
    @DisplayName("Null cursor returns valid staff list")
    void fetchStaffPage() {
        Mono<StaffResponse> result = service.fetchStaffPage(null);
        StepVerifier.create(result)
                .assertNext(res -> {
                    assertTrue(res.response());
                    assertNotNull(res.data());
                    assertFalse(res.data().staffs().isEmpty());
                    assertTrue(res.data().staffs().stream()
                            .anyMatch(s -> s.countryCode().equals("1")));
                })
                .verifyComplete();
    }

    @Test
    @DisplayName("One staff is in USA")
    void fetchAllStaff() {
        Mono<List<StaffDto>> result = service.fetchAllStaff();
        StepVerifier.create(result)
                .assertNext(res -> {
                    assertFalse(res.isEmpty());
                    assertTrue(res.stream()
                            .anyMatch(s -> s.countryCode().equals("1")));
                })
                .verifyComplete();
    }
}