package com.pm.patientservice.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pm.patientservice.model.Patient;

@Repository
public interface PatientRepository extends JpaRepository<Patient, UUID>{

	/** Checks for existing email - must be unique */
    boolean existsByEmail(String email);
    /** Checks for existing email linked with id, if not the same, returns true*/
    boolean existsByEmailAndIdNot(String email, UUID id);

}
