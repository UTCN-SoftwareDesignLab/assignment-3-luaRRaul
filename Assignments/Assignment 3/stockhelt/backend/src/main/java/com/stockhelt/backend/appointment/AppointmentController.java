package com.stockhelt.backend.appointment;

import com.stockhelt.backend.appointment.dto.AppointmentDTO;
import com.stockhelt.backend.appointment.dto.AppointmentMinimalDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

import static com.stockhelt.backend.UrlMapping.*;

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

    @GetMapping("/patient"+ENTITY)
    public List<AppointmentDTO> getAppointmentsForPatient(@PathVariable Long id){
        return appointmentService.getAppointmentsForPatient(id);
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
