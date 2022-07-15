package com.ironship.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ironship.reggie.common.CustomException;
import com.ironship.reggie.dto.SetmealDto;
import com.ironship.reggie.entity.Setmeal;
import com.ironship.reggie.entity.SetmealDish;
import com.ironship.reggie.service.SetmealDIshService;
import com.ironship.reggie.service.SetmealService;
import com.ironship.reggie.mapper.SetmealMapper;
import kotlin.jvm.internal.Lambda;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal> implements SetmealService {
    private final SetmealDIshService setmealDIshService;

    public SetmealServiceImpl(SetmealDIshService setmealDIshService) {
        this.setmealDIshService = setmealDIshService;
    }

    @Override
    @Transactional
    public void saveWithDish(SetmealDto setmealDto) {
        this.save(setmealDto);
        Long id = setmealDto.getId();
        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();
        setmealDishes = setmealDishes.stream().peek(
                (item) -> item.setSetmealId(id)
        ).collect(Collectors.toList());
        setmealDIshService.saveBatch(setmealDishes);
    }

    @Override
    @Transactional
    public void removeWithDish(List<Long> ids) {
        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper = new LambdaQueryWrapper<>();
        setmealLambdaQueryWrapper.in(Setmeal::getId, ids).eq(Setmeal::getStatus, 1);
        //删除了setmeal，
        int count = (int) count(setmealLambdaQueryWrapper);
        if(count > 0){
            throw new CustomException("being sold,cant remove");
        }
        this.removeByIds(ids);

        //删除关联数据 setmealDIsh
        LambdaQueryWrapper<SetmealDish> setmealDishLambdaQueryWrapper = new LambdaQueryWrapper<>();
        setmealDishLambdaQueryWrapper.in(SetmealDish::getSetmealId,ids);

        setmealDIshService.remove(setmealDishLambdaQueryWrapper);
    }
}
