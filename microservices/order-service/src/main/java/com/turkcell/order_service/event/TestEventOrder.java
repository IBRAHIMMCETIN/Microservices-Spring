package com.turkcell.order_service.event;

import java.util.List;
import java.util.UUID;

public record TestEventOrder(UUID orderId, List<TestEventOrderProduct> items) {}