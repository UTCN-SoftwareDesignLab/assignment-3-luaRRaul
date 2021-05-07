package com.stockhelt.backend.patient.mapper;

import com.stockhelt.backend.patient.dto.PatientDTO;
import com.stockhelt.backend.patient.dto.PatientMinimalDTO;
import com.stockhelt.backend.patient.model.Patient;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PatientMapper {
    PatientDTO toDto(Patient patient);

    PatientMinimalDTO toMinimalDto(Patient patient);

    Patient fromDto(PatientDTO patient);

    Patient fromMinimalDto(PatientMinimalDTO patient);
}
