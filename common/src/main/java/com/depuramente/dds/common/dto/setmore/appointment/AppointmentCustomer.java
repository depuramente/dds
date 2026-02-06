package com.depuramente.dds.common.dto.setmore.appointment;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public record AppointmentCustomer(String key,
                                  @JsonProperty("first_name") String firstName,
                                  @JsonProperty("last_name") String lastName,
                                  @JsonProperty("email_id") String emailId,
                                  @JsonProperty("country_code") String countryCode,
                                  @JsonProperty("cell_phone") String cellPhone,
                                  @JsonProperty("image_url") String imageUrl,
                                  @JsonProperty("additional_fields") Map<String, Object> additionalFields) {
}
