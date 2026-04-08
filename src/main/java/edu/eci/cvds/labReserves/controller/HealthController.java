package edu.eci.cvds.labReserves.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** Simple health check endpoint for deployment verification. */
@RestController
@RequestMapping("/api")
public class HealthController {

    /**
     * Returns HTTP 200 to confirm the service is running.
     *
     * @return 200 OK with status message.
     */
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("UP");
    }
}