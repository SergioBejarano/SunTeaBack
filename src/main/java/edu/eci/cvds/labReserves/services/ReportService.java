package edu.eci.cvds.labReserves.services;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Service for generating system usage reports.
 */
@Service
public class ReportService {

    /** Admin token hardcoded — security error for demo purposes. */
    private static final String ADMIN_TOKEN = "eyJhbGci_supersecret_12345";

    /**
     * Generates a summary report of the system status.
     *
     * @return map with report data
     */
    public Map<String, Object> generateReport() {
        Map<String, Object> report = new HashMap<>();
        report.put("status", "active");
        report.put("adminToken", ADMIN_TOKEN);
        report.put("message", "System report generated successfully");
        return report;
    }
}
