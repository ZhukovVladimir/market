package com.example.market.service;

import com.example.market.data.dto.BookedProductDto;
import com.example.market.data.dto.CartDto;
import com.example.market.data.model.BookedProduct;
import com.example.market.data.model.BookedProductId;
import com.example.market.data.model.Cart;
import com.example.market.data.model.Product;
import com.example.market.data.repository.BookedProductRepository;
import com.example.market.data.repository.CartRepository;
import com.example.market.data.repository.ProductRepository;
import com.example.market.exception.BadRequestException;
import com.example.market.exception.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartService {

    private final CartRepository cartRepository;

    private final ProductRepository productRepository;

    private final BookedProductRepository bookedProductRepository;

    private final ModelMapper modelMapper;

    @Autowired
    public CartService(CartRepository cartRepository, ProductRepository productRepository, BookedProductRepository bookedProductRepository, ModelMapper modelMapper) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.bookedProductRepository = bookedProductRepository;
        this.modelMapper = modelMapper;
    }

    //todo look for controller advice (exception handler)
    public BookedProductDto addProduct(Long cartId, Long productId, Integer count) {
        Product storedProduct = productRepository.findById(productId).orElseThrow(ResourceNotFoundException::new);
        BookedProductId bookedProductId = new BookedProductId().setCartId(cartId).setProductId(productId);
        BookedProduct savedProduct = null;

        //if cart contain the product
        if (bookedProductRepository.existsById(bookedProductId)) {
            BookedProduct bookedProduct = bookedProductRepository.findById(bookedProductId)
                    .orElseThrow(ResourceNotFoundException::new);
            count = count + bookedProduct.getCount();

            if (count <= storedProduct.getCount()) {
                savedProduct = bookedProductRepository.save(bookedProduct.setCount(count));
            } else {
                throw new BadRequestException("Not enough products");
            }
        } else {
            if (count <= storedProduct.getCount()) {
                savedProduct = bookedProductRepository.save(new BookedProduct()
                        .setCartId(cartId)
                        .setProductId(productId)
                        .setCount(count)
                        .setProduct(storedProduct));
            } else {
                throw new BadRequestException("Not enough products");
            }
        }

        return modelMapper.map(savedProduct, BookedProductDto.class);
    }

    public List<CartDto> getAll(Long userId) {
        List<Cart> carts = cartRepository.findAllByUserId(userId);

        return carts.stream()
                .map(cart -> modelMapper.map(cart, CartDto.class))
                .collect(Collectors.toList());
    }
}
