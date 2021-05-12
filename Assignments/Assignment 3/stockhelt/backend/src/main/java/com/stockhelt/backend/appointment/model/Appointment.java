package com.stockhelt.backend.appointment.model;

import com.stockhelt.backend.user.model.Doctor;
import com.stockhelt.backend.patient.model.Patient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Table()
public class Appointment implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER,optional = false)
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;

    @ManyToOne(fetch = FetchType.EAGER,optional = false)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @Column
    private Date date;

    @Column
    private int duration;

    @Column(nullable = false, length = 512)
    private String description;
}
