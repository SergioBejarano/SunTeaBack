package edu.eci.cvds.labReserves.controller;

import edu.eci.cvds.labReserves.services.ReportService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Controller for system report endpoints.
 */
@RestController
@RequestMapping("/api/report")
public class ReportController {

    /** Report service instance. */
    private final ReportService reportService;

    /**
     * Constructor for ReportController.
     *
     * @param theReportService the report service
     */
    public ReportController(final ReportService theReportService) {
        this.reportService = theReportService;
    }

    /**
     * Returns a system usage report.
     *
     * @return report data
     */
    @GetMapping("/summary")
    public ResponseEntity<Map<String, Object>> getSummary() {
        return ResponseEntity.ok(reportService.generateReport());
    }
}
