package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.example.VO.ResultVO;
import com.example.entity.Rfid;
import com.example.entity.User;
import com.example.form.LoginForm;
import com.example.mapper.UserMapper;
import com.example.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author admin
 * @since 2024-04-24
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {


    @Autowired
    private UserMapper userMapper;
    @Override
    public ResultVO login(LoginForm loginForm) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name",loginForm.getName());
        User user = this.userMapper.selectOne(queryWrapper);
//        System.out.println("*********************************************" + user.getName()+user.getPassword());
        ResultVO resultVO = new ResultVO();
        if (user ==null){
//            System.out.println("*********************************************" + user.getName()+user.getPassword());
            resultVO.setCode(-1);
        }else{
            if(!user.getPassword().equals(loginForm.getPassword())){
                resultVO.setCode(-2);
            }else {
                resultVO.setCode(0);
                resultVO.setData(user);
                System.out.println("*********************************************" + user.getName()+user.getPassword());
            }
        }

        return resultVO;
    }

    @Override
    public ResultVO resgister(LoginForm loginForm) {

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name",loginForm.getName());
        User user = this.userMapper.selectOne(queryWrapper);
        ResultVO resultVO = new ResultVO();

        if (user != null){
            resultVO.setCode(-1);
            System.out.println("*********************************************" + user.getName() + user.getPassword());

        } else {
            User newUser = new User();
            newUser.setName(loginForm.getName());
            newUser.setPassword(loginForm.getPassword());
            resultVO.setCode(0);
            System.out.println("*********************************************" + newUser.getName() + newUser.getPassword());

        int insert = this.userMapper.insert(newUser);
        if (insert !=1) resultVO.setCode(-1);;

        }

        return resultVO;
    }
}

