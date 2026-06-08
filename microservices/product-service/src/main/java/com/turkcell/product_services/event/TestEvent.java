package com.turkcell.product_services.event;
import java.util.UUID;


public record TestEvent(UUID eventId, String message, UUID productId) {}