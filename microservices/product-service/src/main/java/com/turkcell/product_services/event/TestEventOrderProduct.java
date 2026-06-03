package com.turkcell.product_services.event;

import java.util.UUID;

public record TestEventOrderProduct(UUID productId, int quantity) {}