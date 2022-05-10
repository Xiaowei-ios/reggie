package com.itheima.converter;



import com.itheima.domain.Dish;
import com.itheima.dto.DishDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface DishConverter {

    DishConverter INSTANCE = Mappers.getMapper(DishConverter.class);

    List<DishDto> toDtos(List<Dish> dishs);
}
