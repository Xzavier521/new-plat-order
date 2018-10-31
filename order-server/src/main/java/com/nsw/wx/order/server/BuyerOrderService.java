package com.nsw.wx.order.server;

import com.github.pagehelper.PageInfo;
import com.nsw.wx.order.dto.OrderDTO;
import com.nsw.wx.order.pojo.WeCharOrdeDetail;
import com.nsw.wx.order.pojo.WeCharOrder;

import java.util.List;

/**
 * 买家订单相关
 *
 * @author 张维维
 * date: 2018/10/23/023 15:24
 */

public interface BuyerOrderService {
    public static void main(String[] args) {
        System.out.println("sss");
    }
    /**
     * 创建订单
     * @param orderDTO
     * @return
     */
    OrderDTO create(OrderDTO orderDTO);

    /**
     * 查询订单列表
     * @param buyerOpenid
     * @param page
     * @param limit
     * @return
     */
    PageInfo<WeCharOrder> buyerfindList (String buyerOpenid,Integer page, Integer limit );
    /** 查询订单详情 */
    OrderDTO  findOne(String buyeropenid,String orderId);
    /** 取消订单. */
    OrderDTO cancel(String orderId,String openid);

}
