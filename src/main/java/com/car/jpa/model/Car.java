package com.car.jpa.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name="cars")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="name",length = 200)
    private String name;
    @Column(name="model",length = 200)
    private String model;
    @Column(name="year")
    private int year;
    @Column(name = "price")
    private int price;

    @JoinColumn(name = "country_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Country country;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Category> categories;
}
