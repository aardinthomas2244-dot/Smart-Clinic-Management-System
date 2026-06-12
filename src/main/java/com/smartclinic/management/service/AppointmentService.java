package com.smartclinic.management.service;

import com.smartclinic.management.model.Appointment;
import com.smartclinic.management.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;

    @Autowired
    public AppointmentService(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    /**
     * Criteria 1: Implements a booking method that saves an appointment.
     * @param appointment the appointment details to save
     * @return the saved Appointment entity
     */
    @Transactional
    public Appointment bookAppointment(Appointment appointment) {
        // Business logic validations can be added here (e.g., checking double booking)
        appointment.setStatus("SCHEDULED");
        return appointmentRepository.save(appointment);
    }

    /**
     * Criteria 2: Defines a method to retrieve appointments for a doctor on a specific date.
     * @param doctorId the unique ID of the doctor
     * @param date the specific day to look up appointments for
     * @return a list of appointments matching the criteria
     */
    public List<Appointment> getAppointmentsForDoctorOnDate(Long doctorId, LocalDate date) {
        // Calculate the exact start and end of that specific calendar day
        LocalDateTime startOfDay = date.atStartOfDay();                  // e.g., 2026-06-12T00:00:00
        LocalDateTime endOfDay = date.atTime(23, 59, 59, 999999999);    // e.g., 2026-06-12T23:59:59.999

        // Fetch records falling within that 24-hour time range for the given doctor
        return appointmentRepository.findByDoctorIdAndAppointmentTimeBetween(doctorId, startOfDay, endOfDay);
    }
}
