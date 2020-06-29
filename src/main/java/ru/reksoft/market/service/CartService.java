package ru.reksoft.market.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.reksoft.market.data.dto.BookedProductDto;
import ru.reksoft.market.data.dto.CartDto;
import ru.reksoft.market.data.model.*;
import ru.reksoft.market.data.repository.BookedProductRepository;
import ru.reksoft.market.data.repository.CartRepository;
import ru.reksoft.market.data.repository.ProductRepository;
import ru.reksoft.market.exception.BadRequestException;
import ru.reksoft.market.exception.ResourceNotFoundException;

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

    @PreAuthorize("authentication.principal.carts.contains(@cartRepository.findById(cartId))")
    public BookedProductDto addProduct(Long cartId, Long productId, Integer count) {
        Cart cart = cartRepository.findById(cartId).orElseThrow(ResourceNotFoundException::new);

        if (!(cart.getDeliveryStatus() == DeliveryStatus.PREORDER)) {
            throw new BadRequestException("К сожалению, вы не можете изменять эту корзину");
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
                throw new BadRequestException("К сожалению, в магазине недостаточно продуктов");
            }
        } else {
            if (count <= storedProduct.getCount()) {
                savedProduct = bookedProductRepository.save(new BookedProduct()
                        .setCartId(cartId)
                        .setProductId(productId)
                        .setCount(count)
                        .setProduct(storedProduct));
            } else {
                throw new BadRequestException("К сожалению, в магазине недостаточно продуктов");
            }
        }

        return modelMapper.map(savedProduct, BookedProductDto.class);
    }

    public List<CartDto> getAll(User user, Pageable pageable) {
        if (user == null) throw new ResourceNotFoundException("User not found");
        List<Cart> carts = cartRepository.findAllByUserId(user.getId(), pageable);

        return carts.stream()
                .map(cart -> modelMapper.map(cart, CartDto.class))
                .collect(Collectors.toList());
    }

    @PreAuthorize("authentication.principal.carts.contains(@cartRepository.findById(cartId))")
    public BookedProductDto reduceProduct(Long cartId, Long productId, Integer count) {
        Cart cart = cartRepository.findById(cartId).orElseThrow(ResourceNotFoundException::new);

        if (!(cart.getDeliveryStatus() == DeliveryStatus.PREORDER)) {
            throw new BadRequestException("Вы не можете изменять эту корзину");
        }

        BookedProductId bookedProductId = new BookedProductId().setCartId(cartId).setProductId(productId);
        BookedProduct storedProduct = bookedProductRepository.findById(bookedProductId).orElseThrow(ResourceNotFoundException::new);

        if (count >= storedProduct.getCount()) {
            throw new BadRequestException("Нельзя удалить продуктов больше, чем хранится");
        }
        count = storedProduct.getCount() - count;
        BookedProduct savedProduct = bookedProductRepository.save(storedProduct.setCount(count));

        return modelMapper.map(savedProduct, BookedProductDto.class);
    }

    @PreAuthorize("authentication.principal.carts.contains(@cartRepository.findById(cartId))")
    public void deleteProduct(Long cartId, Long productId) {
        BookedProductId bookedProductId = new BookedProductId().setCartId(cartId).setProductId(productId);
        BookedProduct storedProduct = bookedProductRepository.findById(bookedProductId).orElseThrow(ResourceNotFoundException::new);
        bookedProductRepository.delete(storedProduct);
    }

    @PreAuthorize("user.id == authentication.principal.id")
    @Transactional
    public CartDto confirmOrder(User user, CartDto cartDto) {
        Cart cart = cartRepository.findByIdAndUserId(cartDto.getId(), user.getId()).orElseThrow(ResourceNotFoundException::new);
        setUpBill(cart);

        if (cartDto.getDeliveryAddress().isEmpty()) {
            throw new BadRequestException("Укажите, пожалуйста, адрес доставки");
        }

        if (cart.getBill().compareTo(cartDto.getBill()) != 0) {
            throw new BadRequestException("Ваша сумма неправильная, ожидается: " + cart.getBill());
        }

        cart.setDeliveryAddress(cartDto.getDeliveryAddress());
        cart.setDeliveryStatus(DeliveryStatus.TRANSFER);
        cart = cartRepository.save(cart);
        reduceProductInStore(cart);
        setUpEmptyCart(user);
        return modelMapper.map(cart, CartDto.class);
    }

    private void reduceProductInStore(Cart cart) {
        for (BookedProduct bookedProduct : cart.getProducts()) {
            Product currentProduct = bookedProduct.getProduct();
            int currentCount = currentProduct.getCount();
            int bookedCount = bookedProduct.getCount();
            int remainCount = currentCount - bookedCount;

            if (remainCount == 0) {
                deleteProductFromActiveCarts(bookedProduct.getProductId());
            }

            productRepository.save(bookedProduct.getProduct().setCount(remainCount));
        }
    }

    void setUpEmptyCart(User user) {
        Cart cart = new Cart();
        cart.setUser(user);
        cart.setDeliveryStatus(DeliveryStatus.PREORDER);
        if (!user.getAddress().isEmpty()) {
            cart.setDeliveryAddress(user.getAddress());
        }
        cartRepository.save(cart);
    }

    private void setUpBill(Cart cart) {
        BigDecimal bill = new BigDecimal(0);
        for (BookedProduct elem : cart.getProducts()) {
            bill = bill.add(elem.getProduct().getPrice()
                    .multiply(new BigDecimal(elem.getCount())));
        }
        cart.setBill(bill);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void deleteProductFromActiveCarts(Long idProduct) {

        List<Cart> activeCarts = cartRepository.findAllByDeliveryStatus(DeliveryStatus.PREORDER)
                .orElseThrow(ResourceNotFoundException::new);

        for (Cart activeCart : activeCarts) {
            Long idCart = activeCart.getId();
            BookedProductId storedProductId = new BookedProductId().setCartId(idCart).setProductId(idProduct);

            bookedProductRepository.findById(storedProductId)
                    .ifPresent(bookedProductRepository::delete);
        }
    }
}
