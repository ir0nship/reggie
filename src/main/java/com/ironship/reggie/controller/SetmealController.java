package com.ironship.reggie.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ironship.reggie.common.R;
import com.ironship.reggie.dto.SetmealDto;
import com.ironship.reggie.entity.Category;
import com.ironship.reggie.entity.Setmeal;
import com.ironship.reggie.service.CategoryService;
import com.ironship.reggie.service.SetmealDIshService;
import com.ironship.reggie.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/setmeal")
@Slf4j
public class SetmealController {
    private final SetmealDIshService setmealDIshService;
    private final SetmealService setmealService;
    private final CategoryService categoryService;

    public SetmealController(SetmealDIshService setmealDIshService, SetmealService setmealService, CategoryService categoryService) {
        this.setmealDIshService = setmealDIshService;
        this.setmealService = setmealService;
        this.categoryService = categoryService;
    }

    @PostMapping()
    public R<String> save(@RequestBody SetmealDto setmealDto) {
        setmealService.saveWithDish(setmealDto);
        return R.success("saved");
    }

    @GetMapping("/page")
    public R<Page> page(int page, int pageSize, String name) {
        Page<Setmeal> pageInfo = new Page<>(page, pageSize);
        Page<SetmealDto> setmealDtoPage = new Page<>(page, pageSize);
        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper = new LambdaQueryWrapper<>();
        setmealLambdaQueryWrapper.like(name != null, Setmeal::getName, name);
        setmealLambdaQueryWrapper.orderByDesc(Setmeal::getUpdateTime);
        setmealService.page(pageInfo, setmealLambdaQueryWrapper);

        //套餐分类
        BeanUtils.copyProperties(pageInfo, setmealDtoPage, "records");
        List<Setmeal> pageInfoRecords = pageInfo.getRecords();
        List<SetmealDto> setmealDtoPageRecords = pageInfoRecords.stream().map(
                (item) -> {
                    SetmealDto setmealDto = new SetmealDto();
                    BeanUtils.copyProperties(item, setmealDto);
                    long id = item.getCategoryId();
                    Category byId = categoryService.getById(id);
                    if (byId != null) {
                        setmealDto.setCategoryName(byId.getName());
                    }
                    return setmealDto;
                }
        ).collect(Collectors.toList());

        setmealDtoPage.setRecords(setmealDtoPageRecords);

        return R.success(setmealDtoPage);
    }

    @DeleteMapping
    public R<String> delete(@RequestParam List<Long> ids) {
        setmealService.removeWithDish(ids);
        return R.success("removed");
    }

    @GetMapping("/list")
    public R<List<Setmeal>> list(Setmeal setmeal) {
        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper = new LambdaQueryWrapper<>();
        setmealLambdaQueryWrapper.eq(
                setmeal.getCategoryId() != null, Setmeal::getCategoryId, setmeal.getCategoryId());
        setmealLambdaQueryWrapper.eq(
                setmeal.getStatus() != null, Setmeal::getStatus, setmeal.getStatus());
        setmealLambdaQueryWrapper.orderByDesc(Setmeal::getUpdateTime);

        List<Setmeal> list = setmealService.list(setmealLambdaQueryWrapper);

        return R.success(list);

    }

}
