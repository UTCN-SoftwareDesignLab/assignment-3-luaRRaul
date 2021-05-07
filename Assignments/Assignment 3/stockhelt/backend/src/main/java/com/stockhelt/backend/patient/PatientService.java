package com.stockhelt.backend.patient;

import com.stockhelt.backend.patient.dto.PatientDTO;
import com.stockhelt.backend.patient.dto.PatientMinimalDTO;
import com.stockhelt.backend.patient.mapper.PatientMapper;
import com.stockhelt.backend.patient.model.Patient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class PatientService {
    private final PatientRepository patientRepository;
    private final PatientMapper patientMapper;

    public List<PatientMinimalDTO> allPatientsForList() {
        return patientRepository.findAll().
                stream().map(patientMapper::toMinimalDto)
                .collect(toList());
    }

    public PatientDTO create(PatientDTO patient){
        return patientMapper.toDto(patientRepository.save(patientMapper.fromDto(patient)));
    }

    public PatientDTO get(Long id) {
        return patientMapper.toDto(patientRepository.findById(id).get());
    }

    public PatientDTO edit(Long id, PatientDTO patient) {
        Patient actPatient = findById(id);

        actPatient.setFirstName(patient.getFirstName());
        actPatient.setLastName(patient.getLastName());
        actPatient.setEmail(patient.getEmail());
        actPatient.setDateOfBirth(patient.getDateOfBirth());
        actPatient.setAddress(patient.getCNP());
        actPatient.setAddress(patient.getAddress());

        return patientMapper.toDto(actPatient);
    }

    private Patient findById(Long id) {
        return patientRepository.findById(id)
                .orElseThrow(()->new EntityNotFoundException("Patient not found: "+id));
    }

    public void delete(Long id) {
       patientRepository.deleteById(id);
    }
}
