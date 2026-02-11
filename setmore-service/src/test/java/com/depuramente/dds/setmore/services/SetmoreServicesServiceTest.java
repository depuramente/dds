package com.depuramente.dds.setmore.services;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Tag("EndToEnd")
class SetmoreServicesServiceTest {
    @Autowired
    SetmoreServicesService service;

    @Test
    void fetchAllServices() {
        var request = service.fetchAllServices();
        StepVerifier.create(request)
                .assertNext(res -> {
                    res.forEach(s -> {
                        assertFalse(s.key().isEmpty());
                        assertTrue(s.key().contains("-"));
                        assertTrue(s.duration() > 0);
                        assertTrue(s.cost() >= 0);
                        assertFalse(s.currency().isEmpty());
                        assertFalse(s.description().isEmpty());
                        assertFalse(s.serviceName().isEmpty());
                        assertTrue(s.bufferDuration() > 0);
                        assertTrue(s.staffKeys().stream().allMatch(staffId -> staffId.contains("-")));
                    });
                }).verifyComplete();
    }

    @Test
    void fetchServiceCategories() {
        var request = service.fetchServiceCategories();
        StepVerifier.create(request).assertNext(res -> {
            assertFalse(res.isEmpty());
            res.forEach(c -> {
                assertFalse(c.key().isEmpty());
                assertTrue(c.key().contains("-"));
                assertFalse(c.categoryName().isEmpty());
                assertFalse(c.companyId().isEmpty());
                assertTrue(c.companyId().contains("-"));
                assertTrue(c.categoryOrder() >= 0);
                assertFalse(c.serviceIdList().isEmpty());
                assertTrue(c.serviceIdList().stream().allMatch(serviceId -> serviceId.contains("-")));
                assertFalse(c.brandId().isEmpty());
                assertTrue(c.brandId().contains("-"));
                assertTrue(c.isNew().equals("true") || c.isNew().equals("false"));
            });
        }).verifyComplete();
    }

    @Test
    void fetchServiceByCategoryKey() {
        String categoryKey = "0047468a-b963-44fd-bc91-a39fb53bc664";
        var request = service.fetchServiceByCategoryKey(categoryKey);
        StepVerifier.create(request).assertNext(res -> {
            res.forEach(s -> {
                assertFalse(s.key().isEmpty());
                assertTrue(s.key().contains("-"));
                assertTrue(s.duration() > 0);
                assertTrue(s.cost() >= 0);
                assertFalse(s.currency().isEmpty());
                assertFalse(s.description().isEmpty());
                assertFalse(s.serviceName().isEmpty());
                assertTrue(s.bufferDuration() > 0);
                assertTrue(s.staffKeys().stream().allMatch(staffId -> staffId.contains("-")));
            });
        }).verifyComplete();
    }
}