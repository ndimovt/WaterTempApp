package io.github.ndimovt.WaterTempApp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Entity
@Table(name = "water")
@AllArgsConstructor
@NoArgsConstructor
public class Water {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "town", nullable = false, length = 60)
    private String town;
    @Column(name = "date", updatable = false, nullable = false)
    private Date date;
    @Column(name = "temperature", nullable = false)
    private double temperature;
}
