package com.rviewer.skeletons.domain.responses;

import com.rviewer.skeletons.infrastructure.persistence.DispenserAction;

public interface DispenserActionDto {

    static DispenserActionResponse map(DispenserAction dispenserAction, double flowVolume, double totalSpent) {
        if (dispenserAction == null) return DispenserActionResponse.builder().build();
        return DispenserActionResponse.builder()
                .opened_at(dispenserAction.getOpenAt())
                .closed_at(dispenserAction.getCloseAt())
                .flow_volume(flowVolume)
                .total_spent(totalSpent)
                .build();
    }
}
