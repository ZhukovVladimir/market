package ru.reksoft.market.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.reksoft.market.data.dto.BookedProductDto;
import ru.reksoft.market.data.dto.CartDto;
import ru.reksoft.market.data.model.User;
import ru.reksoft.market.service.CartService;

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
    public BookedProductDto addProduct(@RequestParam Long cartId, @RequestParam Long productId, @RequestParam Integer count) {
        return cartService.addProduct(cartId, productId, count);
    }

    @PutMapping("/reduce")
    public BookedProductDto reduceProduct(@RequestParam Long cartId, @RequestParam Long productId, @RequestParam Integer count) {
        return cartService.reduceProduct(cartId, productId, count);
    }

    @GetMapping
    public List<CartDto> getAllCarts(@AuthenticationPrincipal User user) {
        return cartService.getAll(user.getId());
    }

    @DeleteMapping("/delete")
    public void deleteProduct(@RequestParam Long cartId, @RequestParam Long productId) {
        cartService.deleteProduct(cartId, productId);
    }

    @PutMapping("/confirm")
    public CartDto confirmOrder(@AuthenticationPrincipal User user, @RequestBody @Validated CartDto cartDto) {
        return cartService.confirmOrder(user, cartDto);
    }
}
