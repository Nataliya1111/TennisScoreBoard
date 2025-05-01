package com.nataliya.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Check;

@Entity
@Table(name = "Matches")
@Check(constraints = "player1_id<>player2_id")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Match {

    @Id
    @Column(name = "Id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ToString.Include
    private Long id;

    @ManyToOne
    @JoinColumn(name = "player1_id", referencedColumnName = "id")
    @EqualsAndHashCode.Include
    private Player player1;

    @ManyToOne
    @JoinColumn(name = "player2_id", referencedColumnName = "id")
    @EqualsAndHashCode.Include
    private Player player2;

    @ManyToOne
    @JoinColumn(name = "winner_id", referencedColumnName = "id")
    private Player winner;


}
