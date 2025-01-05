package com.rviewer.skeletons.domain.responses;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record DispenserActionResponse(LocalDateTime opened_at,LocalDateTime closed_at,Double flow_volume,Double total_spent) {
}
