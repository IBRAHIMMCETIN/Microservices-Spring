package com.turkcell.product_services.event;

import java.util.List;
import java.util.UUID;

public record TestEventOrder(UUID orderId, List<TestEventOrderProduct> items) {}