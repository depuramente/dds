package com.depuramente.dds.common.dto.setmore.customer;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public record CreateCustomerRequest(@JsonProperty("first_name") String firstName,
                                    @JsonProperty("last_name") String lastName,
                                    @JsonProperty("email_id") String emailId,
                                    @JsonProperty("country_code") String countryCode,
                                    @JsonProperty("cell_phone") String cellPhone,
                                    @JsonProperty("work_phone") String workPhone,
                                    @JsonProperty("home_phone") String homePhone,
                                    String address,
                                    String city,
                                    String state,
                                    @JsonProperty("postal_code") String postalCode,
                                    @JsonProperty("image_url") String imageUrl,
                                    String comment,
                                    @JsonProperty("additional_fields") Map<String, Object> additionalFields
) {
}
