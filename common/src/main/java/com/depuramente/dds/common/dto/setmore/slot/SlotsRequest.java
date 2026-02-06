package com.depuramente.dds.common.dto.setmore.slot;

import com.fasterxml.jackson.annotation.JsonProperty;

public record SlotsRequest(@JsonProperty("staff_key") String staffKey,
                           @JsonProperty("service_key") String serviceKey,
                           @JsonProperty("selected_date") String selectedDate, // "DD/MM/YYYY"
                           @JsonProperty("off_hours") Boolean offHours,
                           @JsonProperty("double_booking") Boolean doubleBooking,
                           @JsonProperty("slot_limit") Integer slotLimit,
                           String timezone
) {
}
