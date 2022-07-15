package com.ironship.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ironship.reggie.mapper.EmployeeMapper;
import com.ironship.reggie.service.EmployeeService;
import com.ironship.reggie.entity.Employee;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {
}
