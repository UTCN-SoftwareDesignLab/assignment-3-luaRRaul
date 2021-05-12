package com.stockhelt.backend;

import com.stockhelt.backend.appointment.AppointmentService;
import com.stockhelt.backend.appointment.dto.AppointmentDTO;
import com.stockhelt.backend.patient.PatientService;
import com.stockhelt.backend.patient.dto.PatientDTO;
import com.stockhelt.backend.patient.dto.PatientMinimalDTO;
import com.stockhelt.backend.security.AuthService;
import com.stockhelt.backend.security.dto.SignupRequest;
import com.stockhelt.backend.user.UserRepository;
import com.stockhelt.backend.user.UserService;
import com.stockhelt.backend.user.dto.DoctorDTO;
import com.stockhelt.backend.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class Bootstrapper implements ApplicationListener<ApplicationReadyEvent> {

    private final PatientService patientService;

    private final AuthService authService;

    private final AppointmentService appointmentService;

    private final UserService userService;

    @Value("${app.bootstrap}")
    private Boolean bootstrap;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        if(bootstrap) {

            User user = new User();
            System.out.println(user.getClass().getSimpleName());
            authService.register(SignupRequest.builder()
                    .email("root@root.com")
                    .username("root")
                    .password("root123.")
                    .build());
            authService.registerDoctor(SignupRequest.builder()
                    .email("doc@doc.com")
                    .username("doc")
                    .password("doctor123.")
                    .build());
            authService.registerSecretary(SignupRequest.builder()
                    .email("sec@sec.com")
                    .username("sec")
                    .password("secretary123.")
                    .build());

            patientService.create(
                    PatientDTO.builder()
                            .firstName("dummy")
                            .lastName("patient")
                            .email("dummy@email.com")
                            .CNP("u12g3u2180321")
                            .dateOfBirth(new java.util.Date())
                            .address("St. Dummy nr. 4")
                            .build()
            );

            appointmentService.create(
                    AppointmentDTO.builder()
                    .date(new java.util.Date())
                    .duration(60)
                    .description("hello dr")
                    .patient(PatientMinimalDTO.builder().id(1L).build())
                    .doctor(DoctorDTO.builder().id(2L).build())
                    .build()
            );
        }
    }
}
