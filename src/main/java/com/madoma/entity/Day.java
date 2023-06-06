package com.madoma.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
public class Day {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String day;
    private Boolean free;

    @ManyToOne
    @JoinColumn(name = "specialist_id",nullable = false)
    private Specialist specialist;

    @OneToMany(mappedBy = "day",cascade = CascadeType.REMOVE)
    private Set<Time> times;
}
