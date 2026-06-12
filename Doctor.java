package com.smartclinic.management.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import java.util.List;

@Entity
@Table(name = "doctors")
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Doctor name is required")
    @Column(nullable = false)
    private String name;

    @NotBlank(message = "Specialty is required")
    @Column(nullable = false)
    private String specialty;

    @Email(message = "Please provide a valid email address")
    @NotBlank(message = "Email is required")
    @Column(unique = true, nullable = false)
    private String email;

    // Mapping a collection of basic elements (Strings) into a separate join table
    @ElementCollection
    @CollectionTable(
        name = "doctor_available_times", 
        joinColumns = @JoinColumn(name = "doctor_id")
    )
    @Column(name = "available_time")
    private List<String> availableTimes;

    // --- Constructors ---
    public Doctor() {
    }

    public Doctor(String name, String specialty, String email, List<String> availableTimes) {
        this.name = name;
        this.specialty = specialty;
        this.email = email;
        this.availableTimes = availableTimes;
    }

    // --- Getters and Setters ---
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<String> getAvailableTimes() {
        return availableTimes;
    }

    public void setAvailableTimes(List<String> availableTimes) {
        this.availableTimes = availableTimes;
    }
}
