package com.depuramente.dds.common.dto.setmore.service;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record ServiceCategoryDto(String key,
                                 @JsonProperty("categoryName") String categoryName,
                                 @JsonProperty("serviceIdList") List<String> serviceIdList) {
}
