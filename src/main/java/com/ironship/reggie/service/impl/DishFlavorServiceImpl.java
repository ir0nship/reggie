package com.ironship.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ironship.reggie.dto.DishDto;
import com.ironship.reggie.entity.DishFlavor;
import com.ironship.reggie.mapper.DishFlavorMapper;
import com.ironship.reggie.service.DishFlavorService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DishFlavorServiceImpl extends ServiceImpl<DishFlavorMapper, DishFlavor> implements DishFlavorService {
}
