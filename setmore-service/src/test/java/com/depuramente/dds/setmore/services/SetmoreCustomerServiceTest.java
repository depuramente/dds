package com.depuramente.dds.setmore.services;

import com.depuramente.dds.common.dto.setmore.customer.CreateCustomerRequest;
import com.depuramente.dds.common.dto.setmore.customer.CustomerDto;
import org.jspecify.annotations.NonNull;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Tag("E2E")
class SetmoreCustomerServiceTest {

    private static String firstname;
    private static String lastname;
    private static String emailId;
    private static String countryCode;
    private static String cellPhone;
    private static String workPhone;
    private static String homePhone;
    private static String address;
    private static String city;
    private static String state;
    private static String postalCode;
    private static String imageUrl;
    private static String comment;
    private static Map<String, Object> additionalFields;

    @Autowired
    SetmoreCustomerService service;

    @BeforeAll
    static void setup() {
        firstname = "Jane";
        lastname = "Doe Smith";
        emailId = "janedoe@mail.com";
        countryCode = "+1";
        cellPhone = "(123) 123-4567";
        workPhone = "(999) 345-6789";
        homePhone = "(888) 987-5432";
        address = "123 Fake St";
        city = "Fake City";
        state = "Fake State";
        postalCode = "Fake Zip";
        imageUrl = "Fake url";
        comment = "no comment";
        additionalFields = new HashMap<>();
        additionalFields.put("Instagram", "@janedoe");
    }

    @Test
    void createCustomer() {
        var request = buildCreateCustomerRequest();
        Mono<CustomerDto> newCustomer = service.createCustomer(request);

        StepVerifier.create(newCustomer).assertNext(res -> {
            assertEquals(firstname, res.firstName());
            assertEquals(lastname, res.lastName());
            assertEquals(emailId, res.emailId());
        });
    }

    @Test
    void getCustomers() {
        Mono<List<CustomerDto>> result = service.getCustomers(firstname, null, null);
        StepVerifier.create(result)
                .assertNext(res -> {
                    assertFalse(res.isEmpty());
                    assertTrue(res.stream()
                            .anyMatch(customer -> customer.emailId().equals(emailId)));
                })
                .verifyComplete();
    }

    private static @NonNull CreateCustomerRequest buildCreateCustomerRequest() {
        return new CreateCustomerRequest(firstname, lastname, emailId, countryCode, cellPhone,
                workPhone, homePhone, address, city, state, postalCode,
                imageUrl, comment, additionalFields);
    }
}