package com.example.service;

import com.example.VO.ResultVO;
import com.example.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.form.LoginForm;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author admin
 * @since 2024-04-24
 */
public interface UserService extends IService<User> {
    public ResultVO login(LoginForm loginForm);
    public ResultVO resgister(LoginForm loginForm);



}
