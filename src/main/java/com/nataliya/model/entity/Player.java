package com.nataliya.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Players")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Player {

    @Id
    @Column(name = "Id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @EqualsAndHashCode.Include
    @Column(name = "Name", unique = true, nullable = false)
    private String name;

    public Player(String name){
        this.name = name;
    }
}
