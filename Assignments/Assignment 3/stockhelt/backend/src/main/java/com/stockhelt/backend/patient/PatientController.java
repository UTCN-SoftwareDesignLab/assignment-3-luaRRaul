package com.stockhelt.backend.patient;

import com.stockhelt.backend.patient.dto.PatientDTO;
import com.stockhelt.backend.patient.dto.PatientMinimalDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.stockhelt.backend.UrlMapping.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(PATIENTS)
@RequiredArgsConstructor
public class PatientController {
    private final PatientService patientService;

    @GetMapping
    public List<PatientMinimalDTO> allPatients(){
        return patientService.allPatientsForList();
    }


    @GetMapping(ENTITY)
    public PatientDTO getPatient(@PathVariable Long id) {
        return patientService.get(id);
    }

    @PostMapping
    public PatientDTO create(@RequestBody PatientDTO patient){
        return patientService.create(patient);
    }

    @PutMapping(ENTITY)
    public PatientDTO edit(@PathVariable Long id, @RequestBody PatientDTO patient){
        return patientService.edit(id, patient);
    }

    @DeleteMapping(ENTITY)
    public void delete(@PathVariable Long id){
        patientService.delete(id);
    }
}
