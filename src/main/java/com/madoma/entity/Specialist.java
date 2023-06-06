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
public class Specialist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Boolean free;

    @Enumerated(EnumType.STRING)
    private Category category;

    @OneToMany(mappedBy = "specialist",cascade = CascadeType.REMOVE)
    private Set<Day> days;
}
