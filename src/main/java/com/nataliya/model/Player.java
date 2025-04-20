package com.nataliya.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Players")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Player {

    @Id
    @Column(name = "Id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "Name", unique = true, nullable = false)
    private String name;

    public Player(String name){
        this.name = name;
    }
}
