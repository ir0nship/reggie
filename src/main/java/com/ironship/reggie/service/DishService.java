package com.ironship.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ironship.reggie.dto.DishDto;
import com.ironship.reggie.entity.Dish;

public interface DishService extends IService<Dish> {
    public void saveWithFlavor(DishDto dishDto);

    public DishDto getByIdWithFlavor(long id);

    public void updateWithFlavor(DishDto dishDto);
}
