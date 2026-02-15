package com.depuramente.dds.common.dto.setmore.service;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record ServiceCategoryDto(String key,
                                 String categoryName,
                                 String companyId,
                                 int categoryOrder,
                                 List<String> serviceIdList,
                                 Long createdDate,
                                 boolean deleteFlag,
                                 String isNew,
                                 String brandId) {
}