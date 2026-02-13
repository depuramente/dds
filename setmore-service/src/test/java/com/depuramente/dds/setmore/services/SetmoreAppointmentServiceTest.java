package com.depuramente.dds.setmore.services;

import com.depuramente.dds.common.dto.setmore.appointment.AppointmentDto;
import com.depuramente.dds.common.dto.setmore.appointment.CreateAppointmentRequest;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.Comparator;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@Tag("E2E")
class SetmoreAppointmentServiceTest {
    @Value("${setmore.staffKey}")
    private String staffKey;
    @Value("${setmore.service.MANG}")
    private String serviceMANG;
    @Value("${setmore.service.M.0}")
    private String serviceM0;
    @Value("${setmore.service.M.1}")
    private String serviceM1;
    @Value("${setmore.service.P.1}")
    private String serviceP1;
    @Value("${setmore.service.P.2}")
    private String serviceP2;
    @Value("${setmore.service.P.3}")
    private String serviceP3;
    @Value("${setmore.service.3M.1}")
    private String service3M1;
    @Value("${setmore.service.3M.2}")
    private String service3M2;
    @Value("${setmore.service.3M.3}")
    private String service3M3;

    @Autowired
    SetmoreAppointmentService service;
    private final SetmoreAppointmentService mockService = mock(SetmoreAppointmentService.class);

    @Test
    void createAppointment() {
        // Date format for start_time and end_time : yyyy-MM-ddTHH:mm
        String customerKey = "74672edd-bb58-4efd-a42d-c1ad403fb1e1";
        var serviceKey = serviceM1;
        var startTime = "2026-02-08T20:00Z";
        var endTime = "2026-02-08T20:30Z";
        var comment = "Test appointment";
        var label = "No-show";
        var appointmentRequest = new CreateAppointmentRequest(staffKey, serviceKey, customerKey, startTime, endTime, comment, label);

        var request = service.createAppointment(appointmentRequest);
        StepVerifier.create(request)
                .assertNext(res -> {
                    assertTrue(res.response());
                    assertFalse(res.msg().isEmpty());
                    assertTrue(res.code() >= 0);
                    assertNotNull(res.data());

                    var appointment = res.data().appointment();
                    assertNotNull(appointment);
                    assertEquals(startTime, appointment.startTime());
                    assertEquals(endTime, appointment.endTime());
                    assertTrue(appointment.duration() > 0);
                    assertTrue(appointment.cost() >= 0);
                    assertEquals(staffKey, appointment.staffKey());
                    assertEquals(customerKey, appointment.customerKey());
                    assertEquals(serviceKey, appointment.serviceKey());
                    assertEquals(comment, appointment.comment());
                    assertEquals(label, appointment.label());
                }).verifyComplete();
    }

    @Test
    void updateAppointmentLabel() {
        //      Format for startDate and endDate dd-mm-yyyy
        String startDate = "15-02-2026";
        String endDate = "15-02-2026";
        AtomicReference<String> targetAppointmentKey = new AtomicReference<>("");
        String tak = "";
        AtomicReference<AppointmentDto> minApp = new AtomicReference<>();
        AtomicReference<AppointmentDto> maxApp = new AtomicReference<>();

        var appointments = service.fetchAllAppointments(startDate, endDate, null, false);

        var targetAppointment = Objects.requireNonNull(appointments
                        .flatMapMany(Flux::fromIterable)
                        .collectList()
                        .block())
                .stream()
                .min(Comparator.comparing(AppointmentDto::startTime))
                .orElseThrow();
        String key = targetAppointment.key();
        String currentLabel = targetAppointment.label();
        String newLabel = "No-show";
        var update = service.updateAppointmentLabel(key, newLabel);
        StepVerifier.create(update)
                .assertNext(res -> {
                    assertEquals(key, res.key());
                    assertEquals(newLabel, res.label());
                    assertNotEquals(currentLabel, res.label());
                }).verifyComplete();
    }

    @Test
    void fetchAppointmentsPage() {
        String startDate = "15-02-2024";
        String endDate = "15-02-2025";
        boolean customerDetails = false;

        SetmoreAppointmentService spy = spy(service);
        var result = spy.fetchAppointmentsPage(startDate, endDate, null, customerDetails, null);
        verify(spy, atLeast(1)).fetchAppointmentsPage(startDate, endDate, null, customerDetails, null);
    }

    @Test
    void fetchAllAppointments() {
//      Format for startDate and endDate dd-mm-yyyy
        String startDate = "08-02-2026";
        String endDate = "08-02-2026";
        boolean customerDetails = true;
        var result = service.fetchAllAppointments(startDate, endDate, null, customerDetails);

        StepVerifier.create(result)
                .assertNext(res -> {
                    assertFalse(res.isEmpty());
                    assertFalse(res.stream().map(AppointmentDto::customer).toList().isEmpty());
                })
                .verifyComplete();
    }
}