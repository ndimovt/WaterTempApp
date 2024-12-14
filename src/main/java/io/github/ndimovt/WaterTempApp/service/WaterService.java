package io.github.ndimovt.WaterTempApp.service;

import io.github.ndimovt.WaterTempApp.model.Water;
import io.github.ndimovt.WaterTempApp.repository.WaterRepository;
import io.github.ndimovt.WaterTempApp.towns.Town;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Service
public class WaterService {
    private final WaterRepository waterRepository;

    public WaterService(WaterRepository waterRepository) {
        this.waterRepository = waterRepository;
    }
    public List<Water> searchByTown(String town){
        return waterRepository.findByTown(Town.valueOf(town));
    }
    public List<Water> byDateAndTown(Date date, String town){
        return waterRepository.findByDateAndTown(date, Town.valueOf(town));
    }
    public double byYearAndTown(Date date, String town){
        return waterRepository.findByYearAndTown(date, Town.valueOf(town)).orElseThrow();
    }
    public void insert(Water water){
        long id = water.getId();
        String town = water.getTown().name();
        Date date = water.getDate();
        double temperature = water.getTemperature();
        waterRepository.insertSingleRecord(id, town, date, temperature);
    }
    public int insertRecords(MultipartFile file){
        int count = 0;
        try(InputStream is = file.getInputStream();
            Workbook workbook = WorkbookFactory.create(is)){
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> iterator = sheet.iterator();
            while(iterator.hasNext()){
                Row row = iterator.next();
                long id = Long.parseLong(row.getCell(0).toString());
                String town = row.getCell(1).toString();
                Date date = (Date) row.getCell(2);
                double temperature = Double.parseDouble(row.getCell(3).toString());
                int insert = waterRepository.insertMultipleRecords(id, town, date, temperature);
                if(insert != 0){
                    count++;
                }
            }
        }catch (IOException ie){
            ie.printStackTrace();
        }
        return count;
    }
}
