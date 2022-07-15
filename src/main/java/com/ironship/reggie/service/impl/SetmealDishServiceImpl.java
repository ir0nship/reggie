package com.ironship.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ironship.reggie.entity.SetmealDish;
import com.ironship.reggie.mapper.SetmealDishMapper;
import com.ironship.reggie.service.SetmealDIshService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class SetmealDishServiceImpl extends ServiceImpl<SetmealDishMapper, SetmealDish> implements SetmealDIshService {
}
