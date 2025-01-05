package com.rviewer.skeletons.domain.services.persistence;



import com.rviewer.skeletons.infrastructure.persistence.Dispenser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DispenserRepository extends JpaRepository<Dispenser, UUID> {
}
