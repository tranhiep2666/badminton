package com.badminton.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "badminton_clusters")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BadmintonCluster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String address;

    private String hotline;

    @ManyToOne
    @JoinColumn(name = "manager_id")
    private User manager;
}
