package com.turkcell.product_services.web.controller;

import java.time.Instant;
import java.util.UUID;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.turkcell.product_services.entity.OutboxEvent;
import com.turkcell.product_services.entity.OutboxStatus;
import com.turkcell.product_services.entity.TestClass;
import com.turkcell.product_services.event.TestEvent;
import com.turkcell.product_services.repository.OutboxRepository;

@RequestMapping("/api/products")
@RestController
public class ProductsController {
    private final OutboxRepository outboxRepository;
    private final ObjectMapper objectMapper;

    public ProductsController(OutboxRepository outboxRepository, ObjectMapper objectMapper) {
        this.outboxRepository = outboxRepository;
        this.objectMapper = objectMapper;
    }

    @GetMapping("/test")
    public TestClass test2() {
        return new TestClass("Product Service Test Başarılı");
    }
    
    @GetMapping
    public String test(@RequestParam String message) {
        
        UUID id = UUID.randomUUID();
        UUID eventId = UUID.randomUUID();
        var event = new TestEvent(eventId, message, id);
        

        OutboxEvent outboxEvent = new OutboxEvent();
        outboxEvent.setId(eventId);
        outboxEvent.setAggregateType("Product");
        outboxEvent.setAggregateId(id.toString());
        outboxEvent.setEventType("testEvent");
        outboxEvent.setPayload(toJson(event));
        outboxEvent.setStatus(OutboxStatus.PENDING);
        outboxEvent.setCreatedAt(Instant.now());

        outboxRepository.save(outboxEvent);

        return "Başarılı";
    }

    private String toJson(Object o)
    {
        try { return objectMapper.writeValueAsString(o);}
        catch(Exception e) { throw new RuntimeException(e); }
    }
}