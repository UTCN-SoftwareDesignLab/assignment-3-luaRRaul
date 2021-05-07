package com.stockhelt.backend.patient;

import com.stockhelt.backend.patient.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient, Long> {
    Patient findByFirstNameAndLastName(String firstName, String lastName);
}
