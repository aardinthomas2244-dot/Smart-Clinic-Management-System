package com.smartclinic.management.controller;

import com.smartclinic.management.model.Doctor;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/doctors")
public class DoctorController {

    /**
     * GET endpoint to fetch a specific doctor's availability.
     * Requires an Authorization token in the header and uses a dynamic path variable for the ID.
     */
    @GetMapping("/{id}/availability")
    public ResponseEntity<?> getDoctorAvailability(
            @RequestHeader(value = "Authorization", required = false) String token,
            @PathVariable("id") Long id) {

        // 1. Validate the authorization token (Basic security check criteria)
        if (token == null || !token.startsWith("Bearer ")) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("Error: Missing or invalid Authorization token.");
        }

        try {
            // 2. Simulate retrieving a doctor from a database to grab their structured availability list
            // (In a complete application, you would inject and call doctorRepository.findById(id))
            if (id.equals(404L)) { 
                throw new EntityNotFoundException("Doctor with ID " + id + " not found.");
            }

            // Mocking a successful database entity return matching Question 3's structure
            List<String> mockAvailableTimes = Arrays.asList("09:00 AM", "11:30 AM", "03:00 PM");
            Doctor mockDoctor = new Doctor("Dr. Jane Doe", "Cardiology", "jane.doe@smartclinic.com", mockAvailableTimes);
            mockDoctor.setId(id);

            // 3. Return a structured response using ResponseEntity
            return ResponseEntity.ok(mockDoctor.getAvailableTimes());

        } catch (EntityNotFoundException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An unexpected error occurred: " + e.getMessage());
        }
    }
}
