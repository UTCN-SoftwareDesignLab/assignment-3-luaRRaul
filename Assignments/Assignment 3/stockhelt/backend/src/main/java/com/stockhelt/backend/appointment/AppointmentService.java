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
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class AppointmentService {
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private final AppointmentRepository appointmentRepository;
    private final AppointmentMapper appointmentMapper;
    private final UserRepository userRepository;
    private final PatientRepository patientRepository;

    public AppointmentDTO create (AppointmentDTO appointmentDTO){
        if(checkDoctorAvailability(appointmentDTO)){
            Appointment actAppointment = appointmentMapper.fromDto(appointmentDTO);
            actAppointment.setDoctor(userRepository.findDoctorById(appointmentDTO.getDoctor().getId()));
            actAppointment.setPatient(patientRepository.findById(appointmentDTO.getPatient().getId()).get());

            Appointment actRetAppointment = appointmentRepository.save(actAppointment);

            System.out.println(actRetAppointment);

            AppointmentDTO retAppointment = appointmentMapper.toDto(actRetAppointment);

            System.out.println(retAppointment);

            return retAppointment;

        }else{
            //some message
            System.out.println("Dates not valid");
        }

        return null;
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
        if(checkDoctorAvailability(appointment)) {
            Appointment actAppointment = findById(id);

            actAppointment.setPatient(patientRepository.findById(appointment.getPatient().getId()).get());
            actAppointment.setDoctor(userRepository.findDoctorById(appointment.getDoctor().getId()));
            actAppointment.setDate(appointment.getDate());
            actAppointment.setDescription(appointment.getDescription());
            actAppointment.setDuration(appointment.getDuration());

            return appointmentMapper.toDto(appointmentRepository.save(actAppointment));
        }else{
            //not valid duration
            System.out.println("Doctor is not available in the new time interval.");
        }

        return null;
    }

    private Appointment findById(Long id) {
        return appointmentRepository.findById(id).orElseThrow(()->new EntityNotFoundException("Appointment not found: "+id));
    }

    private boolean checkDoctorAvailability(AppointmentDTO appointmentDTO){
        for(Appointment a : appointmentRepository.findAllByDoctorId(appointmentDTO.getDoctor().getId())){
            if(!a.getId().equals(appointmentDTO.getId())) {
                Calendar aTime = Calendar.getInstance();
                aTime.setTime(a.getDate());
                Calendar dtoTime = Calendar.getInstance();
                dtoTime.setTime(a.getDate());
                if ((appointmentDTO.getDate().compareTo(a.getDate()) == 0) && (aTime.getTimeInMillis() >= dtoTime.getTimeInMillis()) && (aTime.getTimeInMillis() <= dtoTime.getTimeInMillis() + appointmentDTO.getDuration() * 60000L)) {
                    return false;
                } else {
                    if ((appointmentDTO.getDate().compareTo(a.getDate()) == 0) && (aTime.getTimeInMillis() + a.getDuration() * 60000L >= dtoTime.getTimeInMillis())) {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    public List<AppointmentDTO> getAppointmentsForPatient(Long id) {
        return appointmentRepository.findAllByPatientId(id).stream().map(appointmentMapper::toDto).filter(a -> a.getDate().before(new java.util.Date())).collect(Collectors.toList());
    }
}
