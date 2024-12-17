package io.github.ndimovt.WaterTempApp.controller;

import io.github.ndimovt.WaterTempApp.model.Water;
import io.github.ndimovt.WaterTempApp.service.WaterService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Validated
public class WaterController {
    private final WaterService waterService;

    public WaterController(WaterService waterService) {
        this.waterService = waterService;
    }
    @GetMapping("/{town}")
    public List<Water> getByTown(@Validated @PathVariable String town){
        return waterService.searchByTown(town);
    }
}
