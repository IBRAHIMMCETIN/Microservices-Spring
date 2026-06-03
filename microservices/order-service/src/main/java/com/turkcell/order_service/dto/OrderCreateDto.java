package com.turkcell.order_service.dto;

import java.util.List;
import com.turkcell.order_service.event.TestEventOrderProduct;

public record OrderCreateDto(List<TestEventOrderProduct> items) {}