package com.depuramente.dds.setmore.services;

import com.depuramente.dds.common.dto.setmore.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.depuramente.dds.common.constants.setmore.SetmoreConstants.*;

@Service
public class SetmoreServicesService {
    private final SetmoreClient client;
    private static final Logger log = LoggerFactory.getLogger(SetmoreServicesService.class);

    public SetmoreServicesService(SetmoreClient client) {
        this.client = client;
    }

    // Fetch all services [GET]
    public Mono<List<ServiceDto>> fetchAllServices() {
        return client.get(SETMORE_SERVICES,
                        ServicesResponse.class,
                        null, null)
                .map(ServicesResponse::data)
                .map(ServicesData::services);
    }

    // Fetch Service Categories [GET]
    public Mono<List<ServiceCategoryDto>> fetchServiceCategories() {
        return client.get(SETMORE_SERVICES_CATEGORIES,
                        ServiceCategoriesResponse.class,
                        null, null)
                .map(ServiceCategoriesResponse::data)
                .map(ServiceCategoriesData::serviceCategories);
    }

    // Fetch Service by Category Key [GET]
    public Mono<List<ServiceDto>> fetchServiceByCategoryKey(String categoryKey) {
        Map<String, String> pathVariables = new HashMap<>();
        pathVariables.put(CATEGORY_KEY, categoryKey);
        return client.get(
                        SETMORE_SERVICES_CATEGORY_KEY,
                        ServicesResponse.class,
                        pathVariables, null)
                .map(ServicesResponse::data)
                .map(ServicesData::services);
    }
}
