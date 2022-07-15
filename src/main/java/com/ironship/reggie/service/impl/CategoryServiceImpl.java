package com.ironship.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ironship.reggie.entity.Category;
import com.ironship.reggie.entity.Dish;
import com.ironship.reggie.entity.Setmeal;
import com.ironship.reggie.mapper.CategoryMapper;
import com.ironship.reggie.service.CategoryService;
import com.ironship.reggie.common.CustomException;

import com.ironship.reggie.service.DishService;
import com.ironship.reggie.service.SetmealService;

import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    public CategoryServiceImpl(DishService dishService, SetmealService setmealService) {
        this.dishService = dishService;
        this.setmealService = setmealService;
    }

    private static DishService dishService;
    private static SetmealService setmealService;


    @Override
    public void remove(Long id) {
        LambdaQueryWrapper<Dish> dishLambdaQueryWrapper = new LambdaQueryWrapper<>();
        dishLambdaQueryWrapper.eq(Dish::getCategoryId, id);
        long count = dishService.count(dishLambdaQueryWrapper);
        if (count > 0) {
            //关联菜品
            throw new CustomException("关联菜品");
        }

        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper = new LambdaQueryWrapper<>();
        setmealLambdaQueryWrapper.eq(Setmeal::getCategoryId, id);
        long count1 = setmealService.count(setmealLambdaQueryWrapper);
        if (count1 > 0) {
            //关联套餐
            throw new CustomException("关联套餐");
        }

        //可以删除
        super.removeById(id);

    }
}
