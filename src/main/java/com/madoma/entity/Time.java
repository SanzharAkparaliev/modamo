package com.madoma.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
public class Time {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String time;
    private Boolean free;

    @ManyToOne
    @JoinColumn(name = "day_id",nullable = false)
    private Day day;
}
