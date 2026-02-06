package com.depuramente.dds.common.dto.setmore.appointment;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CreateAppointmentRequest(@JsonProperty("staff_key") String staffKey,
                                       @JsonProperty("service_key") String serviceKey,
                                       @JsonProperty("customer_key") String customerKey,
                                       @JsonProperty("start_time") String startTime, // "yyyy-MM-dd'T'HH:mm'Z'"
                                       @JsonProperty("end_time") String endTime,
                                       String comment,
                                       String label) {
}
