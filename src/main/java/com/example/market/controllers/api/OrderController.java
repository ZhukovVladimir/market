package com.example.market.controllers.api;

import com.example.market.data.dto.CartDto;
import com.example.market.data.models.User;
import com.example.market.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/orders")
public class OrderController {

    private final CartService cartService;

    @Autowired
    public OrderController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/add")
    public CartDto addProduct(@RequestParam Long cartId, @RequestParam Long productId, @RequestParam Integer count) {
        return cartService.addProduct(cartId, productId, count);
    }

    @GetMapping
    public List<CartDto> getAllCarts(@AuthenticationPrincipal User user) {
        return cartService.getAll(user.getId());
    }
}
