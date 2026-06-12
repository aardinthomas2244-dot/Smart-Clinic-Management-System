package com.smartclinic.management.repository;

import com.smartclinic.management.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

    /**
     * Criteria 1: Method retrieves patient by email using a derived query.
     * @param email the patient's unique email address
     * @return an Optional containing the found Patient, or empty if not found
     */
    Optional<Patient> findByEmail(String email);

    /**
     * Criteria 2: Method retrieves patient using either email or phone number.
     * @param email the patient's email address
     * @param phoneNumber the patient's phone number
     * @return an Optional containing the found Patient, or empty if not found
     */
    Optional<Patient> findByEmailOrPhoneNumber(String email, String phoneNumber);
}
