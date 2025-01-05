package com.rviewer.skeletons.infrastructure.persistence;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class DispenserAction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    UUID id;
    @Column
    LocalDateTime openAt;
    @Column()
    LocalDateTime closeAt;
}
