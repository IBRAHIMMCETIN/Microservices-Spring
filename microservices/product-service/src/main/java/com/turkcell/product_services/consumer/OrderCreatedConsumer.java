package com.turkcell.product_services.consumer;

import java.util.function.Consumer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.turkcell.product_services.event.TestEventOrder;

@Configuration
public class OrderCreatedConsumer {

    @Bean
    public Consumer<TestEventOrder> consumeOrderCreated() {
        return event -> {
            System.out.println("====== YENİ SİPARİŞ EVENT'İ ALINDI ======");
            System.out.println("Sipariş ID: " + event.orderId());
            
            event.items().forEach(item -> {
                System.out.println("Ürün ID: " + item.productId() + 
                                   " için stoktan " + item.quantity() + " adet düşülüyor...");
            });
            System.out.println("=========================================");
        };
    }
}