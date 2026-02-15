package com.depuramente.dds.common.dto.setmore.service;

public record ServiceCategoriesResponse(boolean response,
                                        String msg,
                                        int code,
                                        ServiceCategoriesData data) {
}
