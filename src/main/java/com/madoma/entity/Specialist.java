package com.madoma.entity;


import lombok.*;

import javax.persistence.*;
import java.util.List;

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

    @ManyToOne
    private Category category;


    @OneToMany(mappedBy = "specialist",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Event> events;



}
