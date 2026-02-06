package com.depuramente.dds.common.dto.setmore.appointment;

import java.util.List;

public record FetchAppointmentsData(String cursor,
                                    List<AppointmentDto> appointments) {
}
