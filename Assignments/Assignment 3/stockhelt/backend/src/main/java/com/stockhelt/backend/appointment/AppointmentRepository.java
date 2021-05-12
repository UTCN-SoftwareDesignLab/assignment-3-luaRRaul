package com.stockhelt.backend.appointment;

import com.stockhelt.backend.appointment.dto.AppointmentDTO;
import com.stockhelt.backend.appointment.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findAllByDoctorId(Long id);

    List<Appointment> findAllByPatientId(Long id);
}
