package com.turkcell.order_service.web.controller;

import java.util.UUID;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.web.bind.annotation.*;
import com.turkcell.order_service.dto.OrderCreateDto;
import com.turkcell.order_service.event.TestEventOrder;

@RequestMapping("/api/orders")
@RestController
public class OrderController {

    private final StreamBridge streamBridge;

    public OrderController(StreamBridge streamBridge) {
        this.streamBridge = streamBridge;
    }

    @PostMapping
    public String createOrder(@RequestBody OrderCreateDto dto) {
        UUID orderId = UUID.randomUUID();
        
        var event = new TestEventOrder(orderId, dto.items());
        
        streamBridge.send("orderCreatedEvent-out-0", event);
        
        return "Sipariş başarıyla alındı ve event fırlatıldı. Sipariş ID: " + orderId;
    }
}