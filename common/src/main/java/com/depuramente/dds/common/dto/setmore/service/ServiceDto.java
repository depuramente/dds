package com.depuramente.dds.common.dto.setmore.service;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record ServiceDto(
        String key,
        @JsonProperty("service_name") String serviceName,
        @JsonProperty("staff_keys") List<String> staffKeys,
        Integer duration,
        @JsonProperty("buffer_duration") Integer bufferDuration,
        Integer cost,
        String currency,
        @JsonProperty("image_url") String imageUrl,
        String description
) {}
