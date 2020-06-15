package ru.reksoft.market;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.test.context.junit4.SpringRunner;
import ru.reksoft.market.data.dto.ColorDto;
import ru.reksoft.market.data.dto.MemoryDto;
import ru.reksoft.market.data.dto.ModelDto;
import ru.reksoft.market.data.dto.ProductDto;
import ru.reksoft.market.data.model.Color;
import ru.reksoft.market.data.model.Memory;
import ru.reksoft.market.data.model.Model;
import ru.reksoft.market.data.model.Product;

import java.math.BigDecimal;

@RunWith(SpringRunner.class)
public class TestModelMapper {

    private final ModelMapper modelMapper = new ModelMapper();

    @Test
    public void map() {
        Product product = new Product();
        product.setId(1L);
        product.setColor(new Color().setId(1L));
        product.setCount(10);
        product.setDescription("desc");
        product.setMemory(new Memory().setId(1L));
        product.setModel(new Model().setId(1L));
        product.setName("firstProduct");
        product.setPrice(new BigDecimal(1000));

        ProductDto resultProductDto = new ProductDto().setId(product.getId())
                .setColor(colorToColorDto(product.getColor()))
                .setCount(product.getCount())
                .setDescription(product.getDescription())
                .setMemory(memoryToMemoryDto(product.getMemory()))
                .setModel(modelToModelDto(product.getModel()))
                .setName(product.getName())
                .setPrice(product.getPrice());

        ProductDto mappedProductDto = modelMapper.map(product, ProductDto.class);
        Assert.assertEquals(resultProductDto, mappedProductDto);
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

    private ModelDto modelToModelDto(Model model) {
        ModelDto modelDto = new ModelDto()
                .setId(model.getId())
                .setName(model.getName());
        return modelDto;
    }
}
