package dev.tadeupinheiro.filapostos.controllers;

import dev.tadeupinheiro.filapostos.dtos.NormalQueueRecordDto;
import dev.tadeupinheiro.filapostos.dtos.outputs.NormalQueueOutPutDTO;
import dev.tadeupinheiro.filapostos.entities.NormalQueue;
import dev.tadeupinheiro.filapostos.services.NormalQueueService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/normalQueue")
public class NormalQueueController {

    @Autowired
    private NormalQueueService normalQueueService;

    @PostMapping
    public ResponseEntity<String> saveNormalQueue (@RequestBody @Valid NormalQueueRecordDto normalQueueRecordDto) {
        return normalQueueService.saveNormalQueue(normalQueueRecordDto);
    }

    @GetMapping
    //public List<NormalQueue> findAllNormalQueue() {
    public List<NormalQueueOutPutDTO> findAllNormalQueue (){
        return normalQueueService.findAll().stream().map(this::toDTO).collect(Collectors.toList());
        //return normalQueueService.findAll();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteNormalQueue (@PathVariable Long id) {
        boolean returnDelete = normalQueueService.deleteNormalQueue(id);
        if(!returnDelete) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Fila não foi deletada pois não foi encontrada");
        }
        return ResponseEntity.status(HttpStatus.OK).body("Fila excluída com sucesso");
    }

    /*
    *Método auxiliar para getmapping findAllNormalQueue
    */
    private NormalQueueOutPutDTO toDTO(NormalQueue normalQueue) {
        return new NormalQueueOutPutDTO(
                normalQueue.getId(),
                normalQueue.getDay(),
                normalQueue.getDoctorType(),
                normalQueue.getQuantityVacancies()
        );
    }

}
