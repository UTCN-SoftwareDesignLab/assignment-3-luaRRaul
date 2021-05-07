package com.stockhelt.backend.appointment;

import com.stockhelt.backend.appointment.dto.AppointmentDTO;
import com.stockhelt.backend.appointment.dto.AppointmentMinimalDTO;
import com.stockhelt.backend.appointment.mapper.AppointmentMapper;
import com.stockhelt.backend.appointment.model.Appointment;
import com.stockhelt.backend.patient.PatientRepository;
import com.stockhelt.backend.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final AppointmentMapper appointmentMapper;
    private final UserRepository userRepository;
    private final PatientRepository patientRepository;

    public AppointmentDTO create (AppointmentDTO appointmentDTO){
        Appointment actAppointment = appointmentMapper.fromDto(appointmentDTO);
        actAppointment.setDoctor(userRepository.findDoctorByUsername(appointmentDTO.getDoctorName()));
        String[] patientNameSplit = appointmentDTO.getPatientName().split("\\s+");
        actAppointment.setPatient(patientRepository.findByFirstNameAndLastName(patientNameSplit[0], patientNameSplit[1]));

        Appointment actRetAppointment = appointmentRepository.save(actAppointment);

        System.out.println(actRetAppointment);

        AppointmentDTO retAppointment = appointmentMapper.toDto(actRetAppointment);
        retAppointment.setDoctorName(actRetAppointment.getDoctor().getUsername());
        retAppointment.setPatientName(actRetAppointment.getPatient().getFirstName()+actRetAppointment.getPatient().getLastName());

        System.out.println(retAppointment);

        return retAppointment;
    }

    public AppointmentDTO get (Long id)
    {
        return appointmentMapper.toDto(appointmentRepository.findById(id).get());
    }

    public void delete(Long id) {
        appointmentRepository.deleteById(id);
    }

    public List<AppointmentMinimalDTO> allMinimalAppointmentList() {
        return appointmentRepository.findAll()
                .stream().map(appointmentMapper::toMinimalDto)
                .collect(toList());
    }

    public AppointmentDTO edit(Long id, AppointmentDTO appointment) {
        Appointment actAppointment = findById(id);
        String[] patientNameSplit = appointment.getPatientName().split("\\s+");

        actAppointment.setPatient(patientRepository.findByFirstNameAndLastName(patientNameSplit[0], patientNameSplit[1]));
        actAppointment.setDoctor(userRepository.findDoctorByUsername(appointment.getDoctorName()));
        actAppointment.setDate(appointment.getDate());
        actAppointment.setDescription(appointment.getDescription());

        return appointmentMapper.toDto(actAppointment);
    }

    private Appointment findById(Long id) {
        return appointmentRepository.findById(id).orElseThrow(()->new EntityNotFoundException("Appointment not found: "+id));
    }
}
