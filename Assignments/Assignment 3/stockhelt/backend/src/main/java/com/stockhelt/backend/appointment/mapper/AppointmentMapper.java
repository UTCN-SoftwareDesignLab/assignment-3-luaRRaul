package com.stockhelt.backend.appointment.mapper;

import com.stockhelt.backend.appointment.dto.AppointmentDTO;
import com.stockhelt.backend.appointment.dto.AppointmentMinimalDTO;
import com.stockhelt.backend.appointment.model.Appointment;
import com.stockhelt.backend.patient.dto.PatientMinimalDTO;
import com.stockhelt.backend.user.dto.DoctorDTO;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface AppointmentMapper {
    AppointmentDTO toDto(Appointment appointment);
    @Mappings({
                    @Mapping(target = "doctor", ignore = true),
                    @Mapping(target = "patient", ignore = true)
    })
    Appointment fromDto(AppointmentDTO appointmentDTO);

    AppointmentMinimalDTO toMinimalDto(Appointment appointment);
    Appointment fromMinimalDto(AppointmentMinimalDTO appointmentMinimalDTO);

    @AfterMapping
    default void populateMinimal(Appointment appointment, @MappingTarget AppointmentMinimalDTO appointmentMinimalDTO){
        appointmentMinimalDTO.setDoctor(DoctorDTO.builder()
                .id(appointment.getDoctor().getId())
                .username(appointment.getDoctor().getUsername())
                .build()
        );
        appointmentMinimalDTO.setPatient(PatientMinimalDTO.builder()
                .id(appointment.getPatient().getId())
                .lastName(appointment.getPatient().getLastName())
                .firstName(appointment.getPatient().getFirstName())
                .build()
        );
    }


    @AfterMapping
    default void populate(Appointment appointment, @MappingTarget AppointmentDTO appointmentDTO){
        appointmentDTO.setDoctor(DoctorDTO.builder()
                .id(appointment.getDoctor().getId())
                .username(appointment.getDoctor().getUsername())
                .build()
        );
        appointmentDTO.setPatient(PatientMinimalDTO.builder()
                .id(appointment.getPatient().getId())
                .lastName(appointment.getPatient().getLastName())
                .firstName(appointment.getPatient().getFirstName())
                .build()
        );
    }
}
