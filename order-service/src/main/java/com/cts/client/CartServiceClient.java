package com.cts.client;

import com.cts.dtos.CartDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "cart-service")
public interface CartServiceClient {

    @GetMapping("/api/cart")
    CartDto getCart(@RequestHeader("X-User-Name") String username);

    @DeleteMapping("/api/cart/clear")
    void clearCart(@RequestHeader("X-User-Name") String username);
}