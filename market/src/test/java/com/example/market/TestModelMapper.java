package com.example.market;

import com.example.market.data.dto.ProductDTO;
import com.example.market.data.models.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;

@RunWith(SpringRunner.class)
public class TestModelMapper {
    //@mockito runner + @injectMocks + @Mock для сервиса, в нем сделать мок маппера
    @Autowired
    private ModelMapper modelMapper;

    @Test
    public void map( ) {
        Product product = new Product();
        product.setId(1);
        product.setCarts(new ArrayList<Cart>());
        product.setCategory(new Category());
        product.setColor(new Color());
        product.setCount(10);
        product.setDescription("desc");
        product.setImage("path to img");
        product.setMemory(new Memory());
        product.setModel(new Model());
        product.setName("firstProduct");
        product.setPrice(1000d);

        ProductDTO resultProductDTO = new ProductDTO();
        resultProductDTO.setId(product.getId());
        resultProductDTO.setCategoryName(product.getCategory().getName());
        resultProductDTO.setColorName(product.getColor().getName());
        resultProductDTO.setCount(product.getCount());
        resultProductDTO.setDescription(product.getDescription());
        resultProductDTO.setImage(product.getImage());
        resultProductDTO.setMemoryName(product.getMemory().getVolume());
        resultProductDTO.setModelName(product.getModel().getName());
        resultProductDTO.setName(product.getName());
        resultProductDTO.setPrice(product.getPrice());

        ProductDTO mappedProductDTO = modelMapper.map(product, ProductDTO.class);
        Assert.assertEquals(resultProductDTO, mappedProductDTO);
    }
}
