package com.depuramente.dds.setmore.services;

import com.depuramente.dds.common.dto.setmore.appointment.AppointmentDto;
import com.depuramente.dds.common.dto.setmore.appointment.CreateAppointmentRequest;
import com.depuramente.dds.common.dto.setmore.appointment.CreateAppointmentResponse;
import com.depuramente.dds.common.dto.setmore.appointment.FetchAppointmentsResponse;
import jakarta.annotation.Nullable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.depuramente.dds.common.constants.setmore.SetmoreConstants.*;

@Service
public class SetmoreAppointmentService {
    private final SetmoreClient client;

    public SetmoreAppointmentService(SetmoreClient client) {
        this.client = client;
    }

    public Mono<CreateAppointmentResponse> createAppointment(CreateAppointmentRequest request) {
        return client.post(
                SETMORE_CREATE_APPOINTMENT,
                request,
                CreateAppointmentResponse.class);
    }

    public Mono<AppointmentDto> updateAppointmentLabel(String appointmentKey, String label) {
        Map<String, String> queryParams = Map.of("label", label);
        Map<String, String> pathVariables = Map.of("appointmentKey", appointmentKey);
        return client.put(SETMORE_UPDATE_APPOINTMENT_LABEL, CreateAppointmentResponse.class, pathVariables, queryParams)
                .map(r -> r.data().appointment());
    }

    public Mono<FetchAppointmentsResponse> fetchAppointmentsPage(
            String startDate, String endDate,
            @Nullable String staffKey,
            boolean customerDetails,
            @Nullable String cursor) {
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put(START_DATE, startDate);
        queryParams.put(END_DATE, endDate);
        queryParams.put(CUSTOMER_DETAILS, String.valueOf(customerDetails));
        if (staffKey != null) {
            queryParams.put(STAFF_KEY, staffKey);
        }

        if (cursor != null) {
            queryParams.put(CURSOR, cursor);
        }
        return client.get(SETMORE_API_PATH + APPOINTMENTS,
                FetchAppointmentsResponse.class,
                null,
                queryParams);
    }

    public Flux<AppointmentDto> allAppointmentsFlux(
            String startDate, String endDate,
            @Nullable String staffKey,
            boolean customerDetails) {

        return fetchAppointmentsPage(startDate, endDate, staffKey, customerDetails, null)
                .expand(resp -> {
                    String cursor = resp.data().cursor();
                    if (cursor == null || cursor.isBlank()) return Mono.empty();
                    return fetchAppointmentsPage(startDate, endDate, staffKey, customerDetails, cursor);
                })
                .flatMapIterable(resp -> resp.data().appointments());
    }

    public Mono<List<AppointmentDto>> fetchAllAppointments(
            String startDate, String endDate,
            @Nullable String staffKey,
            boolean customerDetails) {

        return fetchAppointmentsPage(startDate, endDate, staffKey, customerDetails, null)
                .expand(resp -> {
                    String cursor = resp.data().cursor();
                    if (cursor == null || cursor.isBlank()) return Mono.empty();
                    return fetchAppointmentsPage(startDate, endDate, staffKey, customerDetails, cursor);
                })
                .map(resp -> resp.data().appointments())
                .collectList()
                .map(listOfLists -> listOfLists.stream()
                        .flatMap(List::stream)
                        .toList());
    }
}
