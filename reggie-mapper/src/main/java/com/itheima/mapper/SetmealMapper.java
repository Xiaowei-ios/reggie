package com.itheima.mapper;

import com.itheima.domain.Setmeal;
import com.itheima.dto.SetmealDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface SetmealMapper {

    @Select("select * from setmeal")
    List<Setmeal> pageFindAll(int page, int pageSize, String name);

    List<Setmeal> selectByName(String name);

    void save(Setmeal setmeal);

    int countStatusByIds(Long[] ids, int status);

    void deleteByIds(Long[] ids);


    @Select("select * from setmeal where id = #{id} and is_deleted = 0")
    SetmealDto getById(Long id);

    void update(SetmealDto setmealDto);

    @Update("update setmeal set status = #{status} where id = #{ids}")
    void updateStatus(Integer status, Long ids);

    @Select("select * from setmeal where category_id = #{categoryId} and status = #{status} and is_deleted = 0 ")
    List<Setmeal> getByCidAndStatus(long categoryId, int status);

    @Select("select * from setmeal where category_id = #{categoryId}")
    List<Setmeal> getByCid(long categoryId);
}
