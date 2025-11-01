package dev.tadeupinheiro.filapostos.controllers;

import dev.tadeupinheiro.filapostos.dtos.QueueRecordDto;
import dev.tadeupinheiro.filapostos.dtos.outputs.QueueOutPutDTO;
import dev.tadeupinheiro.filapostos.entities.Queue;
import dev.tadeupinheiro.filapostos.services.QueueService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/normalQueue")
public class QueueController {

    @Autowired
    private QueueService queueService;

    @PostMapping
    public ResponseEntity<String> saveQueue (@RequestBody @Valid QueueRecordDto queueRecordDto) {
        return queueService.saveQueue(queueRecordDto);
    }

    @GetMapping
    public List<QueueOutPutDTO> findAllQueue (){
        return queueService.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    @GetMapping("/withVacancies")
    public List<QueueOutPutDTO> findQueueWithVacancies (){
        return queueService.findQueueWithVacancies().stream().map(this::toDTO).collect(Collectors.toList());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteQueue (@PathVariable Long id) {
        boolean returnDelete = queueService.deleteQueue(id);
        if(!returnDelete) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Fila não foi deletada pois não foi encontrada");
        }
        return ResponseEntity.status(HttpStatus.OK).body("Fila excluída com sucesso");
    }

    /*
    *Método auxiliar para getmapping findAllNormalQueue
    */
    private QueueOutPutDTO toDTO(Queue queue) {
        return new QueueOutPutDTO(
                queue.getId(),
                queue.getDay(),
                queue.getDoctorType(),
                queue.getQuantityVacancies()
        );
    }

}
