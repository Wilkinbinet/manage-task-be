package com.quid.innteci.manage.task.dto;

import enums.StatusTaskEnum;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskDto {
    private Long id;

    @NotEmpty(message = "status should not be empty")
    private String title;

    @NotEmpty(message = "status should not be empty")
    private String description;

    private StatusTaskEnum status;

}