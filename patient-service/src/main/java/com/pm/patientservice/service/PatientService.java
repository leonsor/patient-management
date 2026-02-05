package com.pm.patientservice.service;


import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.pm.patientservice.PatientMapper;
import com.pm.patientservice.dto.PatientRequestDTO;
import com.pm.patientservice.dto.PatientResponseDTO;
import com.pm.patientservice.exception.EmailAlreadyExistsException;
import com.pm.patientservice.exception.PatientNotFoundException;
import com.pm.patientservice.model.Patient;
import com.pm.patientservice.repository.PatientRepository;

@Service
public class PatientService {

	private PatientRepository patientRepository;
	
	public PatientService(PatientRepository patientRepository) {
		this.patientRepository =  patientRepository;
	}
	
	public List<PatientResponseDTO> getPatients() {
		List<Patient> patients = patientRepository.findAll();
				
		return patients.stream().map(PatientMapper::toDTO).toList();
	}
	
	public PatientResponseDTO createPatient(PatientRequestDTO patientRequestDTO) {
        if(patientRepository.existsByEmail(patientRequestDTO.getEmail())) {
            throw new EmailAlreadyExistsException(
                    "A patient with this Email already exists: " + patientRequestDTO.getEmail());
        }

		Patient newPatient = patientRepository.save(
				PatientMapper.toModel(patientRequestDTO));
		return PatientMapper.toDTO(newPatient);
	}
	
	public PatientResponseDTO updatePatient(UUID id,
			PatientRequestDTO patientRequestDTO) {
		
		Patient patient = patientRepository
				.findById(id).
				orElseThrow(() -> new PatientNotFoundException("Patient not found with ID:" + id));
		//Check by me, can implement existsByEmailAndIdNot(String email, UUID id) in PatientRepository
		String oldEmail = patient.getEmail();
		String newEmail = patientRequestDTO.getEmail();
		if(!newEmail.equals(oldEmail)) {
			// System.out.println("from PatientService - updated patient has different email address, start check in database"); // DEBUG
	        if(patientRepository.existsByEmail(newEmail)) {
	            throw new EmailAlreadyExistsException(
	                    "A patient with this Email already exists: " + patientRequestDTO.getEmail());
	        }
		}
		// System.out.println("from PatientService - updated patient has the same email address"); // DEBUG
		
		patient.setName(patientRequestDTO.getName());
		patient.setAddress(patientRequestDTO.getAddress());
		patient.setEmail(patientRequestDTO.getEmail());
		patient.setDateOfBirth(LocalDate.parse(patientRequestDTO.getDateOfBirth()));

		Patient updatedPatient = patientRepository.save(patient);
		return PatientMapper.toDTO(updatedPatient);
	}

	public void deletePatient(UUID id) {
		/** Check if patient exists **/
		patientRepository.findById(id).orElseThrow(() -> new PatientNotFoundException("Patient with id " + id + " not found"));
		patientRepository.deleteById(id); 
	}
}
