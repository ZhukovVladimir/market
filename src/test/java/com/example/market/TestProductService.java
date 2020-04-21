package com.example.market;

import com.example.market.data.dto.ProductDto;
import com.example.market.data.models.*;
import com.example.market.data.repositories.ProductRepository;
import com.example.market.data.services.ProductService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TestProductService {

    @InjectMocks
    ProductService productService;
    @Mock
    ProductRepository productRepository;
    @Mock
    ModelMapper modelMapper = new ModelMapper();

    private Product createProduct(Long id, String name, Double price, Integer count, String img, String desc,
                                  Color color, Memory memory, Category category, List<Cart> carts, Model model) {

        return new Product().setId(id)
                .setName(name)
                .setPrice(price)
                .setCount(count)
                .setDescription(desc)
                .setColor(color)
                .setMemory(memory)
                .setCarts(carts)
                .setModel(model);
    }

    private ProductDto productToDTO(Product product) {
        return new ProductDto().setId(product.getId())
                .setName(product.getName())
                .setPrice(product.getPrice())
                .setCount(product.getCount())
                .setDescription(product.getDescription())
                .setColorName(product.getColor().getName())
                .setMemoryVolume(product.getMemory().getVolume())
                .setModelName(product.getModel().getName());
    }

    @Test
    public void findAllTest() {
        List<Product> productsFromRepository = new ArrayList<>();

        Product productOne = createProduct(1l, "prod1", 100d, 10, "path to img", "desc",
                new Color(), new Memory(), new Category(), new ArrayList<Cart>(), new Model());
        Product productTwo = createProduct(2l, "prod2", 100d, 10, "path to img2", "desc2",
                new Color(), new Memory(), new Category(), new ArrayList<Cart>(), new Model());
        Product productThree = createProduct(3l, "prod3", 100d, 10, "path to img3", "desc3",
                new Color(), new Memory(), new Category(), new ArrayList<Cart>(), new Model());

        productsFromRepository.add(productOne);
        productsFromRepository.add(productTwo);
        productsFromRepository.add(productThree);

        List<ProductDto> productsDTOFromMapper = new ArrayList<>();

        productsDTOFromMapper.add(productToDTO(productOne));
        productsDTOFromMapper.add(productToDTO(productTwo));
        productsDTOFromMapper.add(productToDTO(productThree));

        when(productRepository.findAll()).thenReturn(productsFromRepository);
        when(modelMapper.map(productOne, ProductDto.class)).thenReturn(productToDTO(productOne));
        when(modelMapper.map(productTwo, ProductDto.class)).thenReturn(productToDTO(productTwo));
        when(modelMapper.map(productThree, ProductDto.class)).thenReturn(productToDTO(productThree));

        List<ProductDto> productsDTOFromService = productService.findAll();

        Assert.assertEquals(productsDTOFromMapper, productsDTOFromService);
    }
}
