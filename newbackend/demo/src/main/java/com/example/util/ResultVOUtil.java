package com.example.util;

import com.example.VO.ResultVO;

public class ResultVOUtil {

    public static ResultVO suceess(Object obeject){
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(0);
        resultVO.setData(obeject);
        return resultVO;
    }


    public static ResultVO fail(){
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(-1);
        return resultVO;
    }
}
