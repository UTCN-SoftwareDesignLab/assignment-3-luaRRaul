package com.stockhelt.backend.appointment;

import com.stockhelt.backend.BaseControllerTest;
import com.stockhelt.backend.TestCreationFactory;
import com.stockhelt.backend.appointment.dto.AppointmentMinimalDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static com.stockhelt.backend.UrlMapping.APPOINTMENTS;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class AppointmentControllerTest extends BaseControllerTest {
    @InjectMocks
    private AppointmentController controller;

    @Mock
    private AppointmentService appointmentService;

    @BeforeEach
    protected void setUp() {
        super.setUp();
        MockitoAnnotations.openMocks(this);
        controller = new AppointmentController(appointmentService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void allUsers() throws Exception {
        List<AppointmentMinimalDTO> appointmentMinimalDTOList = TestCreationFactory.listOf(AppointmentMinimalDTO.class);
        when(appointmentService.allMinimalAppointmentList()).thenReturn(appointmentMinimalDTOList);

        ResultActions result = mockMvc.perform(get(APPOINTMENTS));
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(appointmentMinimalDTOList));
    }
}