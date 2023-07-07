package com.quid.innteci.manage.task.model;

import enums.StatusTaskEnum;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Task {

    /**
     * The ID of the task.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The title of the task.
     */
    private String title;

    /**
     * The description of the task.
     */
    private String description;

    /**
     * The status of the task.
     */
    @Enumerated(EnumType.STRING)
    private StatusTaskEnum status;

    /**
     * The user associated with the task.
     */
    @ManyToOne
    private User user;

}
