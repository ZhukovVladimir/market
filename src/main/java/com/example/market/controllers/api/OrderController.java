package com.example.market.controllers.api;

import com.example.market.data.dto.CartDto;
import com.example.market.data.dto.ProductDto;
import com.example.market.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/order")
public class OrderController {

    private final CartService cartService;

    @Autowired
    public OrderController(CartService cartService) {
        this.cartService = cartService;
    }

    @PutMapping("/add/{id}")
    public CartDto sddProduct(@PathVariable Long id, @RequestParam Long idProduct) {
        CartDto cartDto = cartService.addProduct(id, idProduct);
        return cartDto;
    }
}
