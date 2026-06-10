package com.badminton.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "courts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Court {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String courtName;

    private String type;

    private Boolean available;

    @ManyToOne
    @JoinColumn(name = "cluster_id")
    private BadmintonCluster cluster;
}