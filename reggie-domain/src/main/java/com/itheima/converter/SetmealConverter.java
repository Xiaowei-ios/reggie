package com.itheima.converter;



import com.itheima.domain.Setmeal;
import com.itheima.dto.SetmealDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface SetmealConverter {

    SetmealConverter INSTANCE = Mappers.getMapper(SetmealConverter.class);

    //属性名称和类型都是一样，所以不用谢mapping
    Setmeal toPo(SetmealDto setmealDto);

    // List<Setmeal> -> List<SetmealDto>
    List<SetmealDto> toDtos(List<Setmeal> setmeals);
}
