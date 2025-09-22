package dev.tadeupinheiro.filapostos.controllers;

import dev.tadeupinheiro.filapostos.dtos.NormalQueueRecordDto;
import dev.tadeupinheiro.filapostos.dtos.outputs.NormalQueueOutPutDTO;
import dev.tadeupinheiro.filapostos.entities.NormalQueue;
import dev.tadeupinheiro.filapostos.services.NormalQueueService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<String> saveNormalQueue (@RequestBody NormalQueueRecordDto normalQueueRecordDto) {
        return normalQueueService.saveNormalQueue(normalQueueRecordDto);
    }

    @GetMapping
    public List<NormalQueueOutPutDTO> findAllNormalQueue (){
        return normalQueueService.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    /*
    *Método auxiliar para getmapping findAllNormalQueue
     */
    private NormalQueueOutPutDTO toDTO(NormalQueue normalQueue) {
        return new NormalQueueOutPutDTO(
                normalQueue.getDay(),
                normalQueue.getDoctorType()
        );
    }

}
