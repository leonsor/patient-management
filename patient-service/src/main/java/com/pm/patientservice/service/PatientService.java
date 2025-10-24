package com.pm.patientservice.service;


import java.util.List;

import com.pm.patientservice.exception.EmailAlreadyExistsException;
import org.springframework.stereotype.Service;

import com.pm.patientservice.PatientMapper;
import com.pm.patientservice.dto.PatientRequestDTO;
import com.pm.patientservice.dto.PatientResponseDTO;
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
}
