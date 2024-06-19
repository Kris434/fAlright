package com.falright.falright.service;

import com.falright.falright.repository.FlightRepository;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class ReportService {

    private final FlightRepository flightRepository;

    public ReportService(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    public List<Object[]> getAircraftWithMostFlights() {
        return flightRepository.findAircraftWithMostFlights();
    }
    public List<Object[]> getFlightWithMostReservations() {
        return flightRepository.findFlightWithMostReservations();
    }
    public List<Object[]> getLongestFlight() {
        return flightRepository.findLongestFlight();
    }
    public List<Object[]> getMostExpensiveFlight() {
        return flightRepository.findMostExpensiveFlight();
    }
    public List<Object[]> getFlightWithMostPassengers() {
        return flightRepository.findFlightWithMostPassengers();
    }


    public ByteArrayInputStream generateExcelReport() throws IOException {
        try (Workbook workbook = new XSSFWorkbook()) {
            ByteArrayOutputStream out = new ByteArrayOutputStream();

            createSheet(workbook, "Aircraft With Most Flights", getAircraftWithMostFlights(), new String[]{"Model", "Number of Flights"});
            createSheet(workbook, "Flight With Most Reservations", getFlightWithMostReservations(), new String[]{"Departure", "Destination", "Number of Reservations"});
            createSheet(workbook, "Longest Flight", getLongestFlight(), new String[]{"Departure", "Destination", "Duration Hours"});
            createSheet(workbook, "Most Expensive Flight", getMostExpensiveFlight(), new String[]{"Departure", "Destination", "Price"});
            createSheet(workbook, "Flight With Most Passengers", getFlightWithMostPassengers(), new String[]{"Departure", "Destination", "Number of Passengers"});

            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        }
    }

    private void createSheet(Workbook workbook, String sheetName, List<Object[]> data, String[] headers) {
        Sheet sheet = workbook.createSheet(sheetName);
        Row headerRow = sheet.createRow(0);

        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
        }

        int rowIdx = 1;
        for (Object[] rowData : data) {
            Row row = sheet.createRow(rowIdx++);
            for (int colIdx = 0; colIdx < rowData.length; colIdx++) {
                Cell cell = row.createCell(colIdx);
                if (rowData[colIdx] != null) {
                    cell.setCellValue(rowData[colIdx].toString());
                } else {
                    cell.setCellValue("");
                }
            }
        }
    }
}
