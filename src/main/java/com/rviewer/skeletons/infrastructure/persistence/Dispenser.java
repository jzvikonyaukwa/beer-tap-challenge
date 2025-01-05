package com.rviewer.skeletons.infrastructure.persistence;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Dispenser {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;


    @Column(nullable = false)
    private double flowVolume;

    @Column
    @Builder.Default
    @Enumerated(EnumType.STRING)
    private Status status=Status.CLOSE;

    @Column
    @Builder.Default
    private double totalSpent=0.0;
    @Builder.Default
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<DispenserAction> dispenserActions = new ArrayList<>();

    public DispenserAction  createAction(){
        var action = new DispenserAction();
        this.dispenserActions.add(action);
        return action;
    }


}
