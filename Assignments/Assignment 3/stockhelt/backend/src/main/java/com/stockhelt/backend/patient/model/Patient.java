package com.stockhelt.backend.patient.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.Date;

@Entity
@Table(
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "email"),
                @UniqueConstraint(columnNames = "CNP")
        })
@RequiredArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Data
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 32)
    private String firstName;

    @Column(nullable = false, length = 16)
    private String lastName;

    @Email
    @Column(nullable = false, length = 64)
    private String email;

    @Column(nullable = false, length = 32)
    private String CNP;

    @Column()
    private Date dateOfBirth;

    @Column(nullable = false, length = 32)
    private String address;
}
