package ru.reksoft.market;

import ru.reksoft.market.data.dto.ColorDto;
import ru.reksoft.market.data.dto.MemoryDto;
import ru.reksoft.market.data.dto.ProductDto;
import ru.reksoft.market.data.model.Color;
import ru.reksoft.market.data.model.Memory;
import ru.reksoft.market.data.model.Product;
import ru.reksoft.market.data.repository.ProductRepository;
import ru.reksoft.market.service.ProductService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static ru.reksoft.market.data.repository.specification.ProductSpecification.defaultSpecification;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TestProductService {

    @InjectMocks
    ProductService productService;
    @Mock
    ProductRepository productRepository;

    @Mock
    ModelMapper modelMapper;

    Pageable pageable = PageRequest.of(0, 2);

    private Product createProduct(Long id, String name, BigDecimal price, Integer count, String desc,
                                  Color color, Memory memory) {

        Product product = new Product();
        product.setId(id);
        product.setColor(color);
        product.setCount(count);
        product.setDescription(desc);
        product.setMemory(memory);
        product.setName(name);
        product.setPrice(price);

        return product;
    }

    private ProductDto productToDTO(Product product) {
        ProductDto productDto = new ProductDto()
                .setColor(colorToColorDto(product.getColor()))
                .setId(product.getId())
                .setName(product.getName())
                .setPrice(product.getPrice())
                .setCount(product.getCount())
                .setDescription(product.getDescription())
                .setMemory(memoryToMemoryDto(product.getMemory()));
        return productDto;
    }

    private ColorDto colorToColorDto(Color color) {
        ColorDto colorDto = new ColorDto()
                .setId(color.getId())
                .setName(color.getName());
        return colorDto;
    }

    private MemoryDto memoryToMemoryDto(Memory memory) {
        MemoryDto memoryDto = new MemoryDto()
                .setId(memory.getId())
                .setVolume(memory.getVolume());
        return memoryDto;
    }

    @Test
    public void findAllTest() {
        List<Product> productsFromRepository = new ArrayList<>();

        Product productOne = createProduct(1l, "prod1", new BigDecimal(100), 10, "desc",
                new Color().setId(1L), new Memory().setId(1L));

        Product productTwo = createProduct(2l, "prod2", new BigDecimal(100), 10, "desc2",
                new Color().setId(1L), new Memory().setId(1L));

        Product productThree = createProduct(3l, "prod3", new BigDecimal(100), 10, "desc3",
                new Color().setId(1L), new Memory().setId(1L));

        productsFromRepository.add(productOne);
        productsFromRepository.add(productTwo);
        productsFromRepository.add(productThree);

        Page<Product> page = new PageImpl<>(productsFromRepository);

        List<ProductDto> productsDTOFromMapper = new ArrayList<>();

        productsDTOFromMapper.add(productToDTO(productOne));
        productsDTOFromMapper.add(productToDTO(productTwo));
        productsDTOFromMapper.add(productToDTO(productThree));

        when(productRepository.findAll(defaultSpecification(), pageable)).thenReturn(page);
        when(modelMapper.map(productOne, ProductDto.class)).thenReturn(productToDTO(productOne));
        when(modelMapper.map(productTwo, ProductDto.class)).thenReturn(productToDTO(productTwo));
        when(modelMapper.map(productThree, ProductDto.class)).thenReturn(productToDTO(productThree));

        List<ProductDto> productsDTOFromService = productService.search(pageable).toList();

        Assert.assertEquals(productsDTOFromMapper, productsDTOFromService);
    }
}
