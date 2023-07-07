package com.quid.innteci.manage.task.dto;

import enums.StatusTaskEnum;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import javax.validation.constraints.NotEmpty;

/**
 * This class represents the data transfer object (DTO) for a task.
 * It contains the fields representing the task's ID, title, description, and status.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskDto {

    /**
     * The ID of the task.
     */
    private Long id;

    /**
     * The title of the task.
     */
    @NotEmpty(message = "Title should not be empty")
    private String title;

    /**
     * The description of the task.
     */
    @NotEmpty(message = "Description should not be empty")
    private String description;

    /**
     * The status of the task.
     */
    private StatusTaskEnum status;

}
