package com.ironship.reggie.dto;

import com.ironship.reggie.entity.Setmeal;
import com.ironship.reggie.entity.SetmealDish;
import lombok.Data;

import java.util.List;

@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}
