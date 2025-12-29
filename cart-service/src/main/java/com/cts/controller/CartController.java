package com.cts.controller;

import com.cts.dtos.CartDto;
import com.cts.dtos.CartItemInput;
import com.cts.dtos.SuccessDto;
import com.cts.service.CartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping("/add")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'SYSTEM')")
    public ResponseEntity<CartDto> addToCart(@RequestHeader("X-User-Name") String username, @Valid @RequestBody CartItemInput cartItemInput) {
        return ResponseEntity.ok(cartService.addItemToCart(username, cartItemInput));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'SYSTEM')")
    public ResponseEntity<CartDto> getCart(@RequestHeader("X-User-Name") String username) {
        return ResponseEntity.ok(cartService.getCartByUsername(username));
    }

    @DeleteMapping("/remove/{itemId}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'SYSTEM')")
    public ResponseEntity<CartDto> removeItem(@RequestHeader("X-User-Name") String username, @PathVariable Long itemId) {
        return ResponseEntity.ok(cartService.removeItemFromCart(username, itemId));
    }

    @DeleteMapping("/clear")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'SYSTEM')")
    public ResponseEntity<SuccessDto> clearCart(@RequestHeader("X-User-Name") String username) {
        SuccessDto result = new SuccessDto(cartService.clearCart(username));
        return ResponseEntity.ok(result);
    }
}