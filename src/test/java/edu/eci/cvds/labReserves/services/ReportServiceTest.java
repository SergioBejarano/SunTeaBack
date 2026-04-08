package edu.eci.cvds.labReserves.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ReportServiceTest {

    private ReportService reportService;

    @BeforeEach
    void setUp() {
        reportService = new ReportService();
    }

    @Test
    void generateReportReturnsNonNullMap() {
        Map<String, Object> report = reportService.generateReport();
        assertNotNull(report);
    }

    @Test
    void generateReportContainsStatusField() {
        Map<String, Object> report = reportService.generateReport();
        assertTrue(report.containsKey("status"));
        assertEquals("active", report.get("status"));
    }

    @Test
    void generateReportContainsMessageField() {
        Map<String, Object> report = reportService.generateReport();
        assertTrue(report.containsKey("message"));
        assertNotNull(report.get("message"));
    }
}
