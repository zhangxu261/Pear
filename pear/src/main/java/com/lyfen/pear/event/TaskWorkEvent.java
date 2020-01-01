package com.lyfen.pear.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TaskWorkEvent {

    private Long taskId;
    private Integer workTime;
}
