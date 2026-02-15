package com.depuramente.dds.common.dto.setmore.service;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record ServiceCategoriesData(@JsonProperty("service_categories") List<ServiceCategoryDto> serviceCategories) {
}
