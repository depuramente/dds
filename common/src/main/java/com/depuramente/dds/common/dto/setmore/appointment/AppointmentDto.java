package com.depuramente.dds.common.dto.setmore.appointment;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AppointmentDto(String key,
                             @JsonProperty("start_time") String startTime,
                             @JsonProperty("end_time") String endTime,
                             Integer duration,
                             @JsonProperty("staff_key") String staffKey,
                             @JsonProperty("service_key") String serviceKey,
                             @JsonProperty("customer_key") String customerKey,
                             AppointmentCustomer customer,
                             Integer cost,
                             String currency,
                             String comment,
                             String label) {
}
