package dev.tadeupinheiro.filapostos.services;

import dev.tadeupinheiro.filapostos.entities.DoctorType;
import dev.tadeupinheiro.filapostos.repositories.DoctorTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DoctorTypeService {

    @Autowired
    private DoctorTypeRepository doctorTypeRepository;

    @Transactional
    public void saveDoctorType(DoctorType doctorType) {
        doctorTypeRepository.save(doctorType);
    }

    @Transactional(readOnly = true)
    public List<DoctorType> findAll() {
        return doctorTypeRepository.findAll();
    }

    @Transactional(readOnly = true)
    public boolean existsBySpecialy(String specialy) {
        return doctorTypeRepository.findAll().stream().map(doctorType -> doctorType.getSpecialy().equalsIgnoreCase(specialy)).findAny().isPresent();
    }

}