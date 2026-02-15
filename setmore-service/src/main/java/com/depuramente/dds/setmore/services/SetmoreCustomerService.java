package com.depuramente.dds.setmore.services;

import com.depuramente.dds.common.dto.setmore.customer.CreateCustomerRequest;
import com.depuramente.dds.common.dto.setmore.customer.CreateCustomerResponse;
import com.depuramente.dds.common.dto.setmore.customer.CustomerDto;
import com.depuramente.dds.common.dto.setmore.customer.GetCustomerResponse;
import jakarta.annotation.Nullable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.depuramente.dds.common.constants.setmore.SetmoreConstants.*;

@Service
public class SetmoreCustomerService {
    private final SetmoreClient client;

    public SetmoreCustomerService(SetmoreClient client) {
        this.client = client;
    }

    public Mono<CustomerDto> createCustomer(CreateCustomerRequest request) {
        return client.post(SETMORE_CREATE_CUSTOMER,
                        request,
                        CreateCustomerResponse.class)
                .map(res -> res.data().customer());
    }

    public Mono<List<CustomerDto>> getCustomers(String firstName,
                                                @Nullable String email,
                                                @Nullable String phone) {
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put(FIRSTNAME, firstName);
        if (email != null) {
            queryParams.put(EMAIL, email);
        }
        if (phone != null) {
            queryParams.put(PHONE, phone);
        }
        return client.get(SETMORE_CUSTOMER,
                        GetCustomerResponse.class,
                        null, queryParams)
                .map(r -> r.data().customer());
    }
}

