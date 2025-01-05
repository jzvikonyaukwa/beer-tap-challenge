package com.rviewer.skeletons.domain.responses;

import com.rviewer.skeletons.infrastructure.persistence.Status;

import java.time.LocalDateTime;

public record DispenserStatusRequest(Status status, LocalDateTime updated_at) {
}
