package com.ironship.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ironship.reggie.common.R;
import com.ironship.reggie.entity.User;

import com.ironship.reggie.service.UserService;
import com.ironship.reggie.utils.ValidateCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Map;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/sendMsg")
    public R<String> sendMsg(@RequestBody User user, HttpSession session) {
        String phone = user.getPhone();
        if (StringUtils.hasLength(phone)) {
            String code = ValidateCodeUtils.generateValidateCode(4).toString();
            log.info("code={}", code);

            session.setAttribute(phone, code);
            return R.success("msgSendSucceed");
        }
        return R.error("msgSendFailed");
    }

    @PostMapping("/login")
    public R<User> login(@RequestBody Map map, HttpSession session) {
        String code = map.get("code").toString();
        String phone = map.get("phone").toString();
        String code1 = session.getAttribute(phone).toString();
        if (code1 != null && code1.equals(code)) {

            LambdaQueryWrapper<User> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
            userLambdaQueryWrapper.eq(User::getPhone, phone);
            User one = userService.getOne(userLambdaQueryWrapper);
            if(one == null){
                one = new User();
                one.setPhone(phone);
                one.setStatus(1);
                userService.save(one);

            }
            session.setAttribute("user",one.getId());
            return R.success(one);
        }

        return R.error("loginFailed");
    }
}
