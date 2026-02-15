package com.depuramente.dds.common.dto.setmore.appointment;

public record CreateAppointmentResponse(boolean response, int code, String msg, CreateAppointmentData data) {
}
