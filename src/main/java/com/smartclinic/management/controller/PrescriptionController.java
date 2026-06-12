package com.smartclinic.management.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class PrescriptionDTO {

    @NotNull(message = "Patient ID is required")
    private Long patientId;

    @NotNull(message = "Doctor ID is required")
    private Long doctorId;

    @NotBlank(message = "Medication name and dosage details are required")
    private String medicationDetails;

    // --- Getters and Setters ---
    public Long getPatientId() { return patientId; }
    public void setPatientId(Long patientId) { this.patientId = patientId; }

    public Long getDoctorId() { return doctorId; }
    public void setDoctorId(Long doctorId) { this.doctorId = doctorId; }

    public String getMedicationDetails() { return medicationDetails; }
    public void setMedicationDetails(String medicationDetails) { this.medicationDetails = medicationDetails; }
}
package com.smartclinic.management.controller;

import com.smartclinic.management.dto.PrescriptionDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/prescriptions")
public class PrescriptionController {

    /**
     * POST endpoint to create and save a new prescription.
     * Validates both the security token and the incoming request body data.
     */
    @PostMapping
    public ResponseEntity<?> createPrescription(
            @RequestHeader(value = "Authorization", required = false) String token,
            @Valid @RequestBody PrescriptionDTO prescriptionDTO,
            BindingResult bindingResult) {

        // 1. Validate the authorization token
        if (token == null || !token.startsWith("Bearer ")) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Unauthorized");
            errorResponse.put("message", "Missing or invalid Authorization token.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
        }

        // 2. Validate the request body payloads using BindingResult
        if (bindingResult.hasErrors()) {
            Map<String, String> validationErrors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error -> 
                validationErrors.put(error.getField(), error.getDefaultMessage())
            );
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(validationErrors);
        }

        try {
            // 3. Simulate saving business logic 
            // (In a full app, you would map this to an Entity and call prescriptionRepository.save())
            
            // 4. Return structured success message using ResponseEntity
            Map<String, Object> successResponse = new HashMap<>();
            successResponse.put("status", "Success");
            successResponse.put("message", "Prescription created and saved successfully.");
            successResponse.put("patientId", prescriptionDTO.getPatientId());
            
            return ResponseEntity.status(HttpStatus.CREATED).body(successResponse);

        } catch (Exception e) {
            Map<String, String> serverErrorResponse = new HashMap<>();
            serverErrorResponse.put("error", "Internal Server Error");
            serverErrorResponse.put("message", "Could not save prescription: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(serverErrorResponse);
        }
    }
}
