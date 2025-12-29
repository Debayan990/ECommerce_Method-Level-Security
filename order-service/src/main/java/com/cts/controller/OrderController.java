package com.cts.controller;

import com.cts.dtos.OrderResponseDto;
import com.cts.dtos.OrderRequest;
import com.cts.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/place")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'SYSTEM')")
    public ResponseEntity<OrderResponseDto> placeOrder(
            @RequestHeader("X-User-Name") String username,
            @Valid @RequestBody OrderRequest orderRequest) {
        return new ResponseEntity<>(orderService.placeOrder(username, orderRequest), HttpStatus.CREATED);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'SYSTEM')")
    public ResponseEntity<List<OrderResponseDto>> getMyOrders(@RequestHeader("X-User-Name") String username) {
        return ResponseEntity.ok(orderService.getMyOrders(username));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'SYSTEM')")
    public ResponseEntity<OrderResponseDto> getOrderById(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.getOrderById(id));
    }
}