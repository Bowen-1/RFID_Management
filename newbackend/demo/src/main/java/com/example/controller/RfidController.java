package com.example.controller;


import com.example.VO.PageVO;
import com.example.VO.ResultVO;
import com.example.entity.Rfid;
import com.example.form.RfidForm;
import com.example.form.SearchForm;
import com.example.service.RfidService;
import com.example.util.ResultVOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author admin
 * @since 2024-04-24
 */
@RestController
@RequestMapping("/rfid")
public class RfidController {



    @PostMapping("/save")
    public ResultVO save(@RequestBody Rfid rfid){
        boolean save = this.rfidService.save(rfid);
        if (!save) {
            return ResultVOUtil.fail();
        }else {
            return ResultVOUtil.suceess(null);
        }
    }

    @Autowired
    private RfidService rfidService;

    @PostMapping("/create")
    public ResultVO create(@RequestBody RfidForm rfidForm){
        Boolean create = this.rfidService.create(rfidForm);
        if (!create){
            return ResultVOUtil.fail();
        }else{
            return ResultVOUtil.suceess(null);
        }
    }

    @GetMapping("/list/{page}/{size}")
    public ResultVO list(@PathVariable("page") Integer page,@PathVariable("size") Integer size){
        PageVO pageVO = this.rfidService.list(page,size);
        return ResultVOUtil.suceess(pageVO);
    }

    @GetMapping("/search")
    public ResultVO search(SearchForm searchForm){
        PageVO pageVO = this.rfidService.search(searchForm);
        return ResultVOUtil.suceess(pageVO);

    }

    @GetMapping("/findById/{id}")
    public ResultVO findById(@PathVariable("id") Integer id){
        Rfid rfid = this.rfidService.getById(id);
        return ResultVOUtil.suceess(rfid);
    }

    @PutMapping("/update")
    public ResultVO update(@RequestBody Rfid rfid){
        boolean update = this.rfidService.updateById(rfid);
        if(!update) return ResultVOUtil.fail();
        return  ResultVOUtil.suceess(null);

    }

    @DeleteMapping("/deleteById/{id}")
    public ResultVO deleteById(@PathVariable("id") Integer id){
        boolean remove = this.rfidService.removeById(id);
        if (!remove) return ResultVOUtil.fail();
        return ResultVOUtil.suceess(null);
    }



}

