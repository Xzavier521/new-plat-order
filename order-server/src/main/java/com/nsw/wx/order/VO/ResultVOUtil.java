package com.nsw.wx.order.VO;

import com.nsw.wx.order.VO.ResultVO;

/**
 * Created by 张维维
 * 2018-10-20 18:03
 */
public class ResultVOUtil {

    public static ResultVO success(Object object,Long count) {
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(0);
        resultVO.setMsg("成功");
        resultVO.setData(object);
        return resultVO;
    }
    public static ResultVO success(Object object) {
        ResultVO resultVO = new ResultVO();
        resultVO.setMsg("成功");
        resultVO.setCode(0);
        resultVO.setData(object);
        return resultVO;
    }
    public static ResultVO success() {
        return success(null);
    }
}
