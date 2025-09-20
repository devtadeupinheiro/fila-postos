package dev.tadeupinheiro.filapostos.controllers;

import dev.tadeupinheiro.filapostos.dtos.NormalQueueRecordDto;
import dev.tadeupinheiro.filapostos.entities.NormalQueue;
import dev.tadeupinheiro.filapostos.services.NormalQueueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public List<NormalQueue> findAllNormalQueue (){
        return normalQueueService.findAll();
    }

}
