package ru.reksoft.market.service;

import ru.reksoft.market.data.dto.BookedProductDto;
import ru.reksoft.market.data.dto.CartDto;
import ru.reksoft.market.data.model.*;
import ru.reksoft.market.data.repository.BookedProductRepository;
import ru.reksoft.market.data.repository.CartRepository;
import ru.reksoft.market.data.repository.ProductRepository;
import ru.reksoft.market.exception.BadRequestException;
import ru.reksoft.market.exception.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
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
        Cart cart = cartRepository.findById(cartId).orElseThrow(ResourceNotFoundException::new);

        if (!(cart.getDeliveryStatus() == DeliveryStatus.PREORDER)) {
            throw new BadRequestException("You can't edit that cart");
        }

        Product storedProduct = productRepository.findById(productId).orElseThrow(ResourceNotFoundException::new);
        BookedProductId bookedProductId = new BookedProductId().setCartId(cartId).setProductId(productId);
        BookedProduct savedProduct;

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


    public BookedProductDto reduceProduct(Long cartId, Long productId, Integer count) {
        Cart cart = cartRepository.findById(cartId).orElseThrow(ResourceNotFoundException::new);

        if (!(cart.getDeliveryStatus() == DeliveryStatus.PREORDER)) {
            throw new BadRequestException("You can't edit that cart");
        }

        BookedProductId bookedProductId = new BookedProductId().setCartId(cartId).setProductId(productId);
        BookedProduct storedProduct = bookedProductRepository.findById(bookedProductId).orElseThrow(ResourceNotFoundException::new);

        if (count >= storedProduct.getCount()) {
            throw new BadRequestException("You can't delete so much products");
        }
        count = storedProduct.getCount() - count;
        BookedProduct savedProduct = bookedProductRepository.save(storedProduct.setCount(count));

        return modelMapper.map(savedProduct, BookedProductDto.class);
    }

    public void deleteProduct(Long cartId, Long productId) {
        BookedProductId bookedProductId = new BookedProductId().setCartId(cartId).setProductId(productId);
        BookedProduct storedProduct = bookedProductRepository.findById(bookedProductId).orElseThrow(ResourceNotFoundException::new);
        bookedProductRepository.delete(storedProduct);
    }

    @Transactional
    public CartDto confirmOrder(User user, CartDto cartDto) {
        Cart cart = cartRepository.findByIdAndUserId(cartDto.getId(), user.getId()).orElseThrow(ResourceNotFoundException::new);
        setUpBill(cart);

        if (cartDto.getDeliveryAddress().isEmpty()) {
            throw new BadRequestException("Delivery address shouldn't be empty");
        }

        if (!cart.getBill().equals(cartDto.getBill())) {
            throw new BadRequestException("Your bill wrong expected: " + cart.getBill());
        }

        cart.setDeliveryAddress(cartDto.getDeliveryAddress());
        cart.setDeliveryStatus(DeliveryStatus.TRANSFER);
        cart = cartRepository.save(cart);
        return modelMapper.map(cart, CartDto.class);
    }

    private void setUpBill(Cart cart) {
        BigDecimal bill = new BigDecimal(0);
        for (BookedProduct elem : cart.getProducts()) {
            bill = bill.add(elem.getProduct().getPrice()
                            .multiply(new BigDecimal(elem.getCount())));
        }
        cart.setBill(bill);
    }
}
