package com.example.controller;


import com.baomidou.mybatisplus.extension.api.R;
import com.example.VO.ResultVO;
import com.example.entity.Rfid;
import com.example.form.LoginForm;
import com.example.form.SearchForm;
import com.example.service.RfidService;
import com.example.service.UserService;
import com.example.util.ResultVOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author admin
 * @since 2024-04-24
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;





    @GetMapping("/login")
    public ResultVO login(LoginForm loginForm){
        ResultVO resultVO = this.userService.login(loginForm);
        return resultVO;
    }


    @PostMapping("/register")
    public ResultVO register(@RequestBody LoginForm loginForm){
        ResultVO resultVO = this.userService.resgister(loginForm);
        return resultVO;
    }





}

