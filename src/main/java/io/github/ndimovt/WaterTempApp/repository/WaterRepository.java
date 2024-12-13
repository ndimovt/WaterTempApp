package io.github.ndimovt.WaterTempApp.repository;

import io.github.ndimovt.WaterTempApp.model.Water;
import io.github.ndimovt.WaterTempApp.towns.Town;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

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
    List<Water> findByDateAndTown(Date date, String town);
    @Query(value = """
            SELECT *, 
                EXTRACT(YEAR FROM water.date) AS year
            FROM water
            WHERE 
                water.date = ?
                AND water.town = ?
            ORDER BY water.date           
            """, nativeQuery = true)
    Optional<Double> findByYearAndTown(Date date, String town);
    int readExcelFile(MultipartFile file);
    void insertRecord(Water water);



}
