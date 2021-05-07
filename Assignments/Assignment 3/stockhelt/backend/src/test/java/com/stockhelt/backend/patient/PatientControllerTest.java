package com.stockhelt.backend.patient;

import com.stockhelt.backend.BackendApplication;
import com.stockhelt.backend.BaseControllerTest;
import com.stockhelt.backend.TestCreationFactory;
import com.stockhelt.backend.patient.dto.PatientMinimalDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.util.List;

import static com.stockhelt.backend.UrlMapping.PATIENTS;
import static com.stockhelt.backend.UrlMapping.USERS;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class PatientControllerTest extends BaseControllerTest {
    @InjectMocks
    private PatientController controller;

    @Mock
    private PatientService patientService;

    @BeforeEach
    protected void setUp() {
        super.setUp();
        MockitoAnnotations.openMocks(this);
        controller = new PatientController(patientService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void allUsers() throws Exception {
        List<PatientMinimalDTO> patientMinimalDTOList = TestCreationFactory.listOf(PatientMinimalDTO.class);
        when(patientService.allPatientsForList()).thenReturn(patientMinimalDTOList);

        ResultActions result = mockMvc.perform(get(PATIENTS));
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(patientMinimalDTOList));
    }

}