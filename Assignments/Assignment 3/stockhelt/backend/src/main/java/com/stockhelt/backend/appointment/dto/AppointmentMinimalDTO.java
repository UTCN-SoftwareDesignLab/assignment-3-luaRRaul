package com.stockhelt.backend.appointment.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Date;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentMinimalDTO {
    private Long id;
    private String doctorName;
    private String patientName;
    private Date date;
}
