package com.rviewer.skeletons.domain.responses;

import com.rviewer.skeletons.infrastructure.persistence.Dispenser;

import java.util.List;

public interface DispenserDto {
    static DispenserResponse map(Dispenser dispenser, List<DispenserActionResponse> dispenserActionResponseList ) {
        if (dispenser == null) return DispenserResponse.builder().build();
        return DispenserResponse.builder()
                .amount(dispenser.getTotalSpent())
                .usages(dispenserActionResponseList)
                .build();
    }
}
