package com.depuramente.dds.setmore.services;

import com.depuramente.dds.common.dto.setmore.slot.SlotsRequest;
import com.depuramente.dds.common.dto.setmore.slot.SlotsResponse;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import static com.depuramente.dds.common.constants.setmore.SetmoreConstants.SETMORE_SLOTS;

@Service
public class SetmoreSlotsService {
    private final SetmoreClient client;

    public SetmoreSlotsService(SetmoreClient client) {
        this.client = client;
    }

    public Mono<SlotsResponse> getAvailableSlots(SlotsRequest request) {
        return client.post(SETMORE_SLOTS,
                request,
                SlotsResponse.class);
    }

}
