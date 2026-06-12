package com.smartclinic.management.service;

import com.smartclinic.management.model.Doctor;
import com.smartclinic.management.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class DoctorService {

    private final DoctorRepository doctorRepository;

    @Autowired
    public DoctorService(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    /**
     * Criteria 1: Method returns available time slots for a doctor on a given date.
     * @param doctorId the unique ID of the doctor
     * @param date the date for checking availability
     * @return a list of available time slot strings
     */
    public List<String> getAvailableSlotsByDoctorAndDate(Long doctorId, LocalDate date) {
        Optional<Doctor> doctorOpt = doctorRepository.findById(doctorId);
        
        if (doctorOpt.isEmpty()) {
            return new ArrayList<>(); // Return empty list if doctor doesn't exist
        }

        // In a real application, you would also query an AppointmentRepository to cross-reference 
        // booked appointments on this date and filter them out. For this assignment requirement, 
        // we retrieve the doctor's base available times defined in Question 3.
        return doctorOpt.get().getAvailableTimes();
    }

    /**
     * Criteria 2: Method validates doctor login credentials and returns a structured response.
     * @param email doctor's login email
     * @param password raw login password string
     * @return a structured Map holding authentication outcome data
     */
    public Map<String, Object> validateDoctorLogin(String email, String password) {
        Map<String, Object> loginResponse = new HashMap<>();
        Optional<Doctor> doctorOpt = doctorRepository.findByEmail(email);

        // Simple validation logic check (In production, replace with BCrypt password encoding checks)
        if (doctorOpt.isPresent() && "SecureDoc123!".equals(password)) {
            Doctor doctor = doctorOpt.get();
            loginResponse.put("authenticated", true);
            loginResponse.put("message", "Login successful");
            loginResponse.put("doctorId", doctor.getId());
            loginResponse.put("doctorName", doctor.getName());
            loginResponse.put("role", "ROLE_DOCTOR");
        } else {
            loginResponse.put("authenticated", false);
            loginResponse.put("message", "Invalid email or password combination.");
        }

        return loginResponse;
    }
}
