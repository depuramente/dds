package com.depuramente.dds.setmore.services;

import com.depuramente.dds.common.dto.setmore.staff.StaffDto;
import com.depuramente.dds.common.dto.setmore.staff.StaffResponse;
import jakarta.annotation.Nullable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.depuramente.dds.common.constants.setmore.SetmoreConstants.CURSOR;
import static com.depuramente.dds.common.constants.setmore.SetmoreConstants.SETMORE_STAFF_PATH;

@Service
public class SetmoreStaffService {
    private final SetmoreClient client;

    public SetmoreStaffService(SetmoreClient client) {
        this.client = client;
    }

    public Mono<StaffResponse> fetchStaffPage(@Nullable String cursor) {
        Map<String, String> queryParams = new HashMap<>();
        if (cursor != null) {
            queryParams.put(CURSOR, cursor);
        }
        return client.get(SETMORE_STAFF_PATH,
                StaffResponse.class,
                null, queryParams);
    }

    public Mono<List<StaffDto>> fetchAllStaff() {
        return fetchStaffPage(null)
                .expand(resp -> {
                    String cursor = resp.data().cursor();
                    if (cursor == null || cursor.isBlank()) return Mono.empty();
                    return fetchStaffPage(cursor);
                })
                .map(resp -> resp.data().staffs())
                .collectList()
                .map(listOfLists -> listOfLists.stream()
                        .flatMap(List::stream)
                        .toList());
    }

}
