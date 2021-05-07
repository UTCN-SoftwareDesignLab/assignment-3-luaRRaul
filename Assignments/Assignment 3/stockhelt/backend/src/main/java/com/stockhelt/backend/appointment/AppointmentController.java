package com.stockhelt.backend.appointment;

import com.stockhelt.backend.appointment.dto.AppointmentDTO;
import com.stockhelt.backend.appointment.dto.AppointmentMinimalDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.stockhelt.backend.UrlMapping.APPOINTMENTS;
import static com.stockhelt.backend.UrlMapping.ENTITY;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(APPOINTMENTS)
@RequiredArgsConstructor
public class AppointmentController {
    private final AppointmentService appointmentService;

    @GetMapping
    public List<AppointmentMinimalDTO> allAppointments(){
        return appointmentService.allMinimalAppointmentList();
    }

    @GetMapping(ENTITY)
    public AppointmentDTO getAppointment(@PathVariable Long id)
    {
        return appointmentService.get(id);
    }

    @PutMapping(ENTITY)
    public AppointmentDTO edit(@PathVariable Long id, @RequestBody AppointmentDTO appointment){
        return appointmentService.edit(id, appointment);
    }

    @DeleteMapping(ENTITY)
    public void delete(@PathVariable Long id){
        appointmentService.delete(id);
    }
 }
