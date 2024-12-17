package io.github.ndimovt.WaterTempApp.repository;

import io.github.ndimovt.WaterTempApp.model.Water;
import io.github.ndimovt.WaterTempApp.towns.Town;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;
@Repository
public interface WaterRepository extends JpaRepository<Water, Long> {
    @Query(value = """
            SELECT * FROM water
            WHERE water.town = ?
            ORDER BY water.date
            """, nativeQuery = true)
    List<Water> findByTown(Town town);
    @Query(value = """
            SELECT * FROM water
            WHERE water.date = ?
                AND water.town = ?
            ORDER BY water.date
            """, nativeQuery = true)
    List<Water> findByDateAndTown(Date date, Town town);
    @Query(value = """
            SELECT *, 
                EXTRACT(YEAR FROM water.date) AS year
            FROM water
            WHERE 
                water.date = ?
                AND water.town = ?
            ORDER BY water.date           
            """, nativeQuery = true)
    Optional<Double> findByYearAndTown(Date date, Town town);
    @Query(value = """
            INSERT INTO water(id, town, date, temperature)
            VALUES (:id, :town, :date, :temperature)
            ON CONFLICT(:town, :date, :temperature)
            DO NOTHING
            """, nativeQuery = true)
    int insertMultipleRecords(@Param("id") long id, @Param("town") String town, @Param("date") Date date, @Param("temperature") double temperature);
    @Query(value = """
            INSERT INTO water(id, town, date, temperature)
            VALUES (:id, :town, :date, :temperature)
            ON CONFLICT(:town, :date, :temperature)
            DO NOTHING
            """, nativeQuery = true)
    void insertSingleRecord(@Param("id") long id, @Param("town") String town, @Param("date") Date date, @Param("temperature") double temperature);



}
