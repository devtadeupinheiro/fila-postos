package dev.tadeupinheiro.filapostos.controllers;

import dev.tadeupinheiro.filapostos.dtos.NormalQueuePatientRecordDTO;
import dev.tadeupinheiro.filapostos.dtos.QueueRecordDTO;
import dev.tadeupinheiro.filapostos.entities.NormalQueue;
import dev.tadeupinheiro.filapostos.entities.NormalQueuePatient;
import dev.tadeupinheiro.filapostos.entities.Patient;
import dev.tadeupinheiro.filapostos.services.DoctorTypeService;
import dev.tadeupinheiro.filapostos.services.NormalQueuePatientService;
import dev.tadeupinheiro.filapostos.services.NormalQueueService;
import dev.tadeupinheiro.filapostos.services.PatientService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/schedule-appointment")
public class QueueController {

    @Autowired
    private NormalQueuePatientService normalQueuePatientService;
    @Autowired
    private PatientService patientService;
    @Autowired
    private DoctorTypeService doctorTypeService;
    @Autowired
    private NormalQueueService normalQueueService;

    @PostMapping
    public ResponseEntity<String> registerVacancy (@RequestBody @Valid QueueRecordDTO queueRecordDTO) {

        var normalQueue = normalQueueService.findNormalQueueById(queueRecordDTO.idQueue());
        var patient = patientService.findBySusNumber(queueRecordDTO.patientSusNumber());

        if (normalQueue == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não foi possível localizar essa fila");
        }
        if (patient == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não foi possível localizar esse paciente");
        }

        //Cria uma lista com todos os pacientes que estão na fila passada como argumento no json
        List<NormalQueuePatient> normalQueuePatientList = normalQueuePatientService.findAllNormalQueuePatient()
                .stream()
                .filter(normalQueuePatient -> normalQueuePatient.getId().getNormalQueue().getId().equals(normalQueue.getId()))
                .toList();

        //Se a lista está vazia, quer dizer que não há pacientes na fila, portanto, cadastro como posição 1.
        if (normalQueuePatientList.size() == 0){
            normalQueuePatientService.saveNormalQueuePatient(new NormalQueuePatientRecordDTO(normalQueue.getId(), patient.getId(), 1));
            return ResponseEntity.status(HttpStatus.CREATED).body("Paciente entrou na fila e sua posição é: " + "1");
        }

        //Se chegou aqui, a lista não está vazia.

        //Aqui o paciente não tem prioridade
        if (patient.getPriorityType().equalsIgnoreCase("NORMAL")){
            int sizeList = normalQueuePatientList.size();
            ++sizeList;
            normalQueuePatientService.saveNormalQueuePatient(new NormalQueuePatientRecordDTO(normalQueue.getId(), patient.getId(), sizeList));
            return ResponseEntity.status(HttpStatus.CREATED).body("Paciente entrou na fila e sua posição é: " + sizeList);
        }

        //Aqui o paciente tem prioridade
        int positionLastPriority = 0;
        for (int i = 1; i <= normalQueuePatientList.size(); i++){
            var normalQueuePatient = normalQueuePatientList.get(i);
            if (!normalQueuePatient.getId().getPatient().getPriorityType().equalsIgnoreCase("NORMAL")){
                positionLastPriority = i;
                /*ATÉ AQUI ACHA OS PRIORITARIOS
                PRECISO ACHAR O ÚLTIMO, VERIFICAR SE TEM NORMAL APÓS ELE E SALVAR DEPOIS.
                SE NÃO TIVER NORMAL APÓS ELE, SALVA NA PRÓXIMA POSIÇÃO
                LEMBRAR DE FAZER UMA VERIFICAÇÃO INICIAL, ANTES DE PERCORRER O CÓDIGO
                SE AINDA TEM VAGA NA FILA
                 */
            }
        }

        return
    }

    /*
    @PostMapping
    public ResponseEntity<String> registerVacancy (@RequestBody QueueRecordDTO queueRecordDTO) {
        if (!patientService.existsBySusNumber(queueRecordDTO.patientSusNumber())){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Paciente não encontrado");
        }
        if (!doctorTypeService.existsBySpecialy(queueRecordDTO.specialy())){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Especialidade não encontrada");
        }

        var patient = new Patient();
        patient = patientService.findBySusNumber(queueRecordDTO.patientSusNumber());

        long quantityPatientBySpecialy = 0;
        List<NormalQueue> normalQueueList = normalQueueService.findAll();
        String sp = queueRecordDTO.specialy();
        Optional<NormalQueue> list = normalQueueList.stream().filter(l -> l.getDoctorType().getSpecialy().equalsIgnoreCase(sp)).findFirst();
        if (list.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Fila de pacientes para a especialidade " + queueRecordDTO.specialy().toUpperCase() + " não encontrada");
        }
        List<NormalQueuePatient> normalQueuePatientList = normalQueuePatientService.findAllNormalQueuePatient();
        boolean existsPatientInQueue =  normalQueuePatientList.stream().anyMatch(p -> p.getId().getPatient().getSusNumber().equalsIgnoreCase(queueRecordDTO.patientSusNumber()));
        if (existsPatientInQueue){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Paciente já está cadastrado nesta fila");
        }
        //PACIENTE PRIORIDADE NORMAL
        if (patient.getPriorityType().equalsIgnoreCase("NORMAL")) {
                quantityPatientBySpecialy = normalQueuePatientList.stream().filter(l -> l.getId().getNormalQueue().getDoctorType().getSpecialy().equalsIgnoreCase(sp)).count();
                System.out.println(quantityPatientBySpecialy);
                quantityPatientBySpecialy += 1;
                var normalQueuePatientRecordDto = new NormalQueuePatientRecordDTO(list.get().getId(), patient.getId(), Integer.valueOf(String.valueOf(quantityPatientBySpecialy)));
                normalQueuePatientService.saveNormalQueuePatient(normalQueuePatientRecordDto);

        } else { //PACIENTE PRIORITÁRIO
            NormalQueuePatient normalQueuePatient = normalQueuePatientList.stream().filter(p -> !p.getId().getPatient().getPriorityType().equalsIgnoreCase("NORMAL"))
                    .collect(Collectors.toCollection(LinkedHashSet::new))
                    .stream()
                    .findFirst()
                    .orElse(null);
            if (normalQueuePatient == null) {
                //Colocar na primeira posição.
            } else {
                //Colocar na posição do último prioritário +2
                //Atentar que o último prioritário pode ser o último da lista. Se o priotitário for o último da lista, acrescentar só +1
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("FALTA CRIAR LÓGICA PARA OS PACIENTES PRIORITÁRIOS");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body("Paciente registrado com sucesso. Sua posição na fila é: " + quantityPatientBySpecialy);
    }

     */

}
