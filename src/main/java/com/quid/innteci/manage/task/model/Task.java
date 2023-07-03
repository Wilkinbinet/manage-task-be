package com.quid.innteci.manage.task.model;

import enums.StatusTaskEnum;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;

    @Enumerated(EnumType.STRING)
    private StatusTaskEnum status;

    @ManyToOne
    private User user;
}
