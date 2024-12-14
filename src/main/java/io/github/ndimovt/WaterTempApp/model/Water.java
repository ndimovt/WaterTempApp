package io.github.ndimovt.WaterTempApp.model;

import io.github.ndimovt.WaterTempApp.towns.Town;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Objects;

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
    private Town town;
    @Column(name = "date", updatable = false, nullable = false)
    private Date date;
    @Column(name = "temperature", nullable = false)
    private double temperature;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Water water)) return false;
        return town == water.town && Objects.equals(date, water.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(town, date);
    }
}
