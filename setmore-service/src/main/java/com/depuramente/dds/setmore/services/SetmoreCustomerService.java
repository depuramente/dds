package com.depuramente.dds.setmore.services;

import com.depuramente.dds.common.dto.setmore.customer.CreateCustomerRequest;
import com.depuramente.dds.common.dto.setmore.customer.CreateCustomerResponse;
import com.depuramente.dds.common.dto.setmore.customer.CustomerDto;
import com.depuramente.dds.common.dto.setmore.customer.GetCustomerResponse;
import jakarta.annotation.Nullable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

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
        return client.get(SETMORE_CUSTOMER,
                        GetCustomerResponse.class,
                        uri -> {

                            uri.queryParam(FIRSTNAME, firstName);
                            if (email != null) uri.queryParam(EMAIL, email);
                            if (phone != null) uri.queryParam(PHONE, phone);
                        })
                .map(r -> r.data().customer());
    }
}

