package com.example.market;

import com.example.market.data.dto.ColorDto;
import com.example.market.data.dto.MemoryDto;
import com.example.market.data.dto.ModelDto;
import com.example.market.data.dto.ProductDto;
import com.example.market.data.models.*;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;

@RunWith(SpringRunner.class)
public class TestModelMapper {

    private ModelMapper modelMapper = new ModelMapper();

    @Test
    public void map() {
        Product product = new Product();
        product.setId(1L);
        product.setCarts(new ArrayList<Cart>());
        product.setColor(new Color());
        product.setCount(10);
        product.setDescription("desc");
        product.setMemory(new Memory());
        product.setModel(new Model());
        product.setName("firstProduct");
        product.setPrice(1000d);

        ProductDto resultProductDTO = new ProductDto(product);

        ProductDto mappedProductDTO = modelMapper.map(product, ProductDto.class);
        Assert.assertEquals(resultProductDTO, mappedProductDTO);
    }
}
