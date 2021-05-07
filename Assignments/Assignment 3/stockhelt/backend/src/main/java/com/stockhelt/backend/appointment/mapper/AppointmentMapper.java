package com.stockhelt.backend.appointment.mapper;

import com.stockhelt.backend.appointment.dto.AppointmentDTO;
import com.stockhelt.backend.appointment.dto.AppointmentMinimalDTO;
import com.stockhelt.backend.appointment.model.Appointment;
import com.stockhelt.backend.user.UserService;
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
    default void populateNamesMinimal(Appointment appointment, @MappingTarget AppointmentMinimalDTO appointmentMinimalDTO){
        appointmentMinimalDTO.setDoctorName(appointment.getDoctor().getUsername());
        appointmentMinimalDTO.setPatientName(appointment.getPatient().getFirstName()+" "+appointment.getPatient().getLastName());
    }


    @AfterMapping
    default void populateName(Appointment appointment, @MappingTarget AppointmentDTO appointmentDTO){
        appointmentDTO.setDoctorName(appointment.getDoctor().getUsername());
        appointmentDTO.setPatientName(appointment.getPatient().getFirstName()+" "+appointment.getPatient().getLastName());
    }
}
