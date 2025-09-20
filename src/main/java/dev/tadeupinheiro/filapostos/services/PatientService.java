package dev.tadeupinheiro.filapostos.services;

import dev.tadeupinheiro.filapostos.entities.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import dev.tadeupinheiro.filapostos.repositories.PatientRepository;

import java.util.List;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    @Transactional
    public void save(Patient patient) {
        patientRepository.save(patient);
    }

    @Transactional(readOnly = true)
    public List<Patient> findAll() {
        return patientRepository.findAll();
    }

    @Transactional(readOnly = true)
    public boolean existsBySusNumber(String susNumber) {
        return patientRepository.existsBySusNumber(susNumber);
    }

}
