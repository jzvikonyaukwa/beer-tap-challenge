package com.rviewer.skeletons.domain.responses;




import lombok.Builder;

import java.util.List;


@Builder

public record DispenserResponse (Double amount, List<DispenserActionResponse> usages){
}
