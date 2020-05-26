package com.example.market.services;

import com.example.market.data.dto.CartDto;
import com.example.market.data.models.Cart;
import com.example.market.data.models.Product;
import com.example.market.data.models.User;
import com.example.market.data.repositories.CartRepository;
import com.example.market.data.repositories.ProductRepository;
import com.example.market.exceptions.BadRequestException;
import com.example.market.exceptions.ResourceNotFoundException;
import io.swagger.models.auth.In;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartService {

    private final CartRepository cartRepository;

    private final ProductRepository productRepository;

    private final ModelMapper modelMapper;

    @Autowired
    public CartService(CartRepository cartRepository, ProductRepository productRepository, ModelMapper modelMapper) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
    }

    //todo look for controller advice (exception handler)
    public CartDto addProduct(Long cartId, Long productId, Integer count) {
        Cart cart = cartRepository.findById(cartId).orElseThrow(ResourceNotFoundException::new);
        Product product = productRepository.findById(productId).orElseThrow(ResourceNotFoundException::new);

        if (cart.getProducts().get(product) != null) {
            count = count + cart.getProducts().get(product);
        }
        if (count <= product.getCount()) {
            cart.getProducts().put(product, count);
        } else {
            throw new BadRequestException("Not enough products in the store");
        }

        cart = cartRepository.save(cart);
        return modelMapper.map(cart, CartDto.class);
    }

    public List<CartDto> getAll(Long userId) {
        List<Cart> carts = cartRepository.findAllByUserId(userId);
        return carts.stream()
                .map(cart -> modelMapper.map(cart, CartDto.class))
                .collect(Collectors.toList());
    }
}
