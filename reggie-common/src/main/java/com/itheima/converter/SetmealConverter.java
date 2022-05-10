package com.itheima.converter;

import com.itheima.domain.Setmeal;
import com.itheima.dto.SetmealDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;


@Mapper
public interface SetmealConverter {
    //创建一个映射器实例
    SetmealConverter INSTANCE = Mappers.getMapper(SetmealConverter.class);

    //Setmeal 转 SetmealDto
    SetmealDto toDTO(Setmeal setmeal);

    //SetmealDto 转 Setmeal
    Setmeal toPO(SetmealDto dto);

    //List<Setmeal> 转 List<SetmealDto>集合
    List<SetmealDto> toDtoList(List<Setmeal> setmeals);
}