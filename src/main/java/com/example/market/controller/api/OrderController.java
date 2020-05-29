package com.example.market.controller.api;

import com.example.market.data.dto.BookedProductDto;
import com.example.market.data.dto.CartDto;
import com.example.market.data.model.User;
import com.example.market.service.CartService;
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
    public BookedProductDto addProduct(@RequestParam Long cartId, @RequestParam Long productId, @RequestParam Integer count) throws InterruptedException {
        return cartService.addProduct(cartId, productId, count);
    }

    @GetMapping
    public List<CartDto> getAllCarts(@AuthenticationPrincipal User user) {
        return cartService.getAll(user.getId());
    }
}
