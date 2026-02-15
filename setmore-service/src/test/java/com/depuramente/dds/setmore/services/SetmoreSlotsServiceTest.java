package com.depuramente.dds.setmore.services;

import com.depuramente.dds.common.dto.setmore.slot.SlotsRequest;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Tag("E2E")
class SetmoreSlotsServiceTest {
    @Autowired
    SetmoreSlotsService service;

    @Value("${setmore.staffKey}")
    private String staffKey;

    @Value("${setmore.service.M.1}")
    private String serviceKey;

    @Test
    void getAvailableSlots() {
        int slotLimit = 60;
        boolean doubleBooking = false;
        boolean offHours = false;
        String selectedDate = "03/03/2026";
        SlotsRequest request = new SlotsRequest(
                staffKey,
                serviceKey,
                selectedDate,
                offHours,
                doubleBooking,
                slotLimit,
                "Europe/Rome"
        );
        var slotRequest = service.getAvailableSlots(request);
        StepVerifier.create(slotRequest)
                .assertNext(slot -> {
                    assertNotNull(slot.data());
                    if (!slot.data().slots().isEmpty()) {
                        slot.data().slots().forEach(s -> {
                            assertTrue(s.contains(":00") && (s.contains("PM") || s.contains("AM")), "Slot should contain AM or PM");
                        });
                    }
                }).verifyComplete();
    }
}