package com.depuramente.dds.common.dto.setmore.staff;

import com.fasterxml.jackson.annotation.JsonProperty;

public record StaffDto(
        String key,
        @JsonProperty("first_name") String firstName,
        @JsonProperty("last_name") String lastName,
        @JsonProperty("email_id") String emailId,
        @JsonProperty("country_code") String countryCode,
        @JsonProperty("work_phone") String workPhone,
        @JsonProperty("image_url") String imageUrl,
        String comment
) {}

